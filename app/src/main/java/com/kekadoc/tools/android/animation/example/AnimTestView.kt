package com.kekadoc.tools.android.animation.example

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.google.android.material.shape.MaterialShapeDrawable
import com.kekadoc.tools.android.AndroidUtils
import com.kekadoc.tools.android.shaper.roundAllCorners
import com.kekadoc.tools.android.shaper.shapedDrawable
import com.kekadoc.tools.android.view.ViewUtils.dpToPx

class AnimTestView : View {

    private var drawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        val color: Int
        color = when (count) {
            0 -> Color.RED
            1 -> Color.GREEN
            2 -> Color.BLUE
            else -> Color.MAGENTA
        }
        Log.e(TAG, "init: $color")

        drawable = shapedDrawable {
            shape { roundAllCorners(dpToPx(16f)) }
            setTint(color)
            setShadowColor(Color.BLACK)
            setStroke(dpToPx(2f), Color.BLUE)
            setRippleColor(AndroidUtils.getThemeColor(context, android.R.attr.colorAccent))
            setElevation(dpToPx(4f))
            setAlpha(255)
        }

        background = drawable
        count++
    }

    override fun setAlpha(alpha: Float) {
        drawable!!.alpha = (alpha * 255).toInt()
    }
    override fun setTranslationZ(translationZ: Float) {
        super.setTranslationZ(translationZ)
    }
    override fun getElevation(): Float {
        return if (drawable is MaterialShapeDrawable) (drawable as MaterialShapeDrawable).elevation else super.getElevation()
    }
    override fun setElevation(elevation: Float) {
        if (drawable is MaterialShapeDrawable) (drawable as MaterialShapeDrawable).elevation = elevation else super.setElevation(elevation)
    }

    companion object {
        private const val TAG = "AnimTestView-TAG"
        private var count = 0
    }

}
