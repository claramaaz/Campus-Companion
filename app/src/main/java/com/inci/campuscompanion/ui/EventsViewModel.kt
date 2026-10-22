package com.inci.campuscompanion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inci.campuscompanion.data.EventsRepository
import com.inci.campuscompanion.model.CampusEvent
import com.inci.campuscompanion.network.NetworkModule
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface EventsUiState {
    data object Loading : EventsUiState
    data class Content(val events: List<CampusEvent>) : EventsUiState
    data object Empty : EventsUiState
    data class Error(val message: String) : EventsUiState
}

class EventsViewModel(
    private val repository: EventsRepository = EventsRepository(NetworkModule.api)
) : ViewModel() {

    private val _state = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val state: StateFlow<EventsUiState> = _state

    init { load() }

    fun load() {
        _state.value = EventsUiState.Loading
        viewModelScope.launch {
            _state.value = try {
                val events = repository.getEvents()
                if (events.isEmpty()) EventsUiState.Empty else EventsUiState.Content(events)
            } catch (e: IOException) {
                EventsUiState.Error("Pas de connexion. Réessaie.")
            } catch (e: HttpException) {
                if (e.code() in 500..599) EventsUiState.Error("Le serveur a un problème. Réessaie plus tard.")
                else EventsUiState.Error("Requête refusée (${e.code()}).")
            } catch (e: Exception) {
                EventsUiState.Error("Une erreur est survenue.")
            }
        }
    }
}