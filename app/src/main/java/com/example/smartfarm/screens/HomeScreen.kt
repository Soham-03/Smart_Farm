package com.example.smartfarm.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.smartfarm.AddCrop
import com.example.smartfarm.Global
import com.example.smartfarm.NoteViewModel
import com.example.smartfarm.R
import com.example.smartfarm.SelectedField
import com.example.smartfarm.model.Field
import com.example.smartfarm.model.Note
import com.example.smartfarm.model.NoteDatabase
import com.example.smartfarm.model.UserAndFields
import com.example.smartfarm.ui.theme.MyGray
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.gms.common.server.response.FastJsonResponse
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.initialize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var userAndFields by remember {
        mutableStateOf(UserAndFields("", ArrayList()))
    }
    val listOfFields by remember {
        mutableStateOf(ArrayList<Field>())
    }
    var response by remember {
        mutableStateOf("response")
    }
    var _notes by remember {
        mutableStateOf(ArrayList<Note>())
    }

    var jsonString by remember {
        mutableStateOf("")
    }

    // Initialize FusedLocationProviderClient
//    Firebase.initialize(LocalContext.current)
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        Firebase.initialize(context)
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("my-device").document("week1")
        val docRefForFields = db.collection("my-device").document("week1")
            .collection("fields")
        val notesQuerySnapshot = db.collection("my-device")
            .document("week1")
            .collection("notes")
            .orderBy("timestamp", Query.Direction.DESCENDING)
        try {
            val documentSnapshot = documentRef.get().await()
            val documentSnapshot2 = docRefForFields.get().await()
            val documentSnapshotNotes = notesQuerySnapshot.get().await()
            val data = documentSnapshot.data
            val listOfFieldNames = documentSnapshot2.documents
            for(field in listOfFieldNames){
                val docsnapshot3 = db.collection("my-device")
                    .document("week1")
                    .collection("fields")
                    .document(field.id)
                    .get()
                    .await()
                val fieldData = docsnapshot3.data
                listOfFields.add(
                   Field(
                        fieldData?.get("n").toString(),
                        fieldData?.get("p").toString(),
                        fieldData?.get("k").toString(),
                        fieldData?.get("moisture").toString(),
                        "",
                        fieldData?.get("polygon").toString(),
                        fieldData!!["crop"].toString(),
                        fieldData["timestamp"].toString(),
                       fieldData["plantedOn"].toString(),
                    )
                )
                println("Field Data:"+fieldData?.get("n").toString()+
                    fieldData?.get("p").toString()+
                    fieldData?.get("k").toString())
            }
            
            if (data != null) {
                userAndFields = UserAndFields(
                    name = data["name"].toString(),
                    listOfFields = listOfFields
                )
                println("List of Fields" + listOfFields)
            }

            val notes = documentSnapshotNotes.documents
            val tempnotes = ArrayList<Note>()
            for (note in notes){
                println("YEs")
                tempnotes.add(Note(image = note["image"].toString(), text = note["text"].toString(), timestamp = note["timestamp"].toString()))
            }
            _notes = tempnotes
            println("Notes: "+_notes)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    var addNoteSelected by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

//    val database = Room.databaseBuilder(context, NoteDatabase::class.java, "notes_database").build()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Color(0xFFFFFFFF)
            )
            .padding(16.dp)
    ) {
        //Get the Current Day
        val dayOfWeek =
            SimpleDateFormat("EEEE", Locale.getDefault()).format(Calendar.getInstance().time)

        // Get the current time
        val currentTime =
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Calendar.getInstance().time)

        // Get the current date
        val currentDate =
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().time)

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.Black, CircleShape)
                        .background(MyGray)
                ) {
                    Image(
                        painterResource(id = R.drawable.farmer),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Hello there 👋",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = userAndFields.name,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MyGray)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Today's Weather",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF184C52),
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = dayOfWeek,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = currentTime,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Text(
                            text = "30℃",
                            color = Color.White,
                            fontSize = 64.sp,
                        )
                        Text(
                            text = "Partly Cloudy",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Percipitaion: 0mm",
                            color = MyGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "Humidity: 66%",
                            color = MyGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "Wind: 10kph",
                            color = MyGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )

                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_sunny),
                        contentDescription = "",
                        modifier = Modifier
                            .weight(0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF406B71)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Today is an Optimal Day for Pesticide Application",
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        //List of Fields
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MyGray
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Notes",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        tint = Color.Black,
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                ) {
                    items(_notes) { mynote ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = mynote.image,
                                contentDescription = "note_img",
                                contentScale = ContentScale.Crop,
                                error = painterResource(id = R.drawable.ic_launcher_background),
                                modifier = Modifier
                                    .size(width = 100.dp, height = 80.dp)
                                    .clip(RoundedCornerShape(18.dp))
                            )
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = formatMilliseconds(mynote.timestamp),
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = mynote.text,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                val showDialog = remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        addNoteSelected = true
                        println("NOOOTESS: "+_notes)
                        showDialog.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF184C52)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "add circle",
                            tint = Color.White
                        )
                        Text(
                            text = "Add New Note",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
                if (showDialog.value) {
                    AddNoteDialog(showDialog)
                }

            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "My Fields",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Add New",
                color = Color.DarkGray,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, AddCrop::class.java)
                        context.startActivity(intent)
                    }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ){
            for(field in listOfFields){
                item(){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MyGray)
                            .padding(12.dp)
                            .clickable {
                                Global.selectedField = field
                                val intent = Intent(context, SelectedField::class.java)
                                context.startActivity(intent)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 100.dp, height = 80.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Gray)
                        ) {
                            Image(
                                painter = if(field.cropName == "tomato"){painterResource(id = R.drawable.tomato_crop)}else{painterResource(id = R.drawable.broccoli)},
                                contentDescription = "",
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier
                                .height(80.dp)
                        ) {
                            Text(
                                text = field.cropName,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Planted on "+field.plantedOn,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                val intent = Intent(context, AddCrop::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF184C52)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "add circle",
                    tint = Color.White
                )
                Text(
                    text = "Add New Crop",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun NotesSingleRow(note: Note){

}

fun formatMilliseconds(timeInMillis: String): String {
    // Parse the millisecond string to a Long
    val millis = timeInMillis.toLong()

    // Create a Date object from the milliseconds
    val date = Date(millis)

    // Create a SimpleDateFormat object with the desired format
    val formatter = SimpleDateFormat("dd-MMM - h:mm a")

    // Return the formatted date string
    return formatter.format(date)
}

private suspend fun makeGetRequest(url: String): String {
    return withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
            val responseStringBuilder = StringBuilder()
            var line: String?
            while (inputStream.readLine().also { line = it } != null) {
                responseStringBuilder.append(line)
            }
            responseStringBuilder.toString()
        } else {
            // Handle error response here
            println("Error:"+connection.responseMessage)
            "Error: ${connection.responseMessage}"
        }
    }
}

private suspend fun gg(): String {
    var responseData = ""
    CoroutineScope(Dispatchers.IO).launch {
        val client = OkHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${Global.latitude}&lon=${Global.longitude}&appid=00e123f67efadf747d9ed5f759c82b1e"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            responseData = response.body?.string().toString()
            println("Weather data for: $responseData")
        }
    }
    return responseData
}

@Preview
@Composable
fun HomePreview(){
    HomeScreen()
}