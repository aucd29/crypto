package net.sarangnamu.common.crypto

import android.text.TextUtils
import org.slf4j.LoggerFactory
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 2.. <p/>
 */

class Crypto {
    private val log = LoggerFactory.getLogger(Cipher::class.java)

    var publicKey: PublicKey? = null
    var privateKey: PrivateKey? = null

    fun execute(params: Params): String? {
        try {
            val cipher  = Cipher.getInstance(params.type)
            val keySpec = SecretKeySpec(params.key.toByteArray(), params.type)

            params.iv?.let {
                cipher.init(params.mode, keySpec, IvParameterSpec(it.toByteArray()))
            } ?: cipher.init(params.mode, keySpec)

            when (params.mode) {
                Cipher.ENCRYPT_MODE -> {
                    return cipher.doFinal(params.data.toByteArray()).toHexString()
                }

                Cipher.DECRYPT_MODE -> {
                    return cipher.doFinal(params.data.hexStringToByteArray()).toString(charset("UTF-8"))
                }
            }
        } catch (e: Exception) {
            log.error("ERROR: ${e.message}")
        }

        return null
    }

    class Params {
        lateinit var type: String
        lateinit var data: String
        lateinit var key : String

        var mode : Int = Cipher.ENCRYPT_MODE
        var iv: String? = null

        fun des() {
            type = "DES"
        }

        fun blowfish() {
            type = "Blowfish"
        }

        fun rsa() {
            type = "RSA";
        }

        fun decrypt() {
            mode = Cipher.DECRYPT_MODE
        }

        fun encrypt() {
            mode = Cipher.ENCRYPT_MODE
        }
    }
}

// https://gist.github.com/fabiomsr/845664a9c7e92bafb6fb0ca70d4e44fd
// https://www.programiz.com/kotlin-programming/examples/convert-byte-array-hexadecimal

private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

fun ByteArray.toHexString(): String? {
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}

fun String.hexStringToByteArray(): ByteArray? {
    if (TextUtils.isEmpty(this)) {
        return null
    }

    val result = ByteArray(length / 2)

    for (i in 0 until length step 2) {
        val firstIndex = HEX_CHARS.indexOf(this[i]);
        val secondIndex = HEX_CHARS.indexOf(this[i + 1]);

        val octet = firstIndex.shl(4).or(secondIndex)
        result.set(i.shr(1), octet.toByte())
    }

    return result
}
