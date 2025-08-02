package com.my.businesscardapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.businesscardapp.ui.theme.BusinessCardAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.Composable
import androidx.compose.material3.LinearProgressIndicator



class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var proximitySensor: Sensor? = null

    // Use MutableState to trigger recomposition
    private var lightLevel by mutableFloatStateOf(0.0f)
    private var proximityLevel by mutableFloatStateOf(0.0f)
    private var magnetometerMessage by mutableStateOf("No magnetometer.")

    @Composable
    fun SensorDisplayScreen(lightLevel: Float, proximityLevel: Float, magnetometerMessage: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SensorCard("Current Light Level", lightLevel / 10000f, "$lightLevel", Color(0xFFB3E5FC), Color(0xFF81D4FA))
            SensorCard("Proximity", proximityLevel / 10f, "$proximityLevel", Color(0xFFFFCDD2), Color(0xFFEF9A9A))
            SensorCard("Magnetometer Status", 1f, magnetometerMessage, Color(0xFFFFE0B2), Color(0xFFFFCC80))
        }
    }

    @Composable
    fun SensorCard(title: String, progress: Float, value: String, startColor: Color, endColor: Color) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(startColor, endColor)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = progress.coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(4.dp)),
                    trackColor = Color.LightGray,
                    color = Color(0xFF00C853) // Green color for the progress bar
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = value, fontSize = 24.sp, color = Color.Black)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        magnetometerMessage = if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            "There's a magnetometer."
        } else {
            "No magnetometer."
        }

        setContent {
            BusinessCardAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SensorDisplayScreen(lightLevel, proximityLevel, magnetometerMessage)
                }
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_LIGHT -> {
                lightLevel = event.values[0]
            }
            Sensor.TYPE_PROXIMITY -> {
                proximityLevel = event.values[0]
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        proximitySensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}