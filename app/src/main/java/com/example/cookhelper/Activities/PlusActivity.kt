package com.example.cookhelper.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.cookhelper.Database.GlobalDB
import com.example.cookhelper.R

class PlusActivity : AppCompatActivity() {
    private lateinit var rName: EditText
    private lateinit var rIng: EditText
    private lateinit var rCooking: EditText
    private val globalDB = GlobalDB()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)
        rName = findViewById(R.id.editName)
        rIng = findViewById(R.id.editIng)
        rCooking = findViewById(R.id.editCooking)
    }

    fun addToGlobalDB(view: View){
        globalDB.writeData(arrayOf(rName.text.toString(),
            rIng.text.toString(), rCooking.text.toString()))
        rName.text.clear()
        rIng.text.clear()
        rCooking.text.clear()
    }

    fun openFridge(view: View){
        goToActivity(FridgeActivity::class.java)
    }
    fun openMenu(view: View){
        goToActivity(MainActivity::class.java)
    }
    fun openBasket(view: View){
        goToActivity(BasketActivity::class.java)
    }
    fun openFavorite(view: View){
        goToActivity(FavoriteActivity::class.java)
    }

    fun Context.goToActivity(classs: Class<*>?) {
        val intent = Intent(this, classs)
        startActivity(intent)
    }
}