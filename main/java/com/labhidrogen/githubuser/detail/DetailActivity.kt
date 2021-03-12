package com.labhidrogen.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.labhidrogen.githubuser.R
import com.labhidrogen.githubuser.databinding.ActivityDetailBinding
import com.labhidrogen.githubuser.follow.FollowerFragment

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var title: String
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var adapter: DetailUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDetail.setHasFixedSize(true)

        //title
        title = resources.getString(R.string.profile)
        setActionBarTitle(title)

        //getString from intent
        val username = intent.getStringExtra(EXTRA_USERNAME)

        showLoading(true)

        //ViewModel
        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        if (username != null) {
            detailUserViewModel.setDetailUser(username)
        }

        detailUserViewModel.getDetailUser().observe(this, { detailItems ->
            if (detailItems != null) {
                adapter.setData(detailItems)
                showLoading(false)
            }
        })
        showRecycleList()

        //Fragment
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tab
        TabLayoutMediator(tabs, viewPager) { tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showRecycleList() {
        adapter = DetailUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvDetail.layoutManager = LinearLayoutManager(this)
        binding.rvDetail.adapter = adapter

    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.tab.visibility = View.INVISIBLE
            binding.viewPager.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.tab.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
        }
    }
}