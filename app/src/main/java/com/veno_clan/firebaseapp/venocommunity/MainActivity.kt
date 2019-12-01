package com.veno_clan.firebaseapp.venocommunity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()/*, NavigationView.OnNavigationItemSelectedListener*/ {

    val auth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cops_button.setOnClickListener { cops_event() }
        cod_button.setOnClickListener { cod_event() }
        rescu_btn.setOnClickListener { rescu_event() }
        //check_app_version()
    }

    fun rescu_event(){
        startActivity(Intent(this, ResuActivity::class.java))
    }

    private fun check_app_version(){
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val lastVersion = remoteConfig.getString("last_version")
        val currentVersion = getAppVersion(this)

        if (lastVersion != currentVersion.toString()) {
            showUpdate()
        }
    }

    fun getAppVersion(context: Context) {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val version = info.versionName
    }

    private fun showUpdate(){
        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("새로운 업데이트")
            .setMessage("현재 사용하시는 앱은 오래된 버전입니다. 최신버전으로 업데이트 해 주세요")
            .setPositiveButton("OK" ) {dialog, whch -> moveStore()}
            .create()
        dialog.show()
    }

    private fun moveStore() {
        val url = "https://naver.com"   //스토어 주소 입력하기
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        finish()
    }

    fun cops_event() {
        startActivity(Intent(this, CopsActivity::class.java))
        Log.i("크리티컬옵스", "터치!")
    }

    fun cod_event() {
        Log.i("콜오브듀티", "터치!")
    }

}


