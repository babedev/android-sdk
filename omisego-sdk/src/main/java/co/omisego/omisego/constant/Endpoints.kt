package co.omisego.omisego.constant

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 5/3/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

object Endpoints {
    const val GET_CURRENT_USER = "me.get"
    const val LOGOUT = "logout"
    const val LIST_BALANCE = "me.list_balances"
    const val LIST_TRANSACTIONS = "me.list_transactions"
    const val CREATE_TRANSACTION_REQUEST = "me.create_transaction_request"
    const val RETRIEVE_TRANSACTION_REQUEST = "me.get_transaction_request"
    const val CONSUME_TRANSACTION_REQUEST = "me.consume_transaction_request"
    const val APPROVE_TRANSACTION = "me.approve_transaction_consumption"
    const val REJECT_TRANSACTION = "me.reject_transaction_consumption"
    const val GET_SETTINGS = "me.get_settings"
}