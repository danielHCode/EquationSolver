package com.daniel.graphingcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.graphingcalculator.data.EquationInput
import com.daniel.graphingcalculator.data.EquationInputAdapter
import parse.Parser


class DrawInput : Fragment() {

    lateinit var drawBtn: ImageButton
    lateinit var inputList: RecyclerView
    lateinit var addInputBtn: Button

    private val inputs = mutableListOf(EquationInput(""))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_draw_input, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawBtn = view.findViewById(R.id.drawButton)
        inputList = view.findViewById(R.id.inputList)
        addInputBtn = view.findViewById(R.id.addEquationBtn)


        inputList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EquationInputAdapter(inputs)
            itemAnimator = DefaultItemAnimator()
        }

        addInputBtn.setOnClickListener {
            inputs+=EquationInput("")
            inputList.adapter?.notifyDataSetChanged()
        }

        drawBtn.setOnClickListener {
            val parser = Parser()
            val expressions = inputs.filter { it.content.isNotEmpty() }.map {
                parser.parse(it.content)
            }
        }
    }

}