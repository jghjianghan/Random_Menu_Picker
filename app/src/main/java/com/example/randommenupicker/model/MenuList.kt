package com.example.randommenupicker.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.*
import java.util.function.Predicate

class MenuList {
    var menuList = ArrayList<Menu>()
    companion object {
        const val FILENAME = "menudata.txt"

        fun getSearchFilterPredicate(keyword: String, category: MenuAttribute): Predicate<Menu> {
            return when (category){
                MenuAttribute.NAMA -> Predicate {it.namaContains(keyword)}
                MenuAttribute.DESKRIPSI -> Predicate {it.deskripsiContains(keyword)}
                MenuAttribute.BAHAN -> Predicate {it.bahanContains(keyword)}
                MenuAttribute.TAG -> Predicate {it.tagContains(keyword)}
                MenuAttribute.RESTO -> Predicate {it.restoContains(keyword)}
            }
        }

        fun getMenuSortComparator(option: SortOption): Comparator<Menu>{
            return when(option){
                SortOption.NAME_ASC-> Comparator{ menu1: Menu, menu2:Menu -> menu1.nama.toLowerCase().compareTo(menu2.nama.toLowerCase())}
                SortOption.NAME_DESC -> Comparator{ menu1: Menu, menu2:Menu -> -menu1.nama.toLowerCase().compareTo(menu2.nama.toLowerCase())}
                SortOption.TIME_ADDED_ASC -> Comparator{ menu1: Menu, menu2:Menu -> -(menu1.idMenu-menu2.idMenu)}
                else -> Comparator{ menu1: Menu, menu2:Menu -> menu1.idMenu-menu2.idMenu}
            }
        }
    }
    fun loadData(context: Context){
        //from file
        var file = File(context.getExternalFilesDir(null), FILENAME)
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<Menu>>(){}.type
        try {
            menuList = gson.fromJson(FileReader((context.getExternalFilesDir(null)?.absolutePath ?: "") + "/" + FILENAME), listType)
        } catch (e: IOException) {
            println("data file not found")
        }

        println("reading menulist:")
        for (i in menuList){
            println("${i.idMenu}: ${i.nama}")
        }

        var maxId = -1
        for (i in menuList){
            if (i.idMenu > maxId)
                maxId = i.idMenu
        }
        Menu.id = maxId+1
    }
    fun writeToFile(context: Context){
        lateinit var fop: FileOutputStream
        lateinit var file: File
        val gson = GsonBuilder().setPrettyPrinting().create()

        val content: String = gson.toJson(menuList)
        println("writing menulist:")
        for (i in menuList){
            println("${i.idMenu}: ${i.nama}")
        }
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

    fun add(
        nama: String,
        deskripsi: String,
        tag: String,
        bahan: String,
        langkah: String,
        resto: String
    ): Boolean{
        menuList.add(Menu(nama, deskripsi, tag, bahan, langkah, resto))
        return true
    }
    fun add(
        menu: Menu
    ): Boolean{
        menuList.add(menu)
        return true
    }

    fun edit(
        id: Int,
        nama: String,
        deskripsi: String,
        tag: String,
        bahan: String,
        langkah: String,
        resto: String
    ): Boolean{
        val duplicate = findMenuById(id)

        return if (duplicate != null){
            duplicate.update(nama, deskripsi, tag, bahan, langkah, resto)
            true
        } else {
            false
        }
    }

    fun delete(
        idMenu: Int,
    ): Boolean{
        val duplicate = findMenuById(idMenu)

        return if (duplicate != null){ //ketemu
            menuList.remove(duplicate)
            true
        } else {
            false
        }
    }

    fun deleteAll(){
        menuList.clear()
    }

    private fun findMenuById(id: Int): Menu? {
        var temp: Menu? = null
        for (i in menuList){
            if (i.idMenu == id){
                temp = i
                break
            }
        }
        return temp
    }

    fun search(keyword: String, category: MenuAttribute): ArrayList<Menu>{
        var filtered = ArrayList<Menu>()
        when(category){
            MenuAttribute.NAMA -> {
                for (i in menuList) {
                    if (i.namaContains(keyword)) {
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.DESKRIPSI -> {
                for (i in menuList) {
                    if (i.deskripsiContains(keyword)) {
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.BAHAN -> {
                for (i in menuList) {
                    if (i.bahanContains(keyword)) {
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.TAG -> {
                for (i in menuList) {
                    if (i.tagContains(keyword)) {
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.RESTO -> {
                for (i in menuList) {
                    if (i.restoContains(keyword)) {
                        filtered.add(i)
                    }
                }
            }
        }
        println("menulist:")
        for (i in menuList){
            println("${i.idMenu}: ${i.nama}")
        }
        return filtered
    }
}