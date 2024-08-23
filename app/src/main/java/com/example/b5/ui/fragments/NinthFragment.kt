package com.example.b5.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b5.R
import com.example.b5.TransferData
import com.example.b5.database.DatabaseHandler
import com.example.b5.ext

class NinthFragment : Fragment() {
    private lateinit var transferData: TransferData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_ninth, container, false)

        println("\nNinth fragment")
        val db = DatabaseHandler(context)
        ext.setStats(db, 9)
        transferData = activity as TransferData
        transferData.setBottomMenuButtons()

        return root
    }
}