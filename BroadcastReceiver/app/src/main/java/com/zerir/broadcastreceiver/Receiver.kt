package com.zerir.broadcastreceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Receiver(
    private val action: String,
    private val onBroadcastUpdated: OnBroadcastUpdated? = null,
    private val cls: Class<*>? = null,
) : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (action == intent?.action) {
            //get state
            val isAirPlanModeEnabled = intent.getBooleanExtra("state", false)
            //passing state to callback
            onBroadcastUpdated?.onUpdate(Intent.ACTION_AIRPLANE_MODE_CHANGED, isAirPlanModeEnabled)
            //start activity with intent contains the state
            cls?.let {
                Intent(context, cls).also {
                    it.putExtra("value", isAirPlanModeEnabled)
                    context?.startActivity(it)
                }
            }
            Log.d("ReceiverState: ", "$isAirPlanModeEnabled")
        }
    }
}

interface OnBroadcastUpdated {
    fun onUpdate(action: String, value: Any)
}