package ly.didex.symbol.model

import com.google.gson.annotations.SerializedName

data class Symbol(
    @SerializedName("symbol")
    var symbol: String,

    @SerializedName("baseCurrencyShortName")
    var baseCurrencyShortName: String,

    @SerializedName("quoteCurrencyShortName")
    var quoteCurrencyShortName: String,

    @SerializedName("quantityIncrement")
    var quantityIncrement: Double,

    @SerializedName("tickSize")
    var tickSize: Double,

    @SerializedName("'takeLiquidityRate")
    var takeLiquidityRate: Double,

    @SerializedName("provideLiquidityRate")
    var provideLiquidityRate: Double,

    @SerializedName("feeSide")
    var feeSide: Int

)