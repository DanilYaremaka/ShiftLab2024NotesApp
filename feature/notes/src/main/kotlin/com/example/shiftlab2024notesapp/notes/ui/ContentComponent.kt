package com.example.shiftlab2024notesapp.notes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    swipedToDelete: (note: Note) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.notes_title)) },
                actions = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            )
        },
        floatingActionButton = {
            Button(
                onClick = onAddClicked,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2) ,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 76.dp
            ),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            //verticalArrangement = Arrangement.spacedBy(8.dp),
            //reverseLayout = true
        ) {
            items(notes, { it.hashCode() }) { note ->
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