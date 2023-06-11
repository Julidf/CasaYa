package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.adapters.PropertyAdapter
import com.example.casaya.adapters.UserPropertyAdapter
import com.example.casaya.entities.Property
import com.example.casaya.repositories.PropertyRepository
import com.example.casaya.viewmodels.UserPropertiesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserPropertiesFragment : Fragment() {

    private val viewModelUserProperties: UserPropertiesViewModel by activityViewModels()
    lateinit var v: View
    lateinit var recyclerUserProperties: RecyclerView
    private lateinit var adapterUserProperty: UserPropertyAdapter
    lateinit var noHavePropertiesTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_properties, container, false)

        recyclerUserProperties = v.findViewById(R.id.recyclerUserProperties)
        noHavePropertiesTextView = v.findViewById(R.id.noHavePropertiesTextView)

        return v
    }

    override fun onStart() {
        super.onStart()

        /**
         * Obtengo la lista inicial de propiedades para enviar al Adapter
         */
        viewModelUserProperties.getMyProperties()

        Log.d(
            "My Properties_2",
            "Mis propiedades ${viewModelUserProperties.myPropertiesList}"
        )

        adapterUserProperty = UserPropertyAdapter(
            myProperties = viewModelUserProperties.myPropertiesList,
            onClick = { position ->
                //Snackbar.make(v, "Click en ${repository.getProperties()[position].getTitle()}", Snackbar.LENGTH_SHORT).show()

                //Guardo referencia de la Property seleccionada en el ViewModel del Fragment
                viewModelUserProperties.selectedProperty =
                    viewModelUserProperties.myPropertiesList[position]
                Log.d(
                    "Selected Property",
                    "Detalle de la propiedad ${viewModelUserProperties.selectedProperty}"
                )
                //val action =
                //PropertiesListFragmentDirections.actionPropertiesListFragmentToPropertyDetailFragment()
                //findNavController().navigate(R.id.action_propertiesListFragment_to_propertyDetailFragment)
            },
            onClickEdit = { position ->
                viewModelUserProperties.selectedProperty =
                    viewModelUserProperties.myPropertiesList[position]
                val action = ContainerProfileFragmentDirections.actionContainerProfileFragmentToPublishPropertyFragment()
                findNavController().navigate(action)
                Log.d(
                    "Edit Property",
                    "Editar propiedad ${viewModelUserProperties.selectedProperty}"
                )
            },
            onClickDelete = { position ->
                viewModelUserProperties.selectedProperty =
                    viewModelUserProperties.myPropertiesList[position]
                Log.d(
                    "Delete Property",
                    "Eliminar propiedad ${viewModelUserProperties.myPropertiesList[position]}"
                )
            },
            requireContext(),
        )


        /**
         * Configuro la forma en que se visualizara el RecyclerView
         */
        recyclerUserProperties.layoutManager = LinearLayoutManager(context)
        recyclerUserProperties.adapter = adapterUserProperty

        /**
         * Observo los cambios de mi collection Properties de la DB
         */
        viewModelUserProperties.myListLiveData.observe(viewLifecycleOwner) { myList ->
            if (myList.isEmpty()) {
                noHavePropertiesTextView.visibility = View.VISIBLE
            }
            adapterUserProperty.submitList(myList.toMutableList())
            adapterUserProperty.notifyDataSetChanged()
        }

    }

}