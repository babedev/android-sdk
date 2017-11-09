package co.omisego.androidsdk.models

import org.json.JSONObject


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/7/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

data class Response(val version: String, val success: Boolean, val data: JSONObject)
