package com.playerzero.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraApp()
        }
    }
}

@Composable
fun CalculadoraApp() {
    var display by remember { mutableStateOf("0") }
    var operador by remember { mutableStateOf("") }
    var valorAnterior by remember { mutableStateOf("") }

    fun onButtonClick(value: String) {
        when (value) {
            "C" -> {
                display = "0"
                operador = ""
                valorAnterior = ""
            }
            "+", "-", "*", "/" -> {
                operador = value
                valorAnterior = display
                display = "0"
            }
            "=" -> {
                if (valorAnterior.isNotEmpty() && operador.isNotEmpty()) {
                    val resultado = when (operador) {
                        "+" -> valorAnterior.toFloat() + display.toFloat()
                        "-" -> valorAnterior.toFloat() - display.toFloat()
                        "*" -> valorAnterior.toFloat() * display.toFloat()
                        "/" -> if (display != "0") valorAnterior.toFloat() / display.toFloat() else "Erro"
                        else -> "Erro"
                    }
                    display = if (resultado is Float && resultado % 1 == 0f) resultado.toInt().toString() else resultado.toString()
                    operador = ""
                    valorAnterior = ""
                }
            }
            else -> {
                display = if (display == "0") value else display + value
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = display,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        val buttons = listOf(
            listOf("1", "2", "3", "-"),
            listOf("4", "5", "6", "*"),
            listOf("7", "8", "9", "/"),
            listOf("C", "0", "=", "+")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (button in row) {
                    Button(
                        onClick = { onButtonClick(button) },
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text(text = button, fontSize = 24.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
