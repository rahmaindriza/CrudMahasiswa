package com.indri.crudmahasiswa

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailMahasiswaActivity : AppCompatActivity() {

    private lateinit var txtDetailNama: TextView
    private lateinit var txtDetailNIM: TextView
    private lateinit var txtDetailJurusan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mahasiswa)

        // Inisialisasi View
        txtDetailNama = findViewById(R.id.txtDetailNama)
        txtDetailNIM = findViewById(R.id.txtDetailNIM)
        txtDetailJurusan = findViewById(R.id.txtDetailJurusan)

        // Mengambil data dari Intent
        val nama = intent.getStringExtra("nama")
        val nim = intent.getStringExtra("nim")
        val jurusan = intent.getStringExtra("jurusan")

        // Menampilkan data ke TextView
        txtDetailNama.text = nama
        txtDetailNIM.text = nim
        txtDetailJurusan.text = jurusan

        // Menambahkan padding untuk sistem bar (opsional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}