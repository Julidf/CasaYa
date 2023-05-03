package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.adapters.PropertyAdapter
import com.example.casaya.entities.PropertyRepository
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class PropertiesListFragment : Fragment() {

    private lateinit var viewModel: PropertiesListViewModel
    lateinit var v: View
    lateinit var recyclerProperties: RecyclerView
    var repository: PropertyRepository = PropertyRepository()
    lateinit var adapterProperty: PropertyAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_properties_list, container, false)
        recyclerProperties = v.findViewById(R.id.recyclerProperties)
        return v
    }

    override fun onStart() {
        super.onStart()
        adapterProperty = PropertyAdapter(repository.getProperties()) { position ->
            //Snackbar.make(v, "Click en ${repository.getProperties()[position].getTitle()}", Snackbar.LENGTH_SHORT).show()
            //val action = PropertiesListFragmentDirections.actionPropertiesListFragmentToPropertyDetailFragment()
            findNavController().navigate(R.id.action_propertiesListFragment_to_propertyDetailFragment)
        }
        //Configuro la forma en que se visualizara el RecyclerView
        recyclerProperties.layoutManager = LinearLayoutManager(context)
        recyclerProperties.adapter = adapterProperty
    }

}