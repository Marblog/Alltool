package com.ai.tool.util.image

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.util.*

class ImageUtils {
    companion object {
        /**
         * 将图片文件转换为 Base64 编码字符串
         */
        fun imageToBase64(filePath: String): String {
            val file = File(filePath)
            val inputStream = FileInputStream(file)
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } != -1) {
                outputStream.write(buffer, 0, length)
            }

            val base64Bytes = outputStream.toByteArray()
            val base64String = Base64.getEncoder().encodeToString(base64Bytes)

            inputStream.close()
            outputStream.close()

            return base64String
        }

        /**
         * 将 Base64 编码字符串转换为图片文件
         */
        fun base64ToImage(base64String: String, outputFilePath: String) {
            val base64Bytes = Base64.getDecoder().decode(base64String)
            val outputStream = File(outputFilePath).outputStream()

            outputStream.write(base64Bytes)

            outputStream.close()
        }
    }
}
