package com.mutualmobile.barricade2

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mutualmobile.barricade2.ui.theme.Barricade2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Barricade2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val ctx = LocalContext.current
    val activity = ctx as Activity
    Text(
        text = "Hello $name!",
        modifier = Modifier.clickable {
            val intent = Intent(ctx.application, BarricadeConfigActivity::class.java)
            intent.flags += Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags += Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT
            }
            activity.startActivity(intent)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Barricade2Theme {
        Greeting("Android")
    }
}
