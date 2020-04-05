package com.example.quizbackend.fcm

import com.google.firebase.messaging.*
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class FCMService {

  fun sendMessage(topic: String, messageString: String) {
    val message = Message
        .builder()
        .putData("value", messageString)
        .setTopic(topic)
//        .setNotification(Notification(topic, messageString))
//        .setAndroidConfig(AndroidConfig
//            .builder()
//            .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
//            .setPriority(AndroidConfig.Priority.HIGH)
//            .setNotification(AndroidNotification
//                .builder()
//                .setTag(topic)
//                .build())
//            .build())
        .build ()
    val response = FirebaseMessaging.getInstance().send(message)
    println(messageString)
  }
}