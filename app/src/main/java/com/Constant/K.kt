
package com.Constant

import org.apache.commons.lang3.StringUtils
/**
 * Created by murtuza khalid saleem 03060824762 on 10/18/2023.
 */

object K {

    object Numbers {
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val HUNDRED = 100
        const val IMPORTANT_STATIC_DATA_SIZE = 5
        const val ONE_MILLIONTH = 0.000001
        const val ONE_MILLION = 1_000_000
        const val CLOSE_ONE_MILLION = 999_999.999999
        const val ONE_BILLION = 1_000_000_000
        const val CLOSE_ONE_BILLION = 999_999_999.999999
        const val ONE_TRILLION = 1_000_000_000_000
    }

    object NetworkState {
        const val Available = "Available"
        const val Unavailable = "Unavailable"
        const val Losing = "Losing"
        const val Lost = "Lost"
    }

    object Duration {
        const val NO_DURATION = 0L
        const val QUARTER_BLINK_OF_EYE = 75L
        const val HALF_BLINK_OF_EYE = 150L
        const val QUARTER_OF_A_SECOND = 250L
        const val BLINK_OF_EYE = 300L
        const val HALF_SECOND = 500L
        const val ONE_SECOND = 1000L
        const val ONE_HALF_SECOND = 1500L
        const val TWO_SECOND = 2000L
        const val THREE_SECOND = 3000L
        const val FIVE_SECOND = 5000L
        const val TEN_SECOND = 10000L
    }

    object StringBaseUtil {
        const val NOT_APPLICABLE = "N/A"
        const val UNDEFINED_VALUE = "UNDEFINED"
        const val COUNTRY = "US"
        const val LANGUAGE_CODE = "en"
        const val DOT = "."
        const val COLON = ":"
        const val EQUAL_SIGN = "="
        const val COMMA = ","
        const val OPERATOR_MINUS = "-"
        const val OPERATOR_PLUS = "+"
        const val FALSE = "false"
        const val TRUE = "true"
        const val NEW_LINE = StringUtils.LF
        const val USD = "$"
        const val OPEN_SQUARE_BRACKETS = "["
        const val OPEN_CURLY_BRACKETS = "{"
        const val OPEN_ROUND_BRACKETS = "("
        const val FORWARD_SLASH = "/"
        const val HORIZONTAL_TABULATION = "\t"
        const val ZERO_WIDTH_SPACE = "\u200B"
    }

}