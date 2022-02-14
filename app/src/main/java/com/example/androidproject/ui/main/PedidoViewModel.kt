package com.example.androidproject.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.DatabaseDao
import com.example.android.trackmysleepquality.database.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PedidoViewModel (
    val database: DatabaseDao
)
    : ViewModel() {

    var diaConsultar = 0

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    var selectedData = Producto(-1,"",0.00)

   init {
       _status.value = ""
       getUsersProperties()
    }

    private val _property = MutableLiveData<ObjetoRetrofit>()

    val property: LiveData<ObjetoRetrofit>
        get() = _property

    private fun getUsersProperties() {
        viewModelScope.launch {
            try {
                var listResult = Api.retrofitService.getProperties()
                _status.value = "Success: ${listResult.size} Mars properties retrieved"
                if (listResult.size > 0) {
                    _property.value = listResult[0]
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }


    fun selectData(prodId:Int, dataSource: DatabaseDao){
        viewModelScope.launch(Dispatchers.Main){
            try{
                selectedData = dataSource.get(prodId)!!
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun insertCurrentData(dataSource: DatabaseDao) {
        viewModelScope.launch(Dispatchers.Main){

            var llaves = numProductos.keys
            var prods: Array<String?> = arrayOfNulls<String>(numProductos.keys.size.toInt())
            var cont = 0
            llaves.forEach{prods[cont]=it
                cont+=1}


            var precios: Array<Double?> = arrayOfNulls<Double>(numProductos.keys.size.toInt())
            cont = 0
            llaves.forEach{precios[cont]=precioProductos[it]
                cont+=1}

            val Prod1 = Producto((dataSource.getAllProducts().value?.size?.plus(1)), prods[0],precios[0])
            val Prod2 = Producto((dataSource.getAllProducts().value?.size?.plus(1)), prods[1],precios[1])
            val Prod3 = Producto((dataSource.getAllProducts().value?.size?.plus(1)), prods[2],precios[2])

            try{
                dataSource.existeProd(Prod1.nombre,Prod1.precio)
            }catch (e:java.lang.Exception) {
                dataSource.insert(Prod1)
            }
            try{
                dataSource.existeProd(Prod2.nombre,Prod2.precio)
            }catch(e:java.lang.Exception) {
                dataSource.insert(Prod2)
            }
            try{
                dataSource.existeProd(Prod3.nombre,Prod3.precio)
            }catch (e:java.lang.Exception){
                dataSource.insert(Prod3)
            }
        }
    }

    val numProductos = mutableMapOf(
        Pair("croquetas de jamon", 0),
        Pair("Montaditos de filete", 0),
        Pair("Un aprobado en android", 0)
    )

    val precioProductos = mapOf<String,Double>(
        Pair("croquetas de jamon", 2.95),
        Pair("Montaditos de filete", 1.95),
        Pair("Un aprobado en android",5.00)
    )

    fun sumarDia(){
        diaConsultar+=1
    }

    fun restarDia(){
        if(diaConsultar==0){
        }else{
            diaConsultar-=1
        }
    }

    fun croquetaMas() {
        val base = numProductos["croquetas de jamon"]
        if (base != null) {
            numProductos["croquetas de jamon"] = base + 1
        }
    }

    fun croquetaMenos() {
        val base = numProductos["croquetas de jamon"]
        if (base != null) {
            numProductos["croquetas de jamon"] = base - 1
        }
    }

    fun getCroquetas(): String {
        return numProductos["croquetas de jamon"].toString()
    }

    fun montaditoMas() {
        val base = numProductos["Montaditos de filete"]
        if (base != null) {
            numProductos["Montaditos de filete"] = base + 1
        }
    }

    fun montaditoMenos() {
        val base = numProductos["Montaditos de filete"]
        if (base != null) {
            numProductos["Montaditos de filete"] = base - 1
        }
    }

    fun getMontaditos(): String {
        return numProductos["Montaditos de filete"].toString()
    }

    fun aprobadoMas() {
        val base = numProductos["Un aprobado en android"]
        if (base != null) {
            numProductos["Un aprobado en android"] = base + 1
        }
    }
    fun sendEmail(context:Context){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("mailto:FeedbackBarDePaco@gmail.com")
        context.startActivity(Intent.createChooser(intent, "E-mail"))
    }
    fun aprobadoMenos() {
        val base = numProductos["Un aprobado en android"]
        if (base != null) {
            numProductos["Un aprobado en android"] = base - 1
        }
    }

    fun getaprobados(): String {
        return numProductos["Un aprobado en android"].toString()
    }

    fun calcularPrecios(): Double{
        var precioTotal = 0.00
        numProductos.forEach{
            precioTotal+= (precioProductos[it.key]!!*it.value)
        }
        return precioTotal
    }
}
