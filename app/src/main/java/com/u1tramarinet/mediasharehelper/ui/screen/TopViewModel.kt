package com.u1tramarinet.mediasharehelper.ui.screen

import androidx.lifecycle.ViewModel
import com.u1tramarinet.mediasharehelper.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor() : ViewModel() {
    fun execute() {
        LogUtil.methodIn(TAG)
    }

    companion object {
        private val TAG = TopViewModel::class.java.simpleName
    }
}