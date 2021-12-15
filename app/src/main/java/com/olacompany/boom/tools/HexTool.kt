/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

/**
 * Provides a class for manipulating hexadecimal numbers.
 *
 * @author Frz
 * @since Revision 206
 * @version 1.0
 */
object HexTool {
    private val HEX =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * Turns a byte into a hexadecimal string.
     *
     * @param byteValue The byte to convert.
     * @return The hexadecimal representation of `byteValue`
     */
    fun toString(byteValue: Byte): String {
        val tmp: Int = byteValue.toInt() shl 8
        val retstr = charArrayOf(HEX[tmp shr 12 and 0x0F], HEX[tmp shr 8 and 0x0F])
        return String(retstr)
    }

    /**
     * Turns an integer into a hexadecimal string.
     *
     * @param intValue The integer to transform.
     * @return The hexadecimal representation of `intValue`.
     */
    fun toString(intValue: Int): String {
        return Integer.toHexString(intValue)
    }

    /**
     * Turns an array of bytes into a hexadecimal string.
     *
     * @param bytes The bytes to convert.
     * @return The hexadecimal representation of `bytes`
     */
    fun toString(bytes: ByteArray): String {
        val hexed = StringBuilder()
        for (i in bytes.indices) {
            hexed.append(toString(bytes[i]))
            hexed.append(' ')
        }
        return hexed.substring(0, hexed.length - 1)
    }


    /**
     * Turns an hexadecimal string into a byte array.
     *
     * @param hex The string to convert.
     * @return The byte array representation of `hex`
     */
    fun getByteArrayFromHexString(hex: String): ByteArray {
        val baos = ByteArrayOutputStream()
        var nexti = 0
        var nextb = 0
        var highoc = true
        outer@ while (true) {
            var number = -1
            while (number == -1) {
                if (nexti == hex.length) {
                    break@outer
                }
                val chr = hex[nexti]
                number = if (chr >= '0' && chr <= '9') {
                    chr - '0'
                } else if (chr >= 'a' && chr <= 'f') {
                    chr - 'a' + 10
                } else if (chr >= 'A' && chr <= 'F') {
                    chr - 'A' + 10
                } else {
                    -1
                }
                nexti++
            }
            if (highoc) {
                nextb = number shl 4
                highoc = false
            } else {
                nextb = nextb or number
                highoc = true
                baos.write(nextb)
            }
        }
        return baos.toByteArray()
    }
}