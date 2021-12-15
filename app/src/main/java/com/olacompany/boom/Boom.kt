package com.olacompany.boom

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class Boom : Application() {

    companion object{
        private var instance: Boom? = null

        fun getInstance(): Boom?{
            checkNotNull(instance){
                "Boom Class has Null"
            }
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSdk.init(this,"461f0cfae5ca4803e6385d9c1d6dbce1")
    }


}