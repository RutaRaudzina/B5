package com.example.b5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.b5.R
import com.example.b5.TransferData
import com.example.b5.database.DatabaseHandler
import com.example.b5.ext
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {
    private var userId = -1
    private lateinit var transferData: TransferData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        println("\nLogin fragment")
        val db = DatabaseHandler(context)
        ext.setStats(db, 11)
        if (ext.taskNr != 0){
            ext.addToSequence()
        }

        root.findViewById<Button>(R.id.login_login_btn).setOnClickListener {
            loginUser(root, db)
        }
        root.findViewById<Button>(R.id.login_create_account_btn).setOnClickListener {
            findNavController().navigate(R.id.action_nav_login_to_createUserFragment)
        }

        transferData = activity as TransferData
        return root
    }

    fun loginUser(view: View, db:DatabaseHandler){
        val email = view.findViewById<TextView>(R.id.login_email_tv).text.toString()
        val password = view.findViewById<TextView>(R.id.login_password_tv).text.toString()

        if (inputCheck(email, password)){
            println("Login button pressed")
            val userId = db.findUser(email, password)
            if (userId > -1){
                println("User found, id = $userId")
                val user = db.getSingleUser(userId)
                this.userId = userId
                transferData.transferUserId(userId)
                transferData.setBottomMenuButtons()
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_nav_login_to_nav_home)
            }
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_LONG).show()
            println("User not found")
        }
    }

    private fun inputCheck(email: String, password: String) : Boolean{
        return email.isNotEmpty() && password.isNotEmpty()
    }

}