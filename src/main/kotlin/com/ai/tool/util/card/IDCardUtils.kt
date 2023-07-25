package com.ai.tool.util.card

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
        fun parseIDCard(idCard: String): String? {
            if (!isValidIDCard(idCard)) {
                return null
            }
            val genderCode = idCard.substring(16, 17).toInt()
            return if (genderCode % 2 == 0) "女" else "男"
        }

        private fun extractBirthDateFromIdCard(idCard: String): Date {
            val birthDateString = idCard.substring(6, 14)
            val dateFormat = SimpleDateFormat("yyyyMMdd")
            return dateFormat.parse(birthDateString)
        }

        private fun calculateAge(birthDate: Date): Int {
            val now = Calendar.getInstance()
            val birthCalendar = Calendar.getInstance()
            birthCalendar.time = birthDate

            var age = now.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
            if (now.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            return age
        }

        // 辅助方法，根据身份证号码计算年龄
        fun calculateAgeFromIdCard(idCard: String): Int {
            val birthDate = extractBirthDateFromIdCard(idCard)
            return calculateAge(birthDate)
        }
    }
}
