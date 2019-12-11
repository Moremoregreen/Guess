package com.mmg.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 100
    val TAG = MaterialActivity::class.java.simpleName
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG, "secret number:${secretNumber.secret}")
        fab.setOnClickListener { view ->
            replay()
        }
        counter.text = secretNumber.count.toString()
        Log.d(TAG, "onCreate: " + secretNumber.secret)
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "Data: count = ${count} / nick = ${nick}")
    }

    private fun replay() {
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
        Log.d(TAG, "replay: SecretNumber = ${secretNumber.secret}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
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
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            })
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD && resultCode == Activity.RESULT_OK){
            val nickname = data?.getStringExtra("NICK")
            Log.d(TAG, "onActivityResult: NickName = ${nickname}")
            replay()
        }
    }
}
