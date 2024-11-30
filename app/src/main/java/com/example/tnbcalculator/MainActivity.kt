package com.example.tnbcalculator

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
//import android.widget.Button
import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import com.example.tnbcalculator.databinding.ActivityMainBinding
import kotlin.text.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Calculate.setOnClickListener{calculateLogic()}

        binding.About.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode",false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        val toggleThemeButton = findViewById<Button>(R.id.Theme)

        toggleThemeButton.setOnClickListener{
            val isCurrentlyDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
            val editor = sharedPreferences.edit()

            if (isCurrentlyDarkMode) {
                // Switch to Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("isDarkMode", false)
            } else {
                // Switch to Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("isDarkMode", true)
            }

            editor.apply() // Save theme state
        }
        
        

    }


    private fun calculateLogic(){
        try {
            var unit = binding.EnterUnit.text.toString().toDoubleOrNull()
            var rebate = binding.EnterRebate.text.toString().toDoubleOrNull()

            if (unit == null || rebate == null )
            {
                Toast.makeText(this, "Please enter valid input values!", Toast.LENGTH_SHORT).show()
                return
            }

            if (rebate <0 || rebate > 5)
            {
                Toast.makeText(this, "Rebate percentage must be between 0% and 5%", Toast.LENGTH_SHORT).show()
                return
            }

            val totalCharges = calculateCharges(unit)
            val finalCost = totalCharges - (totalCharges * (rebate / 100))

            binding.Result.text = String.format("Final Cost: RM %.2f", finalCost)

        }catch (e: Exception) {
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }


    }

    private fun calculateCharges(units: Double): Double {
        return when
        {
            units <= 200 -> units * 0.218
            units <= 300 -> (200 * 0.218) + ((units - 200) * 0.334)
            units <= 600 -> (200 * 0.218) + (100 * 0.334) + ((units - 300) * 0.516)
            else -> (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((units - 600) * 0.546)
        }
    }
}