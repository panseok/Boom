package com.olacompany.boom.viewmodel.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olacompany.boom.model.login.KakaoLogin
import com.olacompany.boom.model.netty.NettyClient

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

    init{
        text.value = "HELLO"
        Log.e("LoginViewModel","LoginServer Connect : ${isConnect}")
    }


    fun kakaoLoginClick(){

        val kakaoLogin = KakaoLogin()

        if(isConnect){
            text.value = "현재 서버는 정상 입니다"
        }else{
            text.value = "현재 서버는 점검 중 입니다."
        }

    }


}