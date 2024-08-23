package com.example.b5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.b5.R
import com.example.b5.TransferData
import com.example.b5.database.DatabaseHandler
import com.example.b5.ext

class EighthFragment : Fragment() {
    private lateinit var transferData: TransferData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_eighth, container, false)

        println("\nEighth fragment")
        val db = DatabaseHandler(context)
        ext.setStats(db, 8)
        transferData = activity as TransferData
        transferData.setBottomMenuButtons()

        return root
    }

}