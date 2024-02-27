package com.mess.service_co

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mess.service_co.databinding.ActivityMainBinding
import com.mess.service_co.databinding.ActivityWorkerBinding
import java.text.DateFormat
import java.util.Calendar

class Worker : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerBinding
    var imageURL: String? = null
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@Worker, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        binding.uploadImage.setOnClickListener {
            val photpicker = Intent(Intent.ACTION_PICK)
            photpicker.type = "image/*"
            activityResultLauncher.launch((photpicker))
        }
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val storageReference = FirebaseStorage.getInstance().reference.child("Worker")
            .child(uri!!.lastPathSegment!!)

        val builder = AlertDialog.Builder(this@Worker)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener{
            dialog.dismiss()
        }
    }
    private fun uploadData(){
        val name=binding.fullname.text.toString()
        val contact=binding.contactno.text.toString()
        val service=binding.services.text.toString()
        val aadhar=binding.aadhar.text.toString()
        val address=binding.address.text.toString()


        val dataClass=DataClass(imageURL,name,contact,service,aadhar,address)
        val currentDate=DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)


        FirebaseDatabase.getInstance().getReference("Worker List").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@Worker, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener{ e->
                Toast.makeText(this@Worker,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}

