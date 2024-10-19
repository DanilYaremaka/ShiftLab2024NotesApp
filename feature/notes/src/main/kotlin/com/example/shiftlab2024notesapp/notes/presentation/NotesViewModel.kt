package com.example.shiftlab2024notesapp.notes.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val router: NoteRouter
) : ViewModel() {

    private val _state = MutableStateFlow<NotesState>(NotesState.Initial)
    val state: StateFlow<NotesState> = _state

    private val notes = mutableStateOf<List<Note>>(emptyList())
    private val isShowFavourite = mutableStateOf(false)
    private val searchQuery = mutableStateOf("")


    init {
        loadNotes()
    }

    fun changeSearchQuery(newValue: String) {
        searchQuery.value = newValue
        Log.d("notesVM", "$newValue, ${searchQuery.value}")
        _state.value = NotesState.Content(
            notes = getList(),
            isShowFavourite = isShowFavourite.value,
            searchQuery = searchQuery.value
        )
    }

    fun changeShowFavouriteState() {
        isShowFavourite.value = !isShowFavourite.value
        getNotes()
    }

    private fun getFavourite(before: List<Note>): List<Note> {
        return if (isShowFavourite.value) before.filter {
            it.isFavourite
        } else before
    }

    private fun getSearchQuery(before: List<Note>): List<Note> {
        return if (searchQuery.value != "") before.filter {
            it.title.contains(searchQuery.value) || it.text.contains(searchQuery.value)
        } else before
    }

    private fun getList(): List<Note> {
        var result = getFavourite(notes.value)

        result = getSearchQuery(result)

        return result
    }

    private fun loadNotes() {
        if (_state.value is NotesState.Content || _state.value is NotesState.Loading)
            return

        _state.value = NotesState.Loading

        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                notes.value = getNotesUseCase()
                _state.value = NotesState.Content(
                    notes = getList(),
                    isShowFavourite = isShowFavourite.value,
                    searchQuery = searchQuery.value
                )
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
        _state.value = NotesState.Content(
            notes = oldList,
            isShowFavourite = isShowFavourite.value,
            searchQuery = searchQuery.value
        )
    }

    fun openNote(note: Note?) {
        if (note != null)
            router.openNote(note)
        else
            router.openNewNote()
    }
}