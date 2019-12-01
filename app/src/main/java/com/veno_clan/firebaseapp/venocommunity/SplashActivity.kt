package com.veno_clan.firebaseapp.venocommunity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity(){

    var TIME_OUT: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.i("스플레쉬 이벤트", "정상작동")

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            Log.i("메인 이벤트", "정상작동")
            finish()
            Log.i("종료 이벤트", "정상작동")
        }, TIME_OUT)


    }

}