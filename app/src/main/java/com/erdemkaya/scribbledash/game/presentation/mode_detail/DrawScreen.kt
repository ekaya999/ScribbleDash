package com.erdemkaya.scribbledash.game.presentation.mode_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.ui.theme.Success
import com.erdemkaya.scribbledash.ui.theme.onSurfaceVariant
import kotlin.math.abs

@Composable
fun DrawScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    paths: List<PathData>,
    currentPath: PathData?,
    undoPaths: List<PathData>,
    redoPaths: List<PathData>,
    onAction: (DrawingAction) -> Unit,
) {
    val canUndo = undoPaths.isNotEmpty()
    val canRedo = redoPaths.isNotEmpty()
    val canClear = paths.isNotEmpty() || currentPath != null

    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "", modifier = modifier, showIcon = true, onClickBack = {
                navHostController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            })
    }, bottomBar = {}, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Time to draw!",
                style = MaterialTheme.typography.displayMedium,
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .shadow(
                        elevation = 4.dp, shape = RoundedCornerShape(10.dp)
                    )
                    .background(Color.White)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    onAction(DrawingAction.OnNewPathStart)
                                },
                                onDragEnd = {
                                    onAction(DrawingAction.OnPathEnd)
                                },
                                onDrag = { change, _ ->
                                    onAction(DrawingAction.OnDraw(change.position))
                                },
                                onDragCancel = {
                                    onAction(DrawingAction.OnPathEnd)
                                }
                            )
                        }) {
                    val thirdWidth = size.width / 3
                    val thirdHeight = size.height / 3

                    drawLine(
                        color = onSurfaceVariant,
                        start = Offset(thirdWidth, 0f),
                        end = Offset(thirdWidth, size.height),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = onSurfaceVariant,
                        start = Offset(2 * thirdWidth, 0f),
                        end = Offset(2 * thirdWidth, size.height),
                        strokeWidth = 2f
                    )

                    drawLine(
                        color = onSurfaceVariant,
                        start = Offset(0f, thirdHeight),
                        end = Offset(size.width, thirdHeight),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = onSurfaceVariant,
                        start = Offset(0f, 2 * thirdHeight),
                        end = Offset(size.width, 2 * thirdHeight),
                        strokeWidth = 2f
                    )

                    paths.fastForEach { pathData ->
                        drawPath(
                            path = pathData.path, color = Color.Black
                        )
                    }
                    currentPath?.let {
                        drawPath(
                            path = it.path, color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onAction(DrawingAction.OnUndoClick) },
                    enabled = canUndo,
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.reply),
                        contentDescription = "Undo",
                        tint = if (canUndo) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
                    )
                }
                Button(
                    onClick = { onAction(DrawingAction.OnRedoClick) },
                    enabled = canRedo,
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.forward),
                        contentDescription = "Redo",
                        tint = if (canRedo) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
                    )
                }
                Button(
                    onClick = { onAction(DrawingAction.OnClearCanvasClick) },
                    enabled = canClear,
                    modifier = Modifier
                        .weight(2f)
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Success,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                    )
                ) {
                    Text(
                        "Clear Canvas".uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (canClear) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(
                            alpha = .8f
                        )
                    )
                }
            }
        }
    })
}

private fun DrawScope.drawPath(
    path: List<Offset>, color: Color, thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 5
            for (i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f, y1 = (from.y + to.y) / 2f, x2 = to.x, y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath, color = color, style = Stroke(
            width = thickness, cap = StrokeCap.Round, join = StrokeJoin.Round
        )
    )
}