package com.mess.service_co

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mess.service_co.databinding.FragmentPostBinding
import java.text.DateFormat
import java.util.*

class Post: Fragment() {

    private lateinit var binding: FragmentPostBinding
    private var imageURL: String? = null
    private var uri: Uri? = null

    private val activityResultLauncher =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data
                uri = data?.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        val view1 = binding.root

        binding.uploadImage.setOnClickListener {
            val photpicker = Intent(Intent.ACTION_PICK)
            photpicker.type = "image/*"
            activityResultLauncher.launch(photpicker)
        }

        binding.saveButton.setOnClickListener {
            saveData()
        }

        return view1
    }

    private fun saveData() {
        val storageReference = FirebaseStorage.getInstance().reference.child("Post Image")
            .child(uri!!.lastPathSegment!!)

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(layoutInflater.inflate(R.layout.progress_layout, null))
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Upload Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData() {
        val dis = binding.edittext.text.toString()
        val yourname = binding.yourname.text.toString()

        val dataClass = JobPost(imageURL,dis,yourname)
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Posts").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Post Successfully", Toast.LENGTH_SHORT).show()
                    // Finish the activity or navigate to another fragment if needed
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to save data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
