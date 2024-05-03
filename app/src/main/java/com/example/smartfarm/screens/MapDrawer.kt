package com.example.smartfarm.screens

import android.graphics.Point
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput

enum class DrawerMotionEvent { idle, down, up, move }

data class MapDrawState(
    val currentPosition: Offset = Offset.Unspecified,
    val event: DrawerMotionEvent = DrawerMotionEvent.idle
)

@Composable
fun MapDrawer(onDrawingEnd: (List<Point>) -> Unit, selectionEnabled: Boolean) {
    var state by remember { mutableStateOf(MapDrawState()) }
    val brush = remember { SolidColor(Color.Black) }
    val path = remember { Path() }
    val screenPoints = mutableListOf<Point>()
    var firstTime by remember {
        mutableStateOf(true)
    }
    val selectionEnabledd = remember {
        mutableStateOf(selectionEnabled)
    }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitPointerEvent().changes
                            .first()
                            .also { changes ->
                                val position = changes.position
                                screenPoints.add(Point(position.x.toInt(), position.y.toInt()))
                                state = state.copy(
                                    currentPosition = position,
                                    event = DrawerMotionEvent.down
                                )
                            }
                        do {
                            val event: PointerEvent = awaitPointerEvent()
                            event.changes.forEach { changes ->
                                val position = changes.position
                                screenPoints.add(Point(position.x.toInt(), position.y.toInt()))
                                state = state.copy(
                                    currentPosition = position,
                                    event = DrawerMotionEvent.move
                                )
                            }
                        } while (event.changes.any { it.pressed })

                        currentEvent.changes
                            .first()
                            .also { change ->
                                state = state.copy(
                                    currentPosition = change.position,
                                    event = DrawerMotionEvent.up
                                )
                                screenPoints.add(
                                    Point(
                                        change.position.x.toInt(),
                                        change.position.y.toInt()
                                    )
                                )
                            }
                        onDrawingEnd.invoke(screenPoints)
                        firstTime = false
                    }
                },
            onDraw = {
                    when (state.event) {
                        DrawerMotionEvent.idle -> Unit
                        DrawerMotionEvent.up, DrawerMotionEvent.move -> path.lineTo(
                            state.currentPosition.x,
                            state.currentPosition.y
                        )
                        DrawerMotionEvent.down -> {
                            path.moveTo(
                                state.currentPosition.x,
                                state.currentPosition.y
                            )
                        }
                    }
                if(firstTime){
                    drawPath(
                        path = path,
                        brush = brush,
                        style = Stroke(width = 5f)
                    )
                }
            }
        )

}


private fun createPathFromPoints(points: List<Point>): Path {
    val path = Path()
    if (points.isNotEmpty()) {
        path.moveTo(points.first().x.toFloat(), points.first().y.toFloat())
        for (i in 1 until points.size) {
            path.lineTo(points[i].x.toFloat(), points[i].y.toFloat())
        }
    }
    return path
}


