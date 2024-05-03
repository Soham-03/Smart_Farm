package com.example.smartfarm.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddNoteDialog(showDialog: MutableState<Boolean>) {
    var noteText by remember { mutableStateOf("") }
//    var imageUri by remember { mutableStateOf<File?>(null) }
//    val storagePermission = rememberPermissionState(permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        uri?.let {
//            imageUri = File(it.path ?: "") // handle the uri to file conversion based on your use case
//        }
//    }

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    TextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        label = { Text("Note Text") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(150.dp)
//                            .clickable {
//                                if (storagePermission.status.isGranted) {
//                                    imagePickerLauncher.launch("image/*")
//                                } else {
//                                    storagePermission.launchPermissionRequest()
//                                }
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = Color.LightGray
//                        )
//                    ) {
//                        imageUri?.let {
//                            // Show selected image using an appropriate Composable (e.g., Image)
//                        } ?: run {
//                            Box(contentAlignment = Alignment.Center) {
//                                Text("Tap to select image")
//                            }
//                        }
//                    }
                    val context = LocalContext.current
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            uploadNoteToFirestore(noteText, context)
                            showDialog.value = false // Close dialog after upload
                        },
                        enabled = noteText.isNotBlank()
                    ) {
                        Text("Upload Note")
                    }
                }
            }
        }
    }
}

fun uploadNoteToFirestore(noteText: String, context: Context) {
    val db = FirebaseFirestore.getInstance()
    val note = hashMapOf(
        "text" to noteText,
        "timestamp" to System.currentTimeMillis().toString(),
        "image" to ""
    )
    db.collection("my-device")
        .document("week1")
        .collection("notes")
        .add(note)
        .addOnSuccessListener {
            Toast.makeText(context, "Added Note", Toast.LENGTH_SHORT).show()
        }
    // Add image file handling here. Typically, you'd upload the file to Firebase Storage first and then store the URL in Firestore
}
