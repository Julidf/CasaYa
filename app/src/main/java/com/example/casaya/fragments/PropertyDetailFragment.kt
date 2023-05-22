package com.example.casaya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.casaya.R
import com.example.casaya.entities.Property
import com.example.casaya.viewmodels.PropertyDetailViewModel

class PropertyDetailFragment : Fragment() {

    private lateinit var viewModel: PropertyDetailViewModel
    private lateinit var view: View
    private lateinit var property: Property

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

        /*
        val bundle = arguments
        val property = bundle?.getParcelable<Property>("propertyClicked")

        if (property != null) {
            titleTextView.text = property.getTitle()
        }

         */

        //property = PropertyDetailFragmentArgs.fromBundle(requireArguments()).propertyClicked
    }

    private fun initializeView(view: View) {
        titleTextView = view.findViewById(R.id.titleTextView)
        tagPublishedTextView = view.findViewById(R.id.tagPublishedTextView)
        characteristicsTextView = view.findViewById(R.id.characteristicsTextView)
        tagOperationTypeTextView = view.findViewById(R.id.tagOperationTypeTextView)
        valuePriceTextView = view.findViewById(R.id.valuePriceTextView)
        valueExpenseTextView = view.findViewById(R.id.valueExpenseTextView)
        titleTextView = view.findViewById(R.id.titleTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)

    }

}