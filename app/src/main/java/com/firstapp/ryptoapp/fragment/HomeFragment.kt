package com.firstapp.ryptoapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.firstapp.ryptoapp.R
import com.firstapp.ryptoapp.adapter.TopLossGainPagerAdapter
import com.firstapp.ryptoapp.adapter.TopMarketAdapter
import com.firstapp.ryptoapp.apis.APiInterface
import com.firstapp.ryptoapp.apis.ApiUtilitied
import com.firstapp.ryptoapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencyList()

        setTabLayout()

        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                } else{
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.contentViewPager) {
            tab, position->
            var title = if (position == 0){
                "Top Gainers"
            } else {
                "Top Losers"
            }
            tab.text = title
        }.attach()

    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO){
            val res= ApiUtilitied.getInstance().create(APiInterface::class.java).getMarketData()

            withContext(Dispatchers.Main){
                binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }

            Log.d("MONU","getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }
}