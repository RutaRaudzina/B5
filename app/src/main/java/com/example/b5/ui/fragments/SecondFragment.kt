package com.example.b5.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b5.R
import com.example.b5.database.DatabaseHandler
import com.example.b5.ext

class SecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_second, container, false)

        println("\nSecond fragment")
        val db = DatabaseHandler(context)
        ext.setStats(db, 2)
        if (ext.taskNr != 0){
            ext.addToSequence()
        }

        return root
    }

}