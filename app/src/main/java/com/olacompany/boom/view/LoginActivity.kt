package com.olacompany.boom.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.olacompany.boom.R
import com.olacompany.boom.databinding.ActivityLoginBinding
import com.olacompany.boom.databinding.DialogCreateUsernameBinding
import com.olacompany.boom.viewmodel.login.CreateNameViewModel
import com.olacompany.boom.viewmodel.login.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val TAG = "LOGIN"
    val loginViewModel: LoginViewModel by viewModels()
    val creatNameViewModel: CreateNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.loginvm = loginViewModel

        binding.kakaoLoginBtn.setOnClickListener {
            requestLogin()
        }

        loginViewModel.haveNickName.observe(this){
            //닉네임이 없다면 (false)상태면 다이얼로그창을 띄움
            if(!loginViewModel.haveNickName.value!!){
                createNameDialog()
            }
        }

        loginViewModel.loginNotice.observe(this){
            if(!loginViewModel.loginNotice.equals("")){
                Toast.makeText(this,loginViewModel.loginNotice.value,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun createNameDialog(){
        val inflater = layoutInflater
        val binding = DataBindingUtil.inflate<DialogCreateUsernameBinding>(inflater, R.layout.dialog_create_username, null, false)
        binding.lifecycleOwner = this
        binding.logindvm = creatNameViewModel

        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.show()


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
                loginViewModel.loginSucced(user.id)
            }
        }
    }
}