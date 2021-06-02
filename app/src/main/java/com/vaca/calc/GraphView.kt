package com.vaca.calc

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.content.ContextCompat


class GraphView : View {
    private val sysPaint = Paint()
    private val sysShadowPaint = Paint()
    private val diaPaint = Paint()
    private val diaShadowPaint = Paint()
    private val boarderPaint = Paint()
    private val gridPaint = Paint()
    private val canvasW = getPixel(R.dimen.w)
    private val canvasH = getPixel(R.dimen.h)
    private val canvasHF = canvasH.toFloat()
    private val canvasWF = canvasW.toFloat()
    private val diaW = getPixel(R.dimen.dia_w).toFloat()

    var seeTime: Long = System.currentTimeMillis()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        boarderPaint.apply {
            color = getColor(R.color.myBlue)
            style = Paint.Style.STROKE
            strokeWidth = getPixel(R.dimen.grid_w).toFloat() * 2
        }

        gridPaint.apply {
            color = getColor(R.color.myGray)
            style = Paint.Style.STROKE
            strokeWidth = getPixel(R.dimen.grid_w).toFloat()
        }

        diaPaint.apply {
            color = getColor(R.color.diaColor)
            style = Paint.Style.FILL
        }
        diaShadowPaint.apply {
            color = getColor(R.color.diaShadowColor)
            style = Paint.Style.FILL
        }
        sysPaint.apply {
            color = getColor(R.color.sysColor)
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        sysShadowPaint.apply {
            color = getColor(R.color.sysShadowColor)
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(getColor(R.color.white))







    }

    private fun getColor(resource_id: Int): Int {
        return ContextCompat.getColor(context, resource_id)
    }

    private fun getPixel(resource_id: Int): Int {
        return resources.getDimensionPixelSize(resource_id)
    }

    override fun onMeasure(width: Int, height: Int) {
        setMeasuredDimension(canvasW, canvasH)
    }



    private fun abs(a: Long, b: Long): Long {
        var c = a.toInt()
        var d = b.toInt()
        if (c > d) {
            return (c - d).toLong()
        } else {
            return (d - c).toLong()
        }
    }

    private fun appropriate(a: Long, b: Long): Boolean {
        return abs(a, b) < 864000000
    }


    var x1 = 0f
    var y1 = 0f
    var cr = ""
    var cr2 = ""
    var gi: Long = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
                gi = seeTime
            }

            ACTION_UP -> {

            }

            ACTION_MOVE -> {
                seeTime = (gi.toFloat() + (x1 - event.x) * 1000000f).toLong()
            }
        }
        return super.onTouchEvent(event)
    }






    private fun drawDia(canvas: Canvas, x: Float, y: Float) {
        val halfWidth = diaW / 2
        val path = Path()
        path.moveTo(x, y + halfWidth)
        path.lineTo(x - halfWidth, y)
        path.lineTo(x, y - halfWidth)
        path.lineTo(x + halfWidth, y)
        path.lineTo(x, y + halfWidth)
        path.close()
        canvas.drawPath(path, diaPaint)
    }

    private fun drawSys(canvas: Canvas, x: Float, y: Float) {
        val halfWidth = diaW / 2
        canvas.drawCircle(x, y, halfWidth, sysPaint)
    }




}