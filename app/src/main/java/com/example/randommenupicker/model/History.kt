package com.example.randommenupicker.model

class History (var keyword: String, var category: MenuAttribute) {
    companion object {
        var id: Int = 0
    }
    var idHistory: Int
    init {
        idHistory = id++
    }

}