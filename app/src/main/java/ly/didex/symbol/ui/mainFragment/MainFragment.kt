package ly.didex.symbol.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.AndroidEntryPoint
import ly.didex.symbol.ui.RequestStatus
import ly.didex.symbol.databinding.MainFragmentBinding
import ly.didex.symbol.model.Symbol
import ly.didex.symbol.ui.mainFragment.adapter.SymbolAdapter

@AndroidEntryPoint
class MainFragment : Fragment(), LifecycleObserver {

    private val viewModel: MainViewModel by viewModels()
    private var binding: MainFragmentBinding? = null
    private lateinit var adapter: SymbolAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = this@MainFragment.viewLifecycleOwner
            viewmodel = viewModel

            this@MainFragment.adapter = SymbolAdapter(viewModel.symbols)
            recyclerView.adapter = this@MainFragment.adapter

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.retry()
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onCreate(savedInstanceState)
        viewModel.symbolLiveData.observe(viewLifecycleOwner, { requestStatues ->
            when (requestStatues) {
                is RequestStatus.SUCCESS -> {
                    if (binding?.swipeRefreshLayout!!.isRefreshing){
                        binding?.swipeRefreshLayout!!.isRefreshing =false
                        viewModel.isRetry = false
                    }
                    viewModel.symbols = requestStatues.symbols as ArrayList<Symbol>
                    adapter.submitList(viewModel.symbols)
                }
                is RequestStatus.ERROR  -> {
                    if (binding?.swipeRefreshLayout!!.isRefreshing){
                        viewModel.isRetry = false
                        binding?.swipeRefreshLayout!!.isRefreshing =false
                    }
                    Toast.makeText(context,requestStatues.errorMessage, Toast.LENGTH_LONG).show()
                }
                else -> Unit
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}