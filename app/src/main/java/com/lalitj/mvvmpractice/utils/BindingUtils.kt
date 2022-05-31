package com.lalitj.mvvmpractice.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:onClick")
fun setOnClick(view: TextView, flag: Boolean) {
    if (flag) {
        view.text = "View Less"
    } else {
        view.text = "View More"
    }
}