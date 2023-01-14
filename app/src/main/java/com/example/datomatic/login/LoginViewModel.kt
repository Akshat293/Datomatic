package com.example.datomatic.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.data.LoginRepository
import com.data.Result
import retrofit2.Callback
import com.example.datomatic.R
import com.example.datomatic.api.ApiClient
import com.example.datomatic.models.Credentials
import com.example.datomatic.models.Token
import com.example.datomatic.utils.SessionManager
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    fun login(username: String, password: String,context: Context) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)
        Log.d("main", username)
        if (result is Result.Success) {
            val credentials=Credentials(username,password)
            apiClient = ApiClient()
            sessionManager=SessionManager(context)
            apiClient.getApiService().getToken(credentials)
                .enqueue(object:Callback<Token>{
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            sessionManager.saveToken(loginResponse.token)
                            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                        }else{
                            _loginResult.value = LoginResult(error = R.string.login_failed)
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        _loginResult.value = LoginResult(error = R.string.login_failed)
                    }
                })

        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}




