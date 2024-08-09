package com.example.b5.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.b5.R
import com.example.b5.database.DatabaseHandler
import com.example.b5.database.User
import kotlin.random.Random

class CreateUserFragment : Fragment() {
    private var avatar = "login"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_user, container, false)
        val db = DatabaseHandler(context)

        root.findViewById<ProgressBar>(R.id.create_prograss_bar).visibility = View.INVISIBLE

        root.findViewById<ImageView>(R.id.create_avatar_iv).setOnClickListener {
            generateUserAvatar(root)
        }

        root.findViewById<Button>(R.id.create_profile_btn).setOnClickListener {
            createProfileClicked(root, db)
        }

        root.findViewById<Button>(R.id.update_profile_btn).setOnClickListener {
            updateUser(root, db)
        }

        root.findViewById<Button>(R.id.delete_user_btn).setOnClickListener {
            deleteUser(root, db)
        }
        return  root
    }

    private fun generateUserAvatar(view: View){
        val avatarNumber = Random.nextInt(1, 4)
        avatar = "login$avatarNumber"

        val resourceId = resources.getIdentifier(avatar, "drawable", requireContext().packageName)
        view.findViewById<ImageView>(R.id.create_avatar_iv).setImageResource(resourceId)
    }

    private fun createProfileClicked(view: View, db: DatabaseHandler){
        enableProgressBar(view, true)
        val username = view.findViewById<TextView>(R.id.create_username_tv).text.toString()
        val email = view.findViewById<TextView>(R.id.create_email_tv).text.toString()
        val password = view.findViewById<TextView>(R.id.create_password_tv).text.toString()

        if(inputCheck(username, email, password)){
            //Create user object
            val user = User(username, email, password, avatar)
            println(user)
            //Add data to database
            db.insertUser(user)

            resetFields(view)
            enableProgressBar(view, false)
            Toast.makeText(requireContext(), "Profile created successfully!", Toast.LENGTH_LONG).show()
            Thread.sleep(2_000)
            findNavController().navigate(R.id.action_createUserFragment_to_nav_login)
        } else {
            enableProgressBar(view, false)
            Toast.makeText(requireContext(), "Please, fill all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(username: String, email: String, password: String) : Boolean{
        return username != "" && email != "" && password != ""
    }

    private fun updateUser(view: View, db: DatabaseHandler){
        enableProgressBar(view, true)
        val username = view.findViewById<TextView>(R.id.create_username_tv).text.toString()
        val email = view.findViewById<TextView>(R.id.create_email_tv).text.toString()
        val password = view.findViewById<TextView>(R.id.create_password_tv).text.toString()
        // Create User object
        val user = User(username, email, password, this.avatar)
        val id = db.findUser(user.email, user.password)

        if(inputCheck(username, email, password) && id > -1) {
            // Update user
            db.updateUser(id, user.username)

            resetFields(view)
            enableProgressBar(view, false)
            Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_LONG).show()
            Thread.sleep(2_000)
            findNavController().navigate(R.id.action_createUserFragment_to_nav_login)
        } else {
            println("Wasn't successful")
            resetFields(view)
            enableProgressBar(view, true)
            Toast.makeText(requireContext(), "User $username not found!", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteUser(view: View, db: DatabaseHandler){
        val email = view.findViewById<TextView>(R.id.create_email_tv).text.toString()
        val password = view.findViewById<TextView>(R.id.create_password_tv).text.toString()
        val userId = db.findUser(email, password)
        if (userId > -1) {
            val message = "Are You sure, You wanna delete the profile?"
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage(message)
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Ok", DialogInterface.OnClickListener {
                        dialog, id ->
                    dialog.dismiss()
                    enableProgressBar(view, true)
                    db.deleteUser(userId)
                    enableProgressBar(view, false)
                    resetFields(view)
                    Toast.makeText(requireContext(), "User deleted successfully", Toast.LENGTH_SHORT).show()
                    Thread.sleep(2_000)
                    findNavController().navigate(R.id.action_createUserFragment_to_nav_login)

                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
            val alert = dialogBuilder.create()
            alert.show()
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enableProgressBar(view: View, enable: Boolean){
        if (enable){
            view.findViewById<ProgressBar>(R.id.create_prograss_bar).visibility = View.VISIBLE
        } else {
            view.findViewById<ProgressBar>(R.id.create_prograss_bar).visibility = View.INVISIBLE
        }
        view.findViewById<Button>(R.id.create_profile_btn).isEnabled = !enable
        view.findViewById<Button>(R.id.update_profile_btn).isEnabled = !enable
        view.findViewById<Button>(R.id.delete_user_btn).isEnabled = !enable
    }

    private fun resetFields(view: View){
        view.findViewById<TextView>(R.id.create_username_tv).text = ""
        view.findViewById<TextView>(R.id.create_email_tv).text = ""
        view.findViewById<TextView>(R.id.create_password_tv).text = ""
        val resourceId = resources.getIdentifier("login", "drawable", requireContext().packageName)
        view.findViewById<ImageView>(R.id.create_avatar_iv).setImageResource(resourceId)
    }
}