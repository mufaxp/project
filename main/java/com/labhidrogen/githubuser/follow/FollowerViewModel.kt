package com.labhidrogen.githubuser.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowerViewModel : ViewModel() {

    private val listFollower = MutableLiveData<ArrayList<Follower>>()
    companion object{
        private val TAG = FollowerViewModel::class.java.simpleName
    }

    fun setListFollower(user: String) {
        val listItems = ArrayList<Follower>()

        val url = "https://api.github.com/users/$user/followers"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ganti ama punya abang yaak hehehe")
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
                        val followerItems = Follower()
                        followerItems.username = data.getString("login")
                        followerItems.id = data.getInt("id").toString()
                        followerItems.avatar = data.getString("avatar_url")
                        listItems.add(followerItems)
                    }
                    listFollower.postValue(listItems)
                    Log.d(TAG, "onSuccess: Finished")
                } catch (e: Exception) {
                    Log.d(TAG, "exception: ${e.message.toString()}")
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

    fun getListFollower(): LiveData<ArrayList<Follower>> {
        return listFollower
    }
}
