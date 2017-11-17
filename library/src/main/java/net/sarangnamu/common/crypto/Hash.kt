package net.sarangnamu.common.crypto

import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 2.. <p/>
 */

private val MD5 = "MD5"
private val SHA1 = "SHA-1"
private val SHA256 = "SHA-256"
private val SHA512 = "SHA-512"

////////////////////////////////////////////////////////////////////////////////////
//
// File
//
////////////////////////////////////////////////////////////////////////////////////

fun File.md5():String? {
    return toHashString(MD5)
}

fun File.sha1(): String ? {
    return toHashString(SHA1)
}

fun File.sha256(): String? {
    return toHashString(SHA256)
}

fun File.sha512(): String? {
    return toHashString(SHA512)
}

fun File.toHashString(type: String): String? {
    return FileInputStream(this).toHashString(type)
}

////////////////////////////////////////////////////////////////////////////////////
//
// FileInputStream
//
////////////////////////////////////////////////////////////////////////////////////

fun FileInputStream.md5():String? {
    return toHashString(MD5)
}

fun FileInputStream.sha1(): String ? {
    return toHashString(SHA1)
}

fun FileInputStream.sha256(): String? {
    return toHashString(SHA256)
}

fun FileInputStream.sha512(): String? {
    return toHashString(SHA512)
}

fun FileInputStream.toHashString(type: String): String? {
    use { ism ->
        val md   = MessageDigest.getInstance(type)
        val buff = ByteArray(DEFAULT_BUFFER_SIZE)
        var size = ism.read(buff)

        while (size != -1) {
            md.update(buff, 0, size)
            size = ism.read(buff)
        }

        return md.digest().toHexString()
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// String
//
////////////////////////////////////////////////////////////////////////////////////

fun String.md5(): String? {
    return toHashString(MD5)
}

fun String.sha1(): String ? {
    return toHashString(SHA1)
}

fun String.sha256(): String? {
    return toHashString(SHA256)
}

fun String.sha512(): String? {
    return toHashString(SHA512)
}

fun String.toHashString(type:String): String? {
    return MessageDigest.getInstance(type).digest(toByteArray(Charset.defaultCharset())).toHexString()
}

////////////////////////////////////////////////////////////////////////////////////
//
// ByteArray
//
////////////////////////////////////////////////////////////////////////////////////

fun ByteArray.md5(): String? {
    return toHashString(MD5)
}

fun ByteArray.sha1(): String ? {
    return toHashString(SHA1)
}

fun ByteArray.sha256(): String? {
    return toHashString(SHA256)
}

fun ByteArray.sha512(): String? {
    return toHashString(SHA512)
}

fun ByteArray.toHashString(type: String): String? {
    return MessageDigest.getInstance(type).digest(this).toHexString()
}