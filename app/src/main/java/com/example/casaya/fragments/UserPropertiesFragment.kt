package com.example.casaya.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
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
import com.google.android.material.snackbar.Snackbar
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

    //Elementos del Dialog
    private lateinit var dialog: Dialog
    private lateinit var titleDialog: TextView
    private lateinit var messageDialog: TextView
    private lateinit var yesButtonDialog: Button
    private lateinit var noButtonDialog: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_properties, container, false)

        //Inicializo el Dialog
        initDialog()

        recyclerUserProperties = v.findViewById(R.id.recyclerUserProperties)
        noHavePropertiesTextView = v.findViewById(R.id.noHavePropertiesTextView)

        return v
    }

    private fun initDialog() {
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        titleDialog = dialog.findViewById(R.id.titleDialog)
        messageDialog = dialog.findViewById(R.id.messageDialog)
        yesButtonDialog = dialog.findViewById(R.id.yesButtonDialog)
        noButtonDialog = dialog.findViewById(R.id.noButtonDialog)
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

                //Seteo mensajes
                titleDialog.text = "Eliminar propiedad"
                messageDialog.text = "Estas seguro de que deseas eliminar la propiedad ubicada en ${viewModelUserProperties.selectedProperty!!.setStringAddressProperty()}"

                dialog.show()

                //Boton SI
                yesButtonDialog.setOnClickListener {
                    viewModelUserProperties.deleteProperty(viewModelUserProperties.selectedProperty!!)
                    Snackbar.make(v, "La propiedad seleccionada, se ha eliminado exitosamente", Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                //Boton NO
                noButtonDialog.setOnClickListener {
                    dialog.dismiss()
                }

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