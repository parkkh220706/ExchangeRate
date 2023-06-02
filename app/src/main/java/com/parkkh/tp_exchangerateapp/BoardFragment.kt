package com.parkkh.tp_exchangerateapp

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.parkkh.tp_exchangerateapp.databinding.FragmentBoardBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BoardFragment : Fragment() {

    lateinit var binding: FragmentBoardBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val now = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ISO_DATE
        val nowDate = now.format(formatDate)

        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://www.koreaexim.go.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService: BoardAPIRetrofitService = retrofit.create(BoardAPIRetrofitService::class.java)
        val call:Call<MutableList<BoardItem>> = retrofitService.getInfo(nowDate)

        call.enqueue(object : Callback<MutableList<BoardItem>>{
            override fun onResponse(
                call: Call<MutableList<BoardItem>>,
                response: Response<MutableList<BoardItem>>
            ) {
                var boardItems:MutableList<BoardItem>? = response.body()

                Toast.makeText(requireContext(), "로딩완료"/*boardItems?.size.toString()*/, Toast.LENGTH_SHORT).show()

                binding.recyclerBoard.adapter=MyExchangeAdapter(requireContext(), boardItems!!)
                //binding.recyclerBoard.adapter= MyExchangeAdapter(requireContext(), boardItems!!)
            }

            override fun onFailure(call: Call<MutableList<BoardItem>>, t: Throwable) {
                Toast.makeText(requireContext(), "로딩실패", Toast.LENGTH_SHORT).show()
            }

        })

//        val call:Call<String> = retrofitService.aaa("20221109")
//
//        call.enqueue( object : Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                var s:String?= response.body()
//                AlertDialog.Builder(requireContext()).setMessage(s!!).show()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(requireContext(), "aaa", Toast.LENGTH_SHORT).show()
//            }
//        })


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentBoardBinding.bind(view)
    }


}