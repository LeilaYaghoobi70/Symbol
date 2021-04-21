package ly.didex.symbol.ui.mainFragment

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.util.NotificationLite.error
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ly.didex.symbol.model.Symbol
import ly.didex.symbol.repository.SymbolRepository
import ly.didex.symbol.ui.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val symbolRepository: SymbolRepository
) : ViewModel() {

    /*private val _symbolMutableLiveData = MutableLiveData<RequestStatus>()
    val symbolLiveData: LiveData<RequestStatus> = _symbolMutableLiveData*/

    var symbols = ArrayList<Symbol>()

  //  private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var isRetry = false

    fun getSymbol() = liveData(Dispatchers.IO) {
        emit(Loading(loading = true))
        try {
            symbols = symbolRepository.getSymbols() as ArrayList<Symbol>
            emit(Success(Unit))
        } catch (ioException: Exception) {
            emit(Failure(ioException.message!!))
        }
    }
/*    init {
        getSymbols()
    }
    private fun getSymbols() {
       compositeDisposable.add(
            symbolRepository.getSymbols()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!isRetry)
                        _symbolMutableLiveData.value = RequestStatus.LOADING
                }
                .subscribeBy(
                    onError = {
                        if (!it.message.isNullOrEmpty())
                        _symbolMutableLiveData.value = RequestStatus.ERROR(it.message!!)
                        else
                        _symbolMutableLiveData.value = RequestStatus.ERROR("خطایی رخ داده است!")
                    },
                    onNext = {
                        _symbolMutableLiveData.value =  RequestStatus.SUCCESS(it)
                        }
                )
        )

    }*/

   /* fun retry() {
        isRetry = true
    }*/

/*    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }*/
}