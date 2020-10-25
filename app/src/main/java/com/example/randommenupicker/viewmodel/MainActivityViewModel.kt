package com.example.randommenupicker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randommenupicker.model.*
import java.util.*
import java.util.function.Predicate
import kotlin.Comparator
import kotlin.collections.ArrayList

class MainActivityViewModel: ViewModel() {
    private val randomizer = MenuChooser()
    private var historyLimit = 20
    private var chosenMenu = MutableLiveData<Menu>()
    private var randomChosenMenu = MutableLiveData<Menu>()
//    private var filteredMenuList = MutableLiveData<ArrayList<Menu>>()
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
    private var searchBarKeyword = MutableLiveData<String>()
    private var liveMenu = MutableLiveData<ArrayList<Menu>>()
    private var liveSortOption = MutableLiveData<SortOption>()
    private var liveCategory = MutableLiveData<MenuAttribute>()


    init {
        randomLimit.value = 0
        page.value = Page.HOME
        searchHistoryStatus.value = false
        writeMenuFlag.value = false
        historyList = HistoryList(historyLimit)
        liveMenu.value = menuList.menuList
        liveSortOption.value = SortOption.NAME_ASC
        searchBarKeyword.value = ""
    }

    fun getSearchBarKeyword() : LiveData<String> {
        return searchBarKeyword
    }

    fun setSearchBarKeyword(keyword : String) {
        searchBarKeyword.value = keyword
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
        liveMenu.value = menuList.menuList
    }
    fun writeAllMenu(context: Context){
        menuList.writeToFile(context)
        setWriteMenuFlag(false)
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

//    fun getFilteredMenuList(): LiveData<ArrayList<Menu>>{
//        return filteredMenuList
//    }
    fun getLiveMenu(): LiveData<ArrayList<Menu>>{
        return liveMenu
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

    fun getSortOption(): LiveData<SortOption>{
        return liveSortOption
    }
    fun setSortOption(sortOption: SortOption){
        liveSortOption.value = sortOption
    }
    fun getLiveCategory(): LiveData<MenuAttribute>{
        return liveCategory
    }
    fun setLiveCategory(atr: MenuAttribute){
        liveCategory.value = atr
    }

    fun addMenu(
        nama:String,
        deskripsi:String,
        tag:String,
        bahan:String,
        langkah:String,
        resto:String
    ): Boolean{
        val newMenu = Menu(nama, deskripsi, tag, bahan, langkah, resto)
        val success = menuList.add(newMenu)
        println("new menu added")

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

    fun getMenuSortComparator(option: SortOption): Comparator<Menu>{
        return MenuList.getMenuSortComparator(option)
    }
    fun getMenuSortComparator(): Comparator<Menu>{
        return MenuList.getMenuSortComparator(liveSortOption.value as SortOption)
    }

    fun searchMenu(keyword: String, category: MenuAttribute){
        if (searchHistoryStatus.value as Boolean){
            historyList.add(keyword, category)
            loadHistory()
        }

        setWriteHistoryFlag(true)
    }
    fun getSearchFilterPredicate(keyword: String, category: MenuAttribute): Predicate<Menu>{
        return MenuList.getSearchFilterPredicate(keyword, category)
    }

    fun clearHistory(){
        historyList.clear()
        loadHistory()
        setWriteHistoryFlag(true)
    }

    fun writeAllHistory(context: Context){
        historyList.writeToFile(context)
    }

    private fun loadHistory(){
        searchHistory.value = historyList.historyList
    }

    fun deleteHistory(historyId : Int) : Boolean {
        val res = historyList.delete(historyId)
        loadHistory()
        return res
    }

    fun getFilteredHistoryList(atr : MenuAttribute) : List<History>{
        return historyList.filter(atr)
    }
}