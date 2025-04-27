package com.erdemkaya.scribbledash.game.presentation

import android.graphics.RectF
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
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
import com.erdemkaya.scribbledash.game.presentation.components.PathData
import com.erdemkaya.scribbledash.game.presentation.components.PathModel
import com.erdemkaya.scribbledash.game.presentation.components.calculatePathLength
import com.erdemkaya.scribbledash.game.presentation.components.calculateScore
import com.erdemkaya.scribbledash.game.presentation.components.createBitmapFromPath
import com.erdemkaya.scribbledash.game.presentation.components.normalizePathForComparison
import com.erdemkaya.scribbledash.game.presentation.components.normalizePathToCanvas
import com.erdemkaya.scribbledash.ui.theme.Success
import com.erdemkaya.scribbledash.ui.theme.onSurfaceVariant
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs
import android.graphics.Path as AndroidPath
import androidx.compose.ui.graphics.Path as ComposePath

@Composable
fun DrawScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    paths: List<PathData>,
    drawings: List<PathModel>,
    currentPath: PathData?,
    undoPaths: List<PathData>,
    redoPaths: List<PathData>,
    onAction: (DrawingAction) -> Unit,
    drawViewModel: DrawViewModel = koinViewModel()
    ) {
    val canUndo = undoPaths.isNotEmpty()
    val canRedo = redoPaths.isNotEmpty()
    val canClear = paths.isNotEmpty() || currentPath != null

    var drawMode by remember {
        mutableStateOf(false)
    }

    var countdown by remember {
        mutableIntStateOf(3)
    }

    LaunchedEffect(key1 = drawMode) {
        if (!drawMode) {
            while (countdown > 0) {
                delay(1000L)
                countdown--
            }
            drawMode = true
        }
    }


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
                val randomDrawing = remember(drawings) {
                    drawings.randomOrNull()
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (drawMode) Modifier.pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { onAction(DrawingAction.OnNewPathStart) },
                                    onDragEnd = { onAction(DrawingAction.OnPathEnd) },
                                    onDrag = { change, _ -> onAction(DrawingAction.OnDraw(change.position)) },
                                    onDragCancel = { onAction(DrawingAction.OnPathEnd) }
                                )
                            } else Modifier
                        )
                ) {
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

                    if (drawMode) {
                        paths.fastForEach { pathData ->
                            drawPath(
                                path = pathData.path,
                                color = Color.Black
                            )
                        }
                        currentPath?.let {
                            drawPath(
                                path = it.path,
                                color = Color.Black
                            )
                        }
                    } else {
                        randomDrawing?.let { drawing ->
                            val normalizedPath = normalizePathToCanvas(
                                drawing.path,
                                drawing.bounds,
                                size
                            )
                            drawPath(
                                path = normalizedPath.asComposePath(),
                                color = Color.Black,
                                style = Stroke(width = 10f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (drawMode) {
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
                        onClick = {
                            val userPath = AndroidPath().apply {
                                paths.forEach { pathData ->
                                    addPath(pathData.path.toAndroidPath())
                                }
                            }

                            val userBounds = RectF()
                            userPath.computeBounds(userBounds, true)

                            val examplePath = drawings.randomOrNull()?.path ?: return@Button
                            val exampleBounds = RectF()
                            examplePath.computeBounds(exampleBounds, true)

                            val targetSize = 1024f

                            val normalizedUserPath = normalizePathForComparison(
                                userPath, userBounds,
                                userStrokeWidth = 3f,
                                exampleStrokeWidth = 3f * 15f,
                                isUser = true,
                                targetSize = targetSize
                            )

                            val normalizedExamplePath = normalizePathForComparison(
                                examplePath, exampleBounds,
                                userStrokeWidth = 3f,
                                exampleStrokeWidth = 3f * 15f,
                                isUser = false,
                                targetSize = targetSize
                            )
                            val userBitmap = createBitmapFromPath(normalizedUserPath, 3f, 1024)
                            val exampleBitmap =
                                createBitmapFromPath(normalizedExamplePath, 3f * 15f, 1024)
                            val coveragePercentage =
                                calculateScore(userBitmap, exampleBitmap) * 100f
                            val userPathLength = calculatePathLength(userPath)
                            val examplePathLength = calculatePathLength(examplePath)

                            val lengthRatio = userPathLength / examplePathLength
                            val missingLengthPenalty = if (lengthRatio < 0.7f) {
                                100f - (lengthRatio * 100f)
                            } else {
                                0f
                            }
                            var finalScore = coveragePercentage - missingLengthPenalty
                            finalScore = finalScore.coerceIn(0f, 100f)

                            drawViewModel.setResultPaths(
                                example = normalizedExamplePath,
                                user = normalizedUserPath
                            )
                            navHostController.navigate("result/${finalScore.toInt()}")
                        },
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
                            "Done".uppercase(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = if (canClear) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = .8f
                            )
                        )
                    }
                }
            } else {
                Text(
                    text = "$countdown seconds left",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(32.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    })
}

private fun DrawScope.drawPath(
    path: List<Offset>, color: Color, thickness: Float = 10f
) {
    val smoothedPath = ComposePath().apply {
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

fun List<Offset>.toAndroidPath(): AndroidPath {
    val androidPath = AndroidPath()

    if (this.isNotEmpty()) {
        androidPath.moveTo(this.first().x, this.first().y)

        for (i in 1 until this.size) {
            val current = this[i]
            androidPath.lineTo(current.x, current.y)
        }
    }

    return androidPath
}