package com.example.smartfarm.screens

import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartfarm.Global
import com.example.smartfarm.R
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun SelectedField() {
    var response by remember {
        mutableStateOf("")
    }
    var prompt by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var generativeModel by remember {
        mutableStateOf(GenerativeModel("",""))
    }
    LaunchedEffect(Unit) {
        generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = "AIzaSyAcODqO3muGpih3AISgU4Dr7hZfFm3GWqU"
        )
        val result = generativeModel.generateContent("You are a machine learning model which gives suggestions based on the crop, and soil nutrient values such as N, P and K values, so firstly tell them the health of soil, and what all suggestions you'll give to the farmer for his field, fertilizers if need which ones to be used and lastly the best crop you'll recommend based on the location that is india and the soil npk values, tell each in one line and keep it short and simnple,give output in bullet points format, Here are the values N:${Global.selectedField!!.n}, P:${Global.selectedField!!.p}, K:${Global.selectedField!!.k}, Location: Lat:${Global.latitude}, Lng:${Global.longitude}, Crop Name: ${Global.selectedField!!.cropName}, Moisture: ${Global.selectedField!!.moist}")
        response = result.text.toString()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 12.dp)
        ){
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "back_arrow",
                tint = Color.Black,
                modifier = Modifier
                    .weight(0.5f)
                    .size(40.dp)
                    .clickable {
                    }
            )
            Column(
                modifier = Modifier
                    .weight(3f)
            ){
                Text(
                    text = Global.selectedField!!.cropName,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Planted On "+Global.selectedField!!.plantedOn,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "back_arrow",
                tint = Color.Black,
                modifier = Modifier
                    .weight(0.5f)
                    .size(40.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(Color(0xFFF8F8F8))
                .fillMaxWidth()
        ){
            Image(
                painter = if(Global.selectedField!!.cropName == "tomato"){painterResource(id = R.drawable.tomato_crop)}else{painterResource(id = R.drawable.broccoli)},
                contentDescription = "crop image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(20.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    ){
                        Text(
                            text = "Soil Nutrients Status",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Last Updated "+Global.selectedField!!.timestamp,
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "More Info",
                        tint = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(text = "Moisture")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(
                        progress = (Global.selectedField!!.moist.toInt()/10).toFloat() / 10,
                        color = Color.Blue,
                        trackColor = Color(0xFFF8F8F8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .height(8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    println("Notro: "+(Global.selectedField!!.p.toInt()/10).toFloat() / 10 )
                    Text(text = "Nitrogen")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(
                        progress = (Global.selectedField!!.n.toInt()/10).toFloat() / 10  ,
                        color = Color.Blue,
                        trackColor = Color(0xFFF8F8F8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .height(8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(text = "Phosphorus")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(
                        progress = (Global.selectedField!!.p.toInt()/10).toFloat() / 10 ,
                        color = Color.Blue,
                        trackColor = Color(0xFFF8F8F8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .height(8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically, 
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(text = "Potassium")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(
                        progress = (Global.selectedField!!.k.toInt()/10).toFloat() / 10 ,
                        color = Color.Blue,
                        trackColor = Color(0xFFF8F8F8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .height(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                    ){
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF8F8F8))
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.ic_history),
                                contentDescription = "Hitory",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "History",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Crop Status",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Setup",
                        color = Color(0xFF34A780)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF8F8F8))
                            .align(Alignment.CenterStart)
                            .padding(10.dp)
                    ){
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    brush = Brush
                                        .horizontalGradient(
                                            0.0f to Color(0xFF1ABE58),
                                            0.5f to Color(0xFF2ECEA8)
                                        )
                                )
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_plant),
                                contentDescription = "Hitory",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Planting Date",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(10.dp)
                    ){
                        Text(
                            text = Global.selectedField!!.plantedOn,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text =  calculateDaysPassed(Global.selectedField!!.plantedOn).toString()+" Days Ago",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                }
            }
            Text(
                text = "Expert's Advice",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp),
            )
            Text(text = response,  modifier = Modifier.padding(start = 20.dp, bottom = 20.dp))
        }
    }
}

fun calculateDaysPassed(dateString: String): Long {
    // Define the date format
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

    // Parse the given date string to LocalDate
    val date = LocalDate.parse(dateString, formatter)

    // Get today's date
    val today = LocalDate.now()

    // Calculate the difference in days
    val daysPassed = ChronoUnit.DAYS.between(date, today)

    return daysPassed
}



@Preview
@Composable
fun PreviewSelectedField(){
    SelectedField()
}