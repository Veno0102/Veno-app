package com.veno_clan.firebaseapp.venocommunity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null //로그인 기능을 auth로 지정해서 널로 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) //엑티비티 호출해서 보여주기

        auth = FirebaseAuth.getInstance() //지정한 auth 초기화

        signup_btn.setOnClickListener { signup_event() } //signup_btn클릭시 signup_event()함수로 던져주기
        login_btn.setOnClickListener { login_check() } //login_btn클릭시 login_check()함수로 던져주기
    }

    fun login_check(){ //login_check()함수 만들기
        if(email.text.toString().isEmpty() || password.text.toString().isEmpty()){ //만약 이메일과 비밀번호의 텍스트가 비어있다면 아래내용 실행
            Toast.makeText(this, "이메일 및 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show() //토스트 이메일 및 비밀번호를 입력해 주세요띄워주기
        }else{ //만약 둘 다 입력 되었다면 아래내용 실행
            login_event() //login_event() 함수로 던져주기
        }
    }
    fun login_event(){ //login_event()함수 만들기
        auth?.signInWithEmailAndPassword(email.text.toString(), password.text.toString()) //위에 설정한 auth호출 후 이메일, 패스워드가 맞다면 아래실행
            ?.addOnCompleteListener { task -> //존재한 계정인지 확인
                if(task.isSuccessful){ //만약 존재한다면
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show() //토스트로 띄워주고
                    moveActivity(auth?.currentUser) //함수 실행
                }else{ //만약 없는 계정이라면
                    Toast.makeText(this, "없는 계정입니다.", Toast.LENGTH_SHORT).show() //토스트 띄워주기
                }
            }
    }

    fun signup_event(){ //signup_event() 함수 만들기
        startActivity(Intent(this, SignupActivity::class.java)) //회원가입 엑티비티로 이동하기
    }

    fun moveActivity(user: FirebaseUser?){ //moveActivity(user: FirebaseUser?) 함수 만들기
        if(user != null){ //만약 유저가 존재하면
            startActivity(Intent(this, MainActivity::class.java)) //메인 엑티비티로 이동
            finish() //엑티비티를 종료하여 누적되는 걸 막아줌
        }
    }

    override fun onStart() { //엑티비티 갱신
        super.onStart() //시작
        moveActivity(auth?.currentUser) //함수 실행
    }
}
