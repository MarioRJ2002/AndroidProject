package com.example.androidproject.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.trackmysleepquality.database.ProdDatabase
import com.example.android.trackmysleepquality.database.Producto
import com.example.android.trackmysleepquality.sleepquality.PedidoViewModelFactory
import com.example.androidproject.databinding.PedidoFragmentBinding

class PedidoFragment : Fragment() {

    private lateinit var viewModel: PedidoViewModel

    companion object {
        fun newInstance() = PedidoFragment()
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = ProdDatabase.getInstance(application).DatabaseDao
        val binding = PedidoFragmentBinding.inflate(layoutInflater)
        var Camarero:String
        var precioTotal:String
        val viewModelFact = PedidoViewModelFactory(dataSource)

        val viewModel =
            ViewModelProvider(
                this, viewModelFact).get(PedidoViewModel::class.java)

        binding.button10.setOnClickListener{
            viewModel.restarDia()
            binding.textView15.setText(viewModel.diaConsultar.toString())
        }
        binding.button11.setOnClickListener{
            viewModel.sumarDia()
            binding.textView15.setText(viewModel.diaConsultar.toString())
        }
        binding.button2.setOnClickListener {
            if (viewModel.getCroquetas().toInt() == 0) {

            } else {
                viewModel.croquetaMenos()
                binding.textView3.setText(viewModel.getCroquetas())
            }
        }

        binding.button3.setOnClickListener{
            viewModel.croquetaMas()
            binding.textView3.setText(viewModel.getCroquetas())
        }

        binding.button4.setOnClickListener {
            if (viewModel.getMontaditos().toInt() == 0) {
            } else {
                viewModel.montaditoMenos()
                binding.textView6.setText(viewModel.getMontaditos())
            }
        }

        binding.button5.setOnClickListener{
            viewModel.montaditoMas()
            binding.textView6.setText(viewModel.getMontaditos())
        }

        binding.button6.setOnClickListener {
            if (viewModel.getaprobados().toInt() == 0) {
            } else {
                viewModel.aprobadoMenos()
                binding.textView9.setText(viewModel.getaprobados())
            }
        }

        binding.button7.setOnClickListener{
            viewModel.aprobadoMas()
            binding.textView9.setText(viewModel.getaprobados())
        }

        binding.button12.setOnClickListener{
            this.context?.let { it1 -> viewModel.sendEmail(it1) }
        }

        binding.button8.setOnClickListener{
            viewModel.selectData(binding.textView15.getText().toString().toInt(),dataSource)
            binding.textView16.setText("ID del producto: "+viewModel.selectedData.productoId)
            binding.textView17.setText("Nombre del producto: "+viewModel.selectedData.nombre)
        }

        binding.button.setOnClickListener {
            precioTotal = "El precio total a pagar por su pedido es:"+viewModel.calcularPrecios().toString()+"$"
            Camarero = "Le atenderá en su pedido: "+viewModel.property.value?.name
            binding.textView10.setText(precioTotal)
            binding.textView14.setText(Camarero)
            //val navController = activity?.let { it1 -> Navigation.findNavController(it1, R.id.navigation_graph) }
            //if (navController != null) { navController.navigate(R.id.action_pedidoFragment_to_pedidoConfirmadoFragment) }
            //view?.findNavController()?.navigate(R.id.action_pedidoFragment_to_pedidoConfirmadoFragment)
            viewModel.insertCurrentData(dataSource)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PedidoViewModel::class.java)

    }

}