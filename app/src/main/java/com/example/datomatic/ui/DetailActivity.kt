package com.example.datomatic.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.datomatic.api.ApiClient
import com.example.datomatic.databinding.ActivityDetailBinding
import com.example.datomatic.models.*
import com.example.datomatic.utils.SessionManager
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var mQuestions: MutableList<Medication> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiClient = ApiClient()
        sessionManager=SessionManager(applicationContext)
        addInRow()
        binding.iconShare.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(true)
            scanner.initiateScan()
        }
    }


    private fun addInRow() {

        //create a new table row
        val tableRow = TableRow(applicationContext)

        //Set new table row layout parameters
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
        val layoutParams1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams1.setMargins(5, 0, 5, 0) // margins as you wish

//        tableRow.setLayoutParams(layoutParams)
//        //add values into row by calling addView()
//        var textView = TextView(applicationContext)
//        textView.text= key
//        tableRow.addView(textView,0)
//
//        textView = TextView(applicationContext)
//        textView.text= value
//        tableRow.addView(textView,1)
//        textView = TextView(applicationContext)
//        textView.text= value
//        tableRow.addView(textView,2)
//        textView = TextView(applicationContext)
//        textView.text= value
//        tableRow.addView(textView,3)
//        textView = TextView(applicationContext)
//        textView.text= value
//        tableRow.addView(textView,4)
//
//        //Finally add the created row into the layout
//        binding.Table.addView(tableRow)

        val token="Bearer "+sessionManager.getToken()
        val precId=sessionManager.getPrescId()
        if (precId != null) {
            binding.progressBar.visibility = View.VISIBLE
            apiClient.getApiService().getPrecDetail(token,precId)
                .enqueue(object:Callback<PrescriptionDetail>{
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<PrescriptionDetail>,
                        response: Response<PrescriptionDetail>
                    ) {
                       val medication=response.body()
                        binding.progressBar.visibility = View.INVISIBLE
                        if (medication != null) {

                            binding.patientName.text="Name: "+medication.prescription.name
                            binding.patientAge.text="Age: "+medication.prescription.age
                            binding.patientGender.text="Gender: "+medication.prescription.gender
                            binding.patientRemark.text="Remark: "+medication.prescription.remarks
                            binding.patientCreated.text="Date: "+medication.prescription.createdAt
                        }
                        if (medication != null) {
                            mQuestions.addAll(medication.prescription.medications)
                            for(med in mQuestions){
                                Toast.makeText(
                                    applicationContext, med._id,
                                    Toast.LENGTH_LONG
                                ).show()
                                tableRow.setLayoutParams(layoutParams)
                                var textView = TextView(applicationContext)
                                textView.text= med.RxNORMcode
                                textView.background
                                tableRow.addView(textView,0)
                                textView = TextView(applicationContext)
                                textView.text= med.medicationName
                                tableRow.addView(textView,1)
                                textView = TextView(applicationContext)
                                textView.text= med.dosage
                                tableRow.addView(textView,2)
                                textView = TextView(applicationContext)
                                textView.text= med.route
                                tableRow.addView(textView,3)
                                textView = TextView(applicationContext)
                                textView.text= med.frequency
                                tableRow.addView(textView,4)
                                binding.Table.addView(tableRow)
                            }
                        }
                    }

                    override fun onFailure(call: Call<PrescriptionDetail>, t: Throwable) {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            applicationContext, "Failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                val token="Bearer "+sessionManager.getToken()
                val precId=sessionManager.getPrescId()
                val share= precId?.let { Share(result.contents, it) }
                if (share != null) {
                    apiClient.getApiService().sharePrec(token,share)
                        .enqueue(object :Callback<Share_mess>{
                            override fun onResponse(
                                call: Call<Share_mess>,
                                response: Response<Share_mess>
                            ) {
                               val message= response.body()?.message
                                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                            }

                            override fun onFailure(call: Call<Share_mess>, t: Throwable) {
                                Toast.makeText(applicationContext, "Try Again", Toast.LENGTH_LONG).show()
                            }
                        })
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}