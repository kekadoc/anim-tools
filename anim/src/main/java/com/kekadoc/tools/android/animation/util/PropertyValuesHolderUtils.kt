package com.kekadoc.tools.android.animation.util

import android.animation.PropertyValuesHolder
import android.view.View
import com.kekadoc.tools.android.animation.Properties

object PropertyValuesHolderUtils {

    fun alpha(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.ALPHA, from, to)
    fun translationX(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, from, to)
    fun translationY(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, from, to)
    fun translationZ(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, from, to)
    fun rotationX(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION_X, from, to)
    fun rotationY(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION_Y, from, to)
    fun rotationZ(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, from, to)
    fun scaleX(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, from, to)
    fun scaleY(from: Float, to: Float): PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, from, to)

    fun progress(from: Int, to: Int): PropertyValuesHolder = PropertyValuesHolder.ofInt(Properties.PROGRESS, from, to)

}