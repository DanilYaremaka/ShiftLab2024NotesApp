package com.example.shiftlab2024notesapp.edit.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiftlab2024notesapp.edit.R
import com.example.shiftlab2024notesapp.shared.entity.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentComponent(
    note: Note,
    onTitleChanged: (String) -> Unit,
    onTextChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onFavouriteClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onFavouriteClicked() }) {
                        Icon(
                            imageVector = if (note.isFavourite) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { onSaveClicked() }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            NoteTitle(
                noteTitle = note.title,
                onTitleChanged = onTitleChanged
            )
            Spacer(modifier = Modifier.height(8.dp))

            NoteInfo(
                charCount = note.text.length,
                lastUpdate = note.dateToString()
            )

            Spacer(modifier = Modifier.height(8.dp))
            NoteText(
                noteText = note.text,
                onTextChanged = onTextChanged
            )
        }
    }

    BackHandler {
        onBackClicked()
    }
}

@Composable
fun NoteTitle(
    noteTitle: String,
    onTitleChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = noteTitle,
        onValueChange = onTitleChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        placeholder = {
            Text(text = stringResource(R.string.title_hint))
        },
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
fun NoteInfo(
    charCount: Int,
    lastUpdate: String?
) {
    val dateInfo = if (lastUpdate.isNullOrBlank()) ""
    else "$lastUpdate | "

    Text(
        text = dateInfo  + pluralStringResource(
            id = R.plurals.chars_count,
            count = charCount,
            charCount
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
}

@Composable
fun NoteText(
    noteText: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = noteText,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
        placeholder = {
            Text(text = stringResource(R.string.text))
        },
        textStyle = TextStyle(
            fontWeight = FontWeight.Normal
        )
    )
}