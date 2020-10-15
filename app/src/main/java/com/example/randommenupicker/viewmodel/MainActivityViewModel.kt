package com.example.randommenupicker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randommenupicker.model.*

class MainActivityViewModel: ViewModel() {
    private val randomizer = MenuChooser()
    private var listMenu = ArrayList<Menu>()
    private var historyLimit = 20
    private var chosenMenu = MutableLiveData<Menu>()
    private var filteredMenuList = MutableLiveData<ArrayList<Menu>>()
    private var searchHistory = MutableLiveData<ArrayList<History>>()
    private var randomLimit = MutableLiveData<Int>()
    private var page = MutableLiveData<Page>()

    private var pageList = MutableLiveData<Array<Page>>()

    init {
        randomLimit.value = 5
        pageList.value = Page.values()
        searchHistory.value = ArrayList<History>()
        page.value = Page.HOME
    }

    fun getChosenMenu(): LiveData<Menu> {
        return chosenMenu
    }
    fun getRandomMenu(){
        chosenMenu.value = randomizer.getMenu(listMenu, randomLimit.value as Int)
    }
    fun chooseMenu(menu: Menu){
        chosenMenu.value = menu
    }
    fun getFilteredMenuList(): LiveData<ArrayList<Menu>>{
        return filteredMenuList
    }
    fun getRandomLimit(): LiveData<Int>{
        return randomLimit
    }
    fun getSearchHistory(): LiveData<ArrayList<History>>{
        return searchHistory
    }
    fun getPage(): LiveData<Page>{
        return page
    }
    fun getPageList(): LiveData<Array<Page>>{
        return pageList
    }

    fun addMenu(
        nama:String,
        deskripsi:String,
        tag:String,
        bahan:String,
        langkah:String,
        resto:String
    ): Boolean{
        val duplicate = findMenuByName(nama)
        return if (duplicate == null){
            listMenu.add(Menu(nama, deskripsi, tag, bahan, langkah, resto))
            true
        } else {
            false
        }
    }

    fun editMenu(
        nama:String,
        deskripsi:String,
        tag:String,
        bahan:String,
        langkah:String,
        resto:String
    ): Boolean{
        val duplicate = findMenuByName(nama)

        return if (duplicate != null){
            listMenu.remove(duplicate)
            listMenu.add(Menu(nama, deskripsi, tag, bahan, langkah, resto))
            true
        } else {
            false
        }
    }

    fun deleteMenu(
        nama:String,
    ): Boolean{
        val duplicate = findMenuByName(nama)

        return if (duplicate != null){
            listMenu.remove(duplicate)
            true
        } else {
            false
        }
    }

    private fun findMenuByName(name: String): Menu? {
        var temp: Menu? = null
        for (i in listMenu){
            if (i.nama.toLowerCase() == name.toLowerCase()){
                temp = i
                break
            }
        }
        return temp
    }
    private fun sortMenu(){
        //TODO: sort the menu list
        //not ssure if needed
    }
    fun searchMenu(keyword: String, category: MenuAttribute){
        var filtered = ArrayList<Menu>()
        when(category){
            MenuAttribute.NAMA -> {
                for (i in listMenu){
                    if (i.namaContains(keyword)){
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.DESKRIPSI -> {
                for (i in listMenu){
                    if (i.deskripsiContains(keyword)){
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.BAHAN -> {
                for (i in listMenu){
                    if (i.bahanContains(keyword)){
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.TAG -> {
                for (i in listMenu){
                    if (i.tagContains(keyword)){
                        filtered.add(i)
                    }
                }
            }
            MenuAttribute.RESTO -> {
                for (i in listMenu){
                    if (i.restoContains(keyword)){
                        filtered.add(i)
                    }
                }
            }
        }

        val tempHist: ArrayList<History> = searchHistory.value as ArrayList<History>
        tempHist.add(History(keyword, category))
        while (tempHist.size > historyLimit){
            tempHist.removeAt(0)
        }
        searchHistory.value = tempHist

        filteredMenuList.value = filtered
    }
}