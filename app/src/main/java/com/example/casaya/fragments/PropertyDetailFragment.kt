package com.example.casaya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.casaya.R
import com.example.casaya.entities.Property
import com.example.casaya.viewmodels.PropertiesListViewModel

class PropertyDetailFragment : Fragment() {

    private val viewModelPropertiesList: PropertiesListViewModel by activityViewModels()
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
    private lateinit var propertyImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_property_detail, container, false)

        //Inicializa cada una de las referencias de los elementos del formulario
        initializeView(view)

        return view
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
            Glide
                .with(this)
                .load(property.getPropertyImageRef())
                .into(propertyImageView);
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
        propertyImageView = view.findViewById(R.id.propertyImageView)
    }

}