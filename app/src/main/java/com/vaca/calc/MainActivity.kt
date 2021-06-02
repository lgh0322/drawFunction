package com.vaca.calc

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val e: Expression = ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
            .variables("x", "y")
            .build()
            .setVariable("x", 2.3)
            .setVariable("y", 3.14)
        val result: Double = e.evaluate()


        e.setVariable("x",5.0)



        Log.e("上岛咖啡","re        ${e.evaluate()}")
    }
}