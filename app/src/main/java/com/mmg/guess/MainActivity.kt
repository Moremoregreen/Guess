package com.mmg.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Secret Number:${secretNumber.secret}")
    }

    fun check(view: View) {
        val guess = ed_number.text.toString().toInt()
        Log.d(TAG, "number:${guess}")
        val diff = secretNumber.validate(guess)
        var message = "Bingo! Lucky Number is ${secretNumber.secret}!"
        when {
            diff < 0 -> message = "Bigger"
            diff > 0 -> message = "Smaller"
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
