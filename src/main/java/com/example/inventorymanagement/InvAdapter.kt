package com.example.inventorymanagement

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlin.collections.ArrayList
import android.view.LayoutInflater

class InvAdapter (private val invList: ArrayList<Model>) :
    RecyclerView.Adapter<InvAdapter.ViewHolder> () {

        private lateinit var mListener :onItemClickListener

        interface onItemClickListener{
            fun onItemClick(position: Int)
        }
    fun setOnItemsClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    //emp_list_item is refer to layout named emp _list_item
    //design file refer to emp_list_item
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.inv_list_item, parent, false)
        return ViewHolder (itemView, mListener)
    }
    //component that had at layout emp_list_item
    // empName = editText
    // empAge= editText
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val currentInv= invList[position]
        holder.tvName.text = currentInv.name
        holder.tvQuantity.text = currentInv.quantity
        holder.tvPrice.text = currentInv.price
        holder.tvTotalPrice.text = currentInv.totalPrice
    }
    override fun getItemCount() : Int {
        return invList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.findViewById(R.id.tvName)
        val tvQuantity : TextView = itemView.findViewById(R.id.tvQuantity)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        val tvTotalPrice : TextView = itemView.findViewById(R.id.tvTotalPrice)

        init {
            itemView.setOnClickListener { clickListener.onItemClick(adapterPosition)
            }
        }

    }

}