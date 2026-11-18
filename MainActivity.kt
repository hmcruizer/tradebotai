package com.tradebotai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TradebotApp() }
    }
}

@Composable
fun TradebotApp() {
    var market by remember { mutableStateOf("Forex") }
    var asset by remember { mutableStateOf("EUR/USD") }
    var prices by remember { mutableStateOf("1.0800, 1.0805, 1.0802, 1.0808, 1.0810") }
    var signal by remember { mutableStateOf("WAIT") }
    var note by remember { mutableStateOf("Enter five or more closing prices and analyze.") }

    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("TradebotAI", style = MaterialTheme.typography.headlineLarge)
                Text("Signal analysis prototype")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { market = "Forex" }) { Text("Forex") }
                    Button(onClick = { market = "OTC" }) { Text("OTC") }
                }
                Text("Selected market: $market")
                OutlinedTextField(asset, { asset = it }, label = { Text("Asset / pair") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(
                    prices, { prices = it },
                    label = { Text("Recent closing prices, comma separated") },
                    supportingText = { Text("Example: 1.0800, 1.0805, 1.0802, 1.0808, 1.0810") },
                    modifier = Modifier.fillMaxWidth(), minLines = 3
                )
                Button(onClick = {
                    val values = prices.split(",", " ", "\n").mapNotNull { it.trim().toDoubleOrNull() }
                    if (values.size < 5) {
                        signal = "WAIT"
                        note = "Enter at least five valid numeric prices."
                    } else {
                        val recent = values.takeLast(5)
                        val delta = recent.takeLast(2).average() - recent.take(2).average()
                        val threshold = recent.average() * 0.00005
                        when {
                            delta > threshold -> { signal = "CALL"; note = "Sample prices trend upward. Demonstration rule only." }
                            delta < -threshold -> { signal = "PUT"; note = "Sample prices trend downward. Demonstration rule only." }
                            else -> { signal = "WAIT"; note = "No clear direction in the entered sample." }
                        }
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Analyze prices") }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Signal", style = MaterialTheme.typography.titleMedium)
                        Text(signal, style = MaterialTheme.typography.headlineLarge)
                        Text(note)
                        Text("Market: $market | Asset: $asset")
                    }
                }
                Text(
                    "Important: prices are entered manually. This app is not connected to Quotex, live Forex/OTC feeds, or trade execution. Signals are experimental and not financial advice. Do not risk real money based on them.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
