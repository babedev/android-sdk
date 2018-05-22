package co.omisego.omisego.model

/*
 * OmiseGO
 *
 *
 * Created by Phuchit Sirimongkolsathien on 17/12/2017 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import android.support.test.runner.AndroidJUnit4
import co.omisego.omisego.extension.bd
import co.omisego.omisego.utils.validateParcel
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [23])
class MintedTokenTest {
    private val mintedToken1 = MintedToken("OMG:8bcda572-9411-43c8-baae-cd56eb0155f3", "OMG", "OmiseGO", 10000.0.bd)
    private val mintedToken2 = MintedToken("ETH:8bcda572-9411-43c8-baae-cd56eb0155f3", "ETH", "Ethereum", 10000.0.bd)
    private val mintedToken3 = MintedToken("OMG:8bcda572-9411-43c8-baae-cd56eb0155f3", "OMG", "OmiseGO", 10.0.bd)
    private val mintedToken4 = MintedToken("OMG:8bcda572-9411-43c8-baae-cd56eb0155f3", "OMG", "OmiseGO", 10000.0.bd)

    @Test
    fun `mintedToken1 should be not compatible with mintedToken2`() {
        mintedToken1 compatWith mintedToken2 shouldEqualTo false
    }

    @Test
    fun `mintedToken1 should be not compatible with mintedToken3`() {
        mintedToken1 compatWith mintedToken3 shouldEqualTo false
    }

    @Test
    fun `mintedToken1 should be compatible with mintedToken4`() {
        mintedToken1 compatWith mintedToken4 shouldEqualTo true
    }

    @Test
    fun `MintedToken should be parcelized correctly`() {
        mintedToken1.validateParcel().apply {
            this shouldEqual mintedToken1
            this shouldNotBe mintedToken1
        }
    }
}
