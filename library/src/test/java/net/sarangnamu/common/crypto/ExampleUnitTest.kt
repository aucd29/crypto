package net.sarangnamu.common.crypto

import android.util.Log
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test_des() {
        System.out.println("==\ntest des\n==")

        val key  = "key!!!@@"   // des는 key 가 8개
        val data = "Hello World"
        val params = Crypto.Params().apply {
            des()
            key(key)
            this.data = data
        }

        val hex = Crypto().encrypt(params)
        System.out.println("encrypted : $hex")

        hex?.let {
            params.data = it
            val str = Crypto().decrypt(params)
            System.out.println("decrypted : ${str.toString()}")

            assertEquals(data, str)
        }
    }

    @Test
    fun test_blowfish() {
        System.out.println("==\ntest blowfish\n==")

        val data = "Hello World"
        val params = Crypto.Params().apply {
            blowfish()
            keyGen()

            this.data = data
        }

        System.out.println("generated key : ${params.key.encoded.toHexString()}")
        val hex = Crypto().encrypt(params)
        System.out.println("encrypted : $hex")

        hex?.let {
            params.data = it
            val str = Crypto().decrypt(params)
            System.out.println("decrypted : ${str.toString()}")

            assertEquals(data, str)
        }
    }

    @Test
    fun test_rsa() {
        System.out.println("==\ntest rsa\n==")

        val data = "Hello World"
        val params = Crypto.RsaParams().apply {
            this.data = data
        }

        val pub = params.publicKeyString()
        System.out.println("public key : ${pub}")

        val encrypted = Crypto().encrypt(params)
        System.out.println("encrypted : ${encrypted}")

        encrypted?.let {
            params.data = encrypted
            val decrypted = Crypto().decrypt(params)
            System.out.println("decrypted : ${decrypted}")

            assertEquals(decrypted, "Hello World")
        } ?: assert(true)
    }

    @Test
    fun test_aes() {
        System.out.println("==\ntest aes\n==")
        val data = "Hello World"
        val params = Crypto.Params().apply {
            aes()
            keyGen(128)

            this.data = data
        }

        System.out.println("generated key : ${params.key.encoded.toHexString()}")

        val encrypted = Crypto().encrypt(params)
        System.out.println("encrypted : $encrypted")

        encrypted?.let {
            params.data = it
            val decrypted = Crypto().decrypt(params)
            System.out.println("decrypted : ${decrypted}")

            assertEquals(decrypted, "Hello World")
        }
    }

    @Test
    fun test_md5() {
        System.out.println("==\ntest md5\n==")
        val data = "hello world"
        val md5Str = data.md5()
        System.out.println("md5 : $md5Str")

        assertEquals(md5Str, "5eb63bbbe01eeed093cb22bb8f5acdc3".toUpperCase())
    }

    @Test
    fun test_sha1() {
        System.out.println("==\ntest sha1\n==")

        val data = "hello world"
        val sha1 = data.sha1()
        System.out.println("sha1 : $sha1")

        assertEquals(sha1, "2aae6c35c94fcfb415dbe95f408b9ce91ee846ed".toUpperCase())
    }

    @Test
    fun test_sha256() {
        System.out.println("==\ntest sha-256\n==")

        val data = "hello world"
        val sha256 = data.sha256()
        System.out.println("sha256 : $sha256")

        assertEquals(sha256, "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9".toUpperCase())
    }
}
