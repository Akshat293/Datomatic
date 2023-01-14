package com.example.datomatic.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.query
import com.example.datomatic.R
import com.example.datomatic.adapters.DoctorAdapter
import com.example.datomatic.api.ApiClient
import com.example.datomatic.databinding.FragmentHomeBinding
import com.example.datomatic.models.Doctor
import com.example.datomatic.models.Doctor_list
import com.example.datomatic.ui.Tab.TabActivity
import com.example.datomatic.utils.Resource
import com.example.datomatic.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var newsAdapter: DoctorAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var mQuestions: MutableList<Doctor> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val intent = Intent(activity, TabActivity::class.java)
            intent.putExtra("id",it)
            startActivity(intent)
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                   binding.progressBar.visibility = View.INVISIBLE
                    response.data.let {
                        Toast.makeText(
                            activity, " Yo Yo honey singh",
                            Toast.LENGTH_LONG
                        ).show()
                        newsAdapter.differ.submitList(it?.doctors)
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
            }
        }

        return root
    }

    private fun initAdapter() {
        newsAdapter = DoctorAdapter(requireContext(),mQuestions)
        binding.newsAdapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
//        binding.progressBar.visibility=View.VISIBLE
//        apiClient = ApiClient()
//        sessionManager= SessionManager(requireContext())
//        val token="Bearer "+sessionManager.getToken()
//        apiClient.getApiService().getDoctor(token).
//        enqueue(object: Callback<Doctor_list> {
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onResponse(
//                call: Call<Doctor_list>,
//                response: Response<Doctor_list>
//            ) {
//                response.body().let { res ->
//                    binding.progressBar.visibility=View.INVISIBLE
//                    val questions = response.body()
//                    if (questions != null) {
//                        mQuestions.addAll(questions.doctors)
//                        newsAdapter.notifyDataSetChanged()
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<Doctor_list>, t: Throwable) {
//                binding.progressBar.visibility=View.INVISIBLE
//               Log.d("main","Failed")
//            }
//        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



