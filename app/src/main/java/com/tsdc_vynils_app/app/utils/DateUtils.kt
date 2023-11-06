package com.tsdc_vynils_app.app.utils
import java.util.Date
import java.text.SimpleDateFormat

object DateUtils {
    fun CustomDateFormat(dateString: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
        val month:String = getNameMonth(SimpleDateFormat("MM").format(date).toInt())
        val day: String = SimpleDateFormat("dd").format(date)
        val year:String = SimpleDateFormat("yyyy").format(date)

        return "$day $month de $year"
    }

    private fun getNameMonth(numberMonth: Int): String {
        return when (numberMonth) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> ""
        }
    }
}