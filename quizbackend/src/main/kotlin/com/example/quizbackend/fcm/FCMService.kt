package com.example.quizbackend.fcm

import com.google.firebase.messaging.*
import org.springframework.stereotype.Service

@Service
class FCMService {

  fun notifyNewQuiz(topic: String, quizName: String) {
    val message = Message
        .builder()
        .putData("quizName", quizName)
        .putData("subject", topic)
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
  }
}