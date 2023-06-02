package com.parkkh.tp_exchangerateapp

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.parkkh.tp_exchangerateapp.databinding.FragmentCalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CalFragment : Fragment() {

    lateinit var binding: FragmentCalBinding
    lateinit var boardItem: MutableList<BoardItem>
    lateinit var dialogItem: Array<String>
    lateinit var dialogItem2: Array<String>
    var selectedItem: String? = null
    var selectedPrice: String? = null





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalBinding.inflate(inflater, container, false)

        retrofit()

        //dialogItem = getData()

        //println("나는 빡대가리다 나는빡대가리다 나는붕어대가리다"+ dialogItem)

        binding.tv1.setOnClickListener {
            showDialog()
            binding.tv2.setText("1")
        }

        binding.btnConvert.setOnClickListener {
            convertFiat()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun retrofit() {

        val now = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ISO_DATE
        val nowDate = now.format(formatDate)



        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.koreaexim.go.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitservice: BoardAPIRetrofitService =
            retrofit.create(BoardAPIRetrofitService::class.java)
        val call: Call<MutableList<BoardItem>> = retrofitservice.getInfo(nowDate)

        call.enqueue(object : Callback<MutableList<BoardItem>> {
            override fun onResponse(
                call: Call<MutableList<BoardItem>>,
                response: Response<MutableList<BoardItem>>
            ) {
                var boardItem2: MutableList<BoardItem>? = response.body()
                Toast.makeText(
                    requireContext(),
                     /*boardItem2?.size.toString()*/ "로딩완료",
                    Toast.LENGTH_SHORT
                ).show()
                if (boardItem2 != null) {
                    boardItem = boardItem2
                    dialogItem = getData()
                    dialogItem2 = getData2()
                }


            }

            override fun onFailure(call: Call<MutableList<BoardItem>>, t: Throwable) {
                Toast.makeText(requireContext(), "로딩실패", Toast.LENGTH_SHORT).show()
            }
        })


    }

    fun showDialog() {
        if(!this::dialogItem.isInitialized){
//            Toast 아직 어쩌고 넣어주면 됨
            //Toast.makeText(requireContext(), "아직 어쩌고..", Toast.LENGTH_SHORT).show()
            return
        }//if 1

        var items = dialogItem
        var items2 = dialogItem2

        AlertDialog.Builder(requireContext()).run {

            setTitle("통화선택")
                .setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(
                            requireContext(),
                            "${items[p1]}선택",
                            Toast.LENGTH_SHORT
                        ).show()
                        selectedItem = items[p1]
                        selectedPrice = items2[p1]

                    }
                })

                .setPositiveButton("yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        binding.tv1.text = selectedItem.toString()
                        binding.tv4.setText(selectedPrice)
                    }

                })

                .setNegativeButton("no", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        //Toast.makeText(requireContext(), "꺼짐~~~~", Toast.LENGTH_SHORT).show()
                    }

                })

            show()
        }

    }

    fun getData(): Array<String> {
        var stringArr: Array<String> = arrayOf()


        if (this::boardItem.isInitialized) {
            for (datas in boardItem) {
                stringArr = stringArr.plus(datas.cur_nm)
            }//for
            return stringArr
        } else {
            Toast.makeText(requireContext(), "서버에서 데이터를 아직 받아오지 않았습니다. ", Toast.LENGTH_SHORT).show()
            return arrayOf()
        }


    }/* getData() */

    fun getData2(): Array<String> {
        var stringArr: Array<String> = arrayOf()

        if (this::boardItem.isInitialized) {
            for (datas in boardItem) {
                stringArr = stringArr.plus(datas.deal_bas_r)
            }//for
            return stringArr
        } else {
            Toast.makeText(requireContext(), "서버에서 데이터를 아직 받아오지 않았습니다. ", Toast.LENGTH_SHORT).show()
            return arrayOf()
        }
    }

    fun convertFiat() {

        var result = 0.0

        if (binding.tv2.text.toString().equals("") || binding.tv4.text.toString().equals("")) {
            Toast.makeText(requireContext(), "값이 없습니다...", Toast.LENGTH_SHORT).show()
        } else {
            //var result : Int = Integer.parseInt(binding.tv2.text.toString()) * Integer.parseInt(binding.tv4.text.toString())

            var s1: String = binding.tv2.text.toString()
            var s2: String = selectedPrice!!.replace(",", "")
            //var s2 : String = binding.tv4.text.toString().replace(",","") // ,때문에 오류나니까.. 없앰.

            result = s1.toDouble() * s2.toDouble()

            binding.tv4.setText(result.toString())

        }

    }











}// class


