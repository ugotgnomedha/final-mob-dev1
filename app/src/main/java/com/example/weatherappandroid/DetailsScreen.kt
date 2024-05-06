package com.example.weatherappandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(onCitySelected: (String) -> Unit, navigateBack: () -> Unit) {
    var city by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Details Screen", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text(text = "Enter City") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onCitySelected(city)
                city = ""
            },
            enabled = city.isNotBlank()
        ) {
            Text(text = "Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = navigateBack
        ) {
            Text(text = "Go Back to Main")
        }
    }
}
