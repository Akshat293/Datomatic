package com.example.datomatic.ui.fragments.Report

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datomatic.R
import com.example.datomatic.api.ApiClient
import com.example.datomatic.models.Prescription
import com.example.datomatic.models.Reports
import com.example.datomatic.utils.Resource
import com.example.datomatic.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ReportViewModel(app: Application) : AndroidViewModel(app) {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private val errorMessage = app.resources.getString(R.string.no_internet)


    val newsLiveData: MutableLiveData<Resource<Reports>> = MutableLiveData()
    init{
        getNews(getApplication())
    }
    @RequiresApi(Build.VERSION_CODES.M)

    private fun getNews(context: Context) =
        viewModelScope.launch {
            safeGetNewsCall(context)
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun safeGetNewsCall(context: Context) {
        newsLiveData.postValue(Resource.Loading())
        try {

            apiClient = ApiClient()
            sessionManager=SessionManager(context)
            val token="Bearer "+sessionManager.getToken()

                apiClient.getApiService().getReports(token).enqueue(object: Callback<Reports> {

                    override fun onResponse(
                        call: Call<Reports>,
                        response: Response<Reports>
                    ) {
                        response.body().let { res ->
                            newsLiveData.postValue(Resource.Success(res))
                        }
                    }

                    override fun onFailure(call: Call<Reports>, t: Throwable) {
                        newsLiveData.postValue(Resource.Error(errorMessage))
                    }


                })



        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsLiveData.postValue(Resource.Error(errorMessage))
                else -> newsLiveData.postValue(Resource.Error(errorMessage))
            }
        }

    }
}