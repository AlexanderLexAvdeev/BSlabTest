package ru.bslab.test.avdeevav.utility

class CurrencyUtil {

    companion object {

        private const val USD = "USD"
        private const val ERROR = "error"

        fun getFormattedString(title: String, currency: String): String {

            return when (currency) {
                USD -> title.toUsdString()
                else -> ERROR
            }
        }

        private fun String.toUsdString(): String {

            return "${this.substring(this.indexOf("$"), this.indexOf(" "))}.00"
        }
    }
}