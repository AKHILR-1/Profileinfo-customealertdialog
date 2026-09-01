package com.example.ca1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ca1.ui.theme.Ca1Theme

data class StudentInfo(
    val name: String,
    val regNo: String,
    val phoneNo: String,
    val email: String,
    val bio: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ca1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun StudentApp(modifier: Modifier = Modifier) {
    var studentInfo by remember {
        mutableStateOf(
            StudentInfo(
                name = "AKHIL R",
                regNo = "12306822",
                phoneNo = "012456789",
                email = "akhil@example.com",
                bio = "this is bio"
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StudentCard(studentInfo = studentInfo)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { showDialog = true }) {
            Text("Update Information")
        }

        if (showDialog) {
            UpdateDialog(
                currentInfo = studentInfo,
                onDismiss = { showDialog = false },
                onSave = { updatedInfo ->
                    studentInfo = updatedInfo
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun StudentCard(studentInfo: StudentInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Student Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            
            InfoRow(label = "Name", value = studentInfo.name)
            InfoRow(label = "Reg No", value = studentInfo.regNo)
            InfoRow(label = "Phone", value = studentInfo.phoneNo)
            InfoRow(label = "Email", value = studentInfo.email)
            InfoRow(label = "Bio", value = studentInfo.bio)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = "$label: ", fontWeight = FontWeight.SemiBold, modifier = Modifier.width(80.dp))
        Text(text = value)
    }
}

@Composable
fun UpdateDialog(
    currentInfo: StudentInfo,
    onDismiss: () -> Unit,
    onSave: (StudentInfo) -> Unit
) {
    var name by remember { mutableStateOf(currentInfo.name) }
    var regNo by remember { mutableStateOf(currentInfo.regNo) }
    var phoneNo by remember { mutableStateOf(currentInfo.phoneNo) }
    var email by remember { mutableStateOf(currentInfo.email) }
    var bio by remember { mutableStateOf(currentInfo.bio) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Update Student Info") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = regNo, onValueChange = { regNo = it }, label = { Text("Reg No") })
                OutlinedTextField(value = phoneNo, onValueChange = { phoneNo = it }, label = { Text("Phone No") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Bio") }, minLines = 3)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(StudentInfo(name, regNo, phoneNo, email, bio))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
