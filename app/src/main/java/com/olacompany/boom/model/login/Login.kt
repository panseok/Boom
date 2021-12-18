package com.olacompany.boom.model.login

import com.olacompany.boom.model.netty.NettyClient

class Login {

    var isNoNameUser = false

    fun setClientUserId(userId: Long){
        NettyClient.getSession().writeAndFlush(LoginPacket.sendLogin(userId))
        NettyClient.setUserId(userId)
    }

    fun setNoNameUser(){
        isNoNameUser = true
    }


}