package com.veno_clan.firebaseapp.venocommunity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.veno_clan.firebaseapp.venocommunity.model.UserModel
import kotlinx.android.synthetic.main.activity_signup.*



class SignupActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        signup_user.setOnClickListener { signUp_event() }
        login_btn.setOnClickListener { login_event() }
        commit.setOnClickListener{ commit_evnet() }
    }

    fun commit_evnet(){
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse("https://community-cc5d3.firebaseapp.com/")
        intent.data = uri
        startActivity(intent)
    }

    fun login_event(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun signUp_event(){

        if(email.text.toString().isNullOrEmpty() || password1.text.toString().isNullOrEmpty() || password2.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "이메일, 인증코드, 비밀번호을 입력 해 주세요.", Toast.LENGTH_SHORT).show()
        } else if (password1.text.toString() != password2.text.toString()){
            Toast.makeText(this, "비밀번호를 다시 확인 해 주세요", Toast.LENGTH_SHORT).show()
        } else{
            email_sigup_event()
        }
    }

    fun email_sigup_event(){
        auth?.createUserWithEmailAndPassword(email.text.toString(), password1.text.toString())
            ?.addOnCompleteListener { task ->
                var uid = task.result!!.user?.uid
                var email_text = task.result!!.user?.email
                var userModel = UserModel()
                userModel.user_email = email.text.toString()
                userModel.uid = FirebaseAuth.getInstance().uid

                if (uid != null) {
                    firestore?.collection("user")?.document(uid)?.set(userModel)?.addOnCompleteListener { task ->
                        Toast.makeText(this, "회원가입중입니다. 잠시만 기달려 주세요", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }

            }
    }
}
