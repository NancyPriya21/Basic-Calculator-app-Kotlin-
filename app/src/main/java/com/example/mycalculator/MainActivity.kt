package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView?=null
    private var lastDot: Boolean=false
    private var lastNumeric: Boolean=false
    private var dotInNumber=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput=findViewById(R.id.tvResult)
    }
    //we should use onclickListener for best practices instead of onClick method= onDigit in activity
    //but its simple here as there are many buttons
    fun onDigit(view: View){   //view is the actual button which is pressed and calls onDigit method
        tvInput?.append((view as Button).text)
        //view.text does not work so as we know view is a button and button has text property
        lastNumeric=true
        lastDot=false
    }
    fun onClear(view:View){
        tvInput?.text=""
    }
    fun onDecimal(view:View){
        if(!lastDot && lastNumeric && !dotInNumber){
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
            dotInNumber=true
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-"))
             false
        else{
             (value.contains("+")|| value.contains("/") ||value.contains("*")
                    ||value.contains("-"))
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(!isOperatorAdded(it.toString()) && lastNumeric){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
                dotInNumber=false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue =tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    tvValue=tvValue.substring(1)
                    prefix="-"
                }
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    val res= one.toDouble() - two.toDouble()
                    val resToString=res.toString()
                    tvInput?.text= removeZeroFromEnd(resToString)
                }
                else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    val res= one.toDouble() + two.toDouble()
                    val resToString=res.toString()
                    tvInput?.text= removeZeroFromEnd(resToString)
                }
                else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    val res= one.toDouble() * two.toDouble()
                    val resToString=res.toString()
                    tvInput?.text= removeZeroFromEnd(resToString)
                }
                else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    val res= one.toDouble() / two.toDouble()
                    val resToString=res.toString()
                    tvInput?.text= removeZeroFromEnd(resToString)
                }
            }catch (e:ArithmeticException){
                    e.printStackTrace()
            }
            dotInNumber=false
        }
    }
    private fun removeZeroFromEnd(result:String):String{
             var value= result
             if(result.contains(".0")){
                 value=result.substring(0, result.length-2)
             }
        return value
    }
    fun removeChar(view: View){
        tvInput?.text?.let{
            val removeChar = it.toString()
            if(removeChar.isNotEmpty()) {
                val newStr = removeChar.substring(0, removeChar.length - 1)
                tvInput?.text = newStr
            }
        }
    }
}