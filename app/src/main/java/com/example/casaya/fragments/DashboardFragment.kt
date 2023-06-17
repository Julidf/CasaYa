package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.casaya.R
import com.example.casaya.viewmodels.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private var viewModel: DashboardViewModel = DashboardViewModel()
    private lateinit var view: View

    //Elementos del Dashboard
    private lateinit var clientsQuantityTextView: TextView
    private lateinit var propertiesQuantityTextView: TextView

    //Elementos de cada Item del Dashboard
    private lateinit var txtApartmentQuantity: TextView
    private lateinit var txtHouseQuantity: TextView
    private lateinit var txtPhQuantity: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        clientsQuantityTextView = view.findViewById(R.id.clientsQuantityTextView)
        propertiesQuantityTextView = view.findViewById(R.id.propertiesQuantityTextView)

        txtApartmentQuantity = view.findViewById(R.id.txtApartmentQuantity)
        txtHouseQuantity = view.findViewById(R.id.txtHouseQuantity)
        txtPhQuantity = view.findViewById(R.id.txtPhQuantity)

        return view
    }

    override fun onStart() {
        super.onStart()

        viewModel.getQuantity()

        viewModel.usersQuantityLiveData.observe(viewLifecycleOwner) { usersQuantity ->
            clientsQuantityTextView.text = usersQuantity.toString()
        }

        viewModel.propertiesQuantityLiveData.observe(viewLifecycleOwner) { propertiesQuantity ->
            propertiesQuantityTextView.text = propertiesQuantity.toString()
        }

        viewModel.apartmentsQuantityLiveData.observe(viewLifecycleOwner) { apartmentsQuantity ->
            txtApartmentQuantity.text = apartmentsQuantity.toString()
        }

        viewModel.housesQuantityLiveData.observe(viewLifecycleOwner) { housesQuantity ->
            txtHouseQuantity.text = housesQuantity.toString()
        }

        viewModel.phQuantityLiveData.observe(viewLifecycleOwner) { phQuantity ->
            txtPhQuantity.text = phQuantity.toString()
        }
    }

}