package com.olacompany.boom.model

class Tools {

    companion object{

        //특수문자 인가 bool
        fun isSpecialCharacters(text: String): Boolean{
            //영문 숫자 한글이 아닐 경우 true
            return !text.matches(Regex("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$"))
        }

        //한글 자음 , 모음 따로 인지 bool
        fun isNoAssemblyKorean(text: String): Boolean {
            return text.matches(Regex(".*[ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+.*"))
        }

        //비속어 인지 bool
        fun isOxfordText(text: String): Boolean {
            val oxfordlist: MutableList<String> = ArrayList()
            oxfordlist.add("애미")
            oxfordlist.add("새끼")
            oxfordlist.add("섹스")
            oxfordlist.add("시발")
            oxfordlist.add("병신")
            oxfordlist.add("한남")
            oxfordlist.add("한녀")
            oxfordlist.add("개년")
            oxfordlist.add("개놈")
            for (str in oxfordlist) {
                if (text.contains(str)) {
                    return true
                }
            }
            return false
        }
    }

}