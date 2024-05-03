package com.example.smartfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartfarm.ui.theme.SmartFarmTheme

class CropRecommend : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen()
                }
            }
        }
    }
}

@Composable
fun Screen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ){
            Card (
                colors = CardDefaults.cardColors(
                    contentColor = Color(0xFFF7F7F7)
                )

            ){
                Column {
                    Text(
                        text = "Pigeon Peas in Farm 1",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(22.dp)
                    )
                    Row {

                    }
                    Image(
                        painter = painterResource(id = R.drawable.peas_plant),
                        contentDescription = "plant",
                        modifier = Modifier
                            .padding(start = 22.dp, top = 10.dp)
                    )
                }
            }
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.pea),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
                    .border(width = 1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )

            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
        Text(
            text = "Based on the data:",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.n),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
                    .border(width = 1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )
            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.p),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
                    .border(width = 1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )

            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.k),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
                    .border(width = 1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )

            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.temperature),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
                    .border(width = 1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )

            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.moist),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
                    .border(width = 1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )

            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SmartFarmTheme {
        Screen()
    }
}