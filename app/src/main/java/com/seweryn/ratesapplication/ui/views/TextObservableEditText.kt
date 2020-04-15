package com.seweryn.ratesapplication.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText

class TextObservableEditText  : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var onTextChangedAction: () -> Unit = {}

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChangedAction.invoke()
        }

    }

    fun setOnTextChangedAction(onTextChangedAction: () -> Unit) {
        this.onTextChangedAction = onTextChangedAction
        removeTextChangedListener(textWatcher)
        addTextChangedListener(textWatcher)
    }

    fun removeOnTextChangedAction() {
        this.onTextChangedAction = {}
        removeTextChangedListener(textWatcher)
    }

}