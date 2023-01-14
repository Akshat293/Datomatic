package com.example.datomatic.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.datomatic.R
import com.example.datomatic.models.PrescriptionX

class PrescriptionAdapter(private val context: Context) :
    RecyclerView.Adapter<PrescriptionAdapter.DocViewHolder>() {
    class DocViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctor_name: TextView = view.findViewById(R.id.doctor_name)
        val last_date: TextView = view.findViewById(R.id.last_date)
        val hospital_name:TextView=view.findViewById(R.id.hospital_name)
        val remark:TextView=view.findViewById(R.id.remarks)
    }

    private val callBack = object : DiffUtil.ItemCallback<PrescriptionX>() {
        override fun areItemsTheSame(oldItem: PrescriptionX, newItem: PrescriptionX): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: PrescriptionX, newItem: PrescriptionX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
        return DocViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article1, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        // val doctor = mlist[position]
        val doctor = differ.currentList[position]
        holder.doctor_name.text = "Name: "+doctor.name
        holder.last_date.text = "Created at: "+doctor.createdAt
        holder.remark.text="Remarks: "+doctor.remarks
        holder.hospital_name.text="Hospital name "+doctor.hospitalName
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(doctor) }
            }

        }

    }

    override fun getItemCount(): Int {
        //return mlist.size
        return differ.currentList.size
    }

    private var onItemClickListener: ((PrescriptionX) -> Unit)? = null

    fun setOnItemClickListener(listener: (PrescriptionX) -> Unit) {
        onItemClickListener = listener
    }
}
