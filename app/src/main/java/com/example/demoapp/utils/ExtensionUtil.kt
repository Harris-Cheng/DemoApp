package com.example.demoapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B, C, R> LiveData<A>.combine(
    liveDataB: LiveData<B>,
    liveDataC: LiveData<C>,
    block: (A?, B?, C?) -> R
): LiveData<R> {
    return MediatorLiveData<R>().apply {
        addSource(this@combine) {
            value = block(this@combine.value, liveDataB.value, liveDataC.value)
        }
        addSource(liveDataB) {
            value = block(this@combine.value, liveDataB.value, liveDataC.value)
        }
        addSource(liveDataC) {
            value = block(this@combine.value, liveDataB.value, liveDataC.value)
        }
    }
}