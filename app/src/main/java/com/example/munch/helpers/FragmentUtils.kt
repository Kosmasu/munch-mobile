package com.example.munch.helpers

import androidx.fragment.app.Fragment

object FragmentUtils {
    fun Fragment.isSafeFragment(): Boolean {
        return !(this.isRemoving || this.activity == null || this.isDetached || !this.isAdded || this.view == null)
    }
}