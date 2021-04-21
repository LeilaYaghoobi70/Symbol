package ly.didex.symbol.repository

import ly.didex.symbol.model.Symbol
import ly.didex.symbol.network.SymbolApi
import javax.inject.Inject

class SymbolRepository @Inject constructor(
    private val api: SymbolApi
) {
    suspend fun getSymbols(): List<Symbol> = api.getSymbols()
}