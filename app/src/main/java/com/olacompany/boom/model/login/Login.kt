package com.olacompany.boom.model.login

import com.olacompany.boom.model.netty.NettyClient
import io.reactivex.subjects.BehaviorSubject

class Login {

    companion object{
        var isLogin : BehaviorSubject<Boolean> = BehaviorSubject.create()
        var haveName : BehaviorSubject<Boolean> = BehaviorSubject.create()

        init {
            haveName.onNext(true)
            isLogin.onNext(false)
        }


        fun setHaveName(have: Boolean){
            haveName.onNext(have)
        }

        fun setClientUserId(userId: Long){
            NettyClient.getSession().writeAndFlush(LoginPacket.sendLogin(userId))
            NettyClient.setUserId(userId)
            isLogin.onNext(true)
        }

    }

}