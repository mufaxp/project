package com.labhidrogen.githubuser.follow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.labhidrogen.githubuser.databinding.FollowingFragmentBinding

class FollowingFragment : Fragment() {
    private lateinit var _binding: FollowingFragmentBinding
    private val binding get() = _binding
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: FollowingAdapter

    companion object {
        var EXTRA_USER = "extra_user"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USER, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowingFragmentBinding.inflate(inflater, container, false)
        binding.rvFollowing.setHasFixedSize(true)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            val username = arguments?.getString(EXTRA_USER)
            followingViewModel = ViewModelProvider(this).get(
                FollowingViewModel::class.java)
            if (username != null){
                followingViewModel.setListFollowing(username)
            }
            followingViewModel.getListFollowing().observe(viewLifecycleOwner, { followingItems->
                if(followingItems != null){
                    adapter.setData(followingItems)
                }
            })
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecycleList()
    }

    private fun showRecycleList() {
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
    }
}