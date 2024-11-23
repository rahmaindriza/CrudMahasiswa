package com.indri.crudmahasiswa.sreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.indri.crudmahasiswa.R
import com.indri.crudmahasiswa.databinding.ActivityAddMahasiswaBinding
import com.indri.crudmahasiswa.helper.MahasiswaDatabaseHelper
import com.indri.crudmahasiswa.model.ModelMahasiswa

class AddMahasiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMahasiswaBinding
    private lateinit var db: MahasiswaDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)

        // Tombol Simpan Mahasiswa
        binding.saveButton.setOnClickListener {
            val nama = binding.namaEditText.text.toString()
            val nim = binding.nimEditText.text.toString()
            val jurusan = binding.jurusanEditText.text.toString()

            // Validasi Input
            if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mahasiswa = ModelMahasiswa(0, nama, nim, jurusan)
            db.insertMahasiswa(mahasiswa)
            finish()
            Toast.makeText(this, "Data Mahasiswa disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}