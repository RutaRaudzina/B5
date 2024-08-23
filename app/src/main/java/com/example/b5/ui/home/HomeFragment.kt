package com.example.b5.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.b5.R
import com.example.b5.TransferData
import com.example.b5.database.DatabaseHandler
import com.example.b5.databinding.FragmentHomeBinding
import com.example.b5.ext
import com.example.b5.ui.fragments.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.LinkedList
import java.util.Queue

class HomeFragment : Fragment() {
    private lateinit var transferData: TransferData
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        println("\nHome fragment")
        val db = DatabaseHandler(context)

        ext.setStats(db, 0)
        val stats = ext.readStats(db)
        println(stats)

        if (ext.userid > -1){
            transferData = activity as TransferData
            transferData.setBottomMenuButtons()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}