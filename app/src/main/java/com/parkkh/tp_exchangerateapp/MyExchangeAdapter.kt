package com.parkkh.tp_exchangerateapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyExchangeAdapter constructor(val context: Context, var items:MutableList<BoardItem>): RecyclerView.Adapter<MyExchangeAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cur_nm: TextView by lazy {itemView.findViewById(R.id.tv_title)}
        val cur_unit: TextView by lazy {itemView.findViewById(R.id.tv_code)}
        val deal_bas_r: TextView by lazy {itemView.findViewById(R.id.tv_price)}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView:View = layoutInflater.inflate(R.layout.board_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:BoardItem = items.get(position)

        holder.cur_nm.text = item.cur_nm
        holder.cur_unit.text = item.cur_unit
        holder.deal_bas_r.text = item.deal_bas_r+" 원"


    }

    override fun getItemCount(): Int = items.size

}
