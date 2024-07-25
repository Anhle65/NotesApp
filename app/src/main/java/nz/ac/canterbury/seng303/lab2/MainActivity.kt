package nz.ac.canterbury.seng303.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nz.ac.canterbury.seng303.lab2.screens.CreateNote
import nz.ac.canterbury.seng303.lab2.screens.NoteCard
import nz.ac.canterbury.seng303.lab2.screens.NoteGrid
import nz.ac.canterbury.seng303.lab2.screens.NoteList
import nz.ac.canterbury.seng303.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2Theme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        // Add your AppBar content here
                        TopAppBar(
                            title = { Text("SENG303 Lab 2") },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(navController = navController, startDestination = "Home") {
                            composable("Home") {
                                Home(navController = navController)
                            }
                            composable(
                                "NoteCard/{noteId}",
                                arguments = listOf(navArgument("noteId") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val noteId = backStackEntry.arguments?.getString("noteId")
                                noteId?.let { noteIdParam: String -> NoteCard(noteIdParam) }
                            }
                            composable("NoteList") {
                                NoteList(navController)
                            }
                            composable("NoteGrid") {
                                NoteGrid(navController)
                            }
                            composable("CreateNote") {
                                CreateNote(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Lab 1")
        Button(onClick = { navController.navigate("CreateNote") }) {
            Text("Create Note")
        }
        Button(onClick = { navController.navigate("NoteCard/1") }) {
            Text("Go to Note Card")
        }
        Button(onClick = { navController.navigate("NoteList") }) {
            Text("Note List")
        }
        Button(onClick = { navController.navigate("NoteGrid") }) {
            Text("Note Grid")
        }
    }
}
