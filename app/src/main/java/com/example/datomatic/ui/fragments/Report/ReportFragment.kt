package com.example.datomatic.ui.fragments.Report

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datomatic.R
import com.example.datomatic.adapters.ReportAdapter
import com.example.datomatic.databinding.FragmentReportBinding
import com.example.datomatic.utils.Resource
import com.example.datomatic.utils.SessionManager

class ReportFragment : Fragment() {

    companion object {
        fun newInstance() = ReportFragment()
    }

    private var _binding:FragmentReportBinding?=null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var newsAdapter: ReportAdapter
    private val viewModel by viewModels<ReportViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initAdapter()
        newsAdapter.setOnItemClickListener {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.fileURL))
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
                        newsAdapter.differ.submitList(it?.reports)
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


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    private fun initAdapter() {
        newsAdapter = ReportAdapter(requireContext())
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