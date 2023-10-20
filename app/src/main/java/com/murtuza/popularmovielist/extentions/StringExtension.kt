package com.murtuza.popularmovielist.extentions

import com.Constant.K
import com.Constant.K.Numbers.ONE
import com.Constant.K.StringBaseUtil.COUNTRY
import com.Constant.K.StringBaseUtil.DOT
import com.Constant.K.StringBaseUtil.LANGUAGE_CODE
import org.apache.commons.lang3.math.NumberUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */
fun <T> T?.convertToSafeBigDecimal(): BigDecimal {
    this?.let { stringValue ->
        val decimal = stringValue.toString().toBigDecimalOrNull()
        val correctValue = decimal?.let {
            decimal
        } ?: kotlin.run {
            stringValue.toString().extractNumbers().ifEmpty { NumberUtils.DOUBLE_ZERO.toString() }.toBigDecimal()
        }

        return correctValue

    } ?: kotlin.run {
        return NumberUtils.DOUBLE_ZERO.toBigDecimal()
    }
}

fun String?.thisOrNA(): String {
    return if (this.isNullOrEmpty())
        K.StringBaseUtil.NOT_APPLICABLE
    else
        this
}

fun String.removeLast(): String {
    return this.lines().joinToString("\n") { it -> it.dropLastWhile {  !it.isLetter() } }
}

fun String.checkEmpty(): String {
    return this.lines().joinToString("\n") {
        (if(it.isNotEmpty()){

        } else {

        }).toString()
    }
}

fun <T> T?.convertToSafeBigDecimalString(): String {
    this?.let { stringValue ->
        return stringValue.convertToSafeBigDecimal().toPlainString()
    } ?: kotlin.run {
        return NumberUtils.DOUBLE_ZERO.toBigDecimal().toPlainString()
    }
}

fun <T> T?.convertToSafeDouble(): Double {
    this?.let { stringValue ->
        val decimal = stringValue.toString().toBigDecimalOrNull()
        decimal?.let {
            return decimal.toDouble()
        } ?: kotlin.run {
            return stringValue.toString().extractNumbers().ifEmpty { NumberUtils.DOUBLE_ZERO.toString() }.toDouble()
        }
    } ?: kotlin.run {
        return NumberUtils.DOUBLE_ZERO
    }
}

fun <T> T?.formatTextToCurrency(decimal: Int = 6, autoDecimal: Boolean = true): String {
    this?.let { incomingValue ->

        val numberFormat = NumberFormat.getCurrencyInstance(Locale(LANGUAGE_CODE, COUNTRY))

        val numberString = incomingValue.toString().convertToSafeBigDecimalString()

        if (autoDecimal) {
            val specialCountList = numberString.split(DOT)

            val specialCount = if (specialCountList.size <= 1)
                NumberUtils.INTEGER_ZERO
            else {

                val checkForZero = specialCountList.getOrNull(1)?.take(decimal)
                val areAllZeros = checkForZero?.all { number -> number == NumberUtils.INTEGER_ZERO.toChar() }

                if (areAllZeros == true)
                    NumberUtils.INTEGER_ZERO
                else
                    checkForZero?.length
            }

            numberFormat.maximumFractionDigits = specialCount.orZero()
        } else
            numberFormat.maximumFractionDigits = decimal

        numberFormat.roundingMode = getCorrectRoundingMethod(incomingValue.convertToSafeDouble())

        val formattedNumberList = numberFormat.format(numberString.convertToSafeDouble()).split(DOT)

        val formattedNumber = StringBuilder()

        formattedNumberList.firstOrNull()?.let { firstNumber ->
            formattedNumber.append(firstNumber)
        }

        if (formattedNumberList.size > 1) {
            formattedNumber.append(DOT)
            formattedNumber.append(formattedNumberList.getOrNull(1)?.dropLastWhile
            { number -> number.toString() == NumberUtils.INTEGER_ZERO.toString() })
        }

        val finale = formattedNumber.toString().dropLastWhile { number -> number.toString() == DOT }

        return finale

    } ?: kotlin.run {
        return K.StringBaseUtil.NOT_APPLICABLE
    }
}

private fun getCorrectRoundingMethod(incomingValue: Double): RoundingMode {
    return if (abs(incomingValue) < ONE) {
        // Set the rounding mode for numbers less than 1
        RoundingMode.HALF_EVEN
    } else {
        // Set the rounding mode for numbers greater than or equal to 1
        RoundingMode.DOWN
    }
}

fun Int?.orZero(): Int {
    return this ?: NumberUtils.INTEGER_ZERO
}

fun String?.extractNumbers(): String {
    return this?.filter {
        it.isDigit() || it.toString() == DOT
    }?.dropLastWhile { number -> number.toString() == DOT }.orEmpty()
}
