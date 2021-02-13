package com.kekadoc.tools.android.animation

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import androidx.annotation.RequiresApi
import androidx.core.animation.addListener
import androidx.core.animation.addPauseListener

open class AnimatorBuilder(animator: Animator) {

    open val animator: Animator = animator

    fun duration(duration: Long) = apply {
        animator.duration = duration
    }
    fun delay(delay: Long) = apply {
        animator.startDelay = delay
    }
    fun interpolator(interpolator: TimeInterpolator) = apply {
        animator.interpolator = interpolator
    }
    fun listener(listener: Animator.AnimatorListener) = apply {
        animator.addListener(listener)
    }
    fun pauseListener(listener: Animator.AnimatorPauseListener) = apply {
        animator.addPauseListener(listener)
    }

    inline fun onEnd(crossinline action: (animator: Animator) -> Unit) = apply {
        animator.addListener(onEnd = action)
    }
    inline fun onStart(crossinline action: (animator: Animator) -> Unit) = apply {
        animator.addListener(onStart = action)
    }
    inline fun onCancel(crossinline action: (animator: Animator) -> Unit) = apply {
        animator.addListener(onCancel = action)
    }
    inline fun onRepeat(crossinline action: (animator: Animator) -> Unit) = apply {
        animator.addListener(onRepeat = action)
    }
    inline fun onResume(crossinline action: (animator: Animator) -> Unit) = apply {
        animator.addPauseListener(onResume = action)
    }
    inline fun onPause(crossinline action: (animator: Animator) -> Unit) = apply {
        animator.addPauseListener(onPause = action)
    }
    inline fun listener(
        crossinline onEnd: (animator: Animator) -> Unit = {},
        crossinline onStart: (animator: Animator) -> Unit = {},
        crossinline onCancel: (animator: Animator) -> Unit = {},
        crossinline onRepeat: (animator: Animator) -> Unit = {}
    ) = apply { animator.addListener(onEnd, onStart, onCancel, onRepeat) }
    inline fun pauseListener(
        crossinline onResume: (animator: Animator) -> Unit = {},
        crossinline onPause: (animator: Animator) -> Unit = {}
    ) = apply { animator.addPauseListener(onResume, onPause) }

}

class ValueAnimatorBuilder(animator: ValueAnimator) : AnimatorBuilder(animator) {

    override val animator: ValueAnimator
        get() = super.animator as ValueAnimator

    fun repeatCount(count: Int) = apply {
        animator.repeatCount = count
    }
    fun repeatMode(@RepeatMode mode: Int) = apply {
        animator.repeatMode = mode
    }

    inline fun onUpdate(crossinline action: (animator: ValueAnimator) -> Unit) = apply {
        animator.addUpdateListener {
            action.invoke(it)
        }
    }
    fun onUpdate(action: ValueAnimator.AnimatorUpdateListener) = apply {
        animator.addUpdateListener(action)
    }

}

fun ValueAnimatorBuilder.repeatReverse(count: Int = 1) {
    repeatMode(ValueAnimator.REVERSE)
    repeatCount(count)
}
fun ValueAnimatorBuilder.repeatRestart(count: Int = 0) {
    repeatMode(ValueAnimator.RESTART)
    repeatCount(count)
}