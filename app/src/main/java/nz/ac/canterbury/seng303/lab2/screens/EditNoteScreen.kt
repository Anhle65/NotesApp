package nz.ac.canterbury.seng303.lab2.screens

import android.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.models.Note
import nz.ac.canterbury.seng303.lab2.viewmodels.EditNoteViewModel
import nz.ac.canterbury.seng303.lab2.viewmodels.NoteViewModel

@Composable
fun EditNote (navController: NavController,
              noteId : String,
              noteViewModel: NoteViewModel,
              editNoteViewModel: EditNoteViewModel
) {
    val context = LocalContext.current
    noteViewModel.getNoteById(noteId = noteId.toIntOrNull())
    val selectedNoteState by noteViewModel.selectedNote.collectAsState(null)
    val note: Note? = selectedNoteState

    LaunchedEffect(key1 = note) {
        if (note != null) {
            editNoteViewModel.updateTitle(note.title)
            editNoteViewModel.updateContent(note.content)
        }else {
            noteViewModel.getNoteById(noteId.toIntOrNull())
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = editNoteViewModel.title,
            onValueChange = {editNoteViewModel.updateTitle(it)},
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Content input
        OutlinedTextField(
            value = editNoteViewModel.content,
            onValueChange = {editNoteViewModel.updateContent(it)},
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .fillMaxHeight()
                .weight(1f)
        )

        // Save button
        Button(
            onClick = {
//                val note = Note(Random.nextInt(0, Int.MAX_VALUE),
//                    title,
//                    content,
//                    System.currentTimeMillis(),
//                    false)
                noteViewModel.editNote(noteId = noteId.toInt(), Note(noteId.toInt(), editNoteViewModel.title, editNoteViewModel.content, timestamp = System.currentTimeMillis(), false))
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Edit note: ")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, id -> /* Run some code on click */
                        navController.navigate("noteList")

                    }
//                    .setNegativeButton("Cancel") { dialog, id ->
//                        // Dismiss the dialog
//                        dialog.dismiss()
//                    }
                val alert = builder.create()
                alert.show()

            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}
