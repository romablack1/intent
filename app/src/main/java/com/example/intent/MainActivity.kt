package com.example.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.intent.ui.theme.IntentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntentTheme {
                ContactBookApp()
            }
        }
    }
}

@Composable
fun ContactBookApp() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Контактная книга",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // 1. Позвонить
            Button(
                onClick = { dialPhoneNumber(context, "+7 (495) 123-45-67") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Позвонить", fontSize = 16.sp)
            }

            // 2. Написать email
            Button(
                onClick = { sendEmail(context, "contact@example.com", "Обращение") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Написать email", fontSize = 16.sp)
            }

            // 3. Показать офис на карте
            Button(
                onClick = { showOnMap(context, 60.0237, 30.2289, "Наш офис") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Показать офис на карте", fontSize = 16.sp)
            }

            // 4. Поделиться контактом
            Button(
                onClick = { shareContact(context, "Контакт: +7 (495) 123-45-67, contact@example.com") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Поделиться контактом", fontSize = 16.sp)
            }
        }
    }
}

// Вспомогательные функции

fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет приложения для звонков", Toast.LENGTH_SHORT).show()
    }
}

fun sendEmail(context: Context, address: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет почтового клиента", Toast.LENGTH_SHORT).show()
    }
}

fun showOnMap(context: Context, latitude: Double, longitude: Double, label: String) {
    val geoUri = "geo:0,0?q=$latitude,$longitude($label)".toUri()
    val intent = Intent(Intent.ACTION_VIEW, geoUri)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет приложения карт", Toast.LENGTH_SHORT).show()
    }
}

fun shareContact(context: Context, text: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
    if (chooser.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooser)
    } else {
        Toast.makeText(context, "Нет приложений для отправки текста", Toast.LENGTH_SHORT).show()
    }
}
