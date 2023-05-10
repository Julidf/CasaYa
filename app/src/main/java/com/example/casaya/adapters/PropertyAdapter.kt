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

    fun addNewProperties(newProperties: List<Property>) {
        Log.d("Property Adapter", "Entrando al notificador del adapter")
        if (newProperties.isNotEmpty()) {
            properties.clear()
            properties.addAll(newProperties)
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: PropertyHolder, position: Int) {
        holder.setTitle(properties[position].getTitle())
        holder.setPrice(properties[position].getPrice())
        holder.setPublicationDays(properties[position].getPublicationDays())
        holder.getCard().setOnClickListener {
            //Cuando escucha un click, le envia a la funcion 'onClick' el valor de la posicion del item
            onClick(position)
        }
    }

    class PropertyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setImage() {

        }

        fun setTitle(title: String) {
            val txtTitleItem: TextView = view.findViewById(R.id.titleListTextView)
            txtTitleItem.text = title
        }

        fun setPublicationDays(days: Long) {
            val txtDayPublicationItem: TextView =
                view.findViewById(R.id.valueTimePublicationListTextView)
            txtDayPublicationItem.text = days.toString()
        }

        fun setPrice(price: Double) {
            val txtPriceItem: TextView = view.findViewById(R.id.priceRentListTextView)
            txtPriceItem.text = "$" + price.toString()
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardProperty)
        }
    }

}