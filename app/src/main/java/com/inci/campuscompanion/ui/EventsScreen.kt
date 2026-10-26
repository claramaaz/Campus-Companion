package com.inci.campuscompanion.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventsScreen(
    state: EventsUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            EventsUiState.Loading -> CircularProgressIndicator()
            EventsUiState.Empty -> Text("Aucun événement pour le moment.")
            is EventsUiState.Error -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(state.message)
                Button(onClick = onRetry) { Text("Retry") }
            }
            is EventsUiState.Content -> Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = onRetry) { Text("Refresh") }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = state.events, key = { it.id }) { event ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(event.title, style = MaterialTheme.typography.titleMedium)
                            Text("${event.timeLabel} · ${event.locationLabel}")
                        }
                    }
                }
            }
        }
    }
}