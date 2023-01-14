package com.example.datomatic.ui.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.datomatic.R
import com.example.datomatic.api.ApiClient
import com.example.datomatic.models.Doctor_list
import com.example.datomatic.utils.Resource
import com.example.datomatic.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.M)
class HomeViewModel(app:Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private val errorMessage =
        app.resources.getString(R.string.no_internet)
    val text: LiveData<String> = _text
    val newsLiveData: MutableLiveData<Resource<Doctor_list>> = MutableLiveData()
  init{
      getNews(getApplication())
  }
    @RequiresApi(Build.VERSION_CODES.M)

    private fun getNews(context:Context) =
        viewModelScope.launch {
            safeGetNewsCall(context)
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun safeGetNewsCall(context:Context) {
        newsLiveData.postValue(Resource.Loading())
        try {

                apiClient = ApiClient()
                sessionManager=SessionManager(context)
                val token="Bearer "+sessionManager.getToken()
                apiClient.getApiService().getDoctor(token).
                enqueue(object: Callback<Doctor_list>{

                    override fun onResponse(
                        call: Call<Doctor_list>,
                        response: Response<Doctor_list>
                    ) {
                        response.body().let { res ->
                            newsLiveData.postValue(Resource.Success(res))
                        }
                    }

                    override fun onFailure(call: Call<Doctor_list>, t: Throwable) {
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager =Application().applicationContext
            .getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(
            activeNetwork
        ) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}