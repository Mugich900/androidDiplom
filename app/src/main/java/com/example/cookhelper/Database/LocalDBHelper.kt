package com.example.cookhelper.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocalDBHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION){
    companion object{
        private val DATABASE_NAME: String = "LocalDB"
        private val VERSION = 1
        val TABLE_BASKET = "basket"
        val TABLE_FAVORITE = "favorite"
        val COLUMN_ID = "_id"
        val COLUMN_NAME_BASKET = "Name"
        val COLUMN_NAME_FAVORITE = "Name"
        val COLUMN_COOKING_FAVORITE = "Cooking"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_BASKET ($COLUMN_ID " +
                "INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME_BASKET " +
                "TEXT);")
        db?.execSQL("CREATE TABLE $TABLE_FAVORITE ($COLUMN_ID " +
                "INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME_FAVORITE " +
                "TEXT, $COLUMN_COOKING_FAVORITE TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF " + TABLE_BASKET)
        db?.execSQL("DROP TABLE IF " + TABLE_FAVORITE)
        onCreate(db)
    }

    fun addToTable(table: String, data: Array<String>, columns: Array<String>){
        val db = this.writableDatabase
        val values = ContentValues()
        for ((i, d) in data.withIndex())
            values.put(columns[i], d)
        db?.insert(table, null, values)
        db?.close()
    }

    fun getFromLocalDB(table: String, columns: Array<String>): Cursor?{
        val db = this.readableDatabase
        return db.query(table, columns,
        null,null,null, null, null)
    }

    fun deleteFromLocalDB(ids: ArrayList<Int>, table: String){
        val db = this.writableDatabase
        for (id in ids){
            db.delete(table, COLUMN_ID + " = ?", arrayOf(id.toString()))
        }
    }
}
