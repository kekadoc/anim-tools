package com.kekadoc.tools.android.animation

import android.animation.Animator

class AnimGenerator<T>(private val creator: Creator<T>) {

    interface Creator<T> {

        companion object {
            const val DEFAULT_TYPE = 0
        }

        fun onCreateAnimation(target: T, type: Int): Animator
    }

    fun create(target: T, type: Int = Creator.DEFAULT_TYPE): Animator {
        return creator.onCreateAnimation(target, type)
    }

}