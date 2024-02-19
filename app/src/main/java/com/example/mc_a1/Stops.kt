package com.example.mc_a1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

data class Stop(val name: String, val distanceKm: Float, val distanceMiles: Float)

@Composable
fun GenerateStopsInput(onGenerateStops: (Int) -> Unit) {
    var numberOfStops by remember { mutableIntStateOf(10) }

    OutlinedTextField(
        value = numberOfStops.toString(),
        onValueChange = {
            numberOfStops = it.toIntOrNull() ?: 0
        },
        label = { Text("Number of Stops") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onGenerateStops(numberOfStops)
        }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

fun generateStops(numberOfStops: Int): List<Stop> {
    val stops = mutableListOf<Stop>()
    stops.add(Stop("Stop ${0}", (0) * 1f, (0) * 0.621f))
    repeat(numberOfStops) {
        val distance = Random.nextInt(5, 16)
        stops.add(Stop("Stop ${it + 1}", (distance) * 1f, (distance) * 0.621f))
    }
    return stops
}

@Composable
fun StopListItem(stop: Stop, showInKm: Boolean) {
    val distance = if (showInKm) stop.distanceKm else stop.distanceMiles
    val formattedDistance = String.format("%.2f", distance)

    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 1.dp,
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            text = "${stop.name}: $formattedDistance ${if (showInKm) "km" else "miles"}",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun DynamicStopList(stops: List<Stop>, showInKm: Boolean) {

    if (stops.size > 11) {
        LazyStopList(stops = stops, showInKm = showInKm)
    } else {
        SimpleStopList(stops = stops, showInKm = showInKm)
    }

    var totalDistance = 0.0
    for (i in stops.indices) {
        totalDistance += if (showInKm) stops[i].distanceKm else stops[i].distanceMiles
    }

    Spacer(modifier = Modifier.height(8.dp))
    val formattedDistance = String.format("%.2f", totalDistance)
    Text("Total Distance: $formattedDistance ${if (showInKm) "km" else "miles"}")
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun SimpleStopList(stops: List<Stop>, showInKm: Boolean) {

    Column {
        stops.forEach { stop ->
            StopListItem(stop = stop, showInKm = showInKm)
        }
    }
}

@Composable
fun LazyStopList(stops: List<Stop>, showInKm: Boolean) {
    LazyColumn(modifier = Modifier.height((400).dp)) {
        items(stops) { stop ->
            StopListItem(stop = stop, showInKm = showInKm)
        }
    }
}

fun calculateTotalDistanceLeft(stops: List<Stop>, currentStopIndex: Int, showInKm: Boolean): Float {
    var totalDistanceLeft = 0f
    for (i in currentStopIndex + 1 until stops.size) {
        totalDistanceLeft += if (showInKm) stops[i].distanceKm else stops[i].distanceMiles
    }
    return totalDistanceLeft
}

fun calculateTotalDistanceCovered(
    stops: List<Stop>,
    currentStopIndex: Int,
    showInKm: Boolean
): Float {
    var totalDistanceCovered = 0f
    for (i in 0 until currentStopIndex + 1) {
        totalDistanceCovered += if (showInKm) stops[i].distanceKm else stops[i].distanceMiles
    }
    return totalDistanceCovered
}

@Composable
fun DistanceSwitchButton(onToggle: (Boolean) -> Unit, showInKm: Boolean) {
    Button(
        onClick = { onToggle(!showInKm) },
        modifier = Modifier
            .padding(4.dp)
    ) {
        Text(if (showInKm) "KM" else "MI")
    }
}