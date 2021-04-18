package ly.didex.symbol.ui.mainFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ly.didex.symbol.databinding.SymbolItemBinding
import ly.didex.symbol.model.Symbol

class SymbolAdapter constructor(
    var symbols: ArrayList<Symbol>
) : RecyclerView.Adapter<SymbolAdapter.SymbolViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolViewHolder =
        SymbolViewHolder(
            SymbolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SymbolViewHolder, position: Int) =
        holder.bind(symbols[position])

    override fun getItemCount(): Int = symbols.size

    fun submitList(newStudents: ArrayList<Symbol>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            NameAnimCallback(newStudents, symbols)
        )

        symbols.clear()
        symbols.addAll(newStudents)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SymbolViewHolder constructor(private val binding: SymbolItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(symbol: Symbol) {
            binding.apply {
                baseCurrencyShortNameViewTextView.text = symbol.baseCurrencyShortName
                symbolTextView.text = symbol.symbol
            }

        }
    }

    inner class NameAnimCallback(
        private var newListNameAnim: List<Symbol>,
        private var oldLIstNameAnim: List<Symbol>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldLIstNameAnim.size

        override fun getNewListSize(): Int = newListNameAnim.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldLIstNameAnim[oldItemPosition] == newListNameAnim[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldLIstNameAnim[oldItemPosition] == newListNameAnim[newItemPosition]

    }
}