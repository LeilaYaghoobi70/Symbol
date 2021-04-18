package ly.didex.symbol.ui

import ly.didex.symbol.model.Symbol

sealed class RequestStatus {
    object LOADING : RequestStatus()
    data class ERROR(val errorMessage: String) : RequestStatus()
    data class SUCCESS(val symbols: List<Symbol>) : RequestStatus()
}
