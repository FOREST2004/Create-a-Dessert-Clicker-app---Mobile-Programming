


package com.example.dessertclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dessertclicker.ui.theme.DessertClickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DessertClickerTheme {
                DessertClickerApp()
            }
        }
    }
}

data class Dessert(
    val imageId: Int,
    val name: String,
    val price: Int,
    var quantity: Int = 0,
    var totalPrice: Int = 0
)

@Composable
fun DessertClickerApp() {
    val desserts = remember {
        mutableStateListOf(
            Dessert(R.drawable.cupcake, "Cupcake", 5),
            Dessert(R.drawable.donut, "Donut", 10)
        )
    }

    var totalQuantity by remember { mutableStateOf(0) }
    var totalPrice by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dessert Clicker") },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display desserts vertically
            desserts.forEach { dessert ->
                DessertItem(
                    dessert = dessert,
                    onDessertClicked = {
                        // Update the clicked dessert
                        dessert.quantity++
                        dessert.totalPrice = dessert.price * dessert.quantity

                        // Recalculate totals
                        totalQuantity = desserts.sumOf { it.quantity }
                        totalPrice = desserts.sumOf { it.totalPrice }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Display totals at the bottom
            TotalSection(totalQuantity = totalQuantity, totalPrice = totalPrice)
        }
    }
}

@Composable
fun DessertItem(dessert: Dessert, onDessertClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(red = 0.5f, green = 0.1f, blue = 0.7f, alpha = 0.1f))
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = dessert.imageId),
            contentDescription = dessert.name,
            modifier = Modifier
                .size(200.dp)
                .clickable { onDessertClicked() }
        )

        Text(
            text = dessert.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Price: $${dessert.price}",
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Quantity: ${dessert.quantity}",
                fontSize = 16.sp
            )

            Text(
                text = "Total: $${dessert.totalPrice}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TotalSection(totalQuantity: Int, totalPrice: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(red = 0.1f, green = 0.1f, blue = 0.1f, alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SUMMARY BILL",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Desserts:",
                fontSize = 30.sp
            )

            Text(
                text = "$totalQuantity",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Revenue:",
                fontSize = 26.sp
            )

            Text(
                text = "$${totalPrice}",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun DessertClickerPreview() {
    DessertClickerTheme {
        DessertClickerApp()
    }
}