package com.example.shiftlab2024notesapp.notes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiftlab2024notesapp.notes.R
import com.example.shiftlab2024notesapp.shared.entity.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentComponent(
    notes: List<Note>,
    onItemSelected: (note: Note) -> Unit,
    onAddClicked: () -> Unit,
    swipedToDelete: (note: Note) -> Unit,
    isShowFavourite: Boolean,
    onShowFavouriteClicked: () -> Unit,
    searchQuery: String,
    searchQueryChanged: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.notes_title)) },
                actions = {
                    IconButton(onClick = { onShowFavouriteClicked() }) {
                        Icon(
                            imageVector = if (isShowFavourite) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Button(
                onClick = onAddClicked,
            ) {
                Icon(imageVector = Icons.Default.Create, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = searchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                placeholder = {
                    Text(text = stringResource(R.string.search_hint))
                }
            )

            if (notes.isNotEmpty())
                NotesListNotEmpty(
                    notes = notes,
                    onItemSelected = onItemSelected,
                    swipedToDelete = swipedToDelete,
                )
            else NoteListEmpty()
        }

    }
}

@Composable
fun NoteListEmpty(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(
                id = R.drawable.notes_icon_256),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.notes_is_empty),
                textAlign = TextAlign.Center
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListNotEmpty(
    notes: List<Note>,
    onItemSelected: (note: Note) -> Unit,
    swipedToDelete: (note: Note) -> Unit,
) {
    val scrollState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2) ,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 76.dp
        ),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState
    ) {
        items(notes, { it.id.hashCode() }) { note ->
            val dismissBoxState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when (it) {
                        SwipeToDismissBoxValue.StartToEnd -> {
                            return@rememberSwipeToDismissBoxState false
                        }

                        SwipeToDismissBoxValue.EndToStart -> {
                            swipedToDelete(note)
                        }

                        SwipeToDismissBoxValue.Settled -> {
                            return@rememberSwipeToDismissBoxState false
                        }
                    }
                    return@rememberSwipeToDismissBoxState true
                },
                positionalThreshold = { it * .50f }
            )
            SwipeToDismissBox(
                modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                state = dismissBoxState,
                backgroundContent = { DismissBackground(dismissBoxState) },
                enableDismissFromStartToEnd = false
            ) {
                NoteItem(
                    note = note,
                    onItemSelected = { onItemSelected(note) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
        SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete"
        )
    }
}

@Composable
fun NoteItem(
    note: Note,
    onItemSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected() }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = note.title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            ),
            text = note.text,
            fontSize = 16.sp,
            maxLines = 4
        )
    }
}