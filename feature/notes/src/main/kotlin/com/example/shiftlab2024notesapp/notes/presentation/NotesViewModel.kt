package com.example.shiftlab2024notesapp.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftlab2024notesapp.notes.domain.usecase.GetNotesUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase
): ViewModel() {

    private val _state = MutableStateFlow<NotesState>(NotesState.Initial)
    val state: StateFlow<NotesState> = _state

    fun loadNotes() {
        if (_state.value is NotesState.Content || _state.value is NotesState.Loading)
            return

        viewModelScope.launch {
            try {
                val notes = getNotesUseCase()
                _state.value = NotesState.Content(notes)
            } catch (ce: CancellationException) {
                throw ce
            } catch (ex: Exception) {
                _state.value = NotesState.Failure(ex.localizedMessage.orEmpty())
            }
        }
    }

    fun reloadNotes() {
        _state.value = NotesState.Initial
        loadNotes()
    }

}