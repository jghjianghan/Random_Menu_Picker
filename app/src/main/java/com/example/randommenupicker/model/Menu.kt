package com.example.randommenupicker.model

class Menu (
    var nama:String,
    var deskripsi:String,
    var listBahan:ArrayList<String>,
    var listTag:ArrayList<String>,
    var listLangkah:ArrayList<String>,
    var listResto:ArrayList<String>,
) {
    constructor():this(
        "",
        "",
        ArrayList<String>(),
        ArrayList<String>(),
        ArrayList<String>(),
        ArrayList<String>()
    ){
    }
    fun namaContains(keyword: String): Boolean{
        return containsIgnoreCase(nama, keyword);
    }
    fun deskripsiContains(keyword: String): Boolean{
        return containsIgnoreCase(deskripsi, keyword)
    }
    fun bahanContains(keyword: String): Boolean{
        for (i in listBahan){
            if (containsIgnoreCase(i, keyword)){
                return true;
            }
        }
        return false;
    }
    fun tagContains(keyword: String): Boolean{
        for (i in listTag){
            if (containsIgnoreCase(i, keyword)){
                return true;
            }
        }
        return false;
    }
    fun restoContains(keyword: String): Boolean{
        for (i in listResto){
            if (containsIgnoreCase(i, keyword)){
                return true;
            }
        }
        return false;
    }
    private fun containsIgnoreCase(s1: String, s2:String):Boolean{
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}