package com.example.casaya.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.adapters.PropertyAdapter
import com.example.casaya.entities.PropertyRepository
import androidx.navigation.fragment.findNavController
import com.example.casaya.entities.Property

class PropertiesListFragment : Fragment() {

    private val viewModelPropertiesList: PropertiesListViewModel by activityViewModels()
    private var repositoryProperties = PropertyRepository()
    lateinit var v: View
    lateinit var recyclerProperties: RecyclerView
    private var propertiesList: MutableList<Property> = repositoryProperties.getAllProperties()
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

                /*
                val propertyClicked = propertiesList[position]
                val bundle = Bundle()
                bundle.putParcelable("propertyClicked", propertyClicked)

                 */

                val action =
                    PropertiesListFragmentDirections.actionPropertiesListFragmentToPropertyDetailFragment()
                findNavController().navigate(R.id.action_propertiesListFragment_to_propertyDetailFragment)
                //findNavController().navigate(action)
                //view.findNavController().navigate(action)
            }
        )

        Log.d("Properties List", "Detalle de la lista ${propertiesList}")


        //Configuro la forma en que se visualizara el RecyclerView
        recyclerProperties.layoutManager = LinearLayoutManager(context)
        recyclerProperties.adapter = adapterProperty
    }

}