package ly.didex.symbol.ui

import ly.didex.symbol.model.Symbol

/*sealed class RequestStatus {
    object LOADING : RequestStatus()
    data class ERROR(val errorMessage: String) : RequestStatus()
    data class SUCCESS(val symbols: List<Symbol>) : RequestStatus()
}*/
sealed class Result<out Success, out Failure,out  Loading>

data class Success<out Success>(val value: Success) : Result<Success, Nothing, Nothing>()
data class Failure<out Failure>(val reason: Failure) : Result<Nothing, Failure, Nothing>()
data class Loading<out Loading>(val loading: Loading): Result<Nothing, Nothing, Loading>()
