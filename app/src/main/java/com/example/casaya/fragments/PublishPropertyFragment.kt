package com.example.casaya.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.casaya.R
import com.example.casaya.viewmodels.PropertiesListViewModel
import gun0912.tedimagepicker.builder.TedImagePicker

class PublishPropertyFragment : Fragment() {

    companion object {
        fun newInstance() = PublishPropertyFragment()
    }

    private val viewModelPropertiesList: PropertiesListViewModel by activityViewModels()
    private lateinit var view: View
    lateinit var insertImages: Button
    lateinit var propertyImages: ImageView

    /**
     * Elementos del formulario
     */
    private lateinit var publicationTitleEditText: EditText
    private lateinit var radioGroupOperationType: RadioGroup
    private lateinit var propertyTypeSpinner: Spinner
    private lateinit var descriptionPropEditText: EditText
    private lateinit var priceRentEditText: EditText
    private lateinit var expensesEditText: EditText
    private lateinit var areaEditText: EditText
    private lateinit var bedRoomsEditText: EditText
    private lateinit var bathRoomsEditText: EditText
    private lateinit var provinceSpinner: Spinner
    private lateinit var streetEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var betweenStreetsEditText: EditText
    private lateinit var postalCodeEditText: EditText
    private lateinit var publishButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_publish_property, container, false)

        //Inicializa cada una de las referencias de los elementos del formulario
        initializeViewElements(view)
        insertImages = view.findViewById(R.id.insertImages)
        propertyImages = view.findViewById(R.id.propertyImages)

        return view
    }

    override fun onResume() {
        super.onResume()

        publicationTitleEditText.apply {
            error = null
            setText("")
        }
        descriptionPropEditText.apply {
            error = null
            setText("")
        }
        priceRentEditText.apply {
            error = null
            setText("")
        }
        expensesEditText.apply {
            error = null
            setText("")
        }
        areaEditText.apply {
            error = null
            setText("")
        }
        bedRoomsEditText.apply {
            error = null
            setText("")
        }
        bathRoomsEditText.apply {
            error = null
            setText("")
        }
        streetEditText.apply {
            error = null
            setText("")
        }
        heightEditText.apply {
            error = null
            setText("")
        }
        betweenStreetsEditText.apply {
            error = null
            setText("")
        }
        postalCodeEditText.apply {
            error = null
            setText("")
        }
    }

    override fun onStart() {
        super.onStart()

        //capturo el evento del boton
        publishButton.setOnClickListener {

            //Verifico si los datos cargados en el form son validos
            if (formIsValid()) {
                //Envio la informacion del formulario al ViewModel
                submitDataForm()

                val action =
                    PublishPropertyFragmentDirections.actionPublishPropertyFragmentToPropertiesListFragment()
                findNavController().navigate(action)
            }
        }

        insertImages.setOnClickListener {
            TedImagePicker.with(requireContext())
                .start { uri ->
                    viewModelPropertiesList.setPropertyImage(uri, requireContext())
                    var imageUri = viewModelPropertiesList.getPropertyImage()
//                    Glide.with(this)
//                    .load(imageUri)
//                    .into(propertyImages)
                }
        }

    }

    private fun formIsValid(): Boolean {
        var isValid = true

        val publicationTitle = publicationTitleEditText.text.toString().trim()
        val descriptionProp = descriptionPropEditText.text.toString().trim()
        val priceRent = priceRentEditText.text.toString().trim()
        val expenses = expensesEditText.text.toString().trim()
        val area = areaEditText.text.toString().trim()
        val bedRooms = bedRoomsEditText.text.toString().trim()
        val bathRooms = bathRoomsEditText.text.toString().trim()
        val street = streetEditText.text.toString().trim()
        val height = heightEditText.text.toString().trim()
        val betweenStreets = betweenStreetsEditText.text.toString().trim()
        val postalCode = postalCodeEditText.text.toString().trim()

        if (publicationTitle.isEmpty()) {
            publicationTitleEditText.error = "Por favor, complete el titulo de la publicacion"
            isValid = false
        }
        if (descriptionProp.isEmpty()) {
            descriptionPropEditText.error = "Por favor, complete la descripcion de la publicacion"
            isValid = false
        }
        if (priceRent.isEmpty() || priceRent.toDouble() <= 0) {
            priceRentEditText.error = "Por favor, complete el precio de Alquiler/Venta de la propiedad"
            isValid = false
        }
        if (expenses.isEmpty() || expenses.toDouble() <= 0) {
            expensesEditText.error = "Por favor, complete el valor de las expensas de Alquiler/Venta de la propiedad"
            isValid = false
        }
        if (area.isEmpty() || area.toDouble() <= 0) {
            areaEditText.error = "Por favor, complete el valor del area de la propiedad"
            isValid = false
        }
        if (bedRooms.isEmpty() || bedRooms.toInt() <= 0) {
            bedRoomsEditText.error = "Por favor, complete el valor de la cantidad de habitaciones de la propiedad"
            isValid = false
        }
        if (bathRooms.isEmpty() || bathRooms.toInt() <= 0) {
            bathRoomsEditText.error = "Por favor, complete el valor de la cantidad de baños de la propiedad"
            isValid = false
        }
        if (street.isEmpty()) {
            streetEditText.error = "Por favor, complete el nombre de la calle de la direccion de la propiedad"
            isValid = false
        }
        if (height.isEmpty() || height.toInt() <= 0) {
            heightEditText.error = "Por favor, complete y/o agregue un valor valido para la altura de la calle"
            isValid = false
        }
        if (betweenStreets.isEmpty()) {
            betweenStreetsEditText.error = "Por favor, complete el nombre de las calles de referencia de la propiedad"
            isValid = false
        }
        if (postalCode.isEmpty()) {
            postalCodeEditText.error = "Por favor, complete el codigo postal de la ubicacion de la propiedad"
            isValid = false
        }

        return isValid
    }

    /**
     * Enviar los datos del formulario al ViewModel de la clase PropertiesListViewModel
     */
    private fun submitDataForm() {
        val title = publicationTitleEditText.text.toString()
        val description = descriptionPropEditText.text.toString()
        val area = areaEditText.text.toString().toDouble()
        val bedRoomsNumber = bedRoomsEditText.text.toString().toInt()
        val bathRoomsNumber = bathRoomsEditText.text.toString().toInt()
        val price = priceRentEditText.text.toString().toDouble()
        val expense = expensesEditText.text.toString().toDouble()
        val isRent = isForRent()
        val propertyType = propertyTypeSpinner.selectedItem.toString()
        val province = provinceSpinner.selectedItem.toString()
        val street = streetEditText.text.toString()
        val height = heightEditText.text.toString().toInt()
        val betweenStreets = betweenStreetsEditText.text.toString()
        val postalCode = postalCodeEditText.text.toString()
        val storageImage =

        viewModelPropertiesList.publishProperty(
            title,
            description,
            area,
            bedRoomsNumber,
            bathRoomsNumber,
            price,
            expense,
            isRent,
            propertyType,
            province,
            street,
            height,
            betweenStreets,
            postalCode
        )

    }

    /**
     * Obtiene el valor boolean del grupo de radio buttons en relacion al tipo de
     * operacion: Alquiler o Venta
     */
    private fun isForRent(): Boolean {
        return when (radioGroupOperationType.checkedRadioButtonId) {
            R.id.rentRadioButton -> true
            R.id.saleRadioButton -> false
            else -> false
        }
    }

    /**
     * Se obtienen todas las referencias de los elementos desde la vista
     */
    private fun initializeViewElements(view: View) {
        publicationTitleEditText = view.findViewById(R.id.publicationTitleEditText)
        radioGroupOperationType = view.findViewById(R.id.radioGroupOperationType)
        propertyTypeSpinner = view.findViewById(R.id.propertyTypeSpinner)
        descriptionPropEditText = view.findViewById(R.id.descriptionPropEditText)
        priceRentEditText = view.findViewById(R.id.priceRentEditText)
        expensesEditText = view.findViewById(R.id.expensesEditText)
        areaEditText = view.findViewById(R.id.areaEditText)
        bedRoomsEditText = view.findViewById(R.id.bedRoomsEditText)
        bathRoomsEditText = view.findViewById(R.id.bathRoomsEditText)
        provinceSpinner = view.findViewById(R.id.provinceSpinner)
        streetEditText = view.findViewById(R.id.streetEditText)
        heightEditText = view.findViewById(R.id.heightEditText)
        betweenStreetsEditText = view.findViewById(R.id.betweenStreetsEditText)
        postalCodeEditText = view.findViewById(R.id.postalCodeEditText)
        publishButton = view.findViewById(R.id.publishButton)

        initializeSpinners()
    }

    /**
     * Se inicializan todos los elementos de la clase Spinner con los valores seleccionados
     * desde el formulario
     */
    private fun initializeSpinners() {
        // Configura el adapter para el spinner de Tipo de Propiedad
        val propertyTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opciones_spinner_typeProperty,
            android.R.layout.simple_spinner_item
        )
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        propertyTypeSpinner.adapter = propertyTypeAdapter

        // Configura el adapter para el spinner de Provincias
        val provinceAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opciones_spinner_province,
            android.R.layout.simple_spinner_item
        )
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner.adapter = provinceAdapter
    }


}