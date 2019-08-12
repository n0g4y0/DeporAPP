package io.github.n0g4y0.deporapp.models


class User(val uid:String, val username:String){

constructor():this("","")



    override fun toString(): String {
        return username
    }

}