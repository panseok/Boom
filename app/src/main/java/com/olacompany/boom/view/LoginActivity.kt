package com.olacompany.boom.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.olacompany.boom.R
import com.olacompany.boom.databinding.ActivityLoginBinding
import com.olacompany.boom.viewmodel.login.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val TAG = "LOGIN"
    val login_viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
       // binding.lifecycleOwner = this
        binding.loginvm = login_viewModel

        binding.kakaoLoginBtn.setOnClickListener {
            requestLogin()
        }

        login_viewModel.boomLogin.observe(this){
            if(it.isNoNameUser){
                Toast.makeText(this,"닉네임이 없어요",Toast.LENGTH_LONG).show()
            }
        }

        login_viewModel.text.observe(this){
            Toast.makeText(this,"${it.toString()}",Toast.LENGTH_LONG).show()
        }

    }

    private fun showCreateNameDialog(){
        val inflater = layoutInflater
        val linearLayout = inflater.inflate(R.layout.dialog_create_username,null)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.dialog_create_username)
        binding.lifecycleOwner = this

    }






    /*
    * 카카오 로그인 관련 함수들  MVVM 패턴 아님
    */
    fun requestLogin(){
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            }
            else if (token != null) {
                requestMe()
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    fun requestMe(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                login_viewModel.loginSucced(user.id)
            }
        }
    }
}