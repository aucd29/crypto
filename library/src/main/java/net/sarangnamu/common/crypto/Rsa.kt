package net.sarangnamu.common.crypto

import java.nio.ByteBuffer
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 17.. <p/>
 */

class Rsa() {
    private val TRANSFORMATION = "RSA/None/PKCS1Padding"
    private val RSA = "RSA"

    var privateKey: PrivateKey
    var publicKey: PublicKey

    init {
        val keyGen  = KeyPairGenerator.getInstance(RSA)
        val keyPair = keyGen.generateKeyPair()
        val publicKey  = keyPair.public
        val privateKey = keyPair.private
        val factory = KeyFactory.getInstance(RSA)
        this.privateKey = factory.generatePrivate(PKCS8EncodedKeySpec(privateKey.encoded))
        this.publicKey  = factory.generatePublic(X509EncodedKeySpec(publicKey.encoded))
    }

    fun publicKeyString(): String? {
        return this.publicKey.encoded.toHexString()
    }

    @Throws(Exception::class)
    fun decrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey)

        return cipher.doFinal(data)
    }
}