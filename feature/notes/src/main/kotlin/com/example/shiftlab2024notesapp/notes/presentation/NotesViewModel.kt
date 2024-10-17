package com.example.shiftlab2024notesapp.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftlab2024notesapp.notes.domain.usecase.AddNoteUseCase
import com.example.shiftlab2024notesapp.notes.domain.usecase.DeleteNoteUseCase
import com.example.shiftlab2024notesapp.notes.domain.usecase.GetNotesUseCase
import com.example.shiftlab2024notesapp.shared.entity.Note
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NotesState>(NotesState.Initial)
    val state: StateFlow<NotesState> = _state

    private lateinit var notes: MutableList<Note>

    fun loadNotes() {
        if (_state.value is NotesState.Content || _state.value is NotesState.Loading)
            return

        viewModelScope.launch {
            try {
                notes = getNotesUseCase().toMutableList()
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

    fun addNote() {
        viewModelScope.launch {
            try {
                addNoteUseCase(
                    Note(
                        title = "zametka",
                        text = "text zametki"
                    )
                )
            } catch (ce: CancellationException) {
                throw ce
            }
        }
    }

    fun deleteNote(note: Note) {
        val state = state.value
        if (state !is NotesState.Content) return

        viewModelScope.launch {
            try {
                deleteNoteUseCase(note)
            } catch (ce: CancellationException) {
                throw ce

            }
        }
        val oldList = state.notes.toMutableList()
        oldList.remove(note)
        _state.value = NotesState.Content(notes = oldList)
    }

}