package com.example.cookhelper.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.cookhelper.R

class FridgeActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)
        editText = findViewById(R.id.searchEditText)
    }

    fun searchRecipe(view: View){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("ing_or_name", editText.text.toString())
        startActivity(intent)
    }

    fun openMenu(view: View){
        goToActivity(MainActivity::class.java)
    }
    fun openPlus(view: View){
        goToActivity(PlusActivity::class.java)
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