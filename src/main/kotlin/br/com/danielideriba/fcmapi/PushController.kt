package br.com.danielideriba.fcmapi

import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@RestController
@RequestMapping()
class NoteController {

    val userDeviceID = "fT4ZK79UVi4:APA91bGVWblv6p_lOyJZnvbzMeIK9C4O5KWD_lB4lAs0VDWhVHrigQrlg4Gu7XBm87kSyibenwzKlLC7vgl33YgHnGKtH6ZoLZk-QrttG98P8RkMvFKqyIVRFlvM0pRN38dv7pCG5lt4"
    @PostMapping("/push")
    fun list(): String {
        pushFCMNotification(userDeviceID)
        return "OK"
    }

    // Method to send Notifications from server to client end.

    val AUTH_KEY_FCM = "AIzaSyDDPLh2j_6G4rnb3gJCR4lF-Waz_6wbeAw"
    val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"

// userDeviceIdKey is the device id you will query from your database

    @Throws(Exception::class)
    fun pushFCMNotification(userDeviceIdKey: String) {

        val authKey = AUTH_KEY_FCM // You FCM AUTH key
        val FMCurl = API_URL_FCM

        val url = URL(FMCurl)
        val conn = url.openConnection() as HttpURLConnection

        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true

        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "key=$authKey")
        conn.setRequestProperty("Content-Type", "application/json")

        val data = JSONObject()
        data.put("id", "123")
        data.put("outroparam", "teste")


        val json = JSONObject()
        json.put("to", userDeviceIdKey.trim { it <= ' ' })
        val info = JSONObject()
        info.put("title", "Notificação legal") // Notification title
        info.put("body", "Tem novidades pra vc!") // Notification body
        info.put("click_action", "danielideriba.com.br.fcmapp.detalhe")

        json.put("notification", info)
        json.put("data", data)

        val wr = OutputStreamWriter(conn.outputStream)
        wr.write(json.toString())
        wr.flush()
        conn.inputStream
    }
}
