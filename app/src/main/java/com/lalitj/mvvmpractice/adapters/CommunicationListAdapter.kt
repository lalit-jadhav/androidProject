package com.lalitj.mvvmpractice.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lalitj.mvvmpractice.R
import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class CommunicationListAdapter(
    var context: Context,
    var requestList: MutableList<RaisedCommunicationResponseDTO>
) :
    RecyclerView.Adapter<CommunicationListAdapter.RequestListCommunicationItemHolder>() {
    private val DASH: String = "N/A"
    var flag_pos = -1;
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestListCommunicationItemHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_list_communication_item_layout, parent, false)
        return RequestListCommunicationItemHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RequestListCommunicationItemHolder, position: Int) {

        holder.tvSMStext.text =
            if (!requestList[position].SMStext.isNullOrEmpty() || requestList[position].SMStext != "")
                "${requestList[position].SMStext}\n[${requestList[position].eventCaptureDate}]"
            else DASH


        if (!requestList[position].eventCaptureDate.isNullOrEmpty() || requestList[position].eventCaptureDate != "") {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val mDate: Date = sdf.parse(requestList[position].eventCaptureDate)
            val timeInMilliseconds: Long = mDate.time
            holder.tveventCaptureDate.text = getDisplayableTime(timeInMilliseconds)
        } else {
            holder.tveventCaptureDate.text = " " + DASH

        }

        holder.imvCollapseclose.visibility = View.GONE
        if (flag_pos == position) {
            holder.tvSMStext.maxLines = Integer.MAX_VALUE;
            holder.imvCollapse.visibility = View.GONE;
            holder.imvCollapseclose.visibility = View.VISIBLE
        } else {
            holder.tvSMStext.maxLines = 2
            holder.imvCollapse.visibility = View.VISIBLE
            holder.imvCollapseclose.visibility = View.GONE
        }

        holder.imvCollapse.setOnClickListener {
            flag_pos = holder.adapterPosition;
            holder.tvSMStext.maxLines = Integer.MAX_VALUE;
            // holder.clRequestDetails.visibility = View.VISIBLE
            holder.imvCollapse.visibility = View.GONE;
            holder.imvCollapseclose.visibility = View.VISIBLE
            notifyDataSetChanged()
        }

        holder.imvCollapseclose.setOnClickListener {
            holder.tvSMStext.maxLines = 2
            // holder.clRequestDetails.visibility = View.GONE
            holder.imvCollapseclose.visibility = View.GONE
            holder.imvCollapse.visibility = View.VISIBLE
            flag_pos = -1
            notifyDataSetChanged()
            val scrollAmount =
                holder.tvSMStext.layout.getLineTop(holder.tvSMStext.lineCount) - holder.tvSMStext.height
            if (scrollAmount > 0)
                holder.tvSMStext.scrollTo(0, scrollAmount)
            else
                holder.tvSMStext.scrollTo(0, 0)
        }
    }

    class RequestListCommunicationItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSMStext: TextView = view.findViewById(R.id.tv_SMStext)
        var tveventCaptureDate: TextView = view.findViewById(R.id.tv_eventCaptureDate)
        var imvCollapse: TextView = view.findViewById(R.id.imv_collapse)
        var imvCollapseclose: TextView = view.findViewById(R.id.imv_collapse_close)

    }

    fun getDisplayableTime(delta: Long): String? {
        var difference: Long = 0
        val mDate = System.currentTimeMillis()
        if (mDate > delta) {
            difference = mDate - delta
            val seconds = difference / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            val months = days / 31
            val years = days / 365
            return if (seconds < 0) {
                "not yet"
            } else if (seconds < 60) {
                if (seconds == 1L) "one second ago" else "$seconds seconds ago"
            } else if (seconds == 0L) {
                "Just now"
            } else if (seconds < 120) {
                "a minute ago"
            } else if (seconds < 2700) // 45 * 60
            {
                "$minutes minutes ago"
            } else if (seconds < 5400) // 90 * 60
            {
                "an hour ago"
            } else if (seconds < 86400) // 24 * 60 * 60
            {
                "$hours hours ago"
            } else if (seconds < 172800) // 48 * 60 * 60
            {
                "Yesterday"
            } else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                "$days days ago"
            } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {
                if (months <= 1) "one month ago" else "$days days ago"
            } else {
                if (years <= 1) "one year ago" else "$years years ago"
            }
        }
        return null
    }
}