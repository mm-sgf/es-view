package com.cfox.esview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cfox.es_view.R
import com.cfox.esview.MainFragment.*

class MainFragment : Fragment(R.layout.fragment_main), ItemClickListener {
    companion object {
        private val items = mutableListOf(
            Item("渐变文字", R.id.action_mainFragment_to_gradientTextFragment),
            Item("会飞的红包", R.id.action_mainFragment_to_trackFlyFragment)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewList = view.findViewById<RecyclerView>(R.id.view_list)
        viewList.adapter = ItemAdapter(this)
        viewList.layoutManager = LinearLayoutManager(context)
    }



    data class Item(val name :String, val navId :Int)

    private class ItemAdapter(val listener: ItemClickListener) : RecyclerView.Adapter<ItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_recycler, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.onBind(items[position], listener)
        }
    }

    private class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameView :Button = itemView.findViewById(R.id.btn_view_name)

        fun onBind(item: Item, listener: ItemClickListener) {
            nameView.text = item.name
            nameView.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    override fun onItemClick(item: Item) {
        findNavController().navigate(item.navId)
    }


}

interface ItemClickListener {
    fun onItemClick(item :Item)
}