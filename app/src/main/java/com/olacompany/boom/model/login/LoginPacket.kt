package com.olacompany.boom.model.login

import com.olacompany.boom.opcode.SendOpcode
import com.olacompany.boom.tools.LittleEndianWriter

class LoginPacket {

    companion object{

        fun sendPong(): ByteArray? {
            val o = LittleEndianWriter()
            o.writeShort(SendOpcode.SERVER_PONG.ordinal)
            return o.packet
        }

        fun sendLogin(userId: Long): ByteArray? {
            val o = LittleEndianWriter()
            o.writeShort(SendOpcode.USER_LOGIN.ordinal)
            o.writeLong(userId)
            return o.packet
        }

        fun sendCheckName(name: String?): ByteArray? {
            val o = LittleEndianWriter()
            o.writeShort(SendOpcode.USER_CHECK_NAME.ordinal)
            o.writeLengthAsciiString(name!!)
            return o.packet
        }

    }
}