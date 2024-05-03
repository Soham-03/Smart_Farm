package com.example.smartfarm

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.room.Room
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.smartfarm.bottomnavigation.BottomNavigation
import com.example.smartfarm.model.NoteDatabase
import com.example.smartfarm.ui.theme.SmartFarmTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request location permissions
        requestLocationPermission()
        setContent {
            SmartFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigation()
                    val checkRequest = PeriodicWorkRequest.Builder(FirestoreCheckWorker::class.java, 15, TimeUnit.MINUTES)
                        .build()

                    WorkManager.getInstance(this).enqueue(checkRequest)
//                    val database = Room.databaseBuilder(this@MainActivity, NoteDatabase::class.java, "notes_database").build()
                }
            }
        }
    }
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, get current location
            getCurrentLocation()
        } else {
            // Permission denied, show a toast or handle accordingly
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestLocationPermission() {
        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission is already granted, get current location
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Get current location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        Global.latitude = latitude
                        Global.longitude = longitude
                        println("Location"+latitude+"--"+longitude)
                        // Use latitude and longitude
                        Toast.makeText(
                            this,
                            "Latitude: $latitude, Longitude: $longitude",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Handle case where location is null
                        Toast.makeText(
                            this,
                            "Unable to retrieve current location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure to retrieve location
                    Toast.makeText(
                        this,
                        "Failed to get current location: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            // Location permission is not granted, request permission again
            requestLocationPermission()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartFarmTheme {
        Greeting("Android")
    }
}