package nz.ac.canterbury.seng303.lab2.screens

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.models.Note
import nz.ac.canterbury.seng303.lab2.util.convertTimestampToReadableTime

@Composable
fun NoteList(navController: NavController) {
    LazyColumn {
        items(Note.getNotes()) { note ->
            NoteItem(navController = navController, note = note)
            Divider() // Add a divider between items
        }
    }
}

@Composable
fun NoteItem(navController: NavController, note: Note) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("NoteCard/${note.id}") },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Display title and timestamp
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = convertTimestampToReadableTime(note.timestamp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Display edit and delete buttons (icons)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                /* We're not looking into state this lab so we won't actually edit anything,
                 though you may want to think about how you could re-use the CreateNote composable
                  (or at least what common functionality you could abstract) for editing */
                Toast.makeText(context, "Can't do that just yet! we'll learn to handle state next lab", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = Color.Blue
                )
            }
            IconButton(onClick = {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Delete note: \"${note.title}\"?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        // We're not looking into state this lab so we won't actually delete anything
                        Toast.makeText(context, "Can't do that just yet! we'll learn to handle state next lab", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}