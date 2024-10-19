package com.example.eje10_divisas

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val currencies = arrayOf("USD", "EUR", "CLP", "GBP")
    private val conversionRates = mapOf(
        "USD a EUR" to 0.85,
        "USD a CLP" to 870.0,
        "USD a GBP" to 0.75,
        "EUR a USD" to 1.18,
        "EUR a CLP" to 1025.0,
        "EUR a GBP" to 0.88,
        "CLP a USD" to 0.0011,
        "CLP a EUR" to 0.00098,
        "CLP a GBP" to 0.00084,
        "GBP a USD" to 1.33,
        "GBP a EUR" to 1.14,
        "GBP a CLP" to 1190.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los componentes del layout
        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        val spinnerCurrencies = findViewById<Spinner>(R.id.spinnerCurrencies)
        val spinnerCurrencies2 = findViewById<Spinner>(R.id.currencies2)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val buttonConvert = findViewById<Button>(R.id.buttonConvert)
        val buttonReset = findViewById<Button>(R.id.buttonReset)

        // Configurar el Spinner con las opciones de divisas
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCurrencies.adapter = adapter
        spinnerCurrencies2.adapter = adapter // Inicialmente, el segundo spinner tiene las mismas divisas

        // Acción del primer spinner
        spinnerCurrencies.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                updateSecondSpinner(spinnerCurrencies.selectedItem.toString(), adapter)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Acción del botón de convertir
        buttonConvert.setOnClickListener {
            val amountStr = editTextAmount.text.toString()
            if (amountStr.isNotEmpty()) {
                val amount = amountStr.toDouble()
                val selectedCurrencyFrom = spinnerCurrencies.selectedItem.toString()
                val selectedCurrencyTo = spinnerCurrencies2.selectedItem.toString()

                // Crear la clave de conversión basada en la selección de divisas
                val conversionKey = "$selectedCurrencyFrom a $selectedCurrencyTo"
                val conversionRate = conversionRates[conversionKey]

                // Realizar la conversión
                if (conversionRate != null) {
                    val result = amount * conversionRate
                    textViewResult.text = "Resultado:\n $result"
                } else {
                    textViewResult.text = "Error:\n Conversión no disponible"
                }
            } else {
                textViewResult.text = "Por favor, ingrese\n un monto válido"
            }
        }

        // Acción del botón de reset
        buttonReset.setOnClickListener {
            editTextAmount.text.clear()
            textViewResult.text = ""
            spinnerCurrencies.setSelection(0)
            spinnerCurrencies2.setSelection(0) // Reiniciar también el segundo spinner
        }
    }

    private fun updateSecondSpinner(selectedCurrency: String, adapter: ArrayAdapter<String>) {
        val filteredCurrencies = currencies.filter { it != selectedCurrency } // Filtrar las divisas
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, filteredCurrencies)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.currencies2).adapter = adapter2 // Actualizar el segundo spinner
        findViewById<Spinner>(R.id.currencies2).setSelection(0) // Reiniciar el segundo spinner a la primera opción
    }
}
