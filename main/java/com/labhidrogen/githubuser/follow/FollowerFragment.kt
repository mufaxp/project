package com.labhidrogen.githubuser.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.labhidrogen.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var _binding: FragmentFollowerBinding
    private val binding get() = _binding
    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var adapter: FollowerAdapter

    companion object {
        var EXTRA_USER = "extra_user"

        fun newInstance(username: String?):FollowerFragment{
            val fragment = FollowerFragment()
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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_follower, container, false)
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        binding.rvFollower.setHasFixedSize(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            val username = arguments?.getString(EXTRA_USER)
            followerViewModel = ViewModelProvider(this).get(
                FollowerViewModel::class.java
            )

            if (username != null){
                followerViewModel.setListFollower(username)
            }

            followerViewModel.getListFollower().observe(viewLifecycleOwner, {followerItems->
                if (followerItems != null){
                    adapter.setData(followerItems)
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecycleList()
    }

    private fun showRecycleList() {
        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollower.layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.adapter = adapter
    }
}
