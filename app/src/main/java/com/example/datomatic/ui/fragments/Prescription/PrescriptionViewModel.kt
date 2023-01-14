package com.example.datomatic.ui.fragments.Prescription

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.datomatic.R
import com.example.datomatic.api.ApiClient
import com.example.datomatic.models.Prescription
import com.example.datomatic.utils.Resource
import com.example.datomatic.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.M)
class PrescriptionViewModel(app: Application) : AndroidViewModel(app) {
    // TODO: Implement the ViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private val errorMessage =
        app.resources.getString(R.string.no_internet)

    val newsLiveData: MutableLiveData<Resource<Prescription>> = MutableLiveData()
    init{
        getNews(getApplication())
    }
    @RequiresApi(Build.VERSION_CODES.M)

    private fun getNews(context:Context) =
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
            val doc_id=sessionManager.getDocid()
            if (doc_id != null) {
                apiClient.getApiService().getPrescription(token,doc_id).enqueue(object: Callback<Prescription> {

                    override fun onResponse(
                        call: Call<Prescription>,
                        response: Response<Prescription>
                    ) {
                        response.body().let { res ->
                            newsLiveData.postValue(Resource.Success(res))
                        }
                    }

                    override fun onFailure(call: Call<Prescription>, t: Throwable) {
                        newsLiveData.postValue(Resource.Error(errorMessage))
                    }


                })
            }


        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsLiveData.postValue(Resource.Error(errorMessage))
                else -> newsLiveData.postValue(Resource.Error(errorMessage))
            }
        }

    }
}