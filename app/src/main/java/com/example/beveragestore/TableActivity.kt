package com.example.beveragestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class TableActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<TableDataClass>
    private lateinit var myAdapter: TableAdapterClass
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<TableDataClass>
    private lateinit var fab: FloatingActionButton
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        database = FirebaseDatabase.getInstance().reference.child("tables")

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)
        fab = findViewById(R.id.fab)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf()
        searchList = arrayListOf()

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    dataList.forEach {
                        if (it.tableName.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                } else {
                    searchList.addAll(dataList)
                }
                myAdapter.updateList(searchList)
                return false
            }
        })

        myAdapter = TableAdapterClass(searchList)
        recyclerView.adapter = myAdapter

        myAdapter.onItemClick = {
            val intent = Intent(this, TabledetailActivity::class.java)
            intent.putExtra("tableData", it)
            startActivity(intent)
        }

        fab.setOnClickListener {
            addNewTable()
        }

        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (dataSnapshot in snapshot.children) {
                    val tableDataClass = dataSnapshot.getValue(TableDataClass::class.java)
                    if (tableDataClass != null) {
                        dataList.add(tableDataClass)
                    }
                }
                searchList.clear()
                searchList.addAll(dataList)
                myAdapter.updateList(searchList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }

    private fun addNewTable() {
        val newTableId = database.push().key ?: return
        val tableNumber = dataList.size + 1
        val newTable = TableDataClass(newTableId, "Table $tableNumber", "Description for Table $tableNumber")
        database.child(newTableId).setValue(newTable)
    }
}
