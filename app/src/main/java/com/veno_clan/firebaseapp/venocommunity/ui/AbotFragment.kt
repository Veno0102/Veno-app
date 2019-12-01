package com.veno_clan.firebaseapp.venocommunity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veno_clan.firebaseapp.venocommunity.R

class AbotFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_about, container, false)

        return fragmentView
    }
}