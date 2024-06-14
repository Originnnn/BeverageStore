package com.example.beveragestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beveragestore.databinding.ActivityTabledetailBinding

class TabledetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTabledetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tableData = intent.getSerializableExtra("tableData") as TableDataClass

        binding.tableName.text = tableData.tableName
        binding.tableDescription.text = tableData.description
    }
}
