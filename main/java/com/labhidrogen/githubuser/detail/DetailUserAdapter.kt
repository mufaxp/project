package com.labhidrogen.githubuser.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.labhidrogen.githubuser.databinding.DetailUserBinding

class DetailUserAdapter : RecyclerView.Adapter<DetailUserAdapter.ListViewHolder>() {
    private val mData = ArrayList<DetailUser>()
//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: DetailUser)
//
//    }
//
//    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    fun setData(items: ArrayList<DetailUser>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            DetailUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ListViewHolder(private val binding: DetailUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: DetailUser) {
            with(binding) {
                Glide.with(itemView)
                    .load(detail.avatar)
                    .apply(
                        RequestOptions()
                            .fitCenter()
                            .override(SIZE_ORIGINAL)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(imgUser)
                tvUsername.text = detail.username
                tvId.text = id(detail.id)
                tvName.text = name(detail.fullname)
                tvLocation.text = location(detail.location)
                tvCompany.text = company(detail.company)
                tvRepo.text = repo(detail.repo)
//                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(detail) }
            }
        }
    }

    private fun id(data: String?): String {
        return "ID : $data"
    }

    private fun name(data: String?): String {
        return when (data) {
            "null" -> {
                "Null, not added yet...!"
            }
            else -> {
                "$data"
            }
        }
    }

    private fun location(data: String?): String {
        return when (data) {
            "null" -> {
                "Location : Unknown"
            }
            else -> {
                "Location : $data"
            }
        }
    }

    private fun company(data: String?): String {
        return when (data) {
            "null" -> {
                "Company : Unknown"
            }
            else -> {
                "Company : $data"
            }
        }
    }

    private fun repo(data: String?): String {
        return "Repository : $data"
    }
}
