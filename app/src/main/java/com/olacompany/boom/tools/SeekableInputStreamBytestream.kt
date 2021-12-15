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
 * Provides an abstract interface to a stream of bytes. This stream can be
 * seeked.
 *
 * @author Frz
 * @version 1.0
 * @since 299
 */
interface SeekableInputStreamBytestream : ByteInputStream {

    @Throws(IOException::class)
    fun seek(offset: Long)

    @Throws(IOException::class)
    fun getPosition(): Long

    override fun toString(b: Boolean): String?
}