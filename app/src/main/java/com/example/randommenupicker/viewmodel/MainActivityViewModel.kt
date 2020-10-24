package com.example.randommenupicker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randommenupicker.model.*

class MainActivityViewModel: ViewModel() {
    private val randomizer = MenuChooser()
    private var listMenu = ArrayList<Menu>()
    private var historyLimit = 20
    private var chosenMenu = MutableLiveData<Menu>()
    private var randomChosenMenu = MutableLiveData<Menu>()
    private var filteredMenuList = MutableLiveData<ArrayList<Menu>>()
    private var searchHistory = MutableLiveData<ArrayList<History>>()
    private var randomLimit = MutableLiveData<Int>()
    private var page = MutableLiveData<Page>()
    private var menuList = MenuList()

    init {
        randomLimit.value = 5
        searchHistory.value = ArrayList<History>()
        page.value = Page.HOME
    }
    fun loadMenu(){
        loadAllMenu()
    }
    fun loadMenu(context: Context){
        menuList.loadData(context)
        loadAllMenu()
    }
    fun writeAllMenu(context: Context){
        menuList.writeToFile(context)
    }

    fun loadAllMenu() {
        filteredMenuList.value = menuList.menuList
    }

    fun getRandomChosenMenu(): LiveData<Menu> {
        return randomChosenMenu
    }

    fun getMenuSize() : Int {
        return menuList.getSize()
    }

    fun getRandomMenu(){
        randomChosenMenu.value = randomizer.getMenu(menuList.menuList, randomLimit.value as Int)
    }

    fun setChosenMenu(idMenu : Int) {
        println("SET CHOSEN MENU")
        for ( menu in menuList.menuList) {
            if(menu.idMenu == idMenu) {
                chosenMenu.value = menu
                println("CHOSEN MENU : " + menu.nama)
                return
            }
        }
        chosenMenu.value = null
    }

    fun getChosenMenu() : LiveData<Menu>{
        return chosenMenu
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
    fun setPage(p: Page){
        println("old page: ${page.value}")
        page.value = p
        println("new page: ${page.value}")
    }

    fun addMenu(
        nama:String,
        deskripsi:String,
        tag:String,
        bahan:String,
        langkah:String,
        resto:String
    ): Boolean{
        return menuList.add(nama, deskripsi, tag, bahan, langkah, resto)
    }

    fun editMenu(
        idMenu: Int,
        nama:String,
        deskripsi:String,
        tag:String,
        bahan:String,
        langkah:String,
        resto:String
    ): Boolean{
        var status = menuList.edit(idMenu, nama, deskripsi, tag, bahan, langkah, resto)
        filteredMenuList.value = filteredMenuList.value
        return status
    }

    fun deleteMenu(
        idMenu:Int,
    ): Boolean{
        return menuList.delete(idMenu)
    }

    private fun sortMenu(){
        //TODO: sort the menu list
        //not ssure if needed
    }

    fun searchMenu(keyword: String, category: MenuAttribute){
        val tempHist: ArrayList<History> = searchHistory.value as ArrayList<History>
        tempHist.add(History(keyword, category))
        while (tempHist.size > historyLimit){
            tempHist.removeAt(0)
        }
        searchHistory.value = tempHist

        filteredMenuList.value = menuList.search(keyword, category)
    }
}