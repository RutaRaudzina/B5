package com.example.b5

import com.google.android.material.bottomnavigation.BottomNavigationView

interface TransferData {
    fun transferUserId(userId: Int)

    fun setBottomMenuButtons(buttonsCount : Int)
}