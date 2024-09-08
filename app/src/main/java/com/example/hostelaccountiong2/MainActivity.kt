package com.example.hostelaccountiong2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.hostelaccountiong2.sheets.TableManager
import com.example.hostelaccountiong2.ui.theme.HostelAccountiong2Theme
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HostelAccountiong2Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    GoogleSheetsManager(runBlocking { getTokens().first })
//                    GoogleSheetsManager("").Menu()
                    TableManager.Menu()
                }
            }
        }
    }
}


