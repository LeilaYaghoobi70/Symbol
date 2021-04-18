package ly.didex.symbol.ui.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ly.didex.symbol.RequestStatus
import ly.didex.symbol.model.Symbol
import ly.didex.symbol.repository.SymbolRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val symbolRepository: SymbolRepository
) : ViewModel() {

    private val _symbolMutableLiveData = MutableLiveData<RequestStatus>()
    val symbolLiveData: LiveData<RequestStatus> = _symbolMutableLiveData

    var symbols = ArrayList<Symbol>()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var isRetry = false

    init {
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

    }

    fun retry(){
        isRetry = true
        getSymbols()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}