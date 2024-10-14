package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var currentInput = StringBuilder()
    var currentOperator = Operator.NONE
    var operand1 : BigDecimal?=null

    enum class Operator{
        NONE,ADD,SUBTRACT,MULTIPLY,DIVIDE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        binding.btnOne.setOnClickListener {
            appendNumber("1")
        }
        binding.btnTwo.setOnClickListener {
            appendNumber("2")
        }
        binding.btnThree.setOnClickListener {
            appendNumber("3")
        }
        binding.btnFour.setOnClickListener {
            appendNumber("4")
        }
        binding.btnFive.setOnClickListener {
            appendNumber("5")
        }
        binding.btnSix.setOnClickListener {
            appendNumber("6")
        }
        binding.btnSeven.setOnClickListener {
            appendNumber("7")
        }
        binding.btnEight.setOnClickListener {
            appendNumber("8")
        }
        binding.btnNine.setOnClickListener {
            appendNumber("9")
        }
        binding.btnDot.setOnClickListener {
            appendNumber(".")
        }
        binding.btnPLus.setOnClickListener {
            setOperator(Operator.ADD)
        }
        binding.btnSub.setOnClickListener {
            setOperator(Operator.SUBTRACT)
        }
        binding.btnMultiply.setOnClickListener {
            setOperator(Operator.MULTIPLY)
        }
        binding.btnDivide.setOnClickListener {
            setOperator(Operator.DIVIDE)
        }
        binding.btnEqual.setOnClickListener {
            calculateResults()
        }
        binding.clear.setOnClickListener {
            clearInput()
        }
        binding.allClear.setOnClickListener {
            allClearInput()
        }
        binding.btnBackspace.setOnClickListener{
            handleBackspace()
        }


    }
    private fun appendNumber(number: String){
        currentInput.append(number)
        updateDisplay();
    }
    private fun handleBackspace(){
        if(currentInput.isNotEmpty()){
            currentInput.deleteCharAt(currentInput.length-1)
            updateDisplay()
        }
    }

    private fun setOperator(operator: Operator){
        if(operand1==null){
            operand1= BigDecimal(currentInput.toString())
            currentInput.clear()
        }
        binding.tvOldInput.text = operand1.toString()
        binding.tvInput.text=""
        currentOperator = operator
        binding.tvCurrentOperand.text = operatorToString(operator)

    }

    private fun operatorToString(operator:Operator):String{
        return when(operator){
            Operator.ADD->"+"
            Operator.SUBTRACT->"-"
            Operator.MULTIPLY->"×"
            Operator.DIVIDE->"÷"
            Operator.NONE->""
        }
    }

    private fun calculateResults(){
        var operand2 = BigDecimal(currentInput.toString())
        var result: BigDecimal?=null
        when(currentOperator){
            Operator.ADD-> result= operand1?.add(operand2)
            Operator.SUBTRACT-> result= operand1?.subtract(operand2)
            Operator.MULTIPLY-> result= operand1?.multiply(operand2)
            Operator.DIVIDE->{
                if(operand2!=BigDecimal.ZERO){
                    result = operand1?.divide(operand2,10,BigDecimal.ROUND_HALF_UP)
                }
            }
            Operator.NONE->result=operand2
        }

        if(result!=null){
            binding.tvOldInput.text = ("$operand1${operatorToString(currentOperator)}$operand2").toString()
            binding.tvInput.text = result.toString()
            operand1 = result
        }
    }

    private fun allClearInput(){
        currentInput.clear()
        operand1 = null;
        currentOperator = Operator.NONE
        binding.tvOldInput.text=""
        binding.tvInput.text="0"
        binding.tvCurrentOperand.text=""

    }

    private fun clearInput(){
        currentInput.clear()
        currentOperator = Operator.NONE
        binding.tvInput.text = "0"
        operand1 = null
    }

    private fun updateDisplay() {
        binding.tvInput.text = currentInput.toString();
    }
}