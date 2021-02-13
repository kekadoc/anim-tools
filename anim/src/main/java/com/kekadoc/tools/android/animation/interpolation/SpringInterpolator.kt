package com.kekadoc.tools.android.animation.interpolation

import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.pow

/**
 * Оттскоки
 * */
class SpringInterpolator(
    /**
     * Количество отскоков
     * */
    private val amplitude: Double,
    /**
     * Промежуток между отскоками
     * */
    private val frequency: Double
    ) : Interpolator {

    override fun getInterpolation(time: Float): Float {
        return (-1.0 * Math.E.pow(-time / amplitude) * cos(frequency * time) + 1.0).toFloat()
    }

}