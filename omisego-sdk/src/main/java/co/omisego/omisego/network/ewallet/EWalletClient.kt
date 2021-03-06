package co.omisego.omisego.network.ewallet

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/10/2017 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import co.omisego.omisego.constant.Exceptions
import co.omisego.omisego.constant.enums.ErrorCode
import co.omisego.omisego.constant.enums.OMGEnum
import co.omisego.omisego.custom.gson.ErrorCodeDeserializer
import co.omisego.omisego.custom.gson.OMGEnumAdapter
import co.omisego.omisego.custom.retrofit2.adapter.OMGCallAdapterFactory
import co.omisego.omisego.custom.retrofit2.converter.OMGConverterFactory
import co.omisego.omisego.custom.retrofit2.executor.MainThreadExecutor
import co.omisego.omisego.network.InterceptorProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.Executor

/**
 * The class EWalletClient represents an object that knows how to interact with OmiseGO API.
 *
 * Create instances using [EWalletClient.Builder] and pass your implementation of [OMGCallback<T>] interface
 * to generate an implementation
 *
 * For example,
 * <code>
 * val eWalletClient = EWalletClient.Builder {
 *      authenticationToken = YOUR_TOKEN
 *      baseUrl = YOUR_BASE_URL
 * }.build()
 *
 * </code>
 *
 */
class EWalletClient {
    internal lateinit var eWalletAPI: EWalletAPI
    internal lateinit var header: InterceptorProvider.Header
    internal lateinit var retrofit: Retrofit

    /**
     * Build a new [EWalletClient].
     * Set [authenticationToken] and [baseUrl] are required before calling [Builder.build].
     * Set [debug] true for printing a log
     *
     * @receiver A [Builder]'s methods.
     */
    class Builder(init: Builder.() -> Unit) {
        var debug: Boolean = false

        /**
         * Set the API [authenticationToken].
         * The [authenticationToken] should be "Base64(api_key:authentication_token)" (without "OMGClient")
         */
        var authenticationToken: String = ""
            set(value) {
                if (value.isEmpty()) throw Exceptions.emptyAuthenticationToken
                field = value
            }

        /**
         * Set the URL of the OmiseGO Wallet API [baseUrl].
         */
        var baseUrl: String = ""
            set(value) {
                if (value.isEmpty()) throw Exceptions.emptyBaseURL
                field = value
            }

        /**
         * Set the callback executor (default UI thread)
         */
        var callbackExecutor: Executor? = null

        /**
         * For testing purpose
         */
        internal var debugUrl: HttpUrl? = null

        /**
         * Create the [EWalletClient] instance using the configured values.
         * Note: Set [Builder.authenticationToken] and [Builder.baseUrl] are required before calling this.
         */
        fun build(): EWalletClient {
            when {
                authenticationToken.isEmpty() -> throw Exceptions.emptyAuthenticationToken
                baseUrl.isEmpty() && debugUrl == null -> throw Exceptions.emptyBaseURL
            }

            /* Initialize the header by authenticationToken */
            val omgHeader = InterceptorProvider.Header(authenticationToken)

            /* Initialize the EWalletClient and delegate the header from the Builder to EWalletClient */
            val eWalletClient = EWalletClient().apply { header = omgHeader }

            /* Initialize the OKHttpClient with header interceptor*/
            val client: OkHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(omgHeader)

                /* If set debug true, then print the http logging */
                if (debug) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }.build()

            /* Use a simple gson for now */
            val gson = GsonBuilder()
                    .registerTypeAdapter(ErrorCode::class.java, ErrorCodeDeserializer())
                    .registerTypeHierarchyAdapter(OMGEnum::class.java, OMGEnumAdapter<OMGEnum>())
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()

            /* Create retrofit with OMGConverter and OMGCaller */
            eWalletClient.retrofit = Retrofit.Builder().apply {
                addConverterFactory(OMGConverterFactory.create(gson))
                addCallAdapterFactory(OMGCallAdapterFactory.create())
                callbackExecutor(callbackExecutor ?: MainThreadExecutor())
                when {
                    debugUrl != null -> baseUrl(debugUrl!!)
                    else -> baseUrl(this@Builder.baseUrl)
                }
                client(client)
            }.build()

            /* Create EWalletAPI client */
            eWalletClient.eWalletAPI = eWalletClient.retrofit.create(EWalletAPI::class.java)
            return eWalletClient
        }

        init {
            init()
        }
    }
}
