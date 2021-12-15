package com.olacompany.boom.tools

import com.olacompany.boom.tools.HexTool
import java.lang.StringBuilder
import java.lang.Exception
import java.io.ByteArrayOutputStream
import com.olacompany.boom.tools.ByteArrayByteStream
import java.nio.charset.StandardCharsets
import android.graphics.Point
import java.io.IOException
import kotlin.jvm.JvmOverloads
import com.olacompany.boom.tools.LittleEndianWriter
import java.nio.charset.Charset
import com.olacompany.boom.tools.ByteInputStream
import kotlin.Throws

class LittleEndianReader(private val bs: ByteArrayByteStream) {
    val byteArray: ByteArray
        get() = bs.byteArray

    fun readByte(): Byte {
        return bs.readByte().toByte()
    }

    fun readByteToInt(): Int {
        return bs.readByte()
    }

    fun readInt(): Int {
        val byte1 = bs.readByte()
        val byte2 = bs.readByte()
        val byte3 = bs.readByte()
        val byte4 = bs.readByte()
        return (byte4 shl 24) + (byte3 shl 16) + (byte2 shl 8) + byte1
    }

    fun readShort(): Short {
        val byte1 = bs.readByte()
        val byte2 = bs.readByte()
        return ((byte2 shl 8) + byte1).toShort()
    }

    fun readUShort(): Int {
        var quest = readShort().toInt()
        if (quest < 0) { //questid 50000 and above, WILL cast to negative, this was tested.
            quest += 65536 //probably not the best fix, but whatever
        }
        return quest
    }

    fun readChar(): Char {
        return readShort().toChar()
    }

    fun readLong(): Long {
        val byte1 = bs.readByte().toLong()
        val byte2 = bs.readByte().toLong()
        val byte3 = bs.readByte().toLong()
        val byte4 = bs.readByte().toLong()
        val byte5 = bs.readByte().toLong()
        val byte6 = bs.readByte().toLong()
        val byte7 = bs.readByte().toLong()
        val byte8 = bs.readByte().toLong()
        return ((byte8 shl 56) + (byte7 shl 48) + (byte6 shl 40) + (byte5 shl 32) + (byte4 shl 24) + (byte3 shl 16)
                + (byte2 shl 8) + byte1)
    }

    fun readFloat(): Float {
        return java.lang.Float.intBitsToFloat(readInt())
    }

    fun readDouble(): Double {
        return java.lang.Double.longBitsToDouble(readLong())
    }

    fun readAsciiString(n: Int): String {
        val ret = ByteArray(n)
        for (x in 0 until n) {
            ret[x] = readByte()
        }
        return String(ret, StandardCharsets.UTF_16)
    }

    fun readLengthAsciiString(): String {
        return readAsciiString(readShort().toInt())
    }

    fun readPos(): Point {
        val x = readShort().toInt()
        val y = readShort().toInt()
        return Point(x, y)
    }

    fun readIntPos(): Point {
        val x = readInt()
        val y = readInt()
        return Point(x, y)
    }

    fun read(num: Int): ByteArray {
        val ret = ByteArray(num)
        for (x in 0 until num) {
            ret[x] = readByte()
        }
        return ret
    }

    fun available(): Long {
        return bs.available()
    }

    override fun toString(): String {
        return bs.toString()
    }

    fun toString(b: Boolean): String {
        return bs.toString(b)
    }

    fun seek(offset: Long) {
        try {
            bs.seek(offset)
        } catch (e: IOException) {
            System.err.println("Seek failed$e")
        }
    }

    fun getPosition(): Long{
        return bs.getPosition()
    }

}