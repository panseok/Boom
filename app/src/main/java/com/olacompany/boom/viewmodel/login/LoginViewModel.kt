package com.olacompany.boom.viewmodel.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olacompany.boom.model.login.Login
import com.olacompany.boom.model.netty.NettyClient
import com.olacompany.boom.model.netty.NettyClientHandler

/*
*   의존 방향은 View -> ViewModel -> Model
*   View는 ViewModel을 OK
*   ViewModel은 View를 NO
*   ViewModel은 Model을 OK
*   Model은 ViewModel을 NO
*/

class LoginViewModel : ViewModel() {

    val text = MutableLiveData<String>()
    val isConnect: Boolean = NettyClient.initLoginServer()
    val boomLogin: MutableLiveData<Login>

    init{
        boomLogin = NettyClientHandler.loginModelData
        text.value = "HELLO ${isConnect}"
        Log.e("LoginViewModel","LoginServer Connect : ${isConnect}")
    }


    fun loginSucced(userId: Long){
        boomLogin.value?.setClientUserId(userId)
        if(isConnect){
            text.value = "현재 서버는 정상 입니다 {${boomLogin.value?.isNoNameUser}}"
        }else{
            text.value = "현재 서버는 점검 중 입니다. {$userId}"
        }

    }

    //닉네임을 생성해야 하는지 체크
    fun isCreateNameFirst(): Boolean{

        println(boomLogin.value?.isNoNameUser)
        return boomLogin.value?.isNoNameUser == true
    }


}