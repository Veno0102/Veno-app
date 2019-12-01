package com.veno_clan.firebaseapp.venocommunity.model

data class ContextModel (
     var context_info: String? = null,
     var user_email: String? = null,
     var imageUrl: String? = null,
     var notification_kind: Long = 0,
     var uid: String? = null,
     var name: String? = null,
     var timestamp: Long? = null
)