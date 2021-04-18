package ly.didex.symbol

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ly.didex.symbol.ui.RequestStatus

object BindingAdapter {
    @BindingAdapter(value = ["handel_loading"], requireAll = true)
    @JvmStatic
    fun  setVisibility(progressBar: View,status: RequestStatus){
            when(status){
                is RequestStatus.LOADING -> progressBar.visibility = View.VISIBLE
                else -> progressBar.visibility = View.GONE
            }
        }

    @BindingAdapter(value = ["handel_error"], requireAll = true)
    @JvmStatic
    fun setVisibility(imageView: ImageView, status: RequestStatus){
        when(status){
            is RequestStatus.ERROR-> imageView.visibility = View.VISIBLE
            else -> imageView.visibility = View.GONE
        }
    }

    @BindingAdapter(value = ["handel_visibility"], requireAll = true)
    @JvmStatic
    fun setVisibility(recyclerView: RecyclerView, status: RequestStatus){
        when(status){
            is RequestStatus.SUCCESS-> recyclerView.visibility = View.VISIBLE
            else -> recyclerView.visibility = View.GONE
        }
    }

}