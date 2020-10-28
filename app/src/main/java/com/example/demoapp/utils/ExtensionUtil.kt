package com.example.demoapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B, R> LiveData<A>.combine(
    liveData: LiveData<B>,
    block: (A?, B?) -> R
): LiveData<R> {
    return MediatorLiveData<R>().apply {
        addSource(this@combine) {
            value = block(this@combine.value, liveData.value)
        }
        addSource(liveData) {
            value = block(this@combine.value, liveData.value)
        }
    }
}