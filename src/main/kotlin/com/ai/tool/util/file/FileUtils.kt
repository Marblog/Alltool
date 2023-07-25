package com.ai.tool.util.file

import java.io.*
import java.nio.file.Files

class FileUtils {
    companion object {
        /**
         * 创建文件夹
         */
        fun createDirectory(directoryPath: String): Boolean {
            val directory = File(directoryPath)
            return if (directory.exists()) {
                true // 文件夹已存在，无需创建
            } else {
                try {
                    directory.mkdirs()
                    true // 创建文件夹成功
                } catch (e: SecurityException) {
                    e.printStackTrace()
                    false // 创建文件夹失败
                }
            }
        }

        /**
         * 创建文件626aef
         */
        fun createFile(filePath: String): Boolean {
            val file = File(filePath)
            return if (file.exists()) {
                true // 文件已存在，无需创建
            } else {
                try {
                    file.createNewFile()
                    true // 创建文件成功
                } catch (e: IOException) {
                    e.printStackTrace()
                    false // 创建文件失败
                }
            }
        }

        /**
         * 删除文件或文件夹
         */
        fun deleteFile(filePath: String): Boolean {
            val file = File(filePath)
            return if (!file.exists()) {
                true // 文件或文件夹不存在，无需删除
            } else {
                try {
                    file.deleteRecursively()
                    true // 删除文件或文件夹成功
                } catch (e: SecurityException) {
                    e.printStackTrace()
                    false // 删除文件或文件夹失败
                }
            }
        }

        /**
         * 拷贝文件
         */
        fun copyFile(sourcePath: String, destinationPath: String): Boolean {
            val sourceFile = File(sourcePath)
            val destinationFile = File(destinationPath)
            return try {
                sourceFile.copyTo(destinationFile, true)
                true // 拷贝文件成功
            } catch (e: IOException) {
                e.printStackTrace()
                false // 拷贝文件失败
            }
        }

        /**
         * 判断文件或文件夹是否存在
         */
        fun exists(filePath: String): Boolean {
            val file = File(filePath)
            return file.exists()
        }

        /**
         * 获取文件大小
         */
        fun getFileSize(filePath: String): Long {
            val file = File(filePath)
            return file.length()
        }

        /**
         * 获取文件扩展名
         */
        fun getFileExtension(filePath: String): String {
            val file = File(filePath)
            val fileName = file.name
            val dotIndex = fileName.lastIndexOf(".")
            return if (dotIndex > 0 && dotIndex < fileName.length - 1) {
                fileName.substring(dotIndex + 1)
            } else {
                ""
            }
        }

        /**
         * 获取文件的父目录路径
         */
        @JvmStatic
        fun getParentDirectory(filePath: String): String {
            val file = File(filePath)
            val parentFile = file.parentFile
            return parentFile?.absolutePath ?: ""
        }

        /**
         * 重命名文件或文件夹
         */
        fun renameFile(oldFilePath: String, newFileName: String): Boolean {
            val oldFile = File(oldFilePath)
            val newFilePath = "${getParentDirectory(oldFilePath)}/$newFileName"
            val newFile = File(newFilePath)
            return if (oldFile.exists()) {
                try {
                    Files.move(oldFile.toPath(), newFile.toPath())
                    true // 重命名成功
                } catch (e: IOException) {
                    e.printStackTrace()
                    false // 重命名失败
                }
            } else {
                false // 文件或文件夹不存在，无法重命名
            }
        }

        /**
         * 读取文件内容
         */
        fun readFile(filePath: String): String {
            val file = File(filePath)
            val stringBuilder = StringBuilder()
            try {
                BufferedReader(FileReader(file)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        stringBuilder.append(line)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }

        /**
         * 写入文件内容
         */
        fun writeFile(filePath: String, content: String): Boolean {
            val file = File(filePath)
            return try {
                BufferedWriter(FileWriter(file)).use { writer ->
                    writer.write(content)
                }
                true // 写入成功
            } catch (e: IOException) {
                e.printStackTrace()
                false // 写入失败
            }
        }
    }
}
