package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.adapters.PropertyAdapter
import com.example.casaya.entities.PropertyRepository
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.casaya.entities.Property

class PropertiesListFragment : Fragment() {

    private val viewModelPropertiesList: PropertiesListViewModel by viewModels()
    private var repositoryProperties = PropertyRepository()
    lateinit var v: View
    lateinit var recyclerProperties: RecyclerView
    private var propertiesList: MutableList<Property> = repositoryProperties.getProperties()
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

        adapterProperty = PropertyAdapter(
            properties = propertiesList,
            onClick = { position ->
                //Snackbar.make(v, "Click en ${repository.getProperties()[position].getTitle()}", Snackbar.LENGTH_SHORT).show()
                val action =
                    PropertiesListFragmentDirections.actionPropertiesListFragmentToPropertyDetailFragment()
                findNavController().navigate(R.id.action_propertiesListFragment_to_propertyDetailFragment)
            }
        )


        // Observar la lista de elementos en el ViewModel y actualiza el adaptador
        Log.d("Observer Adapter", "Entrando al observer del adapter")
        viewModelPropertiesList.getProperties().observe(viewLifecycleOwner) { properties ->
            Log.d("Observer Adapter", "Dentro al observer del adapter")
            adapterProperty.addNewProperties(repositoryProperties.getProperties())
            adapterProperty.notifyItemInserted(properties.size - 1)
        }

        //Configuro la forma en que se visualizara el RecyclerView
        recyclerProperties.layoutManager = LinearLayoutManager(context)
        recyclerProperties.adapter = adapterProperty
    }

}