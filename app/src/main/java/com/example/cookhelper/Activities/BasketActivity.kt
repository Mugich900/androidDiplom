package com.example.cookhelper.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.cookhelper.Database.LocalDBHelper
import com.example.cookhelper.R

class BasketActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var myDB: LocalDBHelper
    private lateinit var basketLayout: LinearLayout
    private lateinit var listRemove: ArrayList<Int>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        editText = findViewById(R.id.editText)
        myDB = LocalDBHelper(this)
        basketLayout = findViewById(R.id.basketLayout)
        listRemove = ArrayList<Int>()
    }

    fun addToBasket(view: View){
        val name: String = editText.text.toString()
        try{
            myDB.addToTable(LocalDBHelper.TABLE_BASKET, arrayOf(name),
                arrayOf(LocalDBHelper.COLUMN_NAME_BASKET))
        }catch (e: SQLiteException){
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
        }
        editText.text.clear()
        onResume()
    }

    override fun onResume() {
        super.onResume()
        basketLayout.removeAllViews()
        val cursor = myDB.getFromLocalDB(LocalDBHelper.TABLE_BASKET, arrayOf(
            LocalDBHelper.COLUMN_ID,
            LocalDBHelper.COLUMN_NAME_BASKET))
        if(cursor?.moveToFirst()!!){
            while (cursor.moveToNext()){
                val product = CheckBox(this)
                product.text = cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COLUMN_NAME_BASKET))
                product.id = cursor.getInt(cursor.getColumnIndexOrThrow(LocalDBHelper.COLUMN_ID))
                product.setOnClickListener{
                    listRemove.add(product.id)
                    product.visibility = View.GONE
                }
                if(product.id in listRemove)
                    continue
                else
                    basketLayout.addView(product)
            }
        }
        cursor.close()
    }

    override fun onPause() {
        super.onPause()
        myDB.deleteFromLocalDB(listRemove, LocalDBHelper.TABLE_BASKET)
        myDB.close()
    }

    fun openFridge(view: View){
        goToActivity(FridgeActivity::class.java)
    }
    fun openPlus(view: View){
        goToActivity(PlusActivity::class.java)
    }
    fun openMenu(view: View){
        goToActivity(MainActivity::class.java)
    }
    fun openFavorite(view: View){
        goToActivity(FavoriteActivity::class.java)
    }

    fun Context.goToActivity(classs: Class<*>?) {
        val intent = Intent(this, classs)
        startActivity(intent)
    }
}