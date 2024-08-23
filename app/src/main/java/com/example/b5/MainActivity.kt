package com.example.b5

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.example.b5.database.DatabaseHandler
import com.example.b5.databinding.ActivityMainBinding
import com.example.b5.ui.fragments.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), TransferData {
    private var userId: Int = -1
    private lateinit var navUsername: TextView
    private lateinit var navEmail: TextView
    private lateinit var navAvatar: ImageView
    private lateinit var db: DatabaseHandler

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHandler(this)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Accessing navigation view header
        val navHeader: View = navView.getHeaderView(0)
        navUsername= navHeader.findViewById(R.id.nav_header_username_tv)
        navEmail = navHeader.findViewById(R.id.nav_header_email_tv)
        navAvatar = navHeader.findViewById(R.id.nav_header_user_image_iv)

        val stats = ext.readStats(db)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_login, R.id.nav_first, R.id.nav_second, R.id.nav_third,
                R.id.nav_fourth, R.id.nav_fifth, R.id.nav_sixth, R.id.nav_seventh, R.id.nav_eighth,
                R.id.nav_ninth, R.id.nav_tenth
            ), drawerLayout
        )

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)

        val menu = bottomNavigationView.getMenu()
        menu.add(Menu.NONE, R.id.nav_sixth, Menu.NONE, getString(R.string.sixth))
            .setIcon(R.drawable.six);
        menu.add(Menu.NONE, R.id.nav_first, Menu.NONE, getString(R.string.first))
            .setIcon(R.drawable.one);
        menu.add(Menu.NONE, R.id.nav_second, Menu.NONE, getString(R.string.second))
            .setIcon(R.drawable.two);



        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun logIn(db: DatabaseHandler){
        if (this.userId > -1){
            val user = db.getSingleUser(userId)

            navUsername.text = user.username
            navEmail.text = user.email
            val avatar = user.avatar
            val resourceId = resources.getIdentifier(avatar, "drawable", this.packageName)
            navAvatar.setImageResource(resourceId)
        }
    }

    override fun transferUserId(userId: Int){
        this.userId = userId
        if (userId > -1) {
            db = DatabaseHandler(this)
            logIn(db)
            ext.userid = this.userId
            println("User id back to main is: ${ext.userid}")
        }
    }

}