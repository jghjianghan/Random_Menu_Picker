package com.example.randommenupicker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randommenupicker.model.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class MainActivityViewModel: ViewModel() {
    private val randomizer = MenuChooser()
    private var historyLimit = 20
    private var chosenMenu = MutableLiveData<Menu>()
    private var randomChosenMenu = MutableLiveData<Menu>()
    private var filteredMenuList = MutableLiveData<ArrayList<Menu>>()
    private var searchHistory = MutableLiveData<ArrayList<History>>()
    private var randomLimit = MutableLiveData<Int>()
    private var page = MutableLiveData<Page>()
    private var menuList = MenuList()
    private var menuTitle = MutableLiveData<String>()
    private var toolbarTitle = MutableLiveData<String>()
    private var writeMenuFlag = MutableLiveData<Boolean>()
    private var writeHistoryFlag = MutableLiveData<Boolean>()
    private var searchHistoryStatus = MutableLiveData<Boolean>()
    private var historyList: HistoryList

    init {
        randomLimit.value = 0
        page.value = Page.HOME
        searchHistoryStatus.value = true
        writeMenuFlag.value = true
        historyList = HistoryList(historyLimit)
    }

    fun getSearchHistoryStatus() : LiveData<Boolean>{
        return searchHistoryStatus
    }

    fun switchSearchHistoryStatus() {
        if(searchHistoryStatus.value != null){
            searchHistoryStatus.value = !(searchHistoryStatus.value as Boolean)
        }
    }

    fun setToolbarTitle(title : String) {
        toolbarTitle.value = title
    }

    fun getToolbarTitle() : LiveData<String> {
        return toolbarTitle
    }

    fun loadAllData(context: Context){
        menuList.loadData(context)
        //sharedPref
        loadAllMenu()
    }
    fun writeAllMenu(context: Context){
        menuList.writeToFile(context)
        setWriteMenuFlag(false)
    }

    fun loadAllMenu() {
        filteredMenuList.value = menuList.menuList
    }

    fun getRandomChosenMenu(): LiveData<Menu> {
        return randomChosenMenu
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

    fun setRandomLimit(newValue : Int) {
        randomLimit.value = newValue
    }

    fun clearSearchHistory() {
        searchHistory.value?.clear()
    }

    fun getSearchHistory(): LiveData<ArrayList<History>>{
        return searchHistory
    }

    fun getLastHistory(): History?{
        if (searchHistory.value != null){
            val size = searchHistory.value!!.size
            if (size>0){
                return searchHistory.value!![size-1]
            }
        }
        return null
    }

    fun getPage(): LiveData<Page>{
        return page
    }
    fun setPage(p: Page){
        println("old page: ${page.value}")
        page.value = p
        println("new page: ${page.value}")
    }

    fun getMenuTitle(): LiveData<String>{
        return menuTitle
    }
    fun setMenuTitle(title: String){
        menuTitle.value = title
        println("menutitle: ${menuTitle.value}")
    }

    fun getWriteMenuFlag(): LiveData<Boolean>{
        return writeMenuFlag
    }
    fun setWriteMenuFlag(boolean: Boolean){
        writeMenuFlag.value = boolean
    }

    fun getWriteHistoryFlag(): LiveData<Boolean>{
        return writeHistoryFlag
    }
    fun setWriteHistoryFlag(boolean: Boolean){
        writeHistoryFlag.value = boolean
    }

    fun addMenu(
        nama:String,
        deskripsi:String,
        tag:String,
        bahan:String,
        langkah:String,
        resto:String
    ): Boolean{
        val success = menuList.add(nama, deskripsi, tag, bahan, langkah, resto)
        if (success){
            setWriteMenuFlag(true)
        }
        return success
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
        val success = menuList.edit(idMenu, nama, deskripsi, tag, bahan, langkah, resto)
        if (success){
            setWriteMenuFlag(true)
        }
        return success
    }

    fun deleteMenu(
        idMenu:Int?,
    ): Boolean{
        if(idMenu != null){
            val success = menuList.delete(idMenu)
            if (success){
                setWriteMenuFlag(true)
            }
            return success
        }
        return false
    }

    fun deleteAllMenu (){
        menuList.deleteAll()
        setWriteMenuFlag(true)
    }

    fun sortMenu(option: SortOption){
        println("sorting")
        if (filteredMenuList.value != null){
            var temp = filteredMenuList.value
            println("not null")
            lateinit var comparator: Comparator<Menu>
            comparator = when(option){
                SortOption.NAME_ASC-> Comparator{ menu1: Menu, menu2:Menu -> menu1.nama.toLowerCase().compareTo(menu2.nama.toLowerCase())}
                SortOption.NAME_DESC -> Comparator{ menu1: Menu, menu2:Menu -> -menu1.nama.toLowerCase().compareTo(menu2.nama.toLowerCase())}
                SortOption.TIME_ADDED_ASC -> Comparator{ menu1: Menu, menu2:Menu -> -(menu1.idMenu-menu2.idMenu)}
                else -> Comparator{ menu1: Menu, menu2:Menu -> menu1.idMenu-menu2.idMenu}
            }

            Collections.sort(temp, comparator)
            filteredMenuList.value = temp
        }
    }

    fun searchMenu(keyword: String, category: MenuAttribute){
        if (searchHistoryStatus.value as Boolean){
            historyList.add(keyword, category)
            loadHistory()
        }
        filteredMenuList.value = menuList.search(keyword, category)
        setWriteHistoryFlag(true)
    }

    fun clearHistory(){
        historyList.clear()
        loadHistory()
        setWriteHistoryFlag(true)
    }

    fun writeAllHistory(context: Context){
        historyList.writeToFile(context)
    }

    fun loadHistory(){
        searchHistory.value = historyList.historyList
    }
}