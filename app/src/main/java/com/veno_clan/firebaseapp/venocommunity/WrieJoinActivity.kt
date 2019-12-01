package com.veno_clan.firebaseapp.venocommunity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.veno_clan.firebaseapp.venocommunity.model.ContextModel
import kotlinx.android.synthetic.main.activity_wrie_join.*
import java.text.SimpleDateFormat
import java.util.*

class WrieJoinActivity : AppCompatActivity(){

    var database: FirebaseFirestore? = null
    var storage: FirebaseStorage? = null
    var auth: FirebaseAuth? = null
    var image_uri: Uri? = null
    var ALBUM_OPEN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrie_join)

        database = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()


        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, ALBUM_OPEN)

        ok_btn.setOnClickListener { ok_event() }

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ALBUM_OPEN) {
            if (resultCode == Activity.RESULT_OK) {
                image_uri = data?.data
                image_image.setImageURI(data?.data)
            } else {
                finish()
                Toast.makeText(this, "이미지 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun ok_event(){
        progress_bar.visibility = View.VISIBLE

        val timestamp = SimpleDateFormat("yyyy.mm.dd - HH:MM").format(Date())
        val image_Name = "JPEG_" + timestamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(image_Name)

        storageRef?.putFile(image_uri!!)?.addOnSuccessListener { taskSnapshot ->
            progress_bar.visibility = View.GONE
            var uri = taskSnapshot.metadata?.reference?.downloadUrl
            var contextModel = ContextModel()
            contextModel.imageUrl = uri.toString()
            contextModel.uid = auth?.currentUser?.uid
            contextModel.context_info = join_write_editText.text.toString()
            contextModel.name = auth?.currentUser?.email
            contextModel.timestamp = System.currentTimeMillis()
            contextModel.notification_kind = 0
            database?.collection("joiner")?.document()?.set(contextModel)

            setResult(Activity.RESULT_OK)
            finish()
            Toast.makeText(this, "업로드가 되었습니다.",Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
                progress_bar.visibility = View.GONE

                Toast.makeText(this, getString(R.string.upload_fail),
                    Toast.LENGTH_SHORT).show()
            }

        }
}

