package com.example.casaya.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.adapters.PropertyAdapter
import androidx.navigation.fragment.findNavController
import com.example.casaya.viewmodels.PropertiesListViewModel

class PropertiesListFragment : Fragment() {

    private val viewModelPropertiesList: PropertiesListViewModel by activityViewModels()
    lateinit var v: View
    lateinit var recyclerProperties: RecyclerView
    lateinit var adapterProperty: PropertyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_properties_list, container, false)
        recyclerProperties = v.findViewById(R.id.recyclerProperties)

        //Le indico al Fragment, que el mismo tendra un OptionsMenu
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_action_search_bar, menu)

        val searcher = menu.findItem(R.id.searcher)
        val searchView = searcher.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModelPropertiesList.searchPropertiesByTitle(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onStart() {
        super.onStart()

        /**
         * Obtengo la lista inicial de propiedades para enviar al Adapter
         */
        viewModelPropertiesList.getProperties()

        adapterProperty = PropertyAdapter(
            properties = viewModelPropertiesList.propertiesList,
            onClick = { position ->
                //Snackbar.make(v, "Click en ${repository.getProperties()[position].getTitle()}", Snackbar.LENGTH_SHORT).show()

                //Guardo referencia de la Property seleccionada en el ViewModel del Fragment
                viewModelPropertiesList.selectedProperty =
                    viewModelPropertiesList.propertiesList[position]
                Log.d(
                    "Selected Property",
                    "Detalle de la propiedad ${viewModelPropertiesList.selectedProperty}"
                )
                val action =
                    PropertiesListFragmentDirections.actionPropertiesListFragmentToPropertyDetailFragment()
                findNavController().navigate(R.id.action_propertiesListFragment_to_propertyDetailFragment)
                //findNavController().navigate(action)
                //view.findNavController().navigate(action)
            }
        )


        //Log.d("Properties List", "Detalle de la lista ${propertiesList}")


        /**
         * Configuro la forma en que se visualizara el RecyclerView
         */
        recyclerProperties.layoutManager = LinearLayoutManager(context)
        recyclerProperties.adapter = adapterProperty

        /**
         * Observo los cambios de la collection Properties de la DB
         */
        viewModelPropertiesList.myListLiveData.observe(viewLifecycleOwner) { myList ->
            adapterProperty.submitList(myList.toMutableList())
            adapterProperty.notifyDataSetChanged()
        }

        //viewModelPropertiesList.fetchMyList()
    }

}