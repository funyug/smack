package net.blockchaind.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import net.blockchaind.smack.Controllers.App
import net.blockchaind.smack.Models.Channel
import net.blockchaind.smack.Models.Message
import net.blockchaind.smack.Utilities.URL_GET_CHANNELS
import net.blockchaind.smack.Utilities.URL_GET_MESSAGES
import org.json.JSONException

/**
 * Created by shiva on 2/17/2018.
 */
object MessageService {

    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    fun getChannels(complete: (Boolean) -> Unit) {
        val channelRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS,null,Response.Listener {response ->
            try {
                for (x in 0 until response.length()) {
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val channelDesc = channel.getString("description")
                    val channelId = channel.getString("_id")

                    val newChannel = Channel(name,channelDesc, channelId)
                    this.channels.add(newChannel)
                    complete(true)
                }
            } catch (e: JSONException) {
                Log.d("JSON","EXC:"+e.localizedMessage)
            }
        }, Response.ErrorListener {error ->
            Log.d("ERROR","Could not retrieve channels")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers.put("Authorization","Bearer${App.prefs.authToken}")
                return headers
            }
        }

        App.prefs.requestQueue.add(channelRequest)
    }

    fun getMessages(channelId: String, complete: (Boolean) -> Unit) {
        val url = "$URL_GET_MESSAGES$channelId"

        val messageRequest = object : JsonArrayRequest(Method.GET,url,null, Response.Listener {response ->
            try {
                for (x in 0 until  response.length()) {
                    val message = response.getJSONObject(x)
                    val messageBody = message.getString("messageBody")
                    val channelId = message.getString("channelId")
                    val id = message.getString("_id")
                    val userName = message.getString("userName")
                    val userAvatar = message.getString("userAvatar")
                    val userAvatarColor = message.getString("userAvatarColor")
                    val timestamp = message.getString("timestamp")

                    val newMessage = Message(messageBody,userName,channelId,userAvatar,userAvatarColor,id,timestamp)
                    this.messages.add(newMessage)
                }
            } catch (e: JSONException) {
                Log.d("JSON","EXC:"+e.localizedMessage)
            }

        }, Response.ErrorListener {error ->
            Log.d("ERROR","Could not retrieve channels")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers.put("Authorization","Bearer${App.prefs.authToken}")
                return headers
            }
        }

        App.prefs.requestQueue.add(messageRequest)
    }

    fun clearMessages() {
        messages.clear()
    }

    fun clearChannels(){
        channels.clear()
    }


}