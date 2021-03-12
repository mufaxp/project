package com.labhidrogen.githubuser.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUserViewModel : ViewModel() {

    private val listDetailUser = MutableLiveData<ArrayList<DetailUser>>()

    fun setDetailUser(user: String) {
        val items = ArrayList<DetailUser>()

        val url = "https://api.github.com/users/$user"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 9fe320f9302135683d17087d22a4ecce333fb901")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val detailUser = DetailUser()
                    detailUser.username = responseObject.getString("login")
                    detailUser.id = responseObject.getInt("id").toString()
                    detailUser.avatar = responseObject.getString("avatar_url")
                    detailUser.fullname = responseObject.getString("name")
                    detailUser.location = responseObject.getString("location")
                    detailUser.company = responseObject.getString("company")
                    detailUser.repo = responseObject.getString("public_repos")
                    items.add(detailUser)
                    listDetailUser.postValue(items)
                } catch (e: Exception) {
                    Log.d("Ouch...!!!", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("Ouch...!!!", errorMessage)
            }
        })
    }

    fun getDetailUser(): LiveData<ArrayList<DetailUser>>{
        return listDetailUser
    }
}