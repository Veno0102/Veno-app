package com.veno_clan.firebaseapp.venocommunity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_resu.*

class ResuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resu)

        ester.setOnClickListener { firstShow() }

    }

    fun firstShow(){
        Toast.makeText(this, "로그캣을 확인 해 주세요",Toast.LENGTH_SHORT).show()
        Log.e("MSG","@Uehs!@$??LSOocnW120$9239@94.OW")
    }

}
