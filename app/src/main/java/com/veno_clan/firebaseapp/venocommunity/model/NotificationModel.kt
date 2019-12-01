package com.veno_clan.firebaseapp.venocommunity.model

class NotificationModel (
    var destinationUid: String? = null,
    val title : String? = null,
    val info : String? = null,
    val notification_kind : Int = 0, //0 - 대기중 , 1 - 불합격 , 2 - 합격
    val timestamp : Long? = null
)