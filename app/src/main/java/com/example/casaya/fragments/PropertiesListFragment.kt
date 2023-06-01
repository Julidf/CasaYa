package com.example.casaya.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.adapters.PropertyAdapter
import androidx.navigation.fragment.findNavController
import com.example.casaya.entities.Property
import com.example.casaya.viewmodels.PropertiesListViewModel

class PropertiesListFragment : Fragment() {

    private val viewModelPropertiesList: PropertiesListViewModel by activityViewModels()
    lateinit var v: View
    lateinit var recyclerProperties: RecyclerView
    lateinit var adapterProperty: PropertyAdapter
    private lateinit var messageResultSearchTextView: TextView
    private var isSearchActive: Boolean = false
    private lateinit var searchView: SearchView
    private var searchQuery: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_properties_list, container, false)
        recyclerProperties = v.findViewById(R.id.recyclerProperties)
        messageResultSearchTextView = v.findViewById(R.id.messageResultSearchTextView)

        //Le indico al Fragment, que el mismo tendra un OptionsMenu
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_action_search_bar, menu)

        val searcher = menu.findItem(R.id.searcher)
        searchView = searcher.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchQuery = it
                    isSearchActive = true
                    viewModelPropertiesList.searchPropertiesByProvince(it) { properties ->
                        updateSearchResultTextView(properties)
                        adapterProperty.submitList(properties.toMutableList())
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener {
            // Lógica para manejar el evento de cierre del SearchView
            isSearchActive = false
            searchView.setQuery("", false)
            viewModelPropertiesList.searchPropertiesByProvince("") { properties ->
                turnOffSearchResultTextView()
                adapterProperty.submitList(properties.toMutableList())
            }
            false
        }
    }

    /**
     * Metodo que oculta el mensaje del resultado de busqueda
     */
    private fun turnOffSearchResultTextView() {
        messageResultSearchTextView.visibility = View.GONE
    }

    /**
     * Metodo que setea un mensaje, cuando el usuario realiza una busqueda
     */
    private fun updateSearchResultTextView(properties: List<Property>) {
        if (properties.isNotEmpty()) {
            messageResultSearchTextView.text =
                "Hemos encontrado ${properties.size} propiedad(es) para tu busqueda, en la provincia de ${properties[0].getProvince()}"
            Log.i("Filtered Properties", "${messageResultSearchTextView.text}")

        } else {
            messageResultSearchTextView.text =
                "No hemos encontrado resultados que coincidan con tu busqueda"
        }
        messageResultSearchTextView.visibility = View.VISIBLE
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
            }
        )

        // Verificar si hay una búsqueda activa
        if (!isSearchActive) {
            adapterProperty.submitList(viewModelPropertiesList.propertiesList.toMutableList())
        } else {
            // Hay una búsqueda activa, mostrar los resultados de la búsqueda
            searchQuery?.let { query ->
                viewModelPropertiesList.searchPropertiesByProvince(query) { properties ->
                    adapterProperty.submitList(properties.toMutableList())
                    updateSearchResultTextView(properties)
                }
            }
        }

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
    }

}