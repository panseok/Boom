package com.olacompany.boom.model.netty

import com.olacompany.boom.model.login.Login
import com.olacompany.boom.model.login.LoginPacket
import com.olacompany.boom.opcode.ReceiveOpcode
import com.olacompany.boom.tools.LittleEndianReader
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter


class NettyClientHandler(var clientStatus: ClientStatus, var channel: Int) :
    ChannelInboundHandlerAdapter() {
    override fun channelActive(ctx: ChannelHandlerContext) {
        if (clientStatus === ClientStatus.LOGIN) {
            NettyClient.setSession(ctx.channel())


        }/* else if (clientStatus === com.olacompany.boom.model.netty.ClientStatus.CHANNEL) {

            com.olacompany.boom.model.netty.NettyClient.getSession().close()
            com.olacompany.boom.model.netty.NettyClient.setSession(ctx.channel())
            com.olacompany.boom.model.netty.NettyClient.getSession()
                .writeAndFlush(LoginPacket.sendConnectChannel(com.olacompany.boom.model.netty.NettyClient.getUserId()))
        }*/
    }

    override fun channelRead(ctx: ChannelHandlerContext, packet: Any) {
        val r: LittleEndianReader = packet as LittleEndianReader
        val opcode: Int = r.readShort().toInt()
        for (recv in ReceiveOpcode.values()) {
            if (recv.ordinal === opcode) {
                try {
                    System.out.println("DEBUG " + recv.name + "  " + r.toString())
                    handlePacket(r, recv)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return
            }
        }
    }

    /*
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //5.수신된 데이터를 모두 읽었을때 호출되는 이벤트 메서드
        ctx.close();//6.서버와 연결된 채널을 닫음
        //6.1 이후 데이터 송수신 채널은 닫히게 되고 클라이언트 프로그램은 종료됨
    }*/
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
     //   LoadingActivity.setConnected(false)
     //   if (clientStatus === com.olacompany.boom.model.netty.ClientStatus.CHANNEL) {
     //       OlaChat.ServerNoticeAlertDialog()
      //  }
    }

    @Throws(Exception::class)
    override fun channelInactive(ctx: ChannelHandlerContext) { // 채널 비활성
        if (clientStatus === com.olacompany.boom.model.netty.ClientStatus.CHANNEL) {
         //   if (LoadingActivity.isConnected()) { //로그아웃 시에는 출력 X
        //        OlaChat.ServerNoticeAlertDialog()
         //   }
        }
    }

    companion object {

        private fun handlePacket(r: LittleEndianReader, recv: ReceiveOpcode) {
            when (recv) {
                ReceiveOpcode.SERVER_PING -> {
                    NettyClient.getSession().writeAndFlush(LoginPacket.sendPong())
                }
                ReceiveOpcode.SERVER_NOTICE -> {

                    //LobbyActivity.getInstance().getServerNoticeList(r)
                }
                ReceiveOpcode.LOGIN_STATUS_MSG -> {
                //    LoginActivity.showLoginStatusMsg(r)
                }
                ReceiveOpcode.LOGIN_CREATE_NAME -> {
                    //설계 미스.
                    Login.setHaveName(false)
                }
                ReceiveOpcode.CONNECT_LOGIN_SERVER_ONLINE -> {
                //    LoadingActivity.setConnected(true)
                }
               /* CONNECT_CHANNEL_SERVER -> {
                    DevTools.startProgressDialogHandleLooper(LoginActivity.getInstance())
                    com.olacompany.olachat.netty.NettyClient.initChannelServer(1)
                }
                CONNECT_CHANNEL_SERVER_ONLINE -> {
                    com.olacompany.olachat.netty.NettyClient.setUserInfo(r)
                    LoginActivity.getInstance().startLobbyActivity()
                }
                USER_CHANGE_PROFILE_IMAGE_RESULT -> {
                    LobbyPagerAdapter.getInstance().getChangeProfileImageResult(r)
                }
                USER_CHANGE_MEMO_RESULT -> {
                    LobbyPagerAdapter.getInstance().getChangeUserMemoResult(r)
                }
                USER_GET_USER_INFO -> {
                    com.olacompany.olachat.netty.NettyClient.setUserInfo(r)
                }
                USER_OPEN_PROFILE -> {
                    UserInfo.ProfileAlertDialog(r)
                }
                LOBBY_ROOM_LIST -> {
                    LobbyActivity.getInstance().getLobbyRoomListPacket(r)
                }
                ROOM_JOIN_REQUEST_RESULT -> {
                    LobbyActivity.getInstance().getUserJoinRoomRequestResult(r)
                }
                ROOM_SEND_NOTICE -> {
                    ChatRoomActivity.getInstance().readNotice(r)
                }
                ROOM_SEND_CHAT -> {
                    ChatRoomActivity.getInstance().readChat(r)
                }
                ROOM_USER_LIST -> {
                    ChatRoomActivity.getInstance().readUserList(r)
                }
                ROOM_EXIT_BAN -> {
                    ChatRoomActivity.getInstance().exitRoom()
                }
                ROOM_INVITE_FRIEND_LIST -> {
                    ChatRoomActivity.OpenInviteFriendDialog(r)
                }
                ROOM_OPEN_INVITE_ACCPET -> {
                    LobbyActivity.InviteRoomAccpetDialog(r)
                }
                FRIEND_NOTICE_MSG -> {
                    val activity: Activity =
                        com.olacompany.olachat.netty.NettyClient.getNowActivity(
                            OlaChat.getInstance().getTopActivityName()
                        )
                    DevTools.showToastShortMsg(activity, r.readLengthAsciiString())
                }
                FRIEND_SEND_USER_LIST -> {
                    FriendActivity.getInstance().readFriendList(r)
                }
                FRIEND_SEND_REQUEST_LIST -> {
                    FriendActivity.getInstance().readRequestList(r)
                }
                ROOM_START_GAME_ACTIVITY -> {
                    ChatRoomActivity.getInstance().handleJoinGameActivity(r)
                }
                GAME_BOOM_SPIN -> {
                    BoomSpinActivity.getInstance().handleBoomSpinReceive(r)
                }*/
            }
        }
    }
}