package com.parkkh.tp_exchangerateapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardAPIRetrofitService {
    @GET("/site/program/financial/exchangeJSON?authkey=hyYNgHl3GrwRPTspXZ2awmqgRTBfgn9J&data=AP01")
    fun getInfo(@Query("searchdate") date:String): Call<MutableList<BoardItem>>

    //@GET("/site/program/financial/exchangeJSON?authkey=hyYNgHl3GrwRPTspXZ2awmqgRTBfgn9J&data=AP01")
    //fun aaa(@Query("searchdate") date:String): Call<String>
}