package com.example.smartfarm.screens

import android.graphics.Point
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Animation
import androidx.compose.animation.core.Transition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartfarm.Global
import com.example.smartfarm.presentation.MapStyle
import com.example.smartfarm.ui.theme.DarkGreen
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.RequestOptions
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(){
    var points by remember { mutableStateOf(listOf<Point>()) }
    var selectionEnabled by remember {
        mutableStateOf(false)
    }
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
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ){
        OutlinedTextField(
            value = prompt,
            onValueChange = {
                prompt = it
            },
            label = {
                Text(text = "How can i help you?")
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
        val scope = rememberCoroutineScope()
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            scope.launch {
                val result = generativeModel.generateContent("You are a farm and agriculture expert based in india, Now answer this question by one of the farmers in india:"+prompt.text)
                response = result.text.toString()
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF184C52)
            )
        ) {
            Text(text = "Get Expert Advise")
        }
        OutlinedTextField(
            value = TextFieldValue(response),
            onValueChange = {

            },
            enabled = false,
            label = {
                Text(text = "Response from Gemma")
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
                .verticalScroll(rememberScrollState())
        )
    }

}
