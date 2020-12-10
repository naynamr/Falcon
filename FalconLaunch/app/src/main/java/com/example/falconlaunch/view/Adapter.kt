package com.example.falconlaunch.view

import android.content.Context
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.falconlaunch.R
import com.example.falconlaunch.BR
import com.example.falconlaunch.models.ApiData
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.falconlaunch.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Adapter(
    private val context: Context,
    private var list: ArrayList<ApiData>
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        Glide
            .with(context)
            .load(list[position].links.patch.small)
            .placeholder(R.mipmap.no_image)
            .into(holder.binding.image)

        holder.binding.name.text = list[position].name + " " + list[position].flight_number
        val dateString: String? = convertServerTimeToDate(
            list[position].static_fire_date_utc,
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "dd MMM yyyy | hh:mm aa"
        )
        if (dateString.isNullOrEmpty()) {
            holder.binding.date.visibility = View.GONE
        } else {
            holder.binding.date.visibility = View.VISIBLE
            holder.binding.date.text = dateString
        }
        if (list[position].success) {
            holder.binding.status.text = context.getString(R.string.success)
        } else {
            holder.binding.status.text = context.getString(R.string.failed)
        }


    }

    class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR._all, data)
            binding.executePendingBindings()
        }
    }

    /**
     *  String to date string conversion
     */
    fun convertServerTimeToDate(dateTime: String?, fromFormat: String?, toFormat: String?): String {
        try {

            if (dateTime.isNullOrEmpty() || fromFormat.isNullOrEmpty() || toFormat.isNullOrEmpty()) {
                return ""
            }

            val dateFormatFrom = SimpleDateFormat(fromFormat, Locale.getDefault())
            dateFormatFrom.timeZone = TimeZone.getTimeZone("GMT")
            val date = dateFormatFrom.parse(dateTime);
            val dateFormatTo = SimpleDateFormat(toFormat, Locale.getDefault())

            return dateFormatTo.format(date)
        } catch (e: Exception) {
            return ""
        }
    }

}