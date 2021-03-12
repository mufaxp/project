package com.labhidrogen.githubuser.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<Following>>()

    companion object {
        private val TAG = FollowingViewModel::class.java.simpleName
    }

    fun setListFollowing(user: String) {
        val listItems = ArrayList<Following>()

        val url = "https://api.github.com/users/$user/following"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ganti ama yang punya abang yaak hehehe")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                Log.d(TAG, "onSuccess: Starting")
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val data = responseArray.getJSONObject(i)
                        val followingItems = Following()
                        followingItems.username = data.getString("login")
                        followingItems.id = data.getInt("id").toString()
                        followingItems.avatar = data.getString("avatar_url")
                        listItems.add(followingItems)
                    }
                    listFollowing.postValue(listItems)
                    Log.d(TAG, "onSuccess: done")
                } catch (e: Exception) {
                    Log.d(TAG, "onSuccess: ${e.message.toString()}")
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
                Log.d(TAG, " onFailure : $errorMessage")
            }
        })
    }

    fun getListFollowing(): LiveData<ArrayList<Following>> {
        return listFollowing
    }
}
