//package com.example.smartfarm
//
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import com.example.smartfarm.model.Field
//import com.example.smartfarm.model.UserAndFields
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
class FirebaseFunctions {}
//
//    Firebase.initialize(LocalContext.current)
//
//    val db = FirebaseFirestore.getInstance()
//    suspend fun getUserAndFields(){
//        val documentRef = db.collection("my-device").document("week1")
//        val documentSnapshot = documentRef.get().await()
//        val data = documentSnapshot.data
//        var userAndFields = (UserAndFields("",ArrayList<Field>())
//        if (data != null) {
//            userAndFields = UserAndFields(
//                name = data["name"].toString(),
//                listOfFields =
//            )
//            println("List of Fields"+listOfFields)
//        }
//    }
//
//    suspend fun getFields(){
//        val docRefForFields = db.collection("my-device").document("week1")
//            .collection("fields")
//            .get()
//        val fieldIds = docRefForFields.result.documents
//
//
//    }
//
//
//    LaunchedEffect(key1 = true) {
//        try {
//
//            val documentSnapshot2 = docRefForFields.get().await()
//
//            val fieldData = documentSnapshot2.data
//            val listOfFields = arrayListOf(Field(
//                fieldData!!["n"].toString(),
//                fieldData["p"].toString(),
//                fieldData["k"].toString(),
//                "fieldData",
//                "",
//                "",
//                ))
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}