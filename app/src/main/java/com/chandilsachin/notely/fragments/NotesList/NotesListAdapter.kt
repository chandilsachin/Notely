package com.chandilsachin.notely.fragments.NotesList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ace.diettracker.util.fromHtml
import com.chandilsachin.notely.R
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.notes_list_item.view.*

/**
 * Created by sachin on 28/1/18.
 */
class NotesListAdapter(context: Context?, var list: List<Note>) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context);
    var onItemClickListener: (note: Note) -> Unit = {}
    var onFavoriteClickListener: (note: Note) -> Single<Boolean> = { Single.create { } }
    var onStarClickListener: (note: Note) -> Single<Boolean> = { Single.create { } }
    var onDeleteItemClickListener: (note: Note) -> Unit = {  }
    var isFavorite: (note: Note) -> Single<Boolean> = { Single.create { } }
    var isStarred: (note: Note) -> Single<Boolean> = { Single.create { } }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(inflater.inflate(R.layout.notes_list_item, null, false))

        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        viewHolder.itemView.setLayoutParams(lp)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    fun getItem(position: Int): Note = list.get(position)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(getItem(position))
        holder?.itemView?.linearLayout?.setOnClickListener { onItemClickListener(getItem(position)) }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.apply {
                textViewTitle.text = fromHtml(note.title)
                textViewDescription.text = fromHtml(note.noteText.trim())
                textViewTimestamp.text = DateUtils.formatDateRange(context, note.timestamp.time,
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME)

                imageViewFavorite.setOnClickListener {
                    onFavoriteClickListener(note).observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                initFavViews(it, imageViewFavorite)
                            }, { it.printStackTrace() })
                }

                imageView2.setOnClickListener {
                    onStarClickListener(note).observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                initStarredViews(it, imageView2)
                            }, { it.printStackTrace() })
                }
                isFavorite(note).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            initFavViews(it, imageViewFavorite)
                        }, {})
                isStarred(note).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            initStarredViews(it, imageView2)
                        }, {})
                ivDelete.setOnClickListener {
                    onDeleteItemClickListener(note)
                }
            }
        }

        fun initFavViews(isFavorite: Boolean, view: ImageView) {
            if (isFavorite) view.setImageResource(R.drawable.ic_favorite_selected_24dp)
            else view.setImageResource(R.drawable.ic_favorite_deselected_24dp)
        }

        fun initStarredViews(isStarred: Boolean, view: ImageView) {
            if (isStarred) view.setImageResource(R.drawable.ic_star_selected_24dp)
            else view.setImageResource(R.drawable.ic_star_deselected_24dp)
        }
    }
}