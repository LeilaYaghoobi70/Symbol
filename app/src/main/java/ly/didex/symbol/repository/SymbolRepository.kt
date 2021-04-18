package ly.didex.symbol.repository

import io.reactivex.Observable
import ly.didex.symbol.model.Symbol
import ly.didex.symbol.network.SymbolApi
import javax.inject.Inject

class SymbolRepository @Inject constructor(
    private val api: SymbolApi
) {
    fun getSymbols(): Observable<List<Symbol>> = api.getSymbols()
}