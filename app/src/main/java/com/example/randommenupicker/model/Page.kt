package com.example.randommenupicker.model

import android.content.res.Resources
import com.example.randommenupicker.R

enum class Page (val text: String) {
    HOME("Home"),
    CARI("Cari"),
    MENU("Menu"),
    SETTING("Setting"),
    EXIT("Exit");

    enum class MenuPage (val text: String){
        LIST_MENU("List Menu"),
        MENU_DETAIL("Detail Menu"),
        ADD_MENU("Tambah Menu"),
        EDIT_MENU("Ubah Menu"),
        HAPUS_MENU("Ubah Menu");
    }
}