package com.example.cookhelper.Database

import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GlobalDB {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Recipe")

    fun writeData(data: Array<String>){
        val recipe = Recipe(database.key.toString(), data[0],
        data[2], data[1])
        database.push().setValue(recipe)
    }

    fun readData(listData: ArrayList<String>, adapter: ArrayAdapter<String>, recipeList: ArrayList<Recipe>, search: String){
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listData.clear()
                if (snapshot.exists()){
                    for (ds: DataSnapshot in snapshot.children){
                        val recipe = ds.getValue(Recipe::class.java)
                        if(search == "null"){
                            recipe?.id = ds.key.toString()
                            recipeList.add(recipe!!)
                            listData.add(recipe?.name!!)
                        }
                        else {
                            if (search in recipe!!.ingredients!! || search in recipe.name!!) {
                                recipe?.id = ds.key.toString()
                                recipeList.add(recipe!!)
                                listData.add(recipe?.name!!)
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun deleteFromDB(id: String){
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    if(ds.key.toString() == id){
                        ds.ref.removeValue()
                        break
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}