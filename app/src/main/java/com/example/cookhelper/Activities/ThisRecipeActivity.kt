package com.example.cookhelper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.cookhelper.Database.GlobalDB
import com.example.cookhelper.Database.LocalDBHelper
import com.example.cookhelper.R

class ThisRecipeActivity : AppCompatActivity() {
    private lateinit var rName: TextView
    private lateinit var rCooking: TextView
    private lateinit var id: String
    private val globalDB = GlobalDB()
    private val localDB = LocalDBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_this_recipe)
        rName = findViewById(R.id.recipeName)
        rCooking = findViewById(R.id.recipeCooking)
        getRIntent()
    }

    fun back(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun deleteRecipe(view: View){
        globalDB.deleteFromDB(id)
        back(view)
    }

    fun addToFavorite(view: View){
        localDB.addToTable(LocalDBHelper.TABLE_FAVORITE, arrayOf(rName.text.toString(), rCooking.text.toString()),
        arrayOf(LocalDBHelper.COLUMN_NAME_FAVORITE, LocalDBHelper.COLUMN_COOKING_FAVORITE))
        val intent = Intent(this, ThisFavoriteRecipeActivity::class.java)
        startActivity(intent)
    }

    private fun getRIntent(){
        val intent: Intent = intent
        rName.text = intent.getStringExtra("name")
        rCooking.text = intent.getStringExtra("cooking")
        id = intent.getStringExtra("id")!!
    }
}