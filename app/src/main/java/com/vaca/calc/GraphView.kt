package com.vaca.calc

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.content.ContextCompat


class GraphView : View {


    private val wavePaint = Paint()







    var lX=-5f;
    var rX=5f;

    var uY=5f;
    var dY=-5f;



    fun initCor(lx:Double,rx:Double,uy:Double,dy:Double){
        lX=lx.toFloat()
        rX=rx.toFloat()
        uY=uy.toFloat()
        dY=dy.toFloat()
    }


    fun transferX(d:Double):Float{
        return ((d-lX)/(rX-lX)*width).toFloat()
    }


    fun transferY(d:Double):Float{
        return (height-(d-dY)/(uY-dY)*height).toFloat()
    }


    fun transferX(d:Float):Float{
        return ((d-lX)/(rX-lX)*width).toFloat()
    }


    fun transferY(d:Float):Float{
        return (height-(d-dY)/(uY-dY)*height).toFloat()
    }




    var fuck:MutableList <Line> = ArrayList()














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

        wavePaint.apply {
            color = getColor(R.color.wave_color)
            style = Paint.Style.STROKE
            strokeWidth = 5.0f
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(getColor(R.color.white))


        if(fuck.isNotEmpty()) {
            var wavePath = Path()
            for (k in fuck) {
                wavePath.moveTo(transferX(k.x1), transferY(k.y1))
                wavePath.lineTo(transferX(k.x2), transferY(k.y2))
            }
            canvas.drawPath(wavePath, wavePaint)
        }





    }

    private fun getColor(resource_id: Int): Int {
        return ContextCompat.getColor(context, resource_id)
    }

    private fun getPixel(resource_id: Int): Int {
        return resources.getDimensionPixelSize(resource_id)
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










}