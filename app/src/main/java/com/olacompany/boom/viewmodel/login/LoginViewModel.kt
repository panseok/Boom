package com.olacompany.boom.viewmodel.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olacompany.boom.model.login.Login
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
    val loginNotice = MutableLiveData<String>()
    val haveNickName = MutableLiveData<Boolean>()
    val isConnect: Boolean = NettyClient.initLoginServer()

    val nameCeacking = Login.haveName.subscribe {
        haveNickName.postValue(Login.haveName.value)
    }
    val getLoginNotice = Login.loginNotice.subscribe{
        loginNotice.postValue(Login.loginNotice.value)
    }



    init{
        text.value = "Boom 에 오신 것을 환영합니다"
        haveNickName.value = true
        Log.e("LoginViewModel","LoginServer Connect : ${isConnect}")
    }


    fun loginSucced(userId: Long){
        Login.setClientUserId(userId)
        if(isConnect){
            text.postValue("현재 서버는 정상 입니다")
        }else{
            text.postValue("현재 서버는 점검 중 입니다")
        }
    }


}