package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notesapp.Adapters.NotesAdapter
import com.example.notesapp.Database.dbHelper
import com.example.notesapp.Entity.NotesEntity
import com.example.notesapp.databinding.ActivityAddNotesBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import java.text.SimpleDateFormat
import java.util.Date

class AddNotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNotesBinding
    lateinit var db: dbHelper
    lateinit var adapter: NotesAdapter
    var choosecolor = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = dbHelper.init(this)

        binding.edtSave.setOnClickListener {
            var title = binding.edtTitle.text.toString()
            var text = binding.edtText.text.toString()
            var format = SimpleDateFormat("DD/MM/YYYY hh:mm:ss a ")
            var current = format.format(Date())
            var data = NotesEntity(title, text, current,false,choosecolor)
            db.notes().addNotes(data)
            adapter.update(filternote(db.notes().getNotes()))
            finish()
        }
        binding.chooseColor.setOnClickListener {

            // Kotlin Code
            MaterialColorPickerDialog
                .Builder(this)                            // Pass Activity Instance
                .setTitle("Pick Theme")                // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)    // Default ColorShape.CIRCLE
                .setColorSwatch(ColorSwatch._300)    // Default ColorSwatch._500
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    this.choosecolor = color
                }
                .show()
        }


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