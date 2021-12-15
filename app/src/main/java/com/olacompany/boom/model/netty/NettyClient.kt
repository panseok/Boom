package com.olacompany.boom.model.netty

import android.util.Log
import com.olacompany.boom.tools.LittleEndianReader
import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.JdkLoggerFactory

class NettyClient(channel: Channel) {

    init {
        session = channel
    }

    companion object {
        @Transient
        private lateinit var session: Channel

        private var userId: Long = -1
        var userName = ""
        var userMemo = ""
        var userProfileImageCode = 0
        var userWin = 0
        var userLose = 0
        var userPopularity = 0

        fun setUserInfo(r: LittleEndianReader) {
            userId = r.readLong()
            userName = r.readLengthAsciiString()
            userProfileImageCode = r.readInt()
            userWin = r.readInt()
            userLose = r.readInt()
            userPopularity = r.readInt()
            userMemo = r.readLengthAsciiString()
        }

        fun getSession(): Channel{
            return session
        }

        fun getUserId(): Long {
            if (userId == -1L) {
                Log.e("USERID ERR", "not save userid")
            }
            return userId
        }

        fun setUserId(userId: Long) {
            Companion.userId = userId
        }

       /* fun getNowActivity(code: Int): Activity? {
            return when (code) {
                0 -> LoginActivity.getInstance()
                1 -> LobbyActivity.getInstance()
                2 -> ChatRoomActivity.getInstance()
                3 -> FriendActivity.getInstance()
                else -> null
            }
        }

        fun getNowActivity(activityName: String?): Activity? {
            return when (activityName) {
                "login.LoginActivity" -> LoginActivity.getInstance()
                "lobby.LobbyActivity" -> LobbyActivity.getInstance()
                "room.ChatRoomActivity" -> ChatRoomActivity.getInstance()
                "friend.FriendActivity" -> FriendActivity.getInstance()
                "game.boomspin.BoomSpinActivity" -> BoomSpinActivity.getInstance()
                else -> null
            }
        }*/

        fun initLoginServer(): Boolean{
            InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE)
            val group: EventLoopGroup = NioEventLoopGroup()
            try {
                val b = Bootstrap()
                b.group(group)
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<SocketChannel?>() {
                        @Throws(Exception::class)
                        override fun initChannel(p0: SocketChannel?) {
                            val p : ChannelPipeline? = p0?.pipeline()
                            p?.addLast("decoder", NettyDecoder())
                            p?.addLast("encoder", NettyEncoder())
                            p?.addLast("handler", NettyClientHandler(ClientStatus.LOGIN, 0)
                            )
                        }

                    })
                val f: ChannelFuture = b.connect("121.159.16.63", 8484).sync()
            } catch (e: InterruptedException) {
                e.printStackTrace()
                return false
            }
            return true
        }

        fun initChannelServer(channel: Int) {
            InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE)
            val group: EventLoopGroup = NioEventLoopGroup()
            try {
                val b = Bootstrap()
                b.group(group)
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<SocketChannel?>() {
                        @Throws(Exception::class)
                        override fun initChannel(p0: SocketChannel?) {
                            val p: ChannelPipeline? = p0?.pipeline()
                            p?.addLast("decoder", NettyDecoder())
                            p?.addLast("encoder", NettyEncoder())
                            p?.addLast("handler", NettyClientHandler(ClientStatus.CHANNEL, channel)
                            )
                        }
                    })
                val f: ChannelFuture = b.connect("121.159.16.63", 8485).sync()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}