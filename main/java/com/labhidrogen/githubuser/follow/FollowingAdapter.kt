package com.labhidrogen.githubuser.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.labhidrogen.githubuser.databinding.ItemFollowingBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private val mData = ArrayList<Following>()

    fun setData(items: ArrayList<Following>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(following: Following) {
            with(binding) {
                Glide.with(itemView)
                    .load(following.avatar)
                    .apply(
                        RequestOptions()
                            .fitCenter()
                            .override(SIZE_ORIGINAL)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(imgFollowing)
                tvFollowing.text = following.username
                tvId.text = id(following.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int =mData.size

    private fun id(data: String?): String {
        return "ID : $data"
    }
}