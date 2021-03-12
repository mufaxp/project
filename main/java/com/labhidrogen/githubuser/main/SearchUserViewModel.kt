package com.labhidrogen.githubuser.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class SearchUserViewModel: ViewModel() {

    private val listUser = MutableLiveData<ArrayList<User>>()

    fun setListUser(user: String){
        val listItems = ArrayList<User>()

        val url = "https://api.github.com/search/users?q=$user"
        val client = AsyncHttpClient()
        client.addHeader("Authorization","token 9fe320f9302135683d17087d22a4ecce333fb901")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()){
                        val data = list.getJSONObject(i)
                        val userItems = User()
                        userItems.username = data.getString("login")
                        userItems.id = data.getInt("id").toString()
                        userItems.avatar = data.getString("avatar_url")
                        listItems.add(userItems)
                    }
                    listUser.postValue(listItems)
                } catch (e: Exception){
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

    fun getListUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}