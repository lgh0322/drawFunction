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






        initFun("x ^ 2 + y ^ 2 ")







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