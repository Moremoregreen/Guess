package com.mmg.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
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
        var message = getString(R.string.bingo)
        when {
            diff < 0 -> message = getString(R.string.bigger)
            diff > 0 -> message = getString(R.string.smaller)
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }
}
