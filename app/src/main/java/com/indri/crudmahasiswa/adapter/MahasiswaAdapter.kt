package com.indri.crudmahasiswa.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.indri.crudmahasiswa.DetailMahasiswaActivity
import com.indri.crudmahasiswa.R
import com.indri.crudmahasiswa.helper.MahasiswaDatabaseHelper
import com.indri.crudmahasiswa.model.ModelMahasiswa
import com.indri.crudmahasiswa.sreen.UpdateMahasiswaActivity

class MahasiswaAdapter(
    private var mahasiswa: List<ModelMahasiswa>,
    context: Context
): RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    private val db : MahasiswaDatabaseHelper = MahasiswaDatabaseHelper(context)

    class MahasiswaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtNama: TextView = itemView.findViewById(R.id.txtNama)
        val txtNIM: TextView = itemView.findViewById(R.id.txtNIM)
        val txtJurusan: TextView = itemView.findViewById(R.id.txtJurusan)
        val cardMahasiswa: CardView = itemView.findViewById(R.id.cardMahasiswa)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa, parent, false)

        return MahasiswaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mahasiswa.size
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mahasiswaData = mahasiswa[position]
        holder.txtNama.text = mahasiswaData.nama
        holder.txtNIM.text = mahasiswaData.nim
        holder.txtJurusan.text = mahasiswaData.jurusan
        holder.cardMahasiswa.setOnClickListener() {
            val intent = Intent(holder.itemView.context, DetailMahasiswaActivity::class.java)
            intent.putExtra("nama", mahasiswaData.nama)
            intent.putExtra("nim", mahasiswaData.nim)
            intent.putExtra("jurusan", mahasiswaData.jurusan)

            holder.itemView.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener() {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Confirmation")
                setMessage("Do you want to continue ?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("Yes") {
                        dialogInterface, i->
                    db.deleteMahasiswa(mahasiswaData.id)
                    refreshData(db.getAllMahasiswa())
                    Toast.makeText(holder.itemView.context, "Note berhasil dihapus", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }

                setNeutralButton("No") {
                        dialogInterface, i->
                    dialogInterface.dismiss()
                }
            }.show()
        }

        holder.btnEdit.setOnClickListener() {
            val intent = Intent(holder.itemView.context, UpdateMahasiswaActivity::class.java).apply {
                putExtra("note_id", mahasiswaData.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    fun refreshData(newNotes: List<ModelMahasiswa>) {
        mahasiswa = newNotes
        notifyDataSetChanged()
    }

}