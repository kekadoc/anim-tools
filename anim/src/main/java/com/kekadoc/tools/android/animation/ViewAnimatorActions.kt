package com.kekadoc.tools.android.animation

import android.animation.*
import android.util.Log
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.kekadoc.tools.android.animation.util.Durations
import com.kekadoc.tools.android.animation.util.PropertyValuesHolderUtils
import com.kekadoc.tools.android.view.ViewUtils.hide
import com.kekadoc.tools.android.view.ViewUtils.show

fun ViewAnimations.leaveLeft(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = 0f
    val to = -view.width.toFloat()
    val translationX = PropertyValuesHolderUtils.translationX(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(1f, 0f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationX, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.leaveLeft(builder: ValueAnimatorBuilding? = null) = ViewAnimations.leaveLeft(target, builder)

fun ViewAnimations.leaveTop(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = 0f
    val to = -view.height.toFloat()
    val translationY = PropertyValuesHolderUtils.translationY(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(1f, 0f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationY, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.leaveTop(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.leaveTop(target, builder)

fun ViewAnimations.leaveRight(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = 0f
    val to = view.width.toFloat()
    val translationX = PropertyValuesHolderUtils.translationX(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(1f, 0f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationX, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.leaveRight(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.leaveRight(target, builder)

fun ViewAnimations.leaveBottom(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = 0f
    val to = view.height.toFloat()
    val translationY = PropertyValuesHolderUtils.translationY(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(1f, 0f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationY, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.leaveBottom(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.leaveBottom(target, builder)



fun ViewAnimations.moveLeft(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = -view.width.toFloat()
    val to = 0f
    val translationX = PropertyValuesHolderUtils.translationX(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(0f, 1f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationX, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.moveLeft(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.moveLeft(target, builder)

fun ViewAnimations.moveTop(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = view.height.toFloat()
    val to = 0f
    val translationY = PropertyValuesHolderUtils.translationY(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(0f, 1f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationY, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.moveTop(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.moveTop(target, builder)

fun ViewAnimations.moveRight(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = view.width.toFloat()
    val to = 0f
    val translationX = PropertyValuesHolderUtils.translationX(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(0f, 1f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationX, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.moveRight(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.moveRight(target, builder)

fun ViewAnimations.moveBottom(view: View, builder: ValueAnimatorBuilding? = null): ValueAnimator {
    val from = view.height.toFloat()
    val to = 0f
    val translationY = PropertyValuesHolderUtils.translationY(from, to)
    val alpha = PropertyValuesHolderUtils.alpha(0f, 1f)
    return ObjectAnimator.ofPropertyValuesHolder(view, translationY, alpha).apply {
        builder?.call(this)
    }
}
fun <T : View> AnimationCreator<T>.moveBottom(builder: ValueAnimatorBuilding? = null): ValueAnimator = ViewAnimations.moveBottom(target, builder)


fun ViewAnimations.flip(view: View, direction: FlipDirection,
    onFront: ((view: View) -> Unit)? = null,
    onBack: ((view: View) -> Unit)? = null, builder: AnimatorBuilding? = null): Animator {

    val animator = AnimatorSet()

    val first = rotationX(view, from = 0f, to = direction.angle) {
        onStart {
            onBack?.invoke(view)
        }
    }
    val second = rotationX(view, from = -direction.angle, to = 0f) {
        onStart {
            onFront?.invoke(view)
        }
    }

    animator.playSequentially(first, second)

    builder?.call(animator)
    return animator
}
fun <T : View> AnimationCreator<T>.flip(
    direction: FlipDirection,
    onFront: ((view: View) -> Unit)? = null,
    onBack: ((view: View) -> Unit)? = null,
    builder: AnimatorBuilding? = null): Animator = ViewAnimations.flip(target, direction, onFront, onBack, builder)

/**
 * FlipDirection
 */
enum class FlipDirection(internal var angle: Float) {

    TOP(-90f),
    BOTTOM(90f);

    companion object {
        @JvmStatic
        fun default(): FlipDirection = FlipDirection.BOTTOM
    }

}

fun ViewAnimations.leaveMoveRotation(
    view: View,
    count: Int,
    startClockwise: Boolean = false,
    onReverse: (() -> Unit)? = null, builder: AnimatorBuilding? = null): Animator {
    val animator = AnimatorSet()
    builder?.call(animator)
    val durationHalf = animator.duration / 2

    val scale_0 = scale(view, 1f, 1f, 0f, 0f) { duration(durationHalf) }
    val rotation_0 = rotationZ(view, 0f, 360f * count) { duration(durationHalf) }

    val scale_1 = scale(view, 0f, 0f, 1f, 1f) { duration(durationHalf) }
    val rotation_1 = rotationZ(view, 360f * count, 0f) {
        duration(durationHalf)
        onStart { onReverse?.invoke() }
    }

    return AnimatorSet().apply {
        if (startClockwise) play(scale_0).with(rotation_0).before(scale_1).before(rotation_1)
        else play(scale_0).with(rotation_1).before(scale_1).before(rotation_0)
        setInterpolator(interpolator)
    }
}
fun <T : View> AnimationCreator<T>.leaveMoveRotation(
    count: Int,
    startClockwise: Boolean = false,
    onReverse: (() -> Unit)? = null,
    builder: AnimatorBuilding? = null): Animator = ViewAnimations.leaveMoveRotation(target, count, startClockwise, onReverse, builder)