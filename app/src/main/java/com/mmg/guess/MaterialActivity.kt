package com.mmg.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val TAG = MaterialActivity::class.java.simpleName
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG, "secret number:${secretNumber.secret}")
        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.replay))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    secretNumber.reset()
                    counter.text = secretNumber.count.toString()
                    ed_number.setText("")
                }
                .setNeutralButton(getString(R.string.cancel), null)
                .show()
        }
        counter.text = secretNumber.count.toString()
    }

    fun check(view: View) {
        val guess = ed_number.text.toString().toInt()
        Log.d(TAG, "number:${guess}")
        val diff = secretNumber.validate(guess)
        var message = getString(R.string.bingo)
        when {
            diff < 0 -> message = getString(R.string.bigger)
            diff > 0 -> message = getString(R.string.smaller)
            diff == 0 && secretNumber.count < 3 -> message =
                getString(R.string.godlike) + secretNumber.secret.toString() + "!"
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        counter.text = secretNumber.count.toString()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }
}
