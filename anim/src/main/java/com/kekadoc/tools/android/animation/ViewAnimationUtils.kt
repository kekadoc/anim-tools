package com.kekadoc.tools.android.animation

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.widget.ProgressBar

object ViewAnimations {

    fun scaleX(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.SCALE_X, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun scaleY(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun rotationZ(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.ROTATION, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun rotationX(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.ROTATION_X, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun rotationY(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.ROTATION_Y, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun translationX(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_X, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun translationY(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun translationZ(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

    fun alpha(
            view: View,
            from: Float,
            to: Float,
            builder: ValueAnimatorBuilding? = null
    ): ValueAnimator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, from, to).apply {
            builder?.invoke(ValueAnimatorBuilder(this))
        }
    }

}


fun <V : View> AnimationCreator<V>.scaleX(
        from: Float = target.scaleX,
        to: Float = target.scaleX,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.scaleX(target, from, to, builder)

fun <V : View> AnimationCreator<V>.scaleY(
        from: Float = target.scaleY,
        to: Float = target.scaleY,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.scaleY(target, from, to, builder)
fun <V : View> AnimationCreator<V>.rotationZ(
        from: Float = target.rotation,
        to: Float = target.rotation,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.rotationZ(target, from, to, builder)
fun <V : View> AnimationCreator<V>.rotationX(
        from: Float = target.rotationX,
        to: Float = target.rotationX,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.rotationX(target, from, to, builder)
fun <V : View> AnimationCreator<V>.rotationY(
        from: Float = target.rotationY,
        to: Float = target.rotationY,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.rotationY(target, from, to, builder)
fun <V : View> AnimationCreator<V>.translationX(
        from: Float = target.translationX,
        to: Float = target.translationX,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.translationX(target, from, to, builder)
fun <V : View> AnimationCreator<V>.translationY(
        from: Float = target.translationY,
        to: Float = target.translationY,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.translationY(target, from, to, builder)
fun <V : View> AnimationCreator<V>.translationZ(
        from: Float = target.translationZ,
        to: Float = target.translationZ,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.translationZ(target, from, to, builder)
fun <V : View> AnimationCreator<V>.alpha(
        from: Float = target.alpha,
        to: Float = target.alpha,
        builder: ValueAnimatorBuilding? = null
) = ViewAnimations.alpha(target, from, to, builder)

fun <V : View> AnimationCreator<V>.scale(
        fromX: Float = target.scaleX,
        fromY: Float = target.scaleY,
        toX: Float = target.scaleX,
        toY: Float = target.scaleY,
        builder: ValueAnimatorBuilding? = null
): ValueAnimator {
    val propScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, fromX, toX)
    val propScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, fromY, toY)
    return ObjectAnimator.ofPropertyValuesHolder(target, propScaleX, propScaleY).apply {
        builder?.call(this)
    }
}


fun ViewAnimations.scale(
        view: View,
        fromX: Float = view.scaleX,
        fromY: Float = view.scaleY,
        toX: Float = view.scaleX,
        toY: Float = view.scaleY,
        builder: ValueAnimatorBuilding? = null
): ValueAnimator {
    val propScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, fromX, toX)
    val propScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, fromY, toY)
    return ObjectAnimator.ofPropertyValuesHolder(view, propScaleX, propScaleY).apply {
        builder?.call(this)
    }
}

fun ViewAnimations.progress(
        progressBar: ProgressBar,
        from: Int,
        to: Int,
        builder: AnimatorBuilding?
): ValueAnimator {
    return ObjectAnimator.ofInt(progressBar, Properties.PROGRESS, from, to).apply {
        builder?.invoke(AnimatorBuilder(this))
    }
}

fun <V : ProgressBar> AnimationCreator<V>.progress(
        from: Int = target.progress,
        to: Int = target.progress,
        builder: AnimatorBuilding? = null
) = ViewAnimations.progress(target, from, to, builder)
