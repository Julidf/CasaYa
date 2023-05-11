package com.example.casaya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.casaya.R
import com.example.casaya.entities.Property

class PublishPropertyFragment : Fragment() {

    companion object {
        fun newInstance() = PublishPropertyFragment()
    }

    private val viewModelPropertiesList: PropertiesListViewModel by activityViewModels()
    private lateinit var view: View

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
        initializeView(view)

        return view
    }

    override fun onStart() {
        super.onStart()

        //capturo el evento del boton
        publishButton.setOnClickListener {
            //Envio la informacion del formulario al ViewModel
            submitDataForm()

            val action = PublishPropertyFragmentDirections.actionPublishPropertyFragmentToPropertiesListFragment()
            findNavController().navigate(action)
        }
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

        viewModelPropertiesList.publishProperty(title, description, area, bedRoomsNumber, bathRoomsNumber, price, expense, isRent, propertyType, province, street, height, betweenStreets, postalCode)

    }

    /**
     * Obtiene el valor boolean del grupo de radio buttons en relacion al tipo de
     * operacion: Alquiler o Venta
     */
    private fun isForRent() : Boolean {
        return when (radioGroupOperationType.checkedRadioButtonId) {
            R.id.rentRadioButton -> true
            R.id.saleRadioButton -> false
            else -> false
        }
    }

    /**
     * Se obtienen todas las referencias de los elementos desde la vista
     */
    private fun initializeView(view: View) {
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