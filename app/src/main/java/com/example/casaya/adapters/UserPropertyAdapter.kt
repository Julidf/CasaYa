package com.example.casaya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.casaya.R
import com.example.casaya.entities.Property
import com.example.casaya.fragments.UserPropertiesFragmentDirections

class UserPropertyAdapter (

    var myProperties: MutableList<Property>,
    var onClick: (Int) -> Unit,
    var onClickEdit: (Int) -> Unit,
    var onClickDelete: (Int) -> Unit,
    val context: Context

    ) : RecyclerView.Adapter<UserPropertyAdapter.UserPropertyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPropertyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_property_item, parent, false)
        return (UserPropertyHolder(view))
    }

    override fun getItemCount(): Int {
        return myProperties.size
    }

    override fun onBindViewHolder(holder: UserPropertyHolder, position: Int) {
        val property = myProperties[position]
        holder.setUbication(property.setStringAddressProperty())
        holder.setPublicationDays(property.setStringPublicationDays())
        holder.getCard().setOnClickListener {
            //Cuando escucha un click, le envia a la funcion 'onClick' el valor de la posicion del item
            onClick(position)
        }
        holder.getEditButton().setOnClickListener {
            //Cuando escucha un click en el Button Edit, le envia a la funcion 'onClickEdit' el valor de la posicion del item
            onClickEdit(position)
        }
        holder.getDeleteButton().setOnClickListener {
            //Cuando escucha un click en el Button Edit, le envia a la funcion 'onClickDelete' el valor de la posicion del item
            onClickDelete(position)
        }
    }

    fun submitList(properties: MutableList<Property>) {
        this.myProperties = properties
    }

    class UserPropertyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setUbication(ubication: String?) {
            val txtUbicationItem: TextView = view.findViewById(R.id.ubicationTextView)
            txtUbicationItem.text = ubication
        }

        fun setPublicationDays(commentDaysPublication: String) {
            val txtDayPublicationItem: TextView =
                view.findViewById(R.id.valueTimePublicationTextView)
            txtDayPublicationItem.text = commentDaysPublication
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardUserProperty)
        }

        fun getEditButton() : ImageButton {
            return view.findViewById(R.id.editButton)
        }

        fun getDeleteButton() : ImageButton {
            return view.findViewById(R.id.deleteButton)
        }
    }


}