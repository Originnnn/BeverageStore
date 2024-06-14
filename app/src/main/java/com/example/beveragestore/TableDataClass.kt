package com.example.beveragestore

import java.io.Serializable

data class TableDataClass(
    val id: String = "",
    val tableName: String = "",
    val description: String = ""
) : Serializable