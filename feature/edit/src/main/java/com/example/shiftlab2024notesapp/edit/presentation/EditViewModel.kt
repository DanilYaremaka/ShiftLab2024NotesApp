package com.example.shiftlab2024notesapp.edit.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftlab2024notesapp.edit.domain.usecase.InsertNoteUseCase
import com.example.shiftlab2024notesapp.shared.entity.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class EditViewModel(
    private val note: Note,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val router: EditRouter
): ViewModel() {

    private val _state = MutableStateFlow<EditState>(EditState.Initial)
    val state: StateFlow<EditState> = _state

    private val title = mutableStateOf(note.title)
    private val text = mutableStateOf(note.text)
    private val isFavourite = mutableStateOf(note.isFavourite)

    fun changeTitle(value: String) {
        title.value = value
        showNote()
    }

    fun changeFavouriteState() {
        isFavourite.value = !isFavourite.value
        showNote()
    }

    fun changeText(value: String) {
        text.value = value
        showNote()
    }

    fun showNote() {
        _state.value = EditState.Content(note.copy(
            title = title.value,
            text = text.value,
            isFavourite = isFavourite.value
        ))
    }

    fun insertNote() {
        val state = state.value
        if (state !is EditState.Content) return

        if (validate()) return

        viewModelScope.launch {
            try {
                insertNoteUseCase(note.copy(
                    text = text.value,
                    title = title.value,
                    isFavourite = isFavourite.value
                ))
                closeNote()
            } catch (ce: CancellationException) {
                throw ce
            } catch (ex: Exception) {
                _state.value = EditState.Failure(ex.localizedMessage.orEmpty())
            }
        }
    }

    private fun validate(): Boolean {
        return (text.value == "" && title.value == "")
    }

    private fun closeNote() {
        router.saveAndClose()
    }

    fun closeDraft() {
        if (note.id == null)
            router.close()
        else closeNote()
    }
}