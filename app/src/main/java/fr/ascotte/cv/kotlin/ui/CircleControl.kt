package fr.ascotte.cv.kotlin.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import fr.ascotte.cv.kotlin.R

class CircleControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

    var paint: Paint = Paint()
    var circleRadius:Float = 1f
    var isFilled:Boolean = false
    var circleStrokeColor = Color.BLACK
    var circleBackgroundColor = Color.BLACK
    var circleStrokeWidth:Float = 1f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = width
        val y = height

        if(circleRadius == -1f)
            circleRadius = if(width >= height) height / 2f else width / 2f

        if(isFilled){

            paint.style =  Paint.Style.FILL
            paint.color = circleBackgroundColor
            canvas.drawCircle(x / 2f, y / 2f, circleRadius- circleStrokeWidth, paint)

            paint.style =  Paint.Style.STROKE
            paint.strokeWidth = circleStrokeWidth
            paint.color = circleStrokeColor
            canvas.drawCircle(x / 2f, y / 2f, circleRadius - circleStrokeWidth, paint)
        }
        else{
            paint.style =  Paint.Style.STROKE
            paint.color = circleStrokeColor
            paint.strokeWidth = circleStrokeWidth
            canvas.drawCircle(x / 2f, y / 2f, circleRadius - circleStrokeWidth, paint)
        }
    }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CircleControl,
            0, 0)

        circleRadius =  typedArray.getFloat(R.styleable.CircleControl_circleRadius, -1f)
        isFilled = typedArray.getBoolean(R.styleable.CircleControl_isFilled, false)
        circleStrokeColor = typedArray.getColor(R.styleable.CircleControl_circleStrokeColor, Color.BLACK)
        circleBackgroundColor = typedArray.getColor(R.styleable.CircleControl_circleBackgroundColor, Color.BLACK)
        circleStrokeWidth = typedArray.getFloat(R.styleable.CircleControl_circleStrokeWidth, 1f)

        typedArray.recycle()
    }
}