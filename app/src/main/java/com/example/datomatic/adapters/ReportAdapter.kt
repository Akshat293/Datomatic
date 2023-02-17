package com.example.datomatic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.datomatic.R
import com.example.datomatic.models.PrescriptionX
import com.example.datomatic.models.Report

class ReportAdapter(private val context: Context) : RecyclerView.Adapter<ReportAdapter.RepoViewHolder>(){
    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctor_name: TextView = view.findViewById(R.id.name)
        val last_date: TextView = view.findViewById(R.id.last_date)
        val phone:TextView=view.findViewById(R.id.phone)
    }
    private val callBack = object : DiffUtil.ItemCallback<Report>() {
        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem._id== newItem._id
        }

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
       return RepoViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_article2, parent, false)
       )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val report = differ.currentList[position]
        holder.apply {
            doctor_name.text=report.name
            last_date.text=report.createdAt
            phone.text=report.phoneNumber
        }
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(report) }
            }

        }
    }

    override fun getItemCount(): Int {
        //return mlist.size
        return differ.currentList.size
    }

    private var onItemClickListener: ((Report) -> Unit)? = null

    fun setOnItemClickListener(listener: (Report) -> Unit) {
        onItemClickListener = listener
    }
}