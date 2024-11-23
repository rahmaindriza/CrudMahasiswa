package com.indri.crudmahasiswa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.indri.crudmahasiswa.adapter.MahasiswaAdapter
import com.indri.crudmahasiswa.databinding.ActivityMainBinding
import com.indri.crudmahasiswa.helper.MahasiswaDatabaseHelper
import com.indri.crudmahasiswa.sreen.AddMahasiswaActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    private lateinit var db: MahasiswaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)
        mahasiswaAdapter = MahasiswaAdapter(db.getAllMahasiswa(), this)

        binding.mahasiswaRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mahasiswaRecyclerView.adapter = mahasiswaAdapter

        binding.addMahasiswaButton.setOnClickListener {
            val intent = Intent(this, AddMahasiswaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val mahasiswaList = db.getAllMahasiswa()
        mahasiswaAdapter.refreshData(mahasiswaList)
    }
}