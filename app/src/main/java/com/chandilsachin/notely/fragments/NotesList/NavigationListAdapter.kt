package com.chandilsachin.notely.fragments.NotesList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chandilsachin.notely.R
import com.chandilsachin.personal_finance.database.entities.Note
import kotlinx.android.synthetic.main.list_item_navigation_drawer.view.*

/**
 * Created by sachin on 28/1/18.
 */
class NavigationListAdapter(context: Context?, var list: List<String>) : RecyclerView.Adapter<NavigationListAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    val selectedItems = mutableListOf<Int>()
    //private var onMenuItemClickListener: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NavigationListAdapter.ViewHolder {
        if (viewType == VIEW_TYPE_SELECTED)
            return ViewHolder(inflater.inflate(R.layout.list_item_navigation_drawer_selected, parent, false))
        else
            return ViewHolder(inflater.inflate(R.layout.list_item_navigation_drawer, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (selectedItems.contains(position)) VIEW_TYPE_SELECTED else VIEW_TYPE_DESELECTED
    }

    override fun getItemCount(): Int = list.size

    fun getItem(position: Int): String = list[position]

    override fun onBindViewHolder(holder: NavigationListAdapter.ViewHolder?, position: Int) {
        holder?.bind(getItem(position))
        holder?.itemView?.setOnClickListener {
            if(selectedItems.contains(position))
                selectedItems.remove(position)
            else
                selectedItems.add(position)
            //onMenuItemClickListener(position)
            notifyItemChanged(position)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(labelText: String){
            itemView.tvLabel.text = labelText
        }
    }

    companion object {
        const val VIEW_TYPE_SELECTED = 1;
        const val VIEW_TYPE_DESELECTED = 2;
    }
}