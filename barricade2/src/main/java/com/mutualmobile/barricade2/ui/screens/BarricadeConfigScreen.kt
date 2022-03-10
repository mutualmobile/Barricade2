package com.mutualmobile.barricade2.ui.screens

import android.app.Activity
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.mutualmobile.barricade2.R
import com.mutualmobile.barricade2.ui.theme.ToolbarRed

@Composable
fun BarricadeConfigScreen() {
    val ctx = LocalContext.current
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
                    IconButton(onClick = {}) {
                        Icon(painterResource(id = R.drawable.ic_timer), "Delay", tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Icon(painterResource(id = R.drawable.ic_undo), "Reset", tint = Color.White)
                    }
                },
                backgroundColor = ToolbarRed
            )
        }
    ) {
    }
}
