package com.kronos.calculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.appbar.MaterialToolbar

class dark_modeActivity : AppCompatActivity() {
    lateinit var switch: SwitchCompat
    lateinit var toolbar: MaterialToolbar
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        switch = findViewById(R.id.switchIcon)
        toolbar = findViewById(R.id.materialToolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPreferences = this.getSharedPreferences("darkMode", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("darkModeYes", true)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("darkModeYes", false)
            }

            editor.apply()
        }

    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = this.getSharedPreferences("darkMode", Context.MODE_PRIVATE)
        val darkMode = sharedPreferences.getBoolean("darkModeYes", false)
        switch.isChecked = darkMode
    }
}