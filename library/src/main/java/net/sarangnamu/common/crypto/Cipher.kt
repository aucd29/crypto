package net.sarangnamu.common.crypto

import android.text.TextUtils
import org.slf4j.LoggerFactory
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 2.. <p/>
 */

class Crypto {
    private val log = LoggerFactory.getLogger(Cipher::class.java)

    fun encrypt(params: Params): String? {
        try {
            val mode = Cipher.ENCRYPT_MODE

            params.init(mode)
            return params.execute(mode)
        } catch (e: Exception) {
            log.error("ERROR: ${e.message}")
        }

        return null
    }

    fun decrypt(params: Params): String? {
        try {
            val mode = Cipher.DECRYPT_MODE

            params.init(mode)
            return params.execute(mode)
        } catch (e: Exception) {
            log.error("ERROR: ${e.message}")
        }

        return null
    }

    open class Params {
        lateinit var cipher: Cipher
        lateinit var transformation: String
        lateinit var algorithm: String
        lateinit var data: String
        lateinit var key : Key

        var iv: String? = null

        fun des() {
            transformation = "DES"
            algorithm  = "DES"
        }

        fun blowfish() {
            transformation = "Blowfish"
            algorithm  = "Blowfish"
        }

        fun aes() {
            transformation = "AES/ECB/PKCS5Padding";
            algorithm  = "AES"
        }

        fun transformation(transformation: String) {
            this.transformation = transformation
        }

        fun keyGen(keySize: Int = 32) {
            val keyGen = KeyGenerator.getInstance(algorithm)
            keyGen.init(keySize, SecureRandom().apply { nextInt() })

            key = keyGen.generateKey()
        }

        open fun key(key: String? = null) {
            key?.let {
                this.key = SecretKeySpec(it.toByteArray(), algorithm)
            }
        }

        open fun init(mode: Int) {
            cipher = Cipher.getInstance(transformation)

            iv?.let {
                cipher.init(mode, key, IvParameterSpec(it.toByteArray()))
            } ?: this.cipher.init(mode, key)
        }

        fun execute(mode: Int): String? {
            when (mode) {
                Cipher.ENCRYPT_MODE ->
                    return cipher.doFinal(data.toByteArray()).toHexString()

                Cipher.DECRYPT_MODE ->
                    return cipher.doFinal(data.hexStringToByteArray()).toString(charset("UTF-8"))
            }

            return null
        }
    }

    class RsaParams : Params() {
        var privateKey: PrivateKey
        var publicKey: PublicKey

        init {
            this.transformation = "RSA/ECB/PKCS1Padding";
            this.algorithm      = "RSA";

            val keyGen     = KeyPairGenerator.getInstance(algorithm)
            val keyPair    = keyGen.generateKeyPair()
            val publicKey  = keyPair.public
            val privateKey = keyPair.private
            val factory    = KeyFactory.getInstance(algorithm)

            this.privateKey = factory.generatePrivate(PKCS8EncodedKeySpec(privateKey.encoded))
            this.publicKey  = factory.generatePublic(X509EncodedKeySpec(publicKey.encoded))
        }

        override fun init(mode: Int) {
            cipher = Cipher.getInstance(transformation)

            when (mode) {
                Cipher.ENCRYPT_MODE -> this.key = this.publicKey
                Cipher.DECRYPT_MODE -> this.key = this.privateKey
            }

            cipher.init(mode, this.key)
        }

        override fun key(key: String?) {

        }

        fun publicKeyString(): String? {
            return this.publicKey.encoded.toHexString()
        }

        fun publicKey(publicKey: PublicKey) {
            this.publicKey = publicKey
        }

        fun publicKey(publicKey: String) {
            val keySpec = X509EncodedKeySpec(publicKey.hexStringToByteArray())
            this.publicKey = KeyFactory.getInstance(algorithm).generatePublic(keySpec)
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
