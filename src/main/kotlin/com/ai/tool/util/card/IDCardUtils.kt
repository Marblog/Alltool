package com.example.card.util.card

import java.text.SimpleDateFormat
import java.util.*

class IDCardUtils {
    companion object {
        /**
         * 验证身份证号码的合法性
         */
        @JvmStatic
        fun isValidIDCard(idCard: String): Boolean {
            if (idCard.length != 18) {
                return false
            }
            val idCardWi = arrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
            val idCardY = arrayOf('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2')
            var sum = 0
            for (i in 0 until 17) {
                val ai = idCard[i].toString().toInt()
                val wi = idCardWi[i]
                sum += ai * wi
            }
            val y = idCardY[sum % 11]
            return idCard[17].uppercaseChar() == y
        }

        /**
         * 解析身份证号码获取出生日期和性别
         */
        fun parseIDCard(idCard: String): Pair<Date, String>? {
            if (!isValidIDCard(idCard)) {
                return null
            }
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val birthDate = dateFormat.parse(idCard.substring(6, 14))

            val genderCode = idCard.substring(16, 17).toInt()
            val gender = if (genderCode % 2 == 0) "女" else "男"
            return Pair(birthDate, gender)
        }
    }
}
