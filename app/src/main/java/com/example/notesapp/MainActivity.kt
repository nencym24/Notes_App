package com.example.notesapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Adapters.NotesAdapter
import com.example.notesapp.Database.dbHelper
import com.example.notesapp.Entity.NotesEntity
import com.example.notesapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var db: dbHelper
    lateinit var adapter: NotesAdapter
    var choosecolor = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = dbHelper.init(this)

        initview()

        binding.addNotes.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, AddNotesActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initview() {

        adapter = NotesAdapter {
            var isPin = false
            if(it.pin){
                isPin = false
            }else{
                isPin = true
            }

            var data = NotesEntity(it.title,it.text,it.date,isPin,it.choosecolor)
            data.id = it.id
            db.notes().updateNotes(data)
            adapter.update(filternote(db.notes().getNotes()))
        }
        adapter.update(filternote(db.notes().getNotes()))
        binding.rcvNotes.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.rcvNotes.adapter = adapter

    }

    private fun filternote(list : List<NotesEntity>) : ArrayList<NotesEntity>{
        var newlist = ArrayList<NotesEntity>()
        for (l in list) {
            if (l.pin) {
                newlist.add(l)
            }
        }
        for (l in list) {
            if (!l.pin) {
                newlist.add(l)
            }
        }
        return newlist
    }
    
}