package com.olacompany.boom.viewmodel.login

import android.graphics.Color
import android.text.Editable
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olacompany.boom.model.Tools
import com.olacompany.boom.model.login.Login

class CreateNameViewModel: ViewModel() {

    val checkNameNotice = MutableLiveData<String>()
    val checkNameNoticeColor = MutableLiveData<Int>()
    val inputName = MutableLiveData<String>()
    var isEnableName: Boolean = false

    init {
        checkNameNoticeColor.postValue(Color.GRAY)
    }

    fun sendCreateNameRequest(){
        if(isEnableName){
            Login.requestCreateName(inputName.value!!)
        }
    }

    fun checkNickNameFirst(editable: Editable) {
        val text: String = editable.toString()

        val length = editable.toString().length

        if (TextUtils.isEmpty(text)) {
            checkNameNotice.postValue("부적절한 닉네임 사용시 제제될 수 있습니다.")
            checkNameNoticeColor.postValue(Color.GRAY)
        }

        if (length > 0) {
            if (Tools.isOxfordText(text) || Tools.isNoAssemblyKorean(text) || Tools.isSpecialCharacters(text)) {
                checkNameNotice.postValue("사용 불가능한 닉네임입니다")
                checkNameNoticeColor.postValue(Color.RED)
                isEnableName = false
            } else {
                checkNameNotice.postValue("사용 가능한 닉네임 입니다.")
                checkNameNoticeColor.postValue(Color.GREEN)
                isEnableName = true
            }
        }

    }



}