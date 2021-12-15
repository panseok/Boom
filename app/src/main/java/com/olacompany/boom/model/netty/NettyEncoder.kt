package com.olacompany.boom.model.netty

import kotlin.Throws
import java.lang.Exception
import io.netty.channel.ChannelHandlerContext
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.MessageToByteEncoder

class NettyEncoder : MessageToByteEncoder<ByteArray>() {
    @Throws(Exception::class)
    override fun encode(ctx: ChannelHandlerContext, pData: ByteArray, buffer: ByteBuf) {
        try {
            val i = pData.size
            buffer.writeInt(i)
            buffer.writeBytes(pData)
        } finally {
            ctx.flush()
        }
    }
}