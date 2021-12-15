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

class LittleEndianWriter @JvmOverloads constructor(size: Int = 32) {
    private val baos: ByteArrayOutputStream
    val packet: ByteArray
        get() = baos.toByteArray()

    fun write(b: Boolean) {
        baos.write(if (b) 1 else 0)
    }

    fun write(b: ByteArray) {
        for (x in b.indices) {
            baos.write(b[x] as ByteArray)
        }
    }

    fun write(b: Int) {
        if (b != -88888) {
            baos.write(b)
        }
    }

    fun writeShort(i: Int) {
        baos.write(i and 0xFF)
        baos.write(i ushr 8 and 0xFF)
    }

    fun writeInt(i: Int) {
        if (i != -88888) {
            baos.write(i and 0xFF)
            baos.write(i ushr 8 and 0xFF)
            baos.write(i ushr 16 and 0xFF)
            baos.write(i ushr 24 and 0xFF)
        }
    }

    fun writeInt(i: Long) {
        baos.write((i and 0xFF) as Int)
        baos.write((i ushr 8 and 0xFF) as Int)
        baos.write((i ushr 16 and 0xFF) as Int)
        baos.write((i ushr 24 and 0xFF) as Int)
    }

    fun writeAsciiString(s: String) {
        write(s.toByteArray(ASCII))
    }

    fun writeAsciiString(s: String, max: Int) {
        var s = s
        if (s.toByteArray(ASCII).size > max) {
            s = s.substring(0, max)
        }
        write(s.toByteArray(ASCII))
        for (i in s.toByteArray(ASCII).size until max) {
            write(0)
        }
    }

    fun writeLengthAsciiString(s: String) {
        writeShort(s.toByteArray(ASCII).size as Int)
        writeAsciiString(s)
    }

    fun writePos(s: Point) {
        writeShort(s.x)
        writeShort(s.y)
    }

    fun writePosInt(s: Point) {
        writeInt(s.x)
        writeInt(s.y)
    }

    fun writeLong(l: Long) {
        baos.write((l and 0xFF) as Int)
        baos.write((l ushr 8 and 0xFF) as Int)
        baos.write((l ushr 16 and 0xFF) as Int)
        baos.write((l ushr 24 and 0xFF) as Int)
        baos.write((l ushr 32 and 0xFF) as Int)
        baos.write((l ushr 40 and 0xFF) as Int)
        baos.write((l ushr 48 and 0xFF) as Int)
        baos.write((l ushr 56 and 0xFF) as Int)
    }

    fun writeReversedLong(l: Long) {
        baos.write((l ushr 32 and 0xFF) as Int)
        baos.write((l ushr 40 and 0xFF) as Int)
        baos.write((l ushr 48 and 0xFF) as Int)
        baos.write((l ushr 56 and 0xFF) as Int)
        baos.write((l and 0xFF) as Int)
        baos.write((l ushr 8 and 0xFF) as Int)
        baos.write((l ushr 16 and 0xFF) as Int)
        baos.write((l ushr 24 and 0xFF) as Int)
    }

    companion object {
        private val ASCII = StandardCharsets.UTF_16
    }

    init {
        baos = ByteArrayOutputStream(size)
    }
}