package com.example.inventorymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat

class MainActivity3 : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var btnSave : Button
    private lateinit var btnReset : Button
    private lateinit var name: EditText
    private lateinit var quantity : EditText
    private lateinit var price: EditText
    private lateinit var totalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        //declare the component
        btnSave = findViewById<Button>(R.id.button4)
        btnReset = findViewById<Button>(R.id.button5)
        name = findViewById<EditText>(R.id.editTextText)
        quantity = findViewById<EditText>(R.id.editTextText2)
        price = findViewById<EditText>(R.id.editTextText3)
        totalPrice = findViewById<TextView>(R.id.textView8)

        //popup message when click button add record
        Toast.makeText(this, "Insert page", Toast.LENGTH_LONG).show()

        btnSave.setOnClickListener{
            //call function save EmployeeData
            //parameter - change the input data to string
            saveInventoryData(name.text.toString(),quantity.text.toString(), price.text.toString(), totalPrice.text.toString())

        }

        btnReset.setOnClickListener {
            name.setText(" ")
            quantity.setText(" ")
            price.setText(" ")
            totalPrice.setText(" ")
        }

    }

    private fun saveInventoryData(n:String, q:String, p:String, t:String)
    {
        //initialize function decimal
        val df = DecimalFormat("#,###,###.##")

        var quantityVal:Double = quantity.text.toString().toDouble()
        var priceVal:Double = price.text.toString().toDouble()

        var mTotal = (quantityVal * priceVal)

        // Calculate the total price and format it
        val formattedTotalPrice = "RM " + df.format(mTotal).toString()
        //totalPrice.text = "RM " + df.format(mTotal).toString()

        //getInstance = get object
        //employee refer to table
        //employee can change to other name
        //link to database named Employee
        dbRef = FirebaseDatabase.getInstance().getReference("Inventory")

        //produce auto generate employee id
        //!! refer must had record or id cannot null
        val id = dbRef.push().key!!

        //em is a object
        //push the data to database
        //empId will autogenerate
        //data will output by user
        //input name and age
        val em = Model(id, n, q, p, formattedTotalPrice)

        //setting to push data inside table
        dbRef.child(id).setValue(em)

            //success record it will pop up success
            .addOnCompleteListener(){
                Toast.makeText(this,"Success ", Toast.LENGTH_LONG).show()
                //fail to record it will popup failure
            }.addOnFailureListener{
                Toast.makeText(this,"Failure", Toast.LENGTH_LONG).show()
            }

        //declare variable i to connect to next pages/ activity
        val i = Intent(this, MainActivity2::class.java)
        startActivity(i)
    }

}