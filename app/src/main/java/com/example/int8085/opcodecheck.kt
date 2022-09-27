package com.example.int8085

import android.widget.Toast
import kotlin.math.E

public fun opCodeCheck(opcodeCheck:String, Index :Int) {
    when(opcodeCheck){
        "3e" -> mviA(Index)
        "06" -> mivB(Index)
        "0e" -> mivC(Index)
        "16" -> mivD(Index)
        "1e" -> mivE(Index)
        "47" -> movB()
        "4e" -> movC()
        "56" -> movDM()
        "7e" -> movM()
        "77" -> movMD()
        "80" -> addB()
        "86" -> addM()
        "90" -> subB()
        "32" -> sta(Index)
        "3a" -> lda(Index)
        "21" -> lxiH(Index)
        "23" -> inxH()
        "0d" -> dcrC()
        "c2" -> jnz(Index)
        "27" -> daa()


    }
}

fun getIndex(Index: Int):Int {
    val secondTwoDigits:String = addressArray[Index+1]
    val firstTwoDigits:String = addressArray[Index+2]
    val joinedArrayValue :String = firstTwoDigits+secondTwoDigits
    val intAddress :Int = joinedArrayValue.toInt(16)
    val getIndex =  intAddress - 8192

    return getIndex

}
fun movB() {
    b = accumulator

}
fun movC() {
    c = M
}

fun lda(Index: Int) {
    val getIndex=getIndex(Index)
    accumulator = addressArray[getIndex].toInt()
}

fun sta(Index: Int) {
    if (dAA==0) {
        val getIndex = getIndex(Index)
        addressArray[getIndex] = accumulator.toString(16)
    }else {
        val getIndex = getIndex(Index)
        addressArray[getIndex] = accumulator.toString()

    }


}

fun addB() {
  accumulator = accumulator + b

}
fun addM() {
    accumulator = accumulator + M
}
fun subB() {
    accumulator = accumulator - b
}

fun mivE(Index:Int) {
    val getData:String = addressArray[Index+1]
    e = getData.toInt()
}

fun mivD(Index:Int) {
    val getData:String = addressArray[Index+1]
    d = getData.toInt()
}

fun mivC(Index:Int) {
    val getData:String = addressArray[Index+1]
    c = getData.toInt()
}

fun mivB(Index:Int) {
    val getData:String = addressArray[Index+1]
    b = getData.toInt()
    addressArray[5] = b.toString(16)
}

fun mviA(Index:Int){
    val getData:String = addressArray[Index+1]
    accumulator = getData.toInt()

    addressArray[8] = accumulator.toString(16)
}
fun lxiH(Index: Int) {
    val getIndex=getIndex(Index)
    M = addressArray[getIndex].toInt()
    MincrementIndex = getIndex

}
fun movM() {
    accumulator = M

}
fun movMD() {
   M = accumulator
}

fun movDM() {
   d = M
}
fun inxH() {
    M = addressArray[MincrementIndex+1].toInt()
}
fun dcrC() {
    c -= 1
}
fun jnz(Index: Int) {
    val getIndex = getIndex(Index)
    var flagGetIndex = getIndex
    while (c>0) {
        opCodeCheck(addressArray[flagGetIndex], flagGetIndex)
        flagGetIndex += 1
    }
}
fun daa() {
    dAA = 1
}