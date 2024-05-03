package com.example.smartfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarm.ui.theme.SmartFarmTheme

//import com.example.smartfarm.ui.theme.SmartFarmTheme
//import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
//import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
//import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
//import com.patrykandpatrick.vico.compose.component.rememberLineComponent
//import com.patrykandpatrick.vico.compose.component.shape.roundedCornerShape
//import com.patrykandpatrick.vico.core.Defaults
//import com.patrykandpatrick.vico.core.chart.layer.ColumnCartesianLayer
//import com.patrykandpatrick.vico.core.component.shape.Shapes
//import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
//import com.patrykandpatrick.vico.core.model.columnSeries
//import com.patrykandpatrick.vico.core.model.lineSeries
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.isActive
//import kotlinx.coroutines.withContext
//import kotlin.random.Random
//
//
class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}
//
//@Composable
//internal fun Chart4(
//    modifier: Modifier,
//) {
//    val modelProducer = remember { CartesianChartModelProducer.build() }
//    LaunchedEffect(Unit) {
//        withContext(Dispatchers.Default) {
//            while (isActive) {
//                modelProducer.tryRunTransaction {
//                    columnSeries {
//                        repeat(3) {
//                            series(
//                                List(Defaults.ColUMN) {
//                                    Defaults.COLUMN_LAYER_MIN_Y +
//                                            Random.nextFloat() * Defaults.COLUMN_LAYER_RELATIVE_MAX_Y
//                                },
//                            )
//                        }
//                    }
//                    lineSeries { series(List(Defaults.ENTRY_COUNT) { Random.nextFloat() * Defaults.MAX_Y }) }
//                }
//                delay(Defaults.TRANSACTION_INTERVAL_MS)
//            }
//        }
//    }
//}
//
//@Composable
//private fun ComposeChart4(
//    modelProducer: CartesianChartModelProducer,
//    modifier: Modifier,
//) {
//    CartesianChartHost(
//        chart =
//        rememberCartesianChart(
//            rememberColumnCartesianLayer(
//                columnProvider =
//                ColumnCartesianLayer.ColumnProvider.series(
//                    columnColors.map { colorz ->
//                        rememberLineComponent(
//                            color = colorz,
//                            thickness = 8.dp,
//                            shape = Shapes.roundedCornerShape(2.dp),
//                        )
//                    },
//                ),
//            ),
//            rememberLineCartesianLayer(
//                lines =
//                listOf(
//                    rememberLineSpec(
//                        shader = DynamicShaders.color(lineColor),
//                        pointConnector = DefaultPointConnector(cubicStrength = 0f),
//                    ),
//                ),
//            ),
//            topAxis = rememberTopAxis(),
//            endAxis = rememberEndAxis(),
//        ),
//        modelProducer = modelProducer,
//        modifier = modifier,
//        marker = rememberMarker(),
//        runInitialAnimation = false,
//        zoomState = rememberVicoZoomState(zoomEnabled = false),
//    )
//}
//
//@Composable
//private fun ViewChart4(
//    modelProducer: CartesianChartModelProducer,
//    modifier: Modifier,
//) {
//    val marker = rememberMarker()
//    AndroidViewBinding(Chart4Binding::inflate, modifier) {
//        with(chartView) {
//            runInitialAnimation = false
//            this.modelProducer = modelProducer
//            this.marker = marker
//        }
//    }
//}
//
//private val columnColors = listOf(Color(0xff916cda), Color(0xffd877d8), Color(0xfff094bb))
//private val lineColor = Color(0xfffdc8c4)