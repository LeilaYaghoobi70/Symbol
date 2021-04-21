package ly.didex.symbol.network

import ly.didex.symbol.model.Symbol
import retrofit2.http.GET

interface SymbolApi {

    @GET("Public/Symbol")
    suspend fun getSymbols(): List<Symbol>
}