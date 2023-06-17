package com.example.casaya

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CustomToast(private val context: Context) {

    fun show(message: String, iconResId: Int) {
        val toast = Toast(context)

        // Crear una vista personalizada para el Toast
        val toastLayout = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null)

        // Configurar el texto y el icono en la vista personalizada
        toastLayout.findViewById<TextView>(R.id.toastText).text = message
        toastLayout.findViewById<ImageView>(R.id.toastIcon).setImageResource(iconResId)

        toast.duration = Toast.LENGTH_LONG
        // Establecer la vista personalizada en el Toast
        toast.view = toastLayout

        // Mostrar el Toast
        toast.show()
    }
}