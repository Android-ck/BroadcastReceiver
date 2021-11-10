package com.zerir.broadcastreceiver

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), OnBroadcastUpdated {

    private lateinit var receiver: Receiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = Receiver(
            action = Intent.ACTION_AIRPLANE_MODE_CHANGED,
            onBroadcastUpdated = this,
            cls = MainActivity::class.java
        )

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val value = intent?.extras?.get("value")
        Log.d("UiState: ", "Intent $value")
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    override fun onUpdate(action: String, value: Any) {
        Log.d("UiState: ", "Callback $value")
    }
}