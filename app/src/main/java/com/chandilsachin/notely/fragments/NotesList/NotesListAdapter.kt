package com.chandilsachin.notely.fragments.NotesList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chandilsachin.personal_finance.database.entities.Note

/**
 * Created by sachin on 28/1/18.
 */
class NotesListAdapter (context: Context, var list: List<Note>): RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context);


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.))
    }

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(){

        }
    }
}