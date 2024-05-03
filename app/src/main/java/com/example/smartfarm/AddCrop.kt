package com.example.smartfarm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarm.ui.theme.DarkGreen
import com.example.smartfarm.ui.theme.SmartFarmTheme

class AddCrop : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting2() {
    var nameOfCrop by remember {
        mutableStateOf(TextFieldValue())
    }
    var idOfIOT by remember {
        mutableStateOf(TextFieldValue())
    }
    var img by remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = if (nameOfCrop.text == "tomato") {
                    painterResource(id = R.drawable.tomato_crop)
                } else if(nameOfCrop.text == "broccoli"){
                    painterResource(id = R.drawable.broccoli)
                }else{
                     painterResource(id = R.drawable.ic_launcher_background)
                     },
                contentScale = ContentScale.Inside,
                contentDescription = "",
                modifier = Modifier
                    .size(height = 60.dp, width = 100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            OutlinedTextField(
                value = nameOfCrop,
                onValueChange = {
                    nameOfCrop = it
                },
                label = {
                    Text(text = "Enter Crop Name")
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.Black,
                    focusedBorderColor = DarkGreen,
                    unfocusedBorderColor = Color.Gray,
                    unfocusedTextColor = Color.Gray
                ),
                modifier = Modifier
                    .weight(4f)
            )
        }

        OutlinedTextField(
            value = nameOfCrop,
            onValueChange = {
                nameOfCrop = it
            },
            label = {
                Text(text = "Enter IOT Device Id")
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = Color.Gray,
                unfocusedTextColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val intent = Intent(context, SelectFieldOnMap::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF184C52)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Proceed",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Filled.KeyboardArrowRight,
                    contentDescription = "add circle",
                    tint = Color.White
                )
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    SmartFarmTheme {
        Greeting2()
    }
}