package com.daniel.graphingcalculator.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.graphingcalculator.R

class EquationInputAdapter(private val inputs: List<EquationInput>) : RecyclerView.Adapter<EquationInputAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView
        val inputField: EditText

        init {
            text = itemView.findViewById(R.id.equationInputNum)
            inputField = itemView.findViewById(R.id.equationInput)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.equation_input_item, parent, false))


    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = position.toString()
        holder.inputField.setText(inputs[position].content)
        holder.inputField.setOnTouchListener { _, _ ->
            inputs[position].content = holder.inputField.text.toString()
            println(inputs[position].content)
            false
        }
    }

    override fun getItemCount(): Int = inputs.size
}