package com.kekadoc.tools.android.animation.interpolation

import android.view.animation.*
import android.view.animation.BounceInterpolator

object Interpolations {
    val OVERSHOOT = OvershootInterpolator()
    val BOUNCE = BounceInterpolator()
    val ACCELERATE_DECELERATE = AccelerateDecelerateInterpolator()
    val ACCELERATE = AccelerateInterpolator()
    val ANTICIPATE = AnticipateInterpolator()
    val ANTICIPATE_OVERSHOOT = AnticipateOvershootInterpolator()
    val LINEAR = LinearInterpolator()
    val CYCLE = CycleInterpolator(1f)
    val DECELERATE = DecelerateInterpolator()
}