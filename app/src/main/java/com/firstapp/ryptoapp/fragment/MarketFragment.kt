package com.firstapp.ryptoapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.firstapp.ryptoapp.adapter.MarketAdapter
import com.firstapp.ryptoapp.apis.APiInterface
import com.firstapp.ryptoapp.apis.ApiUtilitied
import com.firstapp.ryptoapp.databinding.FragmentMarketBinding
import com.firstapp.ryptoapp.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding

    private lateinit var list: List<CryptoCurrency>
    private lateinit var adapter: MarketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarketBinding.inflate(layoutInflater)


        list = listOf()

        adapter = MarketAdapter(requireContext(), list, "market")
        binding.currencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilitied.getInstance().create(APiInterface::class.java).getMarketData()

            if (res.body() != null) {
                withContext(Dispatchers.Main){
                    list = res.body()!!.data.cryptoCurrencyList

                    adapter.updateData(list)
                    binding.spinKitView.visibility = GONE
                }
            }
        }

        searchCoin()

        return binding.root
    }

    lateinit var searchText : String

    private fun searchCoin() {
          binding.searchEditText.addTextChangedListener(object : TextWatcher{
              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

              }

              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

              }

              override fun afterTextChanged(s: Editable?) {
                  searchText = s.toString().toLowerCase()

                  updateRecyclerView()
              }
          })
    }

    private fun updateRecyclerView() {
        val data= ArrayList<CryptoCurrency>()

        for (item in list) {
              val coinName = item.name.lowercase(Locale.getDefault())
            val coinSymbol = item.name.lowercase(Locale.getDefault())

            if (coinName.contains(searchText) || coinSymbol.contains(searchText)){
                 data.add(item)
            }
        }
        adapter.updateData(data)
    }
}