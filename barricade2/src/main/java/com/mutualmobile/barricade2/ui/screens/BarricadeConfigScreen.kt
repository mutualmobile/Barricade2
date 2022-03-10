package com.mutualmobile.barricade2.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.mutualmobile.barricade2.Barricade
import com.mutualmobile.barricade2.R
import com.mutualmobile.barricade2.ui.theme.ToolbarRed
import com.mutualmobile.barricade2.utils.showToast

@Composable
fun BarricadeConfigScreen() {
    val ctx = LocalContext.current
    val barricade by remember { mutableStateOf(Barricade.getInstance()) }
    var isDelayDialogVisible by remember { mutableStateOf(false) }
    var isResetDialogVisible by remember { mutableStateOf(false) }
    var delayTfValue by remember { mutableStateOf(barricade.delay) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Barricade Config", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        (ctx as Activity).onBackPressed()
                    }) {
                        Icon(Icons.Default.ArrowBack, "Go back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        delayTfValue = barricade.delay
                        isDelayDialogVisible = true
                    }) {
                        Icon(painterResource(id = R.drawable.ic_timer), "Delay", tint = Color.White)
                    }
                    IconButton(onClick = {
                        isResetDialogVisible = true
                    }) {
                        Icon(painterResource(id = R.drawable.ic_undo), "Reset", tint = Color.White)
                    }
                },
                backgroundColor = ToolbarRed
            )
        }
    ) {
        AnimatedVisibility(visible = isDelayDialogVisible, enter = fadeIn(), exit = fadeOut()) {
            AlertDialog(
                onDismissRequest = { isDelayDialogVisible = false },
                confirmButton = {
                    Button(onClick = {
                        barricade.delay = delayTfValue
                        if (barricade.delay == delayTfValue) {
                            isDelayDialogVisible = false
                        } else {
                            ctx.showToast("Value not updated! Please try again.")
                        }
                    }) {
                        Text("Set")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isDelayDialogVisible = false
                    }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Delay time") },
                text = {
                    OutlinedTextField(
                        value = delayTfValue.toString(),
                        onValueChange = {
                            it.toLongOrNull()?.let { updatedTfValue ->
                                delayTfValue = updatedTfValue
                            }
                        }
                    )
                }
            )
        }
        AnimatedVisibility(visible = isResetDialogVisible, enter = fadeIn(), exit = fadeOut()) {
            AlertDialog(
                onDismissRequest = { isResetDialogVisible = false },
                confirmButton = {
                    TextButton(onClick = {
                        barricade.reset()
                        isResetDialogVisible = false
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isResetDialogVisible = false
                    }) {
                        Text("No")
                    }
                },
                text = {
                    Text("Are you sure you want to reset the configuration?")
                }
            )
        }
    }
}
