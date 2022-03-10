package com.mutualmobile.barricade2.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.barricade2.Barricade
import com.mutualmobile.barricade2.R
import com.mutualmobile.barricade2.ui.theme.ToolbarRed
import com.mutualmobile.barricade2.utils.showToast

private const val TAG = "BarricadeConfigScreen"

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
                    TextField(
                        value = delayTfValue.toString(),
                        onValueChange = {
                            it.toLongOrNull()?.let { updatedTfValue ->
                                delayTfValue = updatedTfValue
                            } ?: run {
                                delayTfValue = 0
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        )
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
        LazyColumn {
            barricade.getConfig().forEach { config ->
                item {
                    config.value.responses[config.value.defaultIndex].let { defaultResponse ->
                        var isRowExpanded by remember { mutableStateOf(false) }
                        val arrowRotation by animateFloatAsState(targetValue = if (isRowExpanded) 180f else 0f)
                        val cardElevation by animateFloatAsState(targetValue = if (isRowExpanded) 8f else 0f)

                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            Card(
                                elevation = cardElevation.dp,
                                shape = RoundedCornerShape(10),
                                modifier = Modifier.padding(cardElevation.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple()
                                        ) {
                                            isRowExpanded = !isRowExpanded
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Spacer(modifier = Modifier.padding(top = 8.dp))
                                        Text(
                                            text = config.key,
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            style = MaterialTheme.typography.h6,
                                        )
                                        Text(
                                            text = defaultResponse.responseFileName,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 4.dp
                                            ),
                                            style = MaterialTheme.typography.body2,
                                        )
                                        Spacer(modifier = Modifier.padding(top = 8.dp))
                                    }
                                    Icon(
                                        imageVector = Icons.Outlined.KeyboardArrowDown,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(end = 16.dp)
                                            .rotate(arrowRotation)
                                    )
                                }
                            }
                            AnimatedVisibility(
                                visible = isRowExpanded,
                                enter = fadeIn() + slideInVertically(),
                                exit = fadeOut() + slideOutVertically()
                            ) {
                                Column {
                                    config.value.responses.forEachIndexed { index, response ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = cardElevation.dp)
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = rememberRipple()
                                                ) {
                                                    barricade.setResponse("random", index)
                                                    isRowExpanded = false
                                                }
                                        ) {
                                            Text(
                                                response.responseFileName,
                                                style = MaterialTheme.typography.body1,
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
