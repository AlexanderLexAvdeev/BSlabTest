package ru.bslab.test.avdeevav.utility

class CreditsUtil {

    companion object {

        private const val COMMA_RANGE = 3

        fun getFormattedString(credits: Int): String {

            return "%.${COMMA_RANGE}f"
                    .format(credits.toDouble() / Math.pow(10.0, COMMA_RANGE.toDouble()))
                    .replace(".", ",")
        }
    }
}