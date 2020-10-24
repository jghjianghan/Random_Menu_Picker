package com.example.randommenupicker.model

import android.content.res.Resources
import com.example.randommenupicker.R

enum class Page (val text: String) {
    HOME("Home"),
    CARI("Cari"),
    LIST_MENU("Menu"),
    LIST_MENU_DARI_CARI("Menu"),
    MENU_DETAIL("Detail Menu"),
    ADD_MENU("Tambah Menu"),
    EDIT_MENU("Ubah Menu"),
    HAPUS_MENU("Ubah Menu"),
    SETTING("Setting"),
    EXIT("Exit");

    companion object{
        fun getNavMenu() : Array<Page>{
            return arrayOf(HOME, CARI, LIST_MENU, SETTING, EXIT)
        }
    }

}