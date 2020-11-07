package com.example.randommenupicker.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException

class HistoryList (private var historyLimit: Int) {
    var historyList = ArrayList<History>()

    companion object {
        const val FILENAME = "searchHistory.txt"
    }

    fun loadData(context: Context){
        //from file
        var file = File(context.getExternalFilesDir(null), FILENAME)
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<History>>(){}.type
        try {
            historyList = gson.fromJson(FileReader((context.getExternalFilesDir(null)?.absolutePath ?: "") + "/" + FILENAME), listType)
        } catch (e: IOException) {
            println("data file not found")
        }

        historyList.forEach{menu -> println(menu)}

        var maxId = -1
        for (i in historyList){
            if (i.idHistory > maxId)
                maxId = i.idHistory
        }
        History.id = maxId+1
    }

    fun writeToFile(context: Context){
        lateinit var fop: FileOutputStream
        lateinit var file: File
        val gson = GsonBuilder().setPrettyPrinting().create()

        val content: String = gson.toJson(historyList)
        try {
            file = File(context.getExternalFilesDir(null), FILENAME)
            fop = FileOutputStream(file)
            if (!file.exists()){
                file.createNewFile()
            }
            val contentInBytes = content.toByteArray()
            fop.write(contentInBytes)
            fop.flush()
            fop.close()
        } catch (e: IOException){
            e.printStackTrace()
        } finally {
            try {
                fop.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun add (keyword: String, category: MenuAttribute){
        val tempHist = ArrayList<History>()
        historyList.add(History(keyword, category))
        if (tempHist.size > historyLimit){
            for (i in (tempHist.size - historyLimit) until historyLimit){
                tempHist.add(historyList[i])
            }
            historyList = tempHist
        }
    }

    fun delete(historyId : Int) : Boolean{
        var idx = -1
        for ((i, value) in historyList.withIndex()) {
            if(value.idHistory == historyId) {
                idx = i
                break
            }
        }

        if(idx != -1) {
            historyList.removeAt(idx)
            return true
        }
        return false
    }

    fun filter(atr : MenuAttribute) : List<History>{
        return historyList.filter {
            it.category == atr
        }
    }

    fun clear(){
        historyList.clear()
    }
}