package com.olacompany.boom.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateNameViewModel: ViewModel() {

    val checkNameNotice = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val name1 = MutableLiveData<String>()

    init {
        checkNameNotice.postValue("asdfasdf")
    }

    fun test(){
        checkNameNotice.postValue(name.value)
    }



}