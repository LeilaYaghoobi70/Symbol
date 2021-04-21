package ly.didex.symbol.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.AndroidEntryPoint
import ly.didex.symbol.databinding.MainFragmentBinding
import ly.didex.symbol.ui.Failure
import ly.didex.symbol.ui.Success
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
                observeSymbols()
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSymbols()
    }

    private fun observeSymbols() {
        viewModel.getSymbol().observe(viewLifecycleOwner, { requestStatues ->
            when (requestStatues) {
                is Success -> {
                    binding?.apply {
                        swipeRefreshLayout.apply {
                            if (isRefreshing) {
                                isRefreshing = false
                                viewModel.isRetry = false
                            }
                        }
                        progressBar.apply {
                            if (isVisible)
                                visibility = View.GONE
                        }
                        errorImageView.apply {
                            if (isVisible)
                                visibility = View.GONE
                        }
                        recyclerView.visibility = View.VISIBLE
                    }
                    adapter.submitList(viewModel.symbols)
                }
                is Failure -> {
                    binding?.apply {
                        swipeRefreshLayout.apply {
                            if (isRefreshing) {
                                isRefreshing = false
                                viewModel.isRetry = false
                            }
                        }
                        progressBar.apply {
                            if (isVisible)
                                visibility = View.GONE
                        }
                        errorImageView.visibility = View.VISIBLE
                    }

                    Toast.makeText(context, requestStatues.reason, Toast.LENGTH_LONG).show()
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