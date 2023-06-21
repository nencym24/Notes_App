package com.example.notesapp.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notesapp.Entity.NotesEntity
import com.example.notesapp.R
import com.example.notesapp.databinding.NotesItemBinding

class NotesAdapter(pin: (NotesEntity) -> Unit): Adapter<NotesAdapter.NotesHolder>() {
    var pinupdate = pin

    var notes = ArrayList<NotesEntity>()


    class NotesHolder(itemView: NotesItemBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        var binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.apply {
            txtTitle.isSelected = true
            card.setCardBackgroundColor(notes.get(position).choosecolor)
            notes.get(position).apply {
                txtTitle.text = title
                txtText.text = text
                if (pin) {
                    imgPin.setImageResource(R.drawable.pined)
                } else {
                    imgPin.setImageResource(R.drawable.unpin)
                }

                imgPin.setOnClickListener {
                    pinupdate.invoke(notes.get(position))
                }
                card.setCardBackgroundColor(notes.get(position).choosecolor)
            }
        }


    }
    fun update(notes: List<NotesEntity>) {
        this.notes= notes as ArrayList<NotesEntity>
        notifyDataSetChanged()
    }

    fun setNotes(notes: List<NotesEntity>) {
        this.notes = notes as ArrayList<NotesEntity>
    }
}