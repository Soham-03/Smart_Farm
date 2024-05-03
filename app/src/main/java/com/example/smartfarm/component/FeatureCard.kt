package com.example.smartfarm.component

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import com.example.smartfarm.ui.theme.LightGreen

@Composable
fun FeatureCard(icon: Int, title: String){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LightGreen
        )
    ) {

    }
}