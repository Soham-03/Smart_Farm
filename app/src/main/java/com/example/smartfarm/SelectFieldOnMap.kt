package com.example.smartfarm

import android.graphics.Point
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarm.presentation.MapStyle
import com.example.smartfarm.screens.MapDrawer
import com.example.smartfarm.ui.theme.SmartFarmTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

class SelectFieldOnMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3()
                }
            }
        }
    }
}

@Composable
fun Greeting3() {
    var points by remember { mutableStateOf(listOf<Point>()) }
    var selectionEnabled by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
    MyMap(points = points)
    AnimatedVisibility(
        visible = selectionEnabled,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        MapDrawer(
            onDrawingEnd = {
                points = it
            },
            selectionEnabled
        )
    }
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = {
                points = emptyList()
                selectionEnabled = true
            },
                modifier = Modifier

            ) {
                Text("Start Selection")
            }
            Button(onClick = {

            },
                modifier = Modifier
            ) {
                Text("Proceed")
            }
            Button(onClick = {
                points = emptyList()
                selectionEnabled = false
            },
                modifier = Modifier
            ) {
                Text("Cancel Selection")
            }
        }
    }
}


@Composable
fun MyMap(points: List<Point>) {
    val losAngeles = LatLng(Global.latitude!!, Global.longitude!!)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(losAngeles, 18f)
    }

    var geoPoints by remember { mutableStateOf(listOf<LatLng>()) }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapStyleOptions = MapStyleOptions(MapStyle.json)
        )
    ) {
        cameraPositionState.projection?.let { projection ->
            geoPoints = points.map {
                projection.fromScreenLocation(it)
            }
        }
        if (geoPoints.isNotEmpty()) {
            println("Points: "+geoPoints.toString())
            Polygon(
                points = geoPoints,
                fillColor = Color.Red.copy(alpha = 0.4f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    SmartFarmTheme {
        Greeting3()
    }
}