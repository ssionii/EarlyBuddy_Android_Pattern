package com.ssionii.earlybuddy_android_pattern

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_signup.*
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity(){

    private var validateId = true
    private var validatePw = false
    private var validatePwCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_signup)

        setEditTextListener()

    }

    private fun setEditTextListener(){
        act_signup_et_id.onTextChange{ onDataCheck() }
        act_signup_et_pw.onTextChange{ onDataCheck() }
        act_signup_et_pw_check.onTextChange{ onDataCheck() }

        act_signup_et_id.onFocusChange()
        act_signup_et_pw.onFocusChange()
        act_signup_et_pw_check.onFocusChange()
    }

    // edittext의 focus 변화에 따른 처리
    private fun EditText.onFocusChange(){
        this.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    if(((v!! as EditText) == act_signup_et_pw) && validatePw
                        || ((v as EditText) == act_signup_et_pw_check) && validatePwCheck
                        || ((v as EditText) == act_signup_et_id) && validateId){
                        (v as EditText).background = resources.getDrawable(R.drawable.bg_signup_box_focus)
                    }else{
                        (v as EditText).background = resources.getDrawable(R.drawable.bg_signup_box_wrong)
                    }
                } else {
                    (v as EditText).background = resources.getDrawable(R.drawable.bg_signup_box_default)
                }
            }
        })
    }

    // edittext의 text 값의 변화에 따른 처리
    private fun EditText.onTextChange( dataCheck: (String) -> Unit){
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when(this@onTextChange){
                    act_signup_et_pw -> {
                        if(s.length < 6 && s.isNotEmpty()) {
                            act_signup_et_pw.background = resources.getDrawable(R.drawable.bg_signup_box_wrong)
                            act_signup_tv_pw_wrong.visibility = VISIBLE
                            validatePw = false
                        }else if(!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{6,}$", s.toString())){
                            act_signup_tv_pw_wrong.text = "영어+숫자로 구성해주세요."
                        }else{
                            act_signup_et_pw.background = resources.getDrawable(R.drawable.bg_signup_box_focus)
                            act_signup_tv_pw_wrong.visibility = INVISIBLE
                            validatePw = true
                        }

                        if(act_signup_et_pw_check.text.isNotEmpty() && s.toString() != act_signup_et_pw_check.text.toString()){
                            act_signup_tv_pw_check_wrong.visibility = VISIBLE
                            validatePwCheck = false
                        }else{
                            act_signup_tv_pw_check_wrong.visibility = INVISIBLE
                            validatePwCheck = true
                        }
                    }

                    act_signup_et_pw_check -> {
                        if(act_signup_et_pw.text.toString() != s.toString()){
                            act_signup_et_pw_check.background = resources.getDrawable(R.drawable.bg_signup_box_wrong)
                            act_signup_tv_pw_check_wrong.visibility = VISIBLE
                            validatePwCheck = false
                        }else{
                            act_signup_et_pw_check.background = resources.getDrawable(R.drawable.bg_signup_box_focus)
                            act_signup_tv_pw_check_wrong.visibility = INVISIBLE
                            validatePwCheck = true
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                dataCheck(s.toString())
            }
        })
    }

    private fun onDataCheck(){
        if(validateId && validatePw && validatePwCheck
            && act_signup_et_id.text.isNotEmpty()){
            act_signup_cv_done.apply {
                setCardBackgroundColor(Color.parseColor(("#2092ff")))
                isClickable = true
            }
        }else{
            act_signup_cv_done.apply {
                setCardBackgroundColor(resources.getColor(R.color.light_gray))
                isClickable = false
            }
        }
    }

}