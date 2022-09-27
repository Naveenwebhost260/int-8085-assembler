package com.example.int8085

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import java.lang.Integer.parseInt


var flag :Int = 1
var inrFlag :Int = 1
var valueIncrement: Int = 1
var tenPlaceIncrement: Int = 1
var textChangeColorFlag: Int = 0
var accumulator: Int = 0
var activeGoButton:Int = 0
var b:Int = 0
var c:Int = 0
var d:Int = 0
var e:Int = 0
var M:Int = 0
var  MincrementIndex :Int = 0
var dAA = 0

//init array as global
val addressArray = Array<String>(70000) { i -> "00" }

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //opcode viewer
        val opcodeView : TextView = findViewById(R.id.opcodeview)

        // get textView address
        val digital_one: TextView = findViewById(R.id.addressView1)
        val digital_two: TextView = findViewById(R.id.addressView2)
        val digital_three: TextView = findViewById(R.id.addressView3)
        val digital_four: TextView = findViewById(R.id.addressView4)
        //data View

        val digital_six: TextView = findViewById(R.id.adrressView6)
        //function button
        val reset_button: Button = findViewById(R.id.buttonRST)
        val inrButton: Button = findViewById(R.id.buttonINR)
        val dcrButton: Button = findViewById(R.id.buttonDCR)
        val goButton: Button = findViewById(R.id.buttonGO)
        //get button 0-1
        val button_zero: Button = findViewById(R.id.button0)
        val button_one: Button = findViewById(R.id.button1)
        val button_two: Button = findViewById(R.id.button2)
        val button_three: Button = findViewById(R.id.button3)
        val button_four: Button = findViewById(R.id.button4)
        val button_five: Button = findViewById(R.id.button5)
        val button_six: Button = findViewById(R.id.button6)
        val button_seven: Button = findViewById(R.id.button7)
        val button_eight: Button = findViewById(R.id.button8)
        val button_nine: Button = findViewById(R.id.button9)

        //get A-B
        val buttonA: Button = findViewById(R.id.buttonA)
        val buttonB: Button = findViewById(R.id.buttonB)
        val buttonC: Button = findViewById(R.id.buttonC)
        val buttonD: Button = findViewById(R.id.buttonD)
        val buttonE: Button = findViewById(R.id.buttonE)
        val buttonF: Button = findViewById(R.id.buttonF)

        // array init
        fun addressReturnValue(addressIndex: Int): String{

             val getIndexAddress = addressIndex - 8192

             return addressArray[getIndexAddress]
            }
        fun showOpcode(updatedValue:String){
            when (updatedValue){
                "3e" -> opcodeView.text = "MVI A,dB"
                "06" -> opcodeView.text = "MVI B,dB"
                "0e" -> opcodeView.text = "MVI C,dB"
                "16" -> opcodeView.text = "MVI D,dB"
                "1e" -> opcodeView.text = "MVI E,dB"
                "47" -> opcodeView.text = "MOV B,A"
                "7e" -> opcodeView.text = "MOV A,M"
                "4e" -> opcodeView.text = "MOV C,M"
                "21" -> opcodeView.text = "LXI H,addr 16"
                "23" -> opcodeView.text = "INX H"
                "80" -> opcodeView.text = "ADD B"
                "86" -> opcodeView.text = "ADD M"
                "90" -> opcodeView.text = "SUB B"
                "32" -> opcodeView.text = "STA  addr 16"
                "3a" -> opcodeView.text = "LDA  d16"
                "0d" -> opcodeView.text = "DCR C"
                "c2" -> opcodeView.text = "JNZ loop"
                "76" -> opcodeView.text = "HLT- End"
                "27" -> opcodeView.text = "DAA"
                else -> opcodeView.text = "  "
            }
        }
        //get address typed
        fun getAddress(): Int{
            val digitalOneValue :String = digital_one.text.toString()
            val digitalTwoValue:String =  digital_two.text.toString()
            val digitalThreeValue :String=  digital_three.text.toString()
            val digitalFourValue:String =  digital_four.text.toString()
            val IndexForArrayG :String =  digitalOneValue+digitalTwoValue+digitalThreeValue+digitalFourValue

            val addIndexForArray: Int = IndexForArrayG.toInt(16)



            return addIndexForArray
        }
        fun getIndex(address:Int):Int{
            val getIndexAddress = address - 8192
            return getIndexAddress
        }
        //store data to array
        fun storeToAddress(value :String): String{
            val address :Int = getAddress()
            val getIndexAddress = getIndex(address)
            val onesPlace : String= digital_six.text[1].toString()
            val updatedValue : String = onesPlace + value
            digital_six.text = updatedValue
            addressArray[getIndexAddress] = updatedValue
            showOpcode(updatedValue)
            return updatedValue
        }

        // address place value change function
        fun textColorChange(){
            digital_one.setTextColor(Color.parseColor("#8E8E8E"))
            digital_two.setTextColor(Color.parseColor("#8E8E8E"))
            digital_three.setTextColor(Color.parseColor("#8E8E8E"))
            digital_four.setTextColor(Color.parseColor("#8E8E8E"))
          //  digital_five.setTextColor(Color.parseColor("#2E2E2E"))
            digital_six.setTextColor(Color.parseColor("#8E8E8E"))

        }
        fun addressPlaceChange(): Any{
            digital_one.text = digital_two.text
            digital_two.text = digital_three.text
            digital_three.text = digital_four.text
            // color changer
            textChangeColorFlag += 1
            if(textChangeColorFlag >= 1 ){
                digital_four.setTextColor(Color.parseColor("#FFE500"))
            }
            if(textChangeColorFlag >= 2){
                digital_three.setTextColor(Color.parseColor("#FFE500"))
            }
            if(textChangeColorFlag >= 3 ){
                digital_two.setTextColor(Color.parseColor("#FFE500"))
            }
            if(textChangeColorFlag >= 4 ){
                digital_one.setTextColor(Color.parseColor("#FFE500"))
            }

            return 0
        }

        //Go button
        goButton.setOnClickListener(){
            digital_one.text = "e"
            digital_two.text = "x"
            digital_three.text = "e"
            val text = "Enter the starting address"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

            activeGoButton = 1


        }
        //inr button
        inrButton.setOnClickListener(){
            if(digital_one.text != "0" && digital_one.text != "e" && digital_one.text != "s" && activeGoButton != 1 && digital_one.text != "1" && digital_one.text != "10"){
                  inrFlag += 1

                //  digital_five.setTextColor(Color.parseColor("#FFE500"))
                  digital_six.setTextColor(Color.parseColor("#FFE500"))

                  if(inrFlag > 2){

                         valueIncrement += 1
                         val getDigitalFourValue = digital_four.text.toString()
                         val decimalFourValue : Int = getDigitalFourValue.toInt(16)
                         val decimalFourIncrement = decimalFourValue + 1
                         digital_four.text = decimalFourIncrement .toString(16)


                         //tens place increment
                        if(decimalFourIncrement  == 16) {
                            val getDigitalThreeValue = digital_three.text.toString()
                            val decimalThreeValue : Int = getDigitalThreeValue.toInt(16)
                            val decimalThreeIncrement = decimalThreeValue +1
                            digital_three.text = decimalThreeIncrement.toString(16)
                            valueIncrement = 0
                            digital_four.text = valueIncrement.toString(16)

                          //  val returnValueA :Int = getAddress()
                           // val valueInArrayWithAddress :Int = addressReturnValue(returnValueA)
                            //digital_six.text = valueInArrayWithAddress.toString(16)


                            tenPlaceIncrement += 1
                            inrFlag = 4
                            if(decimalThreeIncrement == 16){
                                val getDigitalTwoValue = digital_two.text.toString()
                                val decimalTwoValue : Int = getDigitalTwoValue.toInt(16)
                                val decimalTwoIncrement = decimalTwoValue +1

                                digital_two.text = decimalTwoIncrement.toString(16)
                                valueIncrement = 0
                                digital_three.text = valueIncrement.toString(16)

                                if(decimalTwoIncrement== 16){
                                    val getDigitalOneValue = digital_one.text.toString()
                                    val decimalOneValue : Int = getDigitalOneValue.toInt(16)
                                    val decimalOneIncrement = decimalOneValue +1

                                    digital_one.text = decimalOneIncrement.toString(16)
                                    valueIncrement = 0
                                    digital_two.text = valueIncrement.toString(16)

                                }

                            }

                        }
                }
                val returnValueA :Int = getAddress()
                val valueInArrayWithAddress :String= addressReturnValue(returnValueA)
                digital_six.text = valueInArrayWithAddress.toString()
                opcodeView.text = " "
            //error print
            }
            else if (activeGoButton == 1){
                      val getAddress = getAddress()
                      var index :Int = getIndex(getAddress)
                      //val valueInIndexArray:String = addressArray[index]
                val text = "Program executed successfully"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

                     for (rangeValue in 1..1000){
                        if( addressArray[index] != "76") {
                          val valueInIndexArrayLoop: String = addressArray[index]
                          opCodeCheck(valueInIndexArrayLoop, index)
                            index += 1
                        }
                         else{
                             break
                         }
                    }
                 }

            else {
                digital_one.text = "e"
                digital_two.text = "r"
                digital_three.text = "r"
                val text = "Enter address between 2000 and 9000"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()


                digital_one.setTextColor(Color.parseColor("#FFE500"))
                digital_two.setTextColor(Color.parseColor("#FFE500"))
                digital_three.setTextColor(Color.parseColor("#FFE500"))
                digital_four.setTextColor(Color.parseColor("#FFE500"))
              //  digital_five.setTextColor(Color.parseColor("#FFE500"))
            }

        }

        //dcrButton function
        dcrButton.setOnClickListener(){
            if(digital_one.text != "0" && digital_one.text != "e" && digital_one.text != "s" ){
                inrFlag += 1


                if(inrFlag > 2){
                    valueIncrement += 1
                    val getDigitalFourValue = digital_four.text.toString()
                    val decimalFourValue : Int = getDigitalFourValue.toInt(16)
                    val decimalFourIncrement = decimalFourValue - 1
                    digital_four.text = decimalFourIncrement .toString(16)
                    //tens place increment
                    if(decimalFourIncrement < 0) {
                        val getDigitalThreeValue = digital_three.text.toString()
                        val decimalThreeValue : Int = getDigitalThreeValue.toInt(16)
                        val decimalThreeIncrement = decimalThreeValue -1
                        digital_three.text = decimalThreeIncrement.toString(16)
                        valueIncrement = 15
                        digital_four.text = valueIncrement.toString(16)
                        tenPlaceIncrement += 1
                        inrFlag = 4
                        if(decimalThreeIncrement < 0){
                            val getDigitalTwoValue = digital_two.text.toString()
                            val decimalTwoValue : Int = getDigitalTwoValue.toInt(16)
                            val decimalTwoIncrement = decimalTwoValue -1

                            digital_two.text = decimalTwoIncrement.toString(16)

                            digital_three.text = valueIncrement.toString(16)


                            if(decimalTwoIncrement < 0){
                                val getDigitalOneValue = digital_one.text.toString()
                                val decimalOneValue : Int = getDigitalOneValue.toInt(16)
                                val decimalOneIncrement = decimalOneValue -1


                                digital_one.text = decimalOneIncrement.toString(16)

                                digital_two.text = valueIncrement.toString(16)

                            }

                        }

                    }
                }
                val returnValueA :Int = getAddress()
                val valueInArrayWithAddress :String= addressReturnValue(returnValueA)
                digital_six.text = valueInArrayWithAddress.toString()
                opcodeView.text = " "

                //error print
            }else {
                digital_one.text = "e"
                digital_two.text = "r"
                digital_three.text = "r"

                digital_one.setTextColor(Color.parseColor("#FFE500"))
                digital_two.setTextColor(Color.parseColor("#FFE500"))
                digital_three.setTextColor(Color.parseColor("#FFE500"))
                digital_four.setTextColor(Color.parseColor("#FFE500"))
              //  digital_five.setTextColor(Color.parseColor("#FFE500"))
            }

        }




        //reset button
        reset_button.setOnClickListener() {
            val reset_val = reset()
            inrFlag = 1
            valueIncrement = 1
            inrFlag = 1
            valueIncrement= 0
            tenPlaceIncrement = 1
            textChangeColorFlag = 0
            activeGoButton = 0
            textColorChange()
            digital_one.text = reset_val.toString()
            digital_two.text = reset_val.toString()
            digital_three.text = reset_val.toString()
            digital_four.text = reset_val.toString()
            opcodeView.text = " "
            digital_six.text = "00"



        }

        button_zero.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(80)
            if (inrFlag == 1){
                val return_zero = zero_button()
                addressPlaceChange()
                digital_four.text = return_zero.toString()
             //   var vibe :Vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as Vibrator
               // vibe.vibrate(100)

            }
            else{
                val return_zero = zero_button().toString()
                storeToAddress(return_zero)

            }

        }

        button_one.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val return_one = one_button()
                addressPlaceChange()
                digital_four.text = return_one.toString()
            }else{
                val return_one = one_button().toString()
                storeToAddress(return_one)

            }

        }
        button_two.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag==1){
                val return_two = two_button()
                addressPlaceChange()
                digital_four.text = return_two.toString()
            }else{
                val return_two = two_button().toString()
                storeToAddress(return_two)

            }
        }
        button_three.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1) {
                val return_three = three_button()
                addressPlaceChange()
                digital_four.text = return_three.toString()
            }else{
                val return_three = three_button().toString()
                storeToAddress(return_three)

            }
        }

        button_four.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag==1){
                val return_four = four_button()
                addressPlaceChange()
                digital_four.text = return_four.toString()
            }else{
                val return_four = four_button().toString()
                storeToAddress(return_four)

            }

        }
        button_five.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag==1){
                val return_five = five_button()
                addressPlaceChange()
                digital_four.text = return_five.toString()
            }else{
                val return_five = five_button().toString()
                storeToAddress(return_five)

            }

        }
        button_six.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if(inrFlag==1){
                val return_six = six_button()
                addressPlaceChange()
                digital_four.text = return_six.toString()
            }else{
                val return_six = six_button().toString()
                storeToAddress(return_six)

            }

        }
        button_seven.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag==1){
                val return_seven = seven_button()
                addressPlaceChange()
                digital_four.text = return_seven.toString()
            }else{
                val return_seven = seven_button().toString()
                storeToAddress(return_seven)

            }

        }
        button_eight.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if(inrFlag==1){
                val return_eight = eight_button()
                addressPlaceChange()
                digital_four.text = return_eight.toString()
            }else{
                val return_eight = eight_button().toString()
                storeToAddress(return_eight)

            }

        }
        button_nine.setOnClickListener() {
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if(inrFlag == 1){
                val return_nine = nine_button()
                addressPlaceChange()
                digital_four.text = return_nine.toString()
            }else{
                val return_nine = nine_button().toString()
                storeToAddress(return_nine)

            }
            }
        buttonA.setOnClickListener(){
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val returnA = aButton()
                addressPlaceChange()
                digital_four.text = returnA.toString(16)

            }else{
                val returnA = aButton().toString(16)
                storeToAddress(returnA)

            }
        }
        buttonB.setOnClickListener(){
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val returnB = bButton()
                addressPlaceChange()
                digital_four.text = returnB.toString(16)

            }else{
                val returnB = bButton().toString(16)
                storeToAddress(returnB)

            }
        }
        buttonC.setOnClickListener(){
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val returnC = cButton()
                addressPlaceChange()
                digital_four.text = returnC.toString(16)

            }else{
                val returnC = cButton().toString(16)
                storeToAddress(returnC)

            }
        }
        buttonD.setOnClickListener(){
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val returnD = dButton()
                addressPlaceChange()
                digital_four.text = returnD.toString(16)

            }else{
                val returnD = dButton().toString(16)
                storeToAddress(returnD)

            }
        }
        buttonE.setOnClickListener(){
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val returnE = eButton()
                addressPlaceChange()
                digital_four.text = returnE.toString(16)

            }else{
                val returnE= eButton().toString(16)
                storeToAddress(returnE)

            }
        }
        buttonF.setOnClickListener(){
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(70)
            if (inrFlag == 1){
                val returnF = fButton()
                addressPlaceChange()
                digital_four.text = returnF.toString(16)

            }else{
                val returnF= fButton().toString(16)
                storeToAddress(returnF)

            }
        }




        }



    fun reset(): Int {
        return 0
    }


        private fun zero_button(): Int{
            return 0
        }

        private fun one_button(): Int {
            return 1
        }

        private fun two_button(): Int {
            return 2
        }

        private fun three_button(): Int {
            return 3
        }

        private fun four_button(): Int {
            return 4
        }

        private fun five_button(): Int {
            return 5
        }

        private fun six_button(): Int {
            return 6
        }

        private fun seven_button(): Int {
            return 7
        }

        private fun eight_button(): Int {
            return 8
        }

        private fun nine_button(): Int {
            return 9
        }

       private fun aButton(): Int {
           return 10
       }

       private fun bButton(): Int {
        return 11
       }

       private fun cButton(): Int {
          return 12
        }

      private fun dButton(): Int {
        return 13
      }

      private fun eButton(): Int {
        return 14
      }

      private fun fButton(): Int {
        return 15
     }

}