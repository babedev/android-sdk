package co.omisego.androidsdk.models

import co.omisego.androidsdk.utils.ErrorCode


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/10/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

data class RawData(val response: String?, val success: Boolean, val errorCode: ErrorCode? = null)
