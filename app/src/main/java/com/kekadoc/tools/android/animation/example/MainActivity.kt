package com.kekadoc.tools.android.animation.example

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Property
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kekadoc.tools.android.animation.*
import com.kekadoc.tools.android.drawable
import com.kekadoc.tools.android.log.log
import com.kekadoc.tools.android.view.ViewById
import com.kekadoc.tools.android.view.ViewUtils.doOnMeasureView
import com.kekadoc.tools.android.view.ViewUtils.setProgress
import com.kekadoc.tools.android.view.ViewsCollector
import com.kekadoc.tools.fraction.Fraction
import com.kekadoc.tools.reflection.ReflectionUtils
import com.kekadoc.tools.storage.Iteration
import java.lang.reflect.Field
import java.util.*

@SuppressLint("NonConstantResourceId")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val TAG: String = "MainActivity-TAG"
        private val DURATION = 1000L
        private val DELAY = 0L

        private val COLOR_FROM = Color.RED
        private val COLOR_TO = Color.BLUE

        private val ROTATION_FROM = 0f
        private val ROTATION_TO = 50f

        private val SCALE_FROM = 1f
        private val SCALE_TO = 0.1f

        private val TRANSLATION_FROM = 0f
        private val TRANSLATION_TO = 200f

    }

    @ViewById(id = R.id.activityViewContent)
    private lateinit var activityViewContent: ConstraintLayout
    @ViewById(id = R.id.linearProgress)
    private lateinit var linearProgress: LinearProgressIndicator
    @ViewById(id = R.id.circularProgress)
    private lateinit var circularProgress: CircularProgressIndicator
    @ViewById(id = R.id.materialCardView)
    private lateinit var cardView: CardView
    @ViewById(id = R.id.view_0)
    private lateinit var view_0: AnimTestView
    @ViewById(id = R.id.view_1)
    private lateinit var view_1: AnimTestView
    @ViewById(id = R.id.view_2)
    private lateinit var view_2: AnimTestView
    @ViewById(id = R.id.imageView)
    private lateinit var imageView: ImageView
    @ViewById(id = R.id.testButton)
    private lateinit var button: Button
    @ViewById(id = R.id.textView)
    private lateinit var textView: TextView

    override fun onClick(v: View) {
        when (v.id) {
            R.id.view_0 -> {

            }
            R.id.view_1 -> {
                //testAnimSimple(v)
            }
            R.id.view_2 -> {

            }
            R.id.testButton -> { testAll(view_1) }
        }
    }

    fun ViewsCollector.findAllViews(fields: Any? = null) {
        val clazz: Class<*> = fields?.javaClass ?: javaClass
        ReflectionUtils.getAllFields(clazz, object : Iteration.Single<Field> {
            override fun iteration(item: Field) {
               // Log.e(TAG, "iteration: $item")
                if (item.isAnnotationPresent(ViewById::class.java)) {
                    Log.e(TAG, "iteration: $item")
                    val id: Int =
                            Objects.requireNonNull(item.getAnnotation(ViewById::class.java)).id
                    val access = item.isAccessible
                    item.isAccessible = true
                    try {
                        item[fields] = findViewById(id)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                        Log.e(TAG, "findAllViews Error: ", e)
                    } finally {
                        item.isAccessible = access
                    }
                }
            }
        })
    }

    fun Activity.findAllViews() {
        if (this is ViewsCollector) (this as ViewsCollector).findAllViews()
        else ViewsCollector.asActivity(this).findAllViews(this)
    }
    fun View.findAllViews() {
        if (this is ViewsCollector) (this as ViewsCollector).findAllViews()
        else ViewsCollector.asView(this).findAllViews(this)
    }

    private fun setProgressIndication(fraction: Float) {
        linearProgress.setProgress(fraction)
    }
    private fun runAnimation(animator: ValueAnimator, name: String) {
        animator.addUpdateListener {
            val fraction = it.animatedFraction
            setProgressIndication(fraction)
        }
        animator.doOnStart {
            supportActionBar!!.title = name
        }
        animator.doOnEnd {
            supportActionBar!!.title = "Nothing"
        }
    }
    private fun runAnimation(animator: Animator, name: String) {
        animator.doOnStart {
            supportActionBar!!.title = name
            linearProgress.apply {
                hide()
                isIndeterminate = true
                show()
            }
        }
        animator.doOnEnd {
            supportActionBar!!.title = "Nothing"
            linearProgress.apply {
                hide()
                isIndeterminate = false
                show()
            }
        }
    }
    private fun <A : ValueAnimator> A.registerAnimation(name: String): A {
        runAnimation(this, name)
        return this
    }
    private fun <A : Animator> A.registerAnimation(name: String): A {
        runAnimation(this, name)
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "FF"

        findAllViews()

        view_0.setOnClickListener(this)
        view_1.setOnClickListener(this)
        view_2.setOnClickListener(this)
        button.setOnClickListener(this)

        activityViewContent.doOnMeasureView {
            //test_RotationX(view).start();
            //anim_simple(view_0);
            //test_Color(view_1).start();
        }

        val data = Data("Data", 0)

        val prop = object : Property<Data, Int>(Int::class.java, "code") {
            override fun set(`object`: Data?, value: Int?) {
                `object`?.code = value!!
            }
            override fun get(`object`: Data?): Int {
                return `object`?.code ?: 0
            }
        }

    }

    data class Data(val name: String, var code: Int)

    private var testAllAnimator: Animator? = null
    private fun testAll(view: View) {
        testAllAnimator?.cancel()
        testAllAnimator = view.animator().set {
            playSequentially(
                testAlpha(view),
                testColor(view),
                testRotationX(view),
                testRotationY(view),
                testRotationZ(view),
                testScaleX(view),
                testScaleY(view),
                testTranslationX(view),
                testTranslationY(view),
                testTranslationZ(view),
                testMoveLeft(view),
                testMoveTop(view),
                testMoveRight(view),
                testMoveBottom(view),
                testLeaveLeft(view),
                testLeaveTop(view),
                testLeaveRight(view),
                testLeaveBottom(view),
                testFlip_TOP(view),
                testFlip_BOTTOM(view),
                testScale(view),
                testLeaveMoveRotation(view),
                testTextAnim(),
                testCircularProgressAnim(),
                testLinearProgressAnim()
            )
        }
        testAllAnimator!!.start()
    }

    private fun testAnim(any: Data): Animator {
        return any.animator {
            set {
                play(
                        animate<Data>(0f, 1f) {
                            duration(DURATION)
                            delay(DELAY)
                            onUpdate {
                                //it.animatedValue.log().e(TAG)
                            }
                            listener(
                                    onStart = { Log.e(TAG, "onStart: ") },
                                    onEnd = { Log.e(TAG, "onEnd: ") },
                                    onCancel = { Log.e(TAG, "onCancel: ") },
                                    onRepeat = { Log.e(TAG, "onRepeat: ") }
                            )
                        }.registerAnimation("Data Anim")
                )
            }
        }
    }

    private fun testTextAnim(): Animator {
        return charSequenceAnimator(
            0f, 1500f,
            evaluate = { fraction, startValue, endValue ->
                val value = Fraction.getValue(startValue, endValue, fraction.toDouble())
                value.toString()
            },
            animate = {
                textView.text = it
            }
        ) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("TextAnim")
    }

    private fun testLinearProgressAnim(): Animator {
        return linearProgress.animator().progress(0, 100) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("LinearProgressAnim")
    }
    private fun testCircularProgressAnim(): Animator {
        return circularProgress.animator().progress(0, 100) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("CircularProgressAnim")
    }
    private fun testColor(view: View): Animator {
        return colorAnimator(COLOR_FROM, COLOR_TO, animate = { _, color -> view.setBackgroundColor(color) }) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("Color")
    }

    private fun testAlpha(view: View): Animator {
        return view.animator().alpha(0f, 1f) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("Alpha")
    }
    private fun testRotationX(view: View): Animator {
        return view.animator().rotationX(ROTATION_FROM, ROTATION_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("rotationX")
    }
    private fun testRotationY(view: View): Animator {
        return view.animator().rotationY(ROTATION_FROM, ROTATION_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("rotationY")
    }
    private fun testRotationZ(view: View): Animator {
        return view.animator().rotationZ(ROTATION_FROM, ROTATION_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("rotationZ")
    }
    private fun testScaleX(view: View): Animator {
        return view.animator().scaleX(SCALE_FROM, SCALE_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("scaleX")
    }
    private fun testScaleY(view: View): Animator {
        return view.animator().scaleY(SCALE_FROM, SCALE_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("scaleY")
    }
    private fun testTranslationX(view: View): Animator {
        return view.animator().translationX(ROTATION_FROM, ROTATION_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("translationX")
    }
    private fun testTranslationY(view: View): Animator {
        return view.animator().translationY(ROTATION_FROM, ROTATION_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("translationY")
    }
    private fun testTranslationZ(view: View): Animator {
        return view.animator().translationZ(ROTATION_FROM, ROTATION_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("translationZ")
    }

    private fun testScale(view: View): Animator {
        return view.animator().scale(SCALE_FROM, SCALE_FROM, SCALE_TO, SCALE_TO) {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("scale")
    }

    private fun testMoveLeft(view: View): Animator {
        return view.animator().moveLeft {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("moveLeft")
    }
    private fun testMoveTop(view: View): Animator {
        return view.animator().moveTop {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("moveTop")
    }
    private fun testMoveRight(view: View): Animator {
        return view.animator().moveRight {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("moveRight")
    }
    private fun testMoveBottom(view: View): Animator {
        return view.animator().moveBottom {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("moveBottom")
    }

    private fun testLeaveLeft(view: View): Animator {
        return view.animator().leaveLeft {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("leaveLeft")
    }
    private fun testLeaveTop(view: View): Animator {
        return view.animator().leaveTop {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("leaveTop")
    }
    private fun testLeaveRight(view: View): Animator {
        return view.animator().leaveRight {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("leaveRight")
    }
    private fun testLeaveBottom(view: View): Animator {
        return view.animator().leaveBottom {
            duration(DURATION)
            delay(DELAY)
            repeatReverse()
        }.registerAnimation("leaveBottom")
    }

    private fun testFlip_TOP(view: View): Animator {
        return view.animator().flip(FlipDirection.TOP) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("Flip_TOP")
    }
    private fun testFlip_BOTTOM(view: View): Animator {
        return view.animator().flip(FlipDirection.BOTTOM) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("Flip_BOTTOM")
    }

    private fun testLeaveMoveRotation(view: View): Animator {
        return view.animator().leaveMoveRotation(1) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("LeaveMoveRotation")
    }
    private fun testLeaveMoveRotationImage(view: ImageView): Animator {
        return view.animator().leaveMoveRotation(4, onReverse = {view.setImageDrawable(drawable(R.drawable.none))}) {
            duration(DURATION)
            delay(DELAY)
        }.registerAnimation("LeaveMoveRotationImage")
    }

}