package com.example.smartfarm.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartfarm.Global
import com.example.smartfarm.R
import com.example.smartfarm.SelectedField
import com.example.smartfarm.presentation.MapStyle
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapFactory
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun NotificationScreen(){
    val points1 = "[lat/lng: (19.044190375156873,72.82060414552689), lat/lng: (19.04419734751825,72.8205843642354), lat/lng: (19.044215729196853,72.82053977251053), lat/lng: (19.044234427798838,72.82049484550953), lat/lng: (19.04425629565273,72.82044656574726), lat/lng: (19.04427499425015,72.82040365040302), lat/lng: (19.044293692845468,72.82036609947681), lat/lng: (19.044312074513385,72.82033391296864), lat/lng: (19.0443342592823,72.82030172646046), lat/lng: (19.044359613300287,72.82027490437031), lat/lng: (19.044383065763466,72.82025177031755), lat/lng: (19.04440683514845,72.82023198902607), lat/lng: (19.04443060453002,72.82021589577198), lat/lng: (19.04446261395851,72.82019812613726), lat/lng: (19.044499694179827,72.8201873973012), lat/lng: (19.04453011897063,72.82018203288317), lat/lng: (19.044540260566304,72.8201837092638), lat/lng: (19.044562445304724,72.8201873973012), lat/lng: (19.044586214664022,72.82019443809986), lat/lng: (19.044608082471566,72.82020516693592), lat/lng: (19.04462995027624,72.82022293657064), lat/lng: (19.044647064208302,72.82024271786213), lat/lng: (19.044660375043147,72.82026048749685), lat/lng: (19.044677488972095,72.820287309587), lat/lng: (19.0446942859747,72.82031245529652), lat/lng: (19.044718055315116,72.82033558934927), lat/lng: (19.04474150772766,72.82035704702139), lat/lng: (19.044768763229957,72.82037682831287), lat/lng: (19.044790631013456,72.82038923352957), lat/lng: (19.044817569583362,72.82040365040302), lat/lng: (19.044837852739,72.82041605561972), lat/lng: (19.04485496664964,72.82043047249317), lat/lng: (19.044863206680056,72.82043751329184), lat/lng: (19.044871763634294,72.82044656574726), lat/lng: (19.044881905209106,72.82046634703875), lat/lng: (19.044893631404207,72.82049115747213), lat/lng: (19.044902188356883,72.82052535563707), lat/lng: (19.04490725914343,72.82055754214525), lat/lng: (19.044912329929833,72.82058604061604), lat/lng: (19.044917400716088,72.82061655074358), lat/lng: (19.044920886881542,72.82064504921436), lat/lng: (19.044924056122785,72.82067723572254), lat/lng: (19.044925957667516,72.82070942223072), lat/lng: (19.044925957667516,72.82073825597763), lat/lng: (19.044924056122785,72.82075971364975), lat/lng: (19.044917400716088,72.8207952529192), lat/lng: (19.04490725914343,72.82082408666611), lat/lng: (19.044895532949294,72.82084722071886), lat/lng: (19.044881905209106,72.82086867839098), lat/lng: (19.04486003743764,72.82089181244373), lat/lng: (19.044836268117553,72.82091327011585), lat/lng: (19.044809329550674,72.82093305140734), lat/lng: (19.044785560223335,72.82094914466143), lat/lng: (19.044768763229957,72.82096523791552), lat/lng: (19.044751649310435,72.82098837196827), lat/lng: (19.044736436936038,72.82100982964039), lat/lng: (19.04472471072983,72.82102424651384), lat/lng: (19.044714569145402,72.8210349753499), lat/lng: (19.044687630558776,72.8210473805666), lat/lng: (19.044647064208302,72.82105810940266), lat/lng: (19.044603011675875,72.82106179744005), lat/lng: (19.044562445304724,72.82106347382069), lat/lng: (19.04453518976854,72.82106179744005), lat/lng: (19.04450318035406,72.82105643302202), lat/lng: (19.04447782635801,72.82104570418596), lat/lng: (19.04444898618279,72.82102793455124), lat/lng: (19.04441539212621,72.82100278884172), lat/lng: (19.044374825709212,72.82097060233355), lat/lng: (19.044339330086203,72.8209437802434), lat/lng: (19.044312074513385,72.82091695815325), lat/lng: (19.044295277472084,72.8208938241005), lat/lng: (19.044285135861422,72.82087404280901), lat/lng: (19.04428006505587,72.82086163759232), lat/lng: (19.04428006505587,72.82086163759232), lat/lng: (19.04428006505587,72.82085627317429), lat/lng: (19.04427499425015,72.82084554433823), lat/lng: (19.04426833881741,72.82082945108414), lat/lng: (19.044259781832064,72.82081134617329), lat/lng: (19.04425122484629,72.82079190015793), lat/lng: (19.04423791397863,72.82076507806778), lat/lng: (19.04422587081175,72.82073456794024), lat/lng: (19.044212559942057,72.82070405781269), lat/lng: (19.044198932145804,72.82066851854324)]"
    val points2 = "[lat/lng: (19.044184353571822,72.82018136233091), lat/lng: (19.04418276894414,72.82019209116697), lat/lng: (19.044177698135456,72.82022092491388), lat/lng: (19.044172627326606,72.82024573534727), lat/lng: (19.044170725773252,72.82027456909418), lat/lng: (19.044167556517607,72.82029937952757), lat/lng: (19.04416565496419,72.82032452523708), lat/lng: (19.044164070336326,72.82035134732723), lat/lng: (19.04415899952706,72.82037984579802), lat/lng: (19.04415551334561,72.82040130347013), lat/lng: (19.04414727328006,72.82042812556028), lat/lng: (19.044138716288483,72.8204495832324), lat/lng: (19.044128574668242,72.82046768814325), lat/lng: (19.044121919229635,72.82048009335995), lat/lng: (19.04411177760839,72.82049618661404), lat/lng: (19.044103220614964,72.82051060348749), lat/lng: (19.04409814980385,72.82052468508482), lat/lng: (19.044093078992574,72.82053541392088), lat/lng: (19.044091494364025,72.8205444663763), lat/lng: (19.044086423552542,72.82055720686913), lat/lng: (19.044084521998197,72.82057128846645), lat/lng: (19.044082937369573,72.82058570533991), lat/lng: (19.04408135274091,72.820601798594), lat/lng: (19.044079451186512,72.82062325626612), lat/lng: (19.04407786655782,72.82064840197563), lat/lng: (19.044076281929122,72.82067522406578), lat/lng: (19.04407438037466,72.82069467008114), lat/lng: (19.044072795745933,72.8207053989172), lat/lng: (19.044072795745933,72.82071813941002), lat/lng: (19.044072795745933,72.82073222100735), lat/lng: (19.044072795745933,72.8207466378808), lat/lng: (19.044072795745933,72.82076105475426), lat/lng: (19.044072795745933,72.82077178359032), lat/lng: (19.044072795745933,72.82078251242638), lat/lng: (19.044072795745933,72.82079122960567), lat/lng: (19.044072795745933,72.8207965940237), lat/lng: (19.04407121111718,72.82080195844173), lat/lng: (19.04407121111718,72.82080933451653), lat/lng: (19.044067724933893,72.82082006335258), lat/lng: (19.044066140305077,72.82083079218864), lat/lng: (19.04406265412167,72.8208415210247), lat/lng: (19.04405916793819,72.82085224986076), lat/lng: (19.04405599868042,72.82086297869682), lat/lng: (19.04405092786787,72.82087538391352), lat/lng: (19.044049026313115,72.82088611274958), lat/lng: (19.04404585705514,72.820895165205), lat/lng: (19.044043955500335,72.82090052962303), lat/lng: (19.044042370871317,72.82090589404106), lat/lng: (19.04404078624227,72.82090924680233), lat/lng: (19.04404078624227,72.82091125845909), lat/lng: (19.044038884687403,72.82091125845909), lat/lng: (19.04402367224769,72.82091461122036), lat/lng: (19.04399166273455,72.82091997563839), lat/lng: (19.04395965321524,72.82092366367579), lat/lng: (19.0439393699523,72.82092366367579), lat/lng: (19.04390038804935,72.82091997563839), lat/lng: (19.043853166057097,72.82091662287712), lat/lng: (19.04382083958485,72.82091461122036), lat/lng: (19.043793900853167,72.82091293483973), lat/lng: (19.04378375921187,72.82091293483973), lat/lng: (19.04376189129573,72.82091293483973), lat/lng: (19.043734635628095,72.82091125845909), lat/lng: (19.04371625389622,72.82090924680233), lat/lng: (19.04370611225018,72.8209075704217), lat/lng: (19.043695970603526,72.8209075704217), lat/lng: (19.043683927397325,72.82090589404106), lat/lng: (19.04367061648417,72.82090589404106), lat/lng: (19.043660474835345,72.82090589404106), lat/lng: (19.043650333185905,72.8209038823843), lat/lng: (19.04363670534348,72.82090220600367), lat/lng: (19.04362814832558,72.82090220600367), lat/lng: (19.04362149286691,72.82090052962303), lat/lng: (19.043616422041065,72.82089851796627), lat/lng: (19.04361135121507,72.82089684158564), lat/lng: (19.04360786502211,72.820895165205), lat/lng: (19.043609766581927,72.820895165205), lat/lng: (19.043604695755725,72.82088980078697), lat/lng: (19.04359772336945,72.82087538391352), lat/lng: (19.043589483276204,72.82084487378597), lat/lng: (19.043580926255874,72.8208126872778), lat/lng: (19.043570784601574,72.82078050076962), lat/lng: (19.04356064294664,72.82073590904474), lat/lng: (19.043548916657354,72.8206966817379), lat/lng: (19.04353877500111,72.82065)]"
    var fieldName by remember {
        mutableStateOf("")
    }
//    val points2 =
    val losAngeles = LatLng(Global.latitude!!, Global.longitude!!)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(losAngeles, 18f)
    }
    var selectField by remember {
        mutableStateOf(false)
    }
    var geoPoints by remember { mutableStateOf(listOf<LatLng>()) }
    Box(modifier = Modifier.fillMaxSize()){
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapStyleOptions = MapStyleOptions(MapStyle.json)
            ),
            uiSettings = MapUiSettings(
                compassEnabled = true,
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true
            )
        ){
            var selectedPolygon2StrokeWidth by remember {
                mutableStateOf(1.5f)
            }
            var selectedPolygon1StrokeWidth by remember {
                mutableStateOf(1.5f)
            }
            Polygon(
                points = parseLatLngFromString(points1),
                fillColor = Color.Red.copy(alpha = 0.3f),
                strokeColor = Color.Black,
                strokeWidth = selectedPolygon2StrokeWidth,
                clickable = true,
                onClick = {
                    selectedPolygon2StrokeWidth = 5f
                    selectedPolygon1StrokeWidth = 1.5f
                    selectField = true
                    fieldName = "Tomato Field"
                }
            )
            Polygon(
                points = parseLatLngFromString(points2),
                fillColor = Color.Yellow.copy(alpha = 0.3f),
                strokeColor = Color.Black,
                strokeWidth = selectedPolygon1StrokeWidth,
                clickable = true,
                onClick = {
                    selectedPolygon2StrokeWidth = 1.5f
                    selectedPolygon1StrokeWidth = 5f
                    selectField = true
                    fieldName = "Broccoli Field"
                }
            )

        }
        AnimatedVisibility(
            visible = selectField,
            enter = slideInVertically()+ fadeIn(),
            exit = slideOutVertically()+ fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .padding(start = 16.dp, end = 16.dp, bottom = 100.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ){
                    Image(
                        painter = if(fieldName == "Tomato Field"){painterResource(id = R.drawable.tomato_crop)}else{painterResource(id = R.drawable.broccoli)},
                        contentDescription = "",
                        modifier = Modifier
                            .size(width = 100.dp, height = 80.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Column {
                        Text(
                            text = fieldName,
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Last Updated at 9:45 am",
                            color = Color.Gray,
                            fontSize = 12.sp,
                        )
                    }
                    val context = LocalContext.current
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowRight,
                        contentDescription = "Right Arrow",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(context, SelectedField::class.java)
                                context.startActivity(intent)
                            }
                    )
                }
            }
        }
    }

}

fun parseLatLngFromString(pointsString: String): ArrayList<LatLng> {
    val pointStrings = pointsString
        .replace("[lat/lng: ", "")
        .replace("(","")
        .replace(")", "")
        .replace("]","")
        .split(", lat/lng: ")

    val latLngList: ArrayList<LatLng> = ArrayList()
    for (pointString in pointStrings) {
        val (lat, lng) = pointString.split(",") // Split each point string by comma
        val latLng = LatLng(lat.toDouble(), lng.toDouble())
        latLngList.add(latLng)
    }
    return latLngList
}