package com.kekadoc.tools.android.animation.interpolation

import android.view.animation.Interpolator
import kotlin.math.abs
import kotlin.math.cos

class BounceInterpolator @JvmOverloads constructor(private val bounces: Int = 3) : Interpolator {

    override fun getInterpolation(x: Float): Float {
        return (1.0 + -abs(cos(x * 10 * bounces / Math.PI)) * getCurveAdjustment(x)).toFloat()
    }

    private fun getCurveAdjustment(x: Float): Double {
        return -(2.0 * (1.0 - x) * x * 1 + x * x) + 1.0
    }

}