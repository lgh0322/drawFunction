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







    fun sign(d:Double):Int{
        if(d<0){
            return -1;
        }

        if(d==0.0){
            return 0;
        }
        if(d>0){
            return 1;
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





        initFun("x ^ 2 + y ^ 2 -4")

        val lX=-5.0;
        val rX=5.0;
        val uY=10.0;
        val dY=-10.0;

        val deltaX=0.01;
        val deltaY=0.01;



        val c1=((rX-lX)/deltaX).toInt()
        val c2=((uY-dY)/deltaY).toInt()


        for(k in 0 until c1){
            for(j in 0 until c2){
                val fuck1=k*deltaX
                val fuck2=j*deltaY
                val fuck3=lX+fuck1+deltaX
                val fuck4=lX+fuck1
                val xx= doubleArrayOf(fuck4,fuck3,fuck3, fuck4)


                val fuck5=fuck2+dY
                    val fuck6=fuck2+dY+deltaY

                val yy= doubleArrayOf(fuck6, fuck6, fuck5, fuck5)




                val fuckfuck=DoubleArray(4){
                    0.0
                }

                val fuckfuck2=IntArray(4){
                    0
                }

                for(cc in 0 until 4){
                    fuckfuck[cc]=getValue(xx[cc],yy[cc])
                    fuckfuck2[cc]=sign(fuckfuck[cc])
                }



            }
        }


        binding.graph.initCor(lX,rX,uY,dY)









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