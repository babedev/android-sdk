package co.omisego.omisego.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/6/2017 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

/**
 * Represents the global settings of the provider
 *
 * @param mintedTokens A list of minted tokens available for the provider
 */
@Parcelize
data class Setting(val mintedTokens: List<MintedToken>) : Parcelable
