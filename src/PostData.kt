package com.martynov

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martynov.model.PostModel
import java.io.File

object PostData {

    fun getDataBase(): ArrayList<PostModel> {
        val fileName = "posts.json"
        try {
            val type = object : TypeToken<List<PostModel>>() {}.type
            val result: ArrayList<PostModel> = Gson().fromJson(File(fileName).readText(), type)
            return result
        }
        catch (e:Exception){
            return ArrayList<PostModel>()
        }


    }
}