package com.example.cookhelper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.cookhelper.Database.LocalDBHelper
import com.example.cookhelper.R

class ThisFavoriteRecipeActivity : AppCompatActivity() {
    private lateinit var rName: TextView
    private lateinit var rCooking: TextView
    private lateinit var id: String
    private val localDB = LocalDBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_this_favorite_recipe)
        rName = findViewById(R.id.recipeName)
        rCooking = findViewById(R.id.recipeCooking)
        getRIntent()
    }

    fun deleteRecipe(view: View){
        localDB.deleteFromLocalDB(arrayListOf(id.toInt()), LocalDBHelper.TABLE_FAVORITE)
        back(view)
    }

    private fun getRIntent(){
        val intent: Intent = intent
        rName.text = intent.getStringExtra("name")
        rCooking.text = intent.getStringExtra("cooking")
        id = intent.getStringExtra("id")!!
    }

    fun back(view: View){
        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }
}