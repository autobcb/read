package web.util.hash

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets

class EncryptUtils {
    companion object {
        private const val keyStr = "jhznhuanbznjuyqa"
        private const val ivStr = "jnzhyavblkjhsquy"

        // AES 加密
        fun aesEncode(content: String): String {
            val key = SecretKeySpec(keyStr.toByteArray(StandardCharsets.UTF_8), "AES")
            val iv = IvParameterSpec(ivStr.toByteArray(StandardCharsets.UTF_8))

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)

            val encrypted = cipher.doFinal(content.toByteArray(StandardCharsets.UTF_8))
            return bytesToHex(encrypted)
        }

        // AES 解密
        fun aesDecrypted(hexData: String): String {
            val key = SecretKeySpec(keyStr.toByteArray(StandardCharsets.UTF_8), "AES")
            val iv = IvParameterSpec(ivStr.toByteArray(StandardCharsets.UTF_8))

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, key, iv)

            val encryptedData = hexToBytes(hexData)
            val decrypted = cipher.doFinal(encryptedData)
            return String(decrypted, StandardCharsets.UTF_8)
        }

        // 字节数组转十六进制字符串
        private fun bytesToHex(bytes: ByteArray): String {
            val hexString = StringBuilder()
            for (b in bytes) {
                hexString.append(String.format("%02X", b.toInt() and 0xFF))
            }
            return hexString.toString()
        }

        // 十六进制字符串转字节数组
        private fun hexToBytes(hex: String): ByteArray {
            val len = hex.length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((Character.digit(hex[i], 16) shl 4) +
                        Character.digit(hex[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }
    }
}
