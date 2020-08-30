package com.battery.cms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.Toast
import com.battery.cms.model.UserModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_input_user.*

class InputUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_user)

        // 저장버튼 클릭
        input_user_button.setOnClickListener {
            writeNewUser(editTextTextPersonName.text.toString(), editTextPhone.text.toString(), editTextTextEmailAddress.text.toString())
        }


        // 전화번호 하이픈 자동입력
        editTextPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        back.setOnClickListener {
            Log.d("TAG", "백버튼 클릭.........화면 뒤로 가버림.............")
            finish()
        }
    }

    private fun writeNewUser(username:String, telno:String, email:String) {

        val user = UserModel(username, telno, email)
        FirebaseDatabase.getInstance().reference.child("users").child(telno).setValue(user)
        finish()

    }


}