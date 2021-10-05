package com.hkyriacou.mobile_project2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Button

private const val EXTRA_MESSAGE = "com.hkyriacou.Mobile_Project2.MESSAGE"
private const val TAG = "SavingScreen"

class SavingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saving_screen2)

        val retButton : Button = findViewById(R.id.Return)
        retButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun newIntent(packageContext: Context, message: String): Intent {
            Log.d(TAG, "Creating Intent")
            return Intent(packageContext, SavingScreen::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
        }
    }
}