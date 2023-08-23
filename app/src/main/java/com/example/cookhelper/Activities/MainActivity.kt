package com.example.cookhelper.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.cookhelper.Database.GlobalDB
import com.example.cookhelper.Database.Recipe
import com.example.cookhelper.R

class MainActivity : AppCompatActivity() {
    private lateinit var rList: ListView
    private val  listData: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private val recipeList: ArrayList<Recipe> = ArrayList()
    private val globalDB: GlobalDB = GlobalDB()
    private var search: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rList = findViewById(R.id.recipeList)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listData)
        rList.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        getRIntent()
        getData()
    }

    private fun getData(){
        globalDB.readData(listData, adapter, recipeList, search)
        setOnClickItem()
    }

    private fun getRIntent(){
        search = intent.getStringExtra("ing_or_name").toString()
    }

    private fun setOnClickItem(){
        rList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(panel: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val recipe = recipeList.get(pos)
                recipeIntent(recipe)
            }

        }
    }

    private fun recipeIntent(recipe: Recipe){
        val intent = Intent(this, ThisRecipeActivity::class.java)
        intent.putExtra("name", recipe.name)
        intent.putExtra("cooking", recipe.cooking)
        intent.putExtra("id", recipe.id)
        startActivity(intent)
    }

    fun openFridge(view: View){
        goToActivity(FridgeActivity::class.java)
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