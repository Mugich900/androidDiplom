package com.example.cookhelper.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.cookhelper.Database.LocalDBHelper
import com.example.cookhelper.Database.Recipe
import com.example.cookhelper.R

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rList: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val  listData: ArrayList<String> = ArrayList()
    private val localDB = LocalDBHelper(this)
    private val recipeList: ArrayList<Recipe> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        rList = findViewById(R.id.recipeList)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listData)
        rList.adapter = adapter
        readData()
    }

    private fun readData(){
        listData.clear()
        val cursor = localDB.getFromLocalDB(LocalDBHelper.TABLE_FAVORITE,
            arrayOf(LocalDBHelper.COLUMN_ID, LocalDBHelper.COLUMN_NAME_BASKET, LocalDBHelper.COLUMN_COOKING_FAVORITE))
        if (cursor!!.moveToFirst()){
            while (cursor.moveToNext()){
                listData.add(cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COLUMN_NAME_FAVORITE)))
                recipeList.add(Recipe(cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COLUMN_NAME_FAVORITE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COLUMN_COOKING_FAVORITE))))
            }
        }
        adapter.notifyDataSetChanged()
        cursor.close()
        setOnClickItem()
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
        val intent = Intent(this, ThisFavoriteRecipeActivity::class.java)
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
    fun openMenu(view: View){
        goToActivity(MainActivity::class.java)
    }

    fun Context.goToActivity(classs: Class<*>?) {
        val intent = Intent(this, classs)
        startActivity(intent)
    }
}