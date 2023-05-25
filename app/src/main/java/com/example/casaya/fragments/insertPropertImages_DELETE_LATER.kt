package com.example.casaya.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.casaya.R

class `insertPropertImages_DELETE_LATER` : Fragment() {

    lateinit var v: View
    lateinit var addImages: Button
    lateinit var propertyImages: ImageView
    private val REQUEST_SELECT_PHOTOS = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        addImages = v.findViewById(R.id.insertImages)
//        propertyImages = v.findViewById(R.id.propertyImages)

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_PHOTOS && resultCode == Activity.RESULT_OK) {
            var selectedImages : Uri? = null
            val clipData = data?.clipData

            val imageUri = data?.data
            if (imageUri != null) {
                selectedImages = imageUri
            }

            // Mostrar las imágenes seleccionadas en el ImageView
            Glide.with(this)
                .load(imageUri)
                .into(propertyImages)
        }
    }


    override fun onStart() {
        super.onStart()
        addImages.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(Intent.createChooser(intent, "Seleccionar Fotos"), REQUEST_SELECT_PHOTOS)
        }
    }

}