package com.firstapp.ryptoapp.model

import okhttp3.internal.platform.Platform
import java.io.Serializable

data class CryptoCurrency(
    val auditinfoList : List<AuditInfo>,
    val circulatingSupply: Double,
    val cmcRank : Double,
    val dateAdded : String,
    val id : Int,
    val isActive: Double,
    val isAudited: Boolean,
    val lasyUpdated: String,
    val marketPairCount: Double,
    val maxSupply: Double,
    val name :String,
    val platform: Platform,
    val quote: List<Quote>,
    val selfReportedCirculatingSupply: Double,
    val slug :String,
    val symbol:String,
    val tags: List<String>,
    val totalSupply :Double
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}
