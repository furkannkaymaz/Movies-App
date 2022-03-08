package com.furkan.beinConnectMovies.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import com.furkan.beinConnectMovies.base.BaseLinearLayout
import com.furkan.beinConnectMovies.databinding.MainViewSearchBarBinding
import com.furkan.beinConnectMovies.utils.extensions.CallbackObject

class SearchBarView(context: Context, attrs: AttributeSet? = null) :
    BaseLinearLayout<MainViewSearchBarBinding>(context, attrs) {

    var callbackDidTextChange: CallbackObject<String>? = null

    override fun createView(inflater: LayoutInflater): MainViewSearchBarBinding {
        return MainViewSearchBarBinding.inflate(inflater, this, true)
    }

    override fun viewCreated(attrs: AttributeSet?) {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                callbackDidTextChange?.invoke("${s ?: ""}")
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

}