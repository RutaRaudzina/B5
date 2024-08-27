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
import com.example.b5.database.DatabaseHandler
import com.example.b5.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), TransferData {
    private var userId: Int = -1
    private lateinit var navUsername: TextView
    private lateinit var navEmail: TextView
    private lateinit var navAvatar: ImageView
    private lateinit var db: DatabaseHandler

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu : Menu

    private val iconIds: Array<Int> = arrayOf(
        R.drawable.home, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
        R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten, R.drawable.user
    )

    private val fragmentIds: Array<Int> = arrayOf(
        R.id.nav_home, R.id.nav_first, R.id.nav_second, R.id.nav_third, R.id.nav_fourth, R.id.nav_fifth,
        R.id.nav_sixth, R.id.nav_seventh, R.id.nav_eighth, R.id.nav_ninth, R.id.nav_tenth, R.id.nav_login
    )

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
        ext.setNavView(bottomNavigationView)
        setBottomMenuButtons()

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

    override fun setBottomMenuButtons(){

        val bottomNavigationView = ext.buttomNavView
        bottomNavigationView.setItemIconTintList(null)
        menu = bottomNavigationView.getMenu()
        menu.clear()

        if (userId > -1) {
            var buttonObjects : MutableList<ButtonObject> = mutableListOf<ButtonObject>()
            fillButtonObjects(buttonObjects)
            sortButtons(buttonObjects)

            for (i in 0 .. 4){
                if (buttonObjects[i].fraction <= 0) break
                if (buttonObjects[i].nextFragmentId == ext.curfragment) continue
                menu.add(
                    Menu.NONE, fragmentIds[buttonObjects[i].nextFragmentId],
                    Menu.NONE, ""
                )
                    .setIcon(iconIds[buttonObjects[i].nextFragmentId])
            }

        }
    }

    fun fillButtonObjects(buttonObjects : MutableList<ButtonObject>){
        val stats = ext.readStats(db)
        for (i in 0 .. stats.size - 1) buttonObjects.add(ButtonObject(i, stats[i]))
    }

    fun sortButtons(buttonObjects : MutableList<ButtonObject>){
        for(i in 0..buttonObjects.size - 2){
            for (j in 0..buttonObjects.size-2-i){
                if (buttonObjects[j+1].fraction <= buttonObjects[j].fraction)
                    continue
                var temp = buttonObjects[j]
                buttonObjects[j] = buttonObjects[j+1]
                buttonObjects[j+1] = temp
            }
        }
    }

}