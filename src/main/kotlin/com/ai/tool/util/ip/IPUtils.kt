package com.ai.tool.util.ip
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetAddress
import java.net.NetworkInterface

class IPUtils {
    companion object {
        /**
         * 检查给定的 IP 地址是否有效
         */
        @JvmStatic
        fun isValidIPAddress(ipAddress: String): Boolean {
            return try {
                InetAddress.getByName(ipAddress)
                true
            } catch (e: Exception) {
                false
            }
        }

        /**
         * 获取本机的 IP 地址
         */
        fun getLocalIPAddress(): String {
            return InetAddress.getLocalHost().hostAddress
        }

        /**
         * 将 IP 地址转换为长整型数字表示
         */
        fun ipToLong(ipAddress: String): Long {
            val inetAddress = InetAddress.getByName(ipAddress)
            val bytes = inetAddress.address
            var result: Long = 0
            for (i in bytes.indices) {
                result = result shl 8 or (bytes[i].toInt() and 0xFF).toLong()
            }
            return result
        }

        /**
         * 将长整型数字表示的 IP 转换为字符串表示
         */
        fun longToIP(ip: Long): String {
            return (ip and 0xFF).toString() + "." +
                    (ip shr 8 and 0xFF) + "." +
                    (ip shr 16 and 0xFF) + "." +
                    (ip shr 24 and 0xFF)
        }

        /**
         * 验证 IP 地址格式是否合法
         */
        fun isLegalIPAddress(ipAddress: String): Boolean {
            val pattern = Regex(
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
            )
            return pattern.matches(ipAddress)
        }

        /**
         * 将 IP 地址转换为十进制表示形式
         */
        fun ipToDecimal(ipAddress: String): Long {
            val ipParts = ipAddress.split(".")
            var result: Long = 0
            for (i in 0 until 4) {
                result = result or (ipParts[i].toLong() shl (24 - i * 8))
            }
            return result
        }

        /**
         * 将十进制表示形式的 IP 地址转换为字符串表示形式
         */
        fun decimalToIP(decimal: Long): String {
            val ipParts = mutableListOf<String>()
            for (i in 0 until 4) {
                ipParts.add((decimal shr (24 - i * 8) and 0xFF).toString())
            }
            return ipParts.joinToString(".")
        }

        /**
         * 判断 IP 地址是否为合法的 IPv4 地址
         */
        fun isValidIPv4(ipAddress: String): Boolean {
            val parts = ipAddress.split(".")
            if (parts.size != 4) return false
            for (part in parts) {
                val num = part.toIntOrNull()
                if (num == null || num < 0 || num > 255) {
                    return false
                }
            }
            return true
        }

        /**
         * 判断 IP 地址是否为合法的 IPv6 地址
         */
        fun isValidIPv6(ipAddress: String): Boolean {
            val parts = ipAddress.split(":")
            if (parts.size != 8) return false
            for (part in parts) {
                if (!part.matches("[0-9a-fA-F]{1,4}".toRegex())) {
                    return false

                }
            }
            return true
        }

        /**
         * 获取本机的 IP 地址列表
         */
        fun getLocalIPs(): List<String> {
            val ipAddresses = mutableListOf<String>()
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface = networkInterfaces.nextElement()
                val inetAddresses = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    val inetAddress = inetAddresses.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress) {
                        ipAddresses.add(inetAddress.hostAddress)
                    }
                }
            }
            return ipAddresses
        }

        fun getPublicIPAddress(): String? {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.ipify.org?format=json")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return null
                }
                val responseBody = response.body?.string() ?: return null
                return responseBody.split("\"")[3]
            }
        }
    }
}
