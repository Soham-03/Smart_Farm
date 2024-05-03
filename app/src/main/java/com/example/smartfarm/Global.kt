package com.example.smartfarm

import com.example.smartfarm.model.Field

object Global {
    var latitude:Double? = null
    var longitude:Double? = null
    var fieldDetails = ArrayList<Field>()
    var selectedField: Field? = null
}