package com.example.inventorymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

class MainActivity4 : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var invList: ArrayList<Model>
    private lateinit var invRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        Toast.makeText(this, "Read Page", Toast.LENGTH_LONG).show()
        invList = arrayListOf<Model>()
        dbRef = FirebaseDatabase.getInstance().getReference("Inventory")

        invRecyclerView = findViewById<RecyclerView>(R.id.rvInv)
        invRecyclerView.layoutManager = LinearLayoutManager(this)

        invRecyclerView.visibility = View.VISIBLE

        //PUSH TO FIREBASE
        val id = dbRef.push().key!!

        //panggil data dari db (datasnapshot)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (invSnap in snapshot.children) {
                        var invData = invSnap.getValue(Model::class.java)
                        invList.add(invData!!)
                        Log.d("oneByone", invData.toString())
                    }

                    Log.d("TrackONE", invList.toString())
                    var mAdapter = InvAdapter(invList)
                    invRecyclerView.adapter = mAdapter
                    invRecyclerView.visibility = View.VISIBLE

                    mAdapter.setOnItemsClickListener(object : InvAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent =
                                Intent(this@MainActivity4, InventoryDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("id", invList[position].id)
                            intent.putExtra("name", invList[position].name)
                            intent.putExtra("quantity", invList[position].quantity)
                            intent.putExtra("price", invList[position].price)
                            intent.putExtra("totalPrice", invList[position].totalPrice)
                            startActivity(intent) //panggil in a group

                            Log.d("fbONEX", (invList[position].id).toString())
                        }
                    })


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}