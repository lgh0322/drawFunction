package com.vaca.calc

import android.os.Bundle
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.vaca.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception



class MainActivity : AppCompatActivity(), View.OnClickListener {
    val validLineArray:MutableList <Line> = ArrayList()
    var vibrator: Vibrator? = null
    val funString = MutableLiveData<String>()
    lateinit var binding:ActivityMainBinding

    lateinit var myFun:Expression

    val lX=-5.0;
    val rX=5.0;
    val uY=10.0;
    val dY=-10.0;

    val deltaX=0.3;
    val deltaY=0.3;



    val xCount=((rX-lX)/deltaX).toInt()
    val yCount=((uY-dY)/deltaY).toInt()
    lateinit var  pointXset:DoubleArray
    lateinit var  pointYset:DoubleArray

    val pointValue=DoubleArray(4){
        0.0
    }




    fun sign(d:Double):Int{
        if(d<0){
            return 0;
        }else {
            return 1;
        }
    }


    fun abs(d: Double):Double{
        if(d<0.0){
            return -d
        };
        return d;
    }

    data class Point(val x: Double, val y: Double)

    fun lineSolve(x1:Double,y1:Double,x2:Double,y2:Double,pata:Double):Point{
        var x=x2+(x1-x2)*pata
        var y=y2+(y1-y2)*pata
        return Point(x,y)
    }


    fun lineSolve(x1:Double,y1:Double,v1:Double,x2:Double,y2:Double,v2:Double):Point{
        val pata=abs(v2)/abs(v2-v1)
        var x=x2+(x1-x2)*pata
        var y=y2+(y1-y2)*pata
        return Point(x,y)
    }


    fun case1(a:Int,b:Int,c:Int):Line{
        val p1=lineSolve(pointXset[a],pointYset[a],pointXset[b],pointYset[b],abs(pointValue[b])/abs(pointValue[b]-pointValue[a]))
        val p2=lineSolve(pointXset[c],pointYset[c],pointXset[b],pointYset[b],abs(pointValue[b])/abs(pointValue[b]-pointValue[c]))
        return Line(p1.x.toFloat(),p1.y.toFloat(),p2.x.toFloat(),p2.y.toFloat())
    }


    fun case2(a:Int,b:Int,c:Int,d:Int):Line{
        val p1=lineSolve(pointXset[a],pointYset[a],pointValue[a],pointXset[b],pointYset[b],pointValue[b])
        val p2=lineSolve(pointXset[c],pointYset[c],pointValue[c],pointXset[d],pointYset[d],pointValue[d])
        return Line(p1.x.toFloat(),p1.y.toFloat(),p2.x.toFloat(),p2.y.toFloat())
    }









    override fun dispatchTouchEvent(me: MotionEvent): Boolean {
        if (me.action == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            val v = currentFocus //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v!!.windowToken) //收起键盘
            }
        }
        return super.dispatchTouchEvent(me)
    }




    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {  //判断得到的焦点控件是否包含EditText
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            //得到输入框在屏幕中上下左右的位置
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return if (event.x > left && event.x < right && event.y > top && event.y < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                false
            } else {
                true
            }
        }
        // 如果焦点不是EditText则忽略
        return false
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vibrator = getSystemService(VIBRATOR_SERVICE) as (Vibrator)



        binding.x1.setOnClickListener(this)
        binding.x2.setOnClickListener(this)
        binding.x4.setOnClickListener(this)


        funString.observe(this,{
            initFun(it)
            binding.func.setText(it)
            validLineArray.clear()
            for(xIndex in 0 until xCount){
                for(yIndex in 0 until yCount){

                    val tempBigX=lX+xIndex*deltaX+deltaX
                    val tempSmallX=lX+xIndex*deltaX
                    pointXset= doubleArrayOf(tempSmallX,tempBigX,tempBigX, tempSmallX)

                    val tempSmallY=yIndex*deltaY+dY
                    val tempBigY=yIndex*deltaY+dY+deltaY
                    pointYset= doubleArrayOf(tempBigY, tempBigY, tempSmallY, tempSmallY)




                    /*    val pointSign=IntArray(4){
                            0
                        }*/

                    var signTotal=0;

                    for(vertexIndex in 0 until 4){
                        pointValue[vertexIndex]=getValue(pointXset[vertexIndex],pointYset[vertexIndex])
//                    pointSign[vertexIndex]=sign(pointValue[vertexIndex])
                        signTotal+=(1.shl(3-vertexIndex))*sign(pointValue[vertexIndex])
                    }


                    when(signTotal){
                        1->{
                            validLineArray.add(case1(2,3,0))
                        }
                        2->{
                            validLineArray.add(case1(1,2,3))
                        }
                        4->{
                            validLineArray.add(case1(0,1,2))
                        }
                        8->{
                            validLineArray.add(case1(3,0,1))
                        }

                        7->{
                            validLineArray.add(case1(3,0,1))
                        }
                        11->{
                            validLineArray.add(case1(0,1,2))
                        }

                        13->{
                            validLineArray.add(case1(1,2,3))
                        }
                        14->{
                            validLineArray.add(case1(2,3,0))
                        }

                        //------------------------------------------case 2
                        3->{
                            validLineArray.add(case2(1,2,3,0))

                        }

                        6->{
                            validLineArray.add(case2(0,1,2,3))
                        }

                        9->{
                            validLineArray.add(case2(2,3,0,1))
                        }


                        12->{
                            validLineArray.add(case2(3,0,1,2))
                        }



                    }



                }
            }

            binding.graph.drawLineArray=validLineArray

            binding.graph.invalidate()

        })





        binding.graph.initCor(lX,rX,uY,dY)


        funString.postValue("y ^2 +x^2-5")








    }



























    fun initFun(s:String){
        myFun=ExpressionBuilder(s)
            .variables("x","y")
            .build()
    }


    fun getValue(x:Double,y:Double):Double{
        myFun.setVariable("x",x).setVariable("y",y)
        try {
            return myFun.evaluate()
        }catch (e:Exception){
            return 0.0
        }

    }

    override fun onClick(v: View?) {
        vibrator?.vibrate(30)
        when(v?.id){
            R.id.x1->{
                if( binding.group.visibility!=View.VISIBLE){
                    binding.group.visibility=View.VISIBLE
                }else{
                    binding.group.visibility=View.GONE
                }

            }

            R.id.x2->{

            }

            R.id.x4->{
                funString.postValue(binding.func.text.toString())
            }
        }

    }
}