package com.veno_clan.firebaseapp.venocommunity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veno_clan.firebaseapp.venocommunity.ui.AbotFragment
import com.veno_clan.firebaseapp.venocommunity.ui.NoticeFragment

class CopsActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val ALBUM_OPEN = 10
    val TAG = "로그 기록"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cops)


        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.main_notice -> {
                 val notice = NoticeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, notice)
                    .commit()
                Log.i(TAG,"공지 프래그먼트가 실행되었습니다.")
                return true
            }
            R.id.join_write -> {
                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(Intent(this, WrieJoinActivity::class.java))
                    Toast.makeText(this, "게임 프로필 사진만 첨부 해 주세요", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "저장공간을 읽을 수 없습니다. 설정에서 권한을 추가 해 주세요", Toast.LENGTH_SHORT).show()
                }
                Log.i(TAG,"신청서 프래그먼트가 실행되었습니다.")
                return true
            }
            R.id.action_notification -> {
                Toast.makeText(this, "서비스 준비중입니다.",Toast.LENGTH_SHORT).show()
                /*val notification = NotificationFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, notification)
                    .commit()*/
                Log.i(TAG,"알림 프래그먼트가 실행되었습니다.")
                return true
            }
            R.id.action_about -> {
                val about = AbotFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, about)
                    .commit()
                Log.i(TAG,"정상작동")
                return true
            }
        }
        return false
    }



}
