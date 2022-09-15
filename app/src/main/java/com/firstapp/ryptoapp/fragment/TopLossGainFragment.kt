package com.firstapp.ryptoapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.firstapp.ryptoapp.adapter.MarketAdapter
import com.firstapp.ryptoapp.apis.APiInterface
import com.firstapp.ryptoapp.apis.ApiUtilitied
import com.firstapp.ryptoapp.databinding.FragmentTopLossGainBinding
import com.firstapp.ryptoapp.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlin.collections.ArrayList

class TopLossGainFragment : Fragment() {

    lateinit var binding: FragmentTopLossGainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {

        val position = requireArguments().getInt("position")

            lifecycleScope.launch(Dispatchers.IO){
                val res = ApiUtilitied.getInstance().create(APiInterface:: class.java).getMarketData()

                if (res.body() != null) {

                    withContext(Dispatchers.Main){
                        val dataItem = res.body()!!.data.cryptoCurrencyList
//
//                        Collections.sort(dataItem){
//                           o1,o2 -> (o2.quote[0].percentChange24h.toInt())
//                            .compareTo(o1.quote[0].percentChange24h.toInt())
//                        }

                        binding.spinKitView.visibility = GONE
                        val list = ArrayList<CryptoCurrency>()

                        if (position == 0){
                            list.clear()
                            for (i in 0..9) {
                                list.add(dataItem[i])
                            }

                      binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                          requireContext(),
                          list,
                          "home" )
                        } else {
                            list.clear()
                            for (i in 0..9) {
                                list.add(dataItem[dataItem.size-1-i])
                            }
                         binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                             requireContext(),
                             list,
                             "home")
                        }

                    }
                }
            }
    }
}