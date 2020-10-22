package com.example.randommenupicker.model

class MenuChooser () {
    private var choiceHistory: ArrayList<Menu> = ArrayList()

    private fun trimHistory(historyLimit: Int){
        val tempHist: ArrayList<Menu> = ArrayList()
        var limit = if(historyLimit<choiceHistory.size) {historyLimit} else {choiceHistory.size}
        for ((idx, i) in choiceHistory.withIndex()){
            if (idx >= (choiceHistory.size-limit)){
                tempHist.add(i)
            }
        }
        choiceHistory = tempHist
    }

    fun getMenu(listMenu: ArrayList<Menu>, historyLimit: Int): Menu? {
        var chosen: Menu? = null
        val availableMenu = ArrayList<Menu>()
        trimHistory(if (historyLimit<listMenu.size) {historyLimit} else {listMenu.size-1})
        for (i in listMenu){
            if (!choiceHistory.contains(i)){
                availableMenu.add(i)
            }
        }
        if (availableMenu.size>0){
            chosen = availableMenu[(0 until availableMenu.size).random()]
            choiceHistory.add(chosen)
        }

        return chosen
    }
}