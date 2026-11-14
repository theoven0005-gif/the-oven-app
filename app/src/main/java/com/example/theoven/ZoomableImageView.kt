package com.example.theoven

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.max
import kotlin.math.min

/**
 * ImageView with pinch-to-zoom, pan and double-tap to zoom.
 * Used for the restaurant menu flyers.
 */
class ZoomableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private val matrix = Matrix()
    private val savedMatrix = Matrix()
    private val start = PointF()

    private var mode = NONE
    private var minScale = 1f
    private var maxScale = 5f

    private val scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val values = FloatArray(9)
            matrix.getValues(values)
            val currentScale = values[Matrix.MSCALE_X]
            var factor = detector.scaleFactor
            factor = max(0.5f, min(factor, 3f))
            val newScale = (currentScale * factor).coerceIn(minScale, maxScale)
            factor = newScale / currentScale
            matrix.postScale(factor, factor, detector.focusX, detector.focusY)
            fixTranslation()
            imageMatrix = matrix
            return true
        }
    })

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            val values = FloatArray(9)
            matrix.getValues(values)
            val currentScale = values[Matrix.MSCALE_X]
            val target = if (currentScale > minScale * 1.5f) minScale else minScale * 2.5f
            val factor = target / currentScale
            matrix.postScale(factor, factor, e.x, e.y)
            fixTranslation()
            imageMatrix = matrix
            return true
        }
    })

    init {
        scaleType = ScaleType.MATRIX
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fitToScreen()
    }

    private fun fitToScreen() {
        val d = drawable ?: return
        if (width == 0 || height == 0) return
        val scaleX = width.toFloat() / d.intrinsicWidth
        val scaleY = height.toFloat() / d.intrinsicHeight
        minScale = min(scaleX, scaleY)
        maxScale = minScale * 5f
        matrix.reset()
        matrix.postScale(minScale, minScale)
        val dx = (width - d.intrinsicWidth * minScale) / 2f
        val dy = (height - d.intrinsicHeight * minScale) / 2f
        matrix.postTranslate(dx, dy)
        imageMatrix = matrix
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        post { fitToScreen() }
    }

    private fun fixTranslation() {
        val d = drawable ?: return
        val values = FloatArray(9)
        matrix.getValues(values)
        val scale = values[Matrix.MSCALE_X]
        val imgW = d.intrinsicWidth * scale
        val imgH = d.intrinsicHeight * scale
        val dx = values[Matrix.MTRANS_X]
        val dy = values[Matrix.MTRANS_Y]
        values[Matrix.MTRANS_X] = if (imgW <= width) (width - imgW) / 2f else dx.coerceIn(width - imgW, 0f)
        values[Matrix.MTRANS_Y] = if (imgH <= height) (height - imgH) / 2f else dy.coerceIn(height - imgH, 0f)
        matrix.setValues(values)
    }

    private fun currentScale(): Float {
        val values = FloatArray(9)
        matrix.getValues(values)
        return values[Matrix.MSCALE_X]
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        scaleDetector.onTouchEvent(event)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                savedMatrix.set(matrix)
                start.set(event.x, event.y)
                mode = DRAG
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val dx = event.getX(0) - event.getX(1)
                val dy = event.getY(0) - event.getY(1)
                if (dx * dx + dy * dy > 100f) {
                    savedMatrix.set(matrix)
                    mode = ZOOM
                    parent?.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mode == DRAG && !scaleDetector.isInProgress) {
                    if (currentScale() > minScale * 1.02f) {
                        // Zoomed in: pan the image, keep the ViewPager out
                        parent?.requestDisallowInterceptTouchEvent(true)
                        matrix.set(savedMatrix)
                        matrix.postTranslate(event.x - start.x, event.y - start.y)
                        fixTranslation()
                        imageMatrix = matrix
                    }
                    // At min zoom: do nothing, the ViewPager2 handles page swipes
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mode = NONE
                parent?.requestDisallowInterceptTouchEvent(false)
            }
        }
        return true
    }

    companion object {
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2
    }
}
