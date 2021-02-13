package com.kekadoc.tools.android.animation

import android.animation.*
import android.os.Build
import android.util.Property
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import com.kekadoc.tools.android.animation.util.PairEvaluator

@IntDef(ValueAnimator.RESTART, ValueAnimator.REVERSE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class RepeatMode

@RequiresApi(Build.VERSION_CODES.O)
fun AnimatorSet.reset() {
    currentPlayTime = 0L
}
fun ValueAnimator.reset() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
        setCurrentFraction(0f)
    } else currentPlayTime = 0L
}

typealias AnimatorBuilding = AnimatorBuilder.() -> Unit
fun AnimatorBuilding.call(animator: Animator) {
    invoke(AnimatorBuilder(animator))
}

typealias ValueAnimatorBuilding = ValueAnimatorBuilder.() -> Unit
fun ValueAnimatorBuilding.call(animator: ValueAnimator) {
    invoke(ValueAnimatorBuilder(animator))
}

object Properties {
    val PROGRESS: Property<ProgressBar, Int> = object : Property<ProgressBar, Int>(
        Int::class.java,
        "progress"
    ) {
        override fun get(`object`: ProgressBar): Int {
            return `object`.progress
        }
        override fun set(`object`: ProgressBar, value: Int) {
            `object`.progress = value
        }
    }
}

class AnimationCreator<Target>(val target: Target) {

    fun set(builder: AnimatorSet.() -> Unit): AnimatorSet {
        val set = AnimatorSet()
        builder.invoke(set)
        return set
    }

    fun valuesHolder(property: Property<Target, Float>, from: Float, to: Float): PropertyValuesHolder {
        return PropertyValuesHolder.ofFloat(property, from, to)
    }
    fun valuesHolder(property: Property<Target, Int>, from: Int, to: Int): PropertyValuesHolder {
        return PropertyValuesHolder.ofInt(property, from, to)
    }
    fun <V> valuesHolder(property: Property<Target, V>, evaluator: TypeEvaluator<V>, from: V, to: V): PropertyValuesHolder {
        return PropertyValuesHolder.ofObject(property, evaluator, from, to)
    }

    fun <V> property(property: Property<Target, V>, evaluator: TypeEvaluator<V>, from: V, to: V, builder: ValueAnimatorBuilding? = null): ObjectAnimator {
        val animator = ObjectAnimator.ofObject(target, property, evaluator, from, to)
        builder?.call(animator)
        return animator
    }
    fun property(property: Property<Target, Float>, from: Float, to: Float, builder: ValueAnimatorBuilding? = null): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, property, from, to)
        builder?.call(animator)
        return animator
    }
    fun property(property: Property<Target, Int>, from: Int, to: Int, builder: ValueAnimatorBuilding? = null): ObjectAnimator {
        val animator = ObjectAnimator.ofInt(target, property, from, to)
        builder?.call(animator)
        return animator
    }

    fun <V> animate(evaluator: TypeEvaluator<V>, from: V, to: V, builder: ValueAnimatorBuilding? = null): ValueAnimator {
        val animator = ValueAnimator.ofObject(evaluator, from, to)
        builder?.call(animator)
        return animator
    }
    fun <V> animate(from: Float, to: Float, builder: ValueAnimatorBuilding? = null): ValueAnimator {
        val animator = ValueAnimator.ofFloat(from, to)
        builder?.call(animator)
        return animator
    }
    fun <V> animate(from: Int, to: Int, builder: ValueAnimatorBuilding? = null): ValueAnimator {
        val animator = ValueAnimator.ofInt(from, to)
        builder?.call(animator)
        return animator
    }

}

fun <T> T.animator() = AnimationCreator(this)
fun <T, A : Animator> T.animator(builder: AnimationCreator<T>.() -> A): A {
    return builder.invoke(AnimationCreator(this))
}

inline fun <V> charSequenceAnimator(
    from: V,
    to: V,
    crossinline evaluate: ((fraction: Float, startValue: V, endValue: V) -> CharSequence?),
    crossinline animate: ((chars: CharSequence?) -> Unit),
    noinline builder: AnimatorBuilding? = null
): Animator {
    val evaluator = PairEvaluator<CharSequence?, V> { fraction, startValue, endValue -> evaluate.invoke(
        fraction,
        startValue,
        endValue
    ) }
    val animator = ValueAnimator.ofObject(evaluator, from, to)
    animator.addUpdateListener {
        animate.invoke(it.animatedValue as CharSequence?)
    }
    builder?.invoke(AnimatorBuilder(animator))
    return animator
}

inline fun colorAnimator(
    from: Int,
    to: Int,
    crossinline animate: ((fraction: Float, animatedColor: Int) -> Unit),
    noinline builder: ValueAnimatorBuilding? = null
): ValueAnimator {
    val animator = ValueAnimator.ofArgb(from, to)
    animator.addUpdateListener {
        animate.invoke(it.animatedFraction, it.animatedValue as Int)
    }
    builder?.call(animator)
    return animator
}
