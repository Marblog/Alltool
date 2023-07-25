package com.example.card.util

import java.util.*

class GenerateRandomCode {

    companion object{
        fun generateRandomCode(): String {
            val chars = "0123456789X"
            val random = Random()
            val firstDigit = random.nextInt(10).toString()
            val secondDigit = random.nextInt(10).toString()
            val thirdDigit = random.nextInt(10).toString()
            val lastChar = if (random.nextInt(8) == 0) "X" else chars[random.nextInt(chars.length)].toString()
            return "$firstDigit$secondDigit$thirdDigit$lastChar"
        }
    }

}