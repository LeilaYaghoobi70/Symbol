package ly.didex.symbol.network

import io.reactivex.Observable
import ly.didex.symbol.model.Symbol
import retrofit2.http.GET

interface SymbolApi {

    @GET("Public/Symbol")
    fun getSymbols(): Observable<List<Symbol>>
}