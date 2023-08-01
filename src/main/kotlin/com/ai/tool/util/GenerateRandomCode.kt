package com.ai.tool.util

import java.util.*

class GenerateRandomCode {

    companion object {

        fun generateRandomCode(generateEven: String): String {
            val random = Random()
            val firstDigit = random.nextInt(10).toString()
            val secondDigit = random.nextInt(10).toString()
            var thirdDigit = random.nextInt(10)
            if (generateEven == "1") {
                if (thirdDigit % 2 == 0) {
                    thirdDigit++
                }
            } else if (generateEven == "0") {
                if (thirdDigit % 2 != 0) {
                    thirdDigit = (thirdDigit + 1) % 10
                }
            }
            thirdDigit.toString()
            return "$firstDigit$secondDigit$thirdDigit"
        }

        fun generateIdCard(number17: String): String {
            val chars = "10X98765432"
            val weights = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
            if (number17.length != 17) {
                throw IllegalArgumentException("Invalid 17-digit number")
            }
            var sum = 0
            for (i in 0 until 17) {
                val num = number17[i].toString().toInt()
                sum += num * weights[i]
            }
            val index = sum % 11
            val lastChar = chars[index]
            return "$number17$lastChar"
        }

    }

}