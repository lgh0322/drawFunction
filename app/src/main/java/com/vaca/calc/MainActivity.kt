package com.vaca.calc

import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vaca.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception



class MainActivity : AppCompatActivity(), View.OnClickListener {
    val fuck:MutableList <Line> = ArrayList()
    var vibrator: Vibrator? = null
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


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vibrator = getSystemService(VIBRATOR_SERVICE) as (Vibrator)



        binding.x1.setOnClickListener(this)
        binding.x2.setOnClickListener(this)
        binding.x4.setOnClickListener(this)





        initFun("x ^ 2 + y ^ 2 -12.01")




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
                        fuck.add(case1(2,3,0))
                    }
                    2->{
                        fuck.add(case1(1,2,3))
                    }
                    4->{
                        fuck.add(case1(0,1,2))
                    }
                    8->{
                        fuck.add(case1(3,0,1))
                    }

                    7->{
                        fuck.add(case1(3,0,1))
                    }
                    11->{
                        fuck.add(case1(0,1,2))
                    }

                    13->{
                        fuck.add(case1(1,2,3))
                    }
                    14->{
                        fuck.add(case1(2,3,0))
                    }

                    //------------------------------------------case 2
                    3->{
                        fuck.add(case2(1,2,3,0))

                    }

                    6->{
                        fuck.add(case2(0,1,2,3))
                    }

                    9->{
                        fuck.add(case2(2,3,0,1))
                    }


                    12->{
                        fuck.add(case2(3,0,1,2))
                    }



                }



            }
        }


        binding.graph.initCor(lX,rX,uY,dY)

        binding.graph.fuck=fuck

        Log.e("fuckaaaaaa",fuck.size.toString())
        binding.graph.invalidate()









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
    }
}