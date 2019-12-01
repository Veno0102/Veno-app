package com.veno_clan.firebaseapp.venocommunity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.veno_clan.firebaseapp.venocommunity.R
import com.veno_clan.firebaseapp.venocommunity.model.NotificationModel
import kotlinx.android.synthetic.main.cops_notification_fragment.view.*
import kotlinx.android.synthetic.main.item_notificaion_cops.view.*

class NotificationFragment: Fragment(){

    var fireStore : FirebaseFirestore? = null //FirebaseFirestore 선언
    var notificationSnapshot : ListenerRegistration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fireStore = FirebaseFirestore.getInstance() //FirebaseFirestore 초기화

        val fragmentView = inflater.inflate(R.layout.cops_notification_fragment, container, false) // cops_notification_fragment엑티비티 호출

        fragmentView.notification_recyclerView.adapter = NotificationRecyclerViewAdapter() //리사이클러뷰 어뎁터 연결
        fragmentView.notification_recyclerView.layoutManager = LinearLayoutManager(activity)

        return fragmentView //리턴
    }

    inner class NotificationRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var notificate : ArrayList<NotificationModel>
        var userId_uid : ArrayList<String>

        init {
            notificate = ArrayList()
            userId_uid = ArrayList()

            fireStore?.collection("joiner")
                ?.whereEqualTo("destinationUid", userId_uid)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                notificate.clear()
                    if(querySnapshot == null)return@addSnapshotListener
                    for (snapshot in querySnapshot?.documents!!) {
                        notificate.add(snapshot.toObject(NotificationModel::class.java)!!)
                    }
                    notificate.sortByDescending { it.timestamp }
                    notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notificaion_cops, parent, false) //item_notificaion_cops 호출 후 바인딩
            return CustomViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val notificationTitle = holder.itemView.notification_title
            val message = holder.itemView.notification_info

            when(notificate[position].notification_kind){
                0-> {
                    val kind0_title = getString(R.string.join_application_title)
                    notificationTitle.text = kind0_title

                    val kind0_message = getString(R.string.join_application)
                    message.text = kind0_message
                }
                1 -> {
                    val kind1_title = getString(R.string.failure_title)
                    notificationTitle.text = kind1_title

                    val kind1_message = getString(R.string.failure)
                    message.text = kind1_message
                }
                2 -> {
                    val kind2_title = getString(R.string.acceptance_title)
                    notificationTitle.text = kind2_title

                    val kind2_message = getString(R.string.acceptance)
                    message.text = kind2_message
                }
            }

        }

        override fun getItemCount(): Int {
            return notificate.size
        }

        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)
    }
}