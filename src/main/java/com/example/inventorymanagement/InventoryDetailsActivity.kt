package com.example.inventorymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat

class InventoryDetailsActivity : AppCompatActivity() {

    private lateinit var tvId: TextView
    private lateinit var tvName: TextView
    private lateinit var tvQuantity: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_details)

        tvId = findViewById(R.id.tvId)
        tvName = findViewById(R.id.tvName)
        tvQuantity = findViewById(R.id.tvQuantity)
        tvPrice = findViewById(R.id.tvPrice)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        tvId.text = intent.getStringExtra("id")
        tvName.text = intent.getStringExtra("name")
        tvQuantity.text = intent.getStringExtra("quantity")
        tvPrice.text = intent.getStringExtra("price")
        tvTotalPrice.text = intent.getStringExtra("totalPrice")

        btnDelete.setOnClickListener {
            deleteRecord(tvId.text.toString())
        }

        btnUpdate.setOnClickListener {
            openUpdateDialog(intent.getStringExtra("id").toString(),
                intent.getStringExtra("name").toString(),
                intent.getStringExtra("quantity").toString(),
                intent.getStringExtra("price").toString(),
                intent.getStringExtra("totalPrice").toString())
        }
    }

    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Inventory").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Inventory data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity4::class.java)
            finish()
            startActivity(intent)

        }.addOnFailureListener{ error -> Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        } }

    private fun openUpdateDialog(id: String, name: String, quantity: String, price: String, alertDialog: Any)
    {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etName = mDialogView.findViewById<EditText>(R.id.etName)
        val etQuantity = mDialogView.findViewById<EditText>(R.id.etQuantity)
        val etPrice = mDialogView.findViewById<EditText>(R.id.etPrice)
        val etTotalPrice = mDialogView.findViewById<TextView>(R.id.etTotalPrice)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etName.setText(intent.getStringExtra("name").toString())
        etQuantity.setText(intent.getStringExtra("quantity").toString())
        etPrice.setText(intent.getStringExtra("price").toString())
        etTotalPrice.setText(intent.getStringExtra("totalPrice").toString())

        mDialog.setTitle("Updating Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener { updateInvData(
            id,
            etName.text.toString(),
            etQuantity.text.toString(),
            etPrice.text.toString(),
            etTotalPrice.text.toString(),
        )

            Toast.makeText(applicationContext, "Inventory Data Update", Toast.LENGTH_LONG).show()

            //we are setting update data our text views tvEmpName.text = etName.text.toString()
            tvQuantity.text = etQuantity.text.toString()
            tvPrice.text = etPrice.text.toString()
            tvTotalPrice.text = etTotalPrice.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateInvData(id: String, name: String, quantity: String, price: String, totalPrice: String) {
        // Initialize function decimal
        val df = DecimalFormat("#,###,###.##")

        val quantityVal: Double = tvQuantity.text.toString().toDouble()
        val priceVal: Double = tvPrice.text.toString().toDouble()

        val mTotal = quantityVal * priceVal

        // Calculate the total price and format it
        val formattedTotalPrice = "RM " + df.format(mTotal).toString()

        // Update the TextView with the calculated total price before Firebase operation
        tvTotalPrice.text = formattedTotalPrice

        // Update the Firebase data with mTotal as the total price
        val dbRef = FirebaseDatabase.getInstance().getReference("Inventory").child(id)
        val invInfo = Model(id, name, quantity, price, formattedTotalPrice) // Use formattedTotalPrice here
        dbRef.setValue(invInfo)

        val intent = Intent(this, MainActivity4::class.java)
        finish()
        startActivity(intent)
    }


}