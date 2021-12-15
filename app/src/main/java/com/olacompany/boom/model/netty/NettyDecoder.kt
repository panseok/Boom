package com.olacompany.boom.model.netty

import com.olacompany.boom.tools.ByteArrayByteStream
import com.olacompany.boom.tools.LittleEndianReader
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class NettyDecoder : ByteToMessageDecoder() {
    @Throws(Exception::class)
    protected override fun decode(
        channelHandlerContext: ChannelHandlerContext,
        byteBuf: ByteBuf,
        list: MutableList<Any>
    ) {
        val headSize = 4
        val readAble: Int = byteBuf.readableBytes()
        if (readAble < headSize) {
            return
        }
        byteBuf.markReaderIndex()
        val size: Int = byteBuf.readInt()
        if (readAble < size + headSize) {
            byteBuf.resetReaderIndex()
            return
        }
        val data = ByteArray(size)
        byteBuf.readBytes(data)
        byteBuf.markReaderIndex()
        list.add(LittleEndianReader(ByteArrayByteStream(data)))
    }
}