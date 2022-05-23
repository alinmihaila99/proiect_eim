package com.example.myquizzkotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.myquizzkotlin.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BroadcastReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context, intent: Intent) {

        val settings = context.getSharedPreferences("dailyGoal", 0)
        val userId = settings.getInt("logedinUserId", 2)


        val call = RetrofitInstance.api.getPosition(userId.toString());


        call.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("Fail!!", "Fail####" + t.message)
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful()){

                    println(response.body());
                    val intent = Intent(context, MainActivity::class.java)
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
                    val builder :Notification.Builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val notificationChannel = NotificationChannel("123", "alin", NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(context, "123")
                            .setContentTitle("Urmareste evolutia ta in clasament")
                            .setContentText("Pozitia ta in clasament este   " + response.body().toString())
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentIntent(pendingIntent)
                    } else {

                        builder = Notification.Builder(context)
                            .setContentTitle("Urmareste evolutia ta in clasament")
                            .setContentText("Pozitia ta in clasament este   " + response.body().toString())
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentIntent(pendingIntent)
                    }
                    notificationManager.notify(1234, builder.build())

                }
            }
        })
        Toast.makeText(context, "Airplane mode changed", Toast.LENGTH_SHORT).show();
    }

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private const val TAG = "BroadcastReceiver"
    }
}