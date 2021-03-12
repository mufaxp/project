package com.labhidrogen.githubuser.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.labhidrogen.githubuser.databinding.ItemFollowerBinding

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    private val mData = ArrayList<Follower>()

    fun setData(items: ArrayList<Follower>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemFollowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: Follower) {
            with(binding) {
                Glide.with(itemView)
                    .load(follower.avatar)
                    .apply(
                        RequestOptions()
                            .fitCenter()
                            .override(SIZE_ORIGINAL)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(imgUser)
                tvUsername.text = follower.username
                tvId.text = id(follower.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    private fun id(data: String?): String {
        return "ID : $data"
    }
}