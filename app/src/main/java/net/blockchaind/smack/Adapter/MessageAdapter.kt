package net.blockchaind.smack.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.blockchaind.smack.Models.Message
import net.blockchaind.smack.R
import net.blockchaind.smack.Services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by shiva on 2/20/2018.
 */
class MessageAdapter(val context: Context, val messages : ArrayList<Message>): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindMessage(context,messages[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView?.findViewById<ImageView>(R.id.messageUserImage)
        val timestamp = itemView?.findViewById<TextView>(R.id.timestampLabel)
        val userName = itemView?.findViewById<TextView>(R.id.messageUserLabel)
        val messageBody = itemView?.findViewById<TextView>(R.id.messageBodyLabel)

        fun bindMessage(context: Context,message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar,"drawable",context.packageName)
            userImage?.setImageResource(resourceId)
            userImage?.setBackgroundColor(UserDataService.returnAvatarColor(message.avatarColor))
            userName?.text = message.userName
            timestamp?.text = message.timestamp
            messageBody?.text = message.message

        }

        fun returnDateString(isoString: String) : String {
            val isoFormatter = SimpleDateFormat("yyyyy-MM-dd'T'HH:mm::ss.SSS'Z'")
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
            var convertedDate = Date()

            try {
                convertedDate = isoFormatter.parse(isoString)
            } catch(e: ParseException) {
                Log.d("PARSE","cannot parse date")
            }

            val outDateString = SimpleDateFormat("E,h:mm a",Locale.getDefault())
            return outDateString.format(convertedDate)
        }
    }

}