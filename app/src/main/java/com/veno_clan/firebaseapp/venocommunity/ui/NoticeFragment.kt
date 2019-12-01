package com.veno_clan.firebaseapp.venocommunity.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.veno_clan.firebaseapp.venocommunity.CopsActivity
import com.veno_clan.firebaseapp.venocommunity.R
import com.veno_clan.firebaseapp.venocommunity.model.NoticeModel
import kotlinx.android.synthetic.main.activity_cops.*
import kotlinx.android.synthetic.main.cops_notice_fragment.view.*
import kotlinx.android.synthetic.main.item_notice.view.*


class NoticeFragment : Fragment(){

    var database: FirebaseFirestore? = null
    var aaa : View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        aaa = inflater.inflate(R.layout.cops_notice_fragment, container, false) // cops_notification_fragment엑티비티 호출

        database = FirebaseFirestore.getInstance()

        return aaa //리턴
    }

    override fun onResume() {
        super.onResume()
        aaa?.notice_recyclerView?.adapter = NotificationRecyclerViewAdapter() //리사이클러뷰 어뎁터 연결
        aaa?.notice_recyclerView?.layoutManager = LinearLayoutManager(activity)
        var mainActivity = activity as CopsActivity
        mainActivity.progress_bar.visibility = View.GONE
    }

    inner class NotificationRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var notice : ArrayList<NoticeModel>
        var userId_uid : ArrayList<String>

        init {
            notice = ArrayList()
            userId_uid = ArrayList()

            database?.collection("notice")?.orderBy("number_counter", Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                notice.clear()
                for (snapshot in querySnapshot!!.documents){
                    val item = snapshot.toObject(NoticeModel::class.java)
                    if (item != null) {
                        notice.add(item)
                    }
                }
                Log.d("추적","데이터베이스에 값이 있습니다.")
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false) //item_notificaion_cops 호출 후 바인딩
            return CustomViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as CustomViewHolder).itemView

            viewHolder.notice_title.text = notice[position].Notice_title
            viewHolder.notice_info.text = notice[position].Notice_info
            viewHolder.notice_time.text = notice[position].timestamp.toString()
        }

        override fun getItemCount(): Int {
            return notice.size
        }

        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)
    }

}
