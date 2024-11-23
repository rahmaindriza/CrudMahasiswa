package com.indri.crudmahasiswa.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.indri.crudmahasiswa.model.ModelMahasiswa

class MahasiswaDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mahasiswaapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "mahasiswa"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_JURUSAN = "jurusan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAMA TEXT, $COLUMN_NIM TEXT, $COLUMN_JURUSAN TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertMahasiswa(note: ModelMahasiswa) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, note.nama)
            put(COLUMN_NIM, note.nim)
            put(COLUMN_JURUSAN, note.jurusan)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // get semua data dari database
    fun getAllMahasiswa(): List<ModelMahasiswa> {
        val noteList = mutableListOf<ModelMahasiswa>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME";
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
            val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
            val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))

            Log.d("Databasehelper", "fetched note-id $id, name $nama")

            val note = ModelMahasiswa(id, nama, nim, jurusan)
            noteList.add(note)
        }
        cursor.close()
        db.close()
        return noteList
    }

    fun updateMahasiswa(note: ModelMahasiswa) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, note.nama)
            put(COLUMN_NIM, note.nim)
            put(COLUMN_JURUSAN, note.jurusan)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getMahasiswaById(noteId: Int): ModelMahasiswa {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToNext()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
        val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
        val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))
        cursor.close()
        db.close()

        return ModelMahasiswa(id, nama, nim, jurusan)
    }

    fun deleteMahasiswa(noteId: Int) {
        val db =  writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}