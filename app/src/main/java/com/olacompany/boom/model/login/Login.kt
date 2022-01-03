package com.olacompany.boom.model.login

import com.olacompany.boom.model.netty.NettyClient
import com.olacompany.boom.tools.LittleEndianReader
import io.reactivex.subjects.BehaviorSubject

class Login {

    companion object{
        var loginNotice : BehaviorSubject<String> = BehaviorSubject.create()
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
        }

        fun requestCreateName(name: String){
            NettyClient.getSession().writeAndFlush(LoginPacket.sendCheckName(name))
        }

        fun setLoginSucceed(succeed: Boolean){
            isLogin.onNext(succeed)
        }

        fun getLoginNoticeData(r: LittleEndianReader){
            val type: Short = r.readShort()
            var message = ""
            message = if (type.toInt() == 0) {
                "가입에 성공하였습니다."
            } else if (type.toInt() == 1) {
                "가입에 실패 하였습니다."
            } else if (type.toInt() == 2) {
                "닉네임 설정이 완료되었습니다."
            } else if (type.toInt() == 3) {
                "닉네임 설정에 실패하였습니다."
            } else if (type.toInt() == 4) {
                "로그인에 성공하였습니다."
            } else if (type.toInt() == 5) {
                "이미 사용중인 닉네임 입니다."
            } else {
                r.readLengthAsciiString()
            }
            loginNotice.onNext(message)
        }

    }

}