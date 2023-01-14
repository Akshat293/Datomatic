package com.example.datomatic.adapters

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
import com.example.datomatic.models.Doctor
import org.w3c.dom.Text

class DoctorAdapter(private val context: Context , private var mlist:List<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.DocViewHolder>() {
     class DocViewHolder(view: View) : RecyclerView.ViewHolder(view){
         val doctor_name:TextView=view.findViewById(R.id.doctor_name)
         val last_date:TextView=view.findViewById(R.id.last_date)

     }

    private val callBack = object : DiffUtil.ItemCallback<Doctor>() {
        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
       return DocViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
       )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
       // val doctor = mlist[position]
        val doctor =differ.currentList[position]
        holder.doctor_name.text= doctor.name
        holder.last_date.text=doctor.recentVisitDate
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

    private var onItemClickListener: ((Doctor) -> Unit)? = null

    fun setOnItemClickListener(listener: (Doctor) -> Unit) {
        onItemClickListener = listener
    }
}