/*
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

import com.olacompany.boom.tools.SeekableInputStreamBytestream
import kotlin.Throws
import java.io.IOException

/**
 * Provides for an abstraction layer for an array of bytes.
 *
 * @author Frz
 * @version 1.0
 * @since Revision 326
 */
class ByteArrayByteStream (val byteArray: ByteArray) : SeekableInputStreamBytestream {
    private var pos = 0
    private var bytesRead: Long = 0

    /**
     * Gets the current position of the stream.
     *
     * @return The current position of the stream.
     */
    override fun getPosition(): Long {
        return pos.toLong()
    }

    /**
     * Seeks the pointer the the specified position.
     *
     * @param offset The position you wish to seek to.
     */
    @Throws(IOException::class)
    override fun seek(offset: Long) {
        pos = offset.toInt()
    }

    /**
     * Returns the numbers of bytes read from the stream.
     *
     * @return The number of bytes read.
     */
    override fun getBytesRead(): Long {
        return bytesRead
    }

    /**
     * Reads a byte from the current position.
     *
     * @return The byte as an integer.
     */
    override fun readByte(): Int {
        bytesRead++
        return byteArray[pos++].toInt() and 0xFF
    }

    /**
     * Returns the current stream as a hexadecimal string of values. Shows the
     * entire stream, and the remaining data at the current position.
     *
     * @return The current stream as a string.
     * @see Object.toString
     */
    override fun toString(): String {
        return toString(false)
    }

    override fun toString(b: Boolean): String {
        var nows = ""
        if (byteArray.size - pos > 0) {
            val now = ByteArray(byteArray.size - pos)
            System.arraycopy(byteArray, pos, now, 0, byteArray.size - pos)
            nows = HexTool.toString(now)
        }
        return if (b) {
            """
     All: ${HexTool.toString(byteArray)}
     Now: $nows
     """.trimIndent()
        } else {
            "Data: $nows"
        }
    }

    /**
     * Returns the number of bytes available from the stream.
     *
     * @return Number of bytes available as a long integer.
     */
    override fun available(): Long {
        return (byteArray.size - pos).toLong()
    }
}