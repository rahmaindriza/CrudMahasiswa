package com.indri.crudmahasiswa.sreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.indri.crudmahasiswa.R
import com.indri.crudmahasiswa.databinding.ActivityUpdateMahasiswaBinding
import com.indri.crudmahasiswa.helper.MahasiswaDatabaseHelper
import com.indri.crudmahasiswa.model.ModelMahasiswa

class UpdateMahasiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateMahasiswaBinding
    private lateinit var db: MahasiswaDatabaseHelper
    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)
        var noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        val mahasiswa = db.getMahasiswaById(noteId)
        binding.etEditNama.setText(mahasiswa.nama)
        binding.etEditNIM.setText(mahasiswa.nim)
        binding.etEditJurusan.setText(mahasiswa.jurusan)

        binding.btnUpdateNote.setOnClickListener() {
            val nama = binding.etEditNama.text.toString()
            val nim = binding.etEditNIM.text.toString()
            val jurusan = binding.etEditJurusan.text.toString()

            val updateMahasiswa = ModelMahasiswa(noteId, nama, nim, jurusan)
            db.updateMahasiswa(updateMahasiswa)
            finish()
        }
    }
}