package com.example.datomatic.ui.fragments.Prescription

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datomatic.MainActivity
import com.example.datomatic.R
import com.example.datomatic.adapters.DoctorAdapter
import com.example.datomatic.adapters.PrescriptionAdapter
import com.example.datomatic.databinding.FragmentHomeBinding
import com.example.datomatic.databinding.FragmentPrescriptionBinding
import com.example.datomatic.ui.DetailActivity
import com.example.datomatic.ui.home.HomeViewModel
import com.example.datomatic.utils.Resource
import com.example.datomatic.utils.SessionManager

class PrescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = PrescriptionFragment()
    }
    private var _binding: FragmentPrescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PrescriptionViewModel>()
    private lateinit var sessionManager: SessionManager
    private lateinit var newsAdapter: PrescriptionAdapter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrescriptionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sessionManager= SessionManager(requireContext())
         initAdapter()
       newsAdapter.setOnItemClickListener {
            sessionManager.savePrecId(it._id)
            val myIntent = Intent(activity, DetailActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(requireContext(), android.R.anim.fade_in, android.R.anim.fade_out)
            this.startActivity(myIntent, options.toBundle())
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data.let {
                        Toast.makeText(
                            activity, it?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        newsAdapter.differ.submitList(it?.prescriptions)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data.let {
                        Toast.makeText(
                            activity, R.string.no_internet,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        }




        return  root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun initAdapter() {
        newsAdapter = PrescriptionAdapter(requireContext())
        binding.newsAdapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}