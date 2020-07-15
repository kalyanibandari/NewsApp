package com.example.newsapp

import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.newsapp.view.WebViewActivity
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal


class ApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .setNotificationOpenedHandler(NotificationOpenedHandler())
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
    }

    inner class NotificationOpenedHandler : OneSignal.NotificationOpenedHandler {
        override fun notificationOpened(result: OSNotificationOpenResult) {
            val actionType = result.action.type
            val data = result.notification.payload.additionalData
            val activityToBeOpened: String?
            var activity: String
            if (data != null) {
                activityToBeOpened = data.optString("activityToBeOpened", null)
                if (activityToBeOpened != null && activityToBeOpened == "ABC") {
                    Log.i("OneSignal", "customkey set with value: $activityToBeOpened")
                    val intent = Intent(this@ApplicationClass, WebViewActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                } else if (activityToBeOpened != null && activityToBeOpened == "DEF") {
                    val intent = Intent(this@ApplicationClass, WebViewActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                }
            }
        }
    }
}