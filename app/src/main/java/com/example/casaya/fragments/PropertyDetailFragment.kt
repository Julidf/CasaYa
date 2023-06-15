package com.example.casaya.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.casaya.R
import com.example.casaya.adapters.entities.Property
import com.example.casaya.viewmodels.PropertiesListViewModel
import com.example.casaya.viewmodels.PropertyDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import kotlin.math.log

class PropertyDetailFragment : Fragment() {
    private lateinit var viewModelPropertiesList: PropertiesListViewModel
    private lateinit var view: View
    private var selectedProperty: Property? = null

    /**
     * Elementos de la pantalla
     */
    private lateinit var titleTextView: TextView
    private lateinit var tagPublishedTextView: TextView
    private lateinit var characteristicsTextView: TextView
    private lateinit var tagOperationTypeTextView: TextView
    private lateinit var valuePriceTextView: TextView
    private lateinit var valueExpenseTextView: TextView
    private lateinit var descriptionTextView: TextView

    private lateinit var whatsappButton: ImageButton
    private lateinit var btnClose: ImageView
    private lateinit var btnSend: Button
    private lateinit var whatsappMessage: EditText

    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_property_detail, container, false)
        viewModelPropertiesList = ViewModelProvider(this).get(PropertiesListViewModel::class.java)
        //Inicializa cada una de las referencias de los elementos del formulario
        initializeView(view)
        whatsappButton = view.findViewById(R.id.whatsappButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        whatsappButton.setOnClickListener {
            val inflater = layoutInflater
            val popupView = inflater.inflate(R.layout.whatsapp_popup, null)

            btnSend = popupView.findViewById(R.id.btnSend)
            btnClose = popupView.findViewById(R.id.btnClose)

            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            // Set dismiss behavior when touching outside the popup window
            popupWindow.isTouchable = true
            popupWindow.isFocusable = true
            popupWindow.isOutsideTouchable = false
            val backgroundDrawable = ColorDrawable(Color.BLACK)
            backgroundDrawable.alpha = 160
            popupWindow.setBackgroundDrawable(backgroundDrawable)
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            // Pop-up buttons
            whatsappMessage = popupView.findViewById(R.id.whatsappMessage)
            btnSend.setOnClickListener {
                if (whatsappMessage.text.isNotEmpty()) {
                    var userPhone: String = ""
                    viewLifecycleOwner.lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            val user = viewModelPropertiesList.getOwner()
                            Log.d("----------", user.toString())
                            if (user != null) {
                                userPhone = user.getPhone();
                                val intent = Intent(Intent.ACTION_VIEW)
                                val url =
                                    "https://api.whatsapp.com/send?phone=" + userPhone.toString() + "&text=" + URLEncoder.encode(
                                        whatsappMessage.text.toString(),
                                        "UTF-8"
                                    )
                                intent.setPackage("com.whatsapp")
                                intent.data = Uri.parse(url)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Error al obtener el usuario",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(activity, "Please Enter Msg Or Number !!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            btnClose.setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }


    override fun onStart() {
        super.onStart()

        //Obtengo la referencia de la Property que ha sido seleccionada desde el RecyclerView
        selectedProperty = viewModelPropertiesList.selectedProperty

        //Muestro en cada campo, el valor de cada atributo de la Property seleccionada
        loadDataIntoView(selectedProperty)
    }

    /**
     * Metodo que setea el texto que se debe mostrar en cada TextView de la vista
     */
    private fun loadDataIntoView(property: Property?) {
        if (property != null) {
            titleTextView.text = property.getTitle()
            tagPublishedTextView.text = property.setStringPublicationDays()
            descriptionTextView.text = property.getDescription()
            characteristicsTextView.text = setPropertyCharacteristics(property)
            tagOperationTypeTextView.text = property.obtainOperationType()
            valuePriceTextView.text = "$ ${property.getPrice()}"
            valueExpenseTextView.text = "$ ${property.getExpense()}"
            userId = "$ ${property.getUserId()}"
        }
    }

    /**
     * Metodo que arma el String de las caracteristicas de la propiedad
     */
    private fun setPropertyCharacteristics(property: Property): String {
        val area = property.getArea()
        val bedRooms = property.getBedRoomsNumber()
        val bathRooms = property.getBathRoomsNumber()
        val address = setStringAddressProperty(property)

        return "$area m² · $bedRooms Dorm. · $bathRooms Baño(s) · $address"
    }

    /**
     * Metodo que arma el String de la Direccion de la propiedad
     */
    private fun setStringAddressProperty(property: Property): String {
        val street = property.getStreet()
        val height = property.getHeight()
        val province = property.getProvince()

        return "$street $height, $province"
    }

    private fun initializeView(view: View) {
        titleTextView = view.findViewById(R.id.titleTextView)
        tagPublishedTextView = view.findViewById(R.id.tagPublishedTextView)
        characteristicsTextView = view.findViewById(R.id.characteristicsTextView)
        tagOperationTypeTextView = view.findViewById(R.id.tagOperationTypeTextView)
        valuePriceTextView = view.findViewById(R.id.valuePriceTextView)
        valueExpenseTextView = view.findViewById(R.id.valueExpenseTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)

    }

}