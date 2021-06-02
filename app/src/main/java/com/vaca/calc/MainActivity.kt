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


/*
abs: absolute value
acos: arc cosine
asin: arc sine
atan: arc tangent
cbrt: cubic root
ceil: nearest upper integer
cos: cosine
cosh: hyperbolic cosine
exp: euler's number raised to the power (e^x)
floor: nearest lower integer
log: logarithmus naturalis (base e)
log2: logarithm to base 2
log10: logarithm to base 10
sin: sine
sinh: hyperbolic sine
sqrt: square root
tan: tangent
tanh: hyperbolic tangent
signum: signum of a value
Addition: '2 + 2'
Subtraction: '2 - 2'
Multiplication: '2 * 2'
Division: '2 / 2'
Exponential: '2 ^ 2'
Unary Minus,Plus (Sign Operators): '+2 - (-2)'
Modulo: '2 % 2'
*/

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var vibrator: Vibrator? = null
    lateinit var binding:ActivityMainBinding

    lateinit var myFun:Expression

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vibrator = getSystemService(VIBRATOR_SERVICE) as (Vibrator)







        binding.x1.setOnClickListener(this)
        binding.x2.setOnClickListener(this)
        binding.x4.setOnClickListener(this)






        initFun("1/x")



        val ff1=DoubleArray(500){
            it/50.0-5.0
        }


           val gg2=DoubleArray(500){
               it2->
               val c=getValue(ff1[it2])
               if(c.isNaN()){
                   0.0
               }else if(c.isInfinite()){
                    0.0
               }else{
                   c
               }
           }



        for(k in gg2){
            Log.e( "螺丝钉咖啡碱考虑","fuck ${k}")
        }

        Log.e("fuck","看见士大夫艰苦了  ${gg2[0]}")








        binding.graph.waveData=gg2
        binding.graph.waveDataX=ff1

        binding.graph.invalidate()




















    }

    fun initFun(s:String){
        myFun=ExpressionBuilder(s)
            .variables("x")
            .build()
    }


    fun getValue(x:Double):Double{
        myFun.setVariable("x",x)
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