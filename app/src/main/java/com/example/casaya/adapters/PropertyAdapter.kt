package com.example.casaya.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.casaya.R
import com.example.casaya.entities.Property

class PropertyAdapter(

    var properties: MutableList<Property>,
    var onClick: (Int) -> Unit

) : RecyclerView.Adapter<PropertyAdapter.PropertyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.property_item, parent, false)
        return (PropertyHolder(view))
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: PropertyHolder, position: Int) {
        val property = properties[position]
        holder.setTitle(property.getTitle())
        holder.setPrice(property.getPrice())
        holder.setPublicationDays(property.setStringPublicationDays())
        holder.setOperationType(property.obtainOperationType())
        holder.getCard().setOnClickListener {
            //Cuando escucha un click, le envia a la funcion 'onClick' el valor de la posicion del item
            onClick(position)
        }
    }

    fun submitList(properties: MutableList<Property>) {
        this.properties = properties
    }

    class PropertyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setImage() {

        }

        fun setTitle(title: String?) {
            val txtTitleItem: TextView = view.findViewById(R.id.titleListTextView)
            txtTitleItem.text = title
        }

        fun setPublicationDays(commentDaysPublication: String) {
            val txtDayPublicationItem: TextView =
                view.findViewById(R.id.valueTimePublicationListTextView)
            txtDayPublicationItem.text = commentDaysPublication
        }

        fun setPrice(price: Double) {
            val txtPriceItem: TextView = view.findViewById(R.id.priceRentListTextView)
            txtPriceItem.text = "$" + price.toString()
        }

        fun setOperationType(operationType: String) {
            val txtOperationType: TextView = view.findViewById(R.id.operationTypeListTextView)
            txtOperationType.text = operationType
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardProperty)
        }
    }

}