package com.olacompany.boom.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.olacompany.boom.Boom
import com.olacompany.boom.R
import com.olacompany.boom.databinding.ActivityMainBinding
import com.olacompany.boom.viewmodel.login.LoginViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val login_viewModel: LoginViewModel by viewModels()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.loginvm = login_viewModel

    }

    fun requestLogin(){
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "로그인 실패", error)
            }
            else if (token != null) {
                Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken}")
            }
        }
        Boom.getInstance()

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(Boom.getInstance()!!)) {
            UserApiClient.instance.loginWithKakaoTalk(Boom.getInstance()!!.applicationContext, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(Boom.getInstance()!!.applicationContext, callback = callback)
        }
    }
}