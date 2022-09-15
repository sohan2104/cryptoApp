package com.firstapp.ryptoapp.apis

import com.firstapp.ryptoapp.model.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface APiInterface {

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
  suspend fun getMarketData() : Response<MarketModel>

}