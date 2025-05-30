package com.erdemkaya.scribbledash.game.presentation

import android.graphics.RectF
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.game.presentation.components.enums.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.enums.GameMode
import com.erdemkaya.scribbledash.game.presentation.components.utils.CanvasColor
import com.erdemkaya.scribbledash.game.presentation.components.utils.PenColor
import com.erdemkaya.scribbledash.game.presentation.components.utils.comparePaths
import com.erdemkaya.scribbledash.game.presentation.components.utils.countdownTimer
import com.erdemkaya.scribbledash.game.presentation.components.utils.getCanvasColorByName
import com.erdemkaya.scribbledash.game.presentation.components.utils.getImageIdByName
import com.erdemkaya.scribbledash.game.presentation.components.utils.getPenColorByName
import com.erdemkaya.scribbledash.game.presentation.components.utils.normalizePathToCanvas
import com.erdemkaya.scribbledash.game.presentation.models.PathData
import com.erdemkaya.scribbledash.game.presentation.models.PathModel
import com.erdemkaya.scribbledash.ui.theme.Success
import com.erdemkaya.scribbledash.ui.theme.onSurfaceVariant
import kotlinx.coroutines.delay
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
    difficulty: Difficulty,
    successfulDrawCount: Int,
    gameMode: GameMode,
    onAction: (DrawingAction) -> Unit,
    speedDrawCount: Int,
    activePen: String,
    activeCanvas: String
) {
    val canUndo = undoPaths.isNotEmpty()
    val canRedo = redoPaths.isNotEmpty()
    val canSubmit = paths.isNotEmpty() || currentPath != null
    val context = LocalContext.current

    var drawMode by remember {
        mutableStateOf(false)
    }

    var countdown by remember {
        mutableIntStateOf(3)
    }

    var startCountDown by remember {
        mutableStateOf(false)
    }

    val countdownText by countdownTimer(
        totalSeconds = 120, start = startCountDown, drawMode = drawMode
    )

    val shownDrawings = remember {
        mutableStateListOf<PathModel>()
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

    var randomDrawing by remember {
        mutableStateOf<PathModel?>(null)
    }

    LaunchedEffect(drawMode) {
        if (!drawMode) {
            val remaining = drawings.filterNot { shownDrawings.contains(it) }
            if (remaining.isEmpty()) {
                shownDrawings.clear()
            } else {
                val next = remaining.random()
                shownDrawings += next
                randomDrawing = next
                onAction(DrawingAction.OnExampleSet(next))
            }
        }
    }

    LaunchedEffect(countdownText) {
        if (gameMode == GameMode.SPEED && countdownText < 1) {
            onAction(DrawingAction.OnSpeedGameEnded)
            navHostController.navigate("result")
        }
    }

    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "",
            modifier = modifier,
            showIcon = true,
            onClickBack = {
                onAction(DrawingAction.OnClearStatisticsClick)
                onAction(DrawingAction.OnClearCanvasClick)
                navHostController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            gameMode = gameMode,
            countdownTime = countdownText,
            showTitle = true,
            drawCount = successfulDrawCount
        )
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
            val canvasColor = getCanvasColorByName(activeCanvas)
            var canvasSpecial = false
            var backgroundBox = Color.White
            when (canvasColor) {
                is CanvasColor.Solid -> {
                    backgroundBox = canvasColor.color
                    canvasSpecial = false
                }

                else -> {
                    backgroundBox = Color.White
                    canvasSpecial = true
                }
            }
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .shadow(
                        elevation = 4.dp, shape = RoundedCornerShape(10.dp)
                    )
                    .background(backgroundBox)
            ) {
                if (canvasSpecial) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = ImageBitmap.imageResource(getImageIdByName(activeCanvas)!!),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (drawMode) Modifier.pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    startCountDown = true
                                    onAction(DrawingAction.OnNewPathStart)
                                },
                                onDragEnd = { onAction(DrawingAction.OnPathEnd) },
                                onDrag = { change, _ -> onAction(DrawingAction.OnDraw(change.position)) },
                                onDragCancel = { onAction(DrawingAction.OnPathEnd) })
                        } else Modifier)) {
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
                    val penColor = getPenColorByName(activePen)
                    val tintColor = when (penColor) {
                        is PenColor.Solid -> penColor.color
                        else -> Color.Black
                    }
                    if (drawMode) {
                        paths.fastForEach { pathData ->
                            drawPath(
                                path = pathData.path, color = tintColor
                            )
                        }
                        currentPath?.let {
                            drawPath(
                                path = it.path, color = tintColor
                            )
                        }
                    } else {
                        randomDrawing?.let { drawing ->
                            val normalizedPath = normalizePathToCanvas(
                                drawing.path, drawing.bounds, size
                            )
                            drawPath(
                                path = normalizedPath.asComposePath(),
                                color = tintColor,
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
                        .height(IntrinsicSize.Min)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onAction(DrawingAction.OnUndoClick) },
                        enabled = canUndo,
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        shape = RoundedCornerShape(16.dp)
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
                            .size(64.dp)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.forward),
                            contentDescription = "Redo",
                            tint = if (canRedo) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            val userPath = AndroidPath().apply {
                                paths.forEach { pathData ->
                                    addPath(pathData.path.toAndroidPath())
                                }
                            }

                            val userBounds = RectF()
                            userPath.computeBounds(userBounds, true)

                            val examplePath = randomDrawing?.path ?: return@Button
                            val exampleBounds = RectF()
                            examplePath.computeBounds(exampleBounds, true)

                            val difficultyMultiplier = when (difficulty) {
                                Difficulty.Beginner -> 15f
                                Difficulty.Challenging -> 7f
                                Difficulty.Master -> 4f
                            }

                            val finalScore = comparePaths(
                                context = context,
                                userPath = userPath,
                                examplePath = examplePath,
                                exampleStrokeMultiplier = difficultyMultiplier
                            )
                            onAction(
                                DrawingAction.OnDoneClick(
                                    example = PathModel(examplePath, exampleBounds),
                                    user = PathModel(userPath, userBounds),
                                    score = finalScore.toInt(),
                                    successfulDrawCount = if ((gameMode == GameMode.SPEED && finalScore.toInt() >= 40) || (gameMode == GameMode.ENDLESS && finalScore.toInt() >= 70)) successfulDrawCount + 1 else successfulDrawCount,
                                    speedDrawCount = speedDrawCount + 1
                                )
                            )
                            //for testing
                            //onAction(DrawingAction.OnClearDataStore)

                            if (gameMode == GameMode.SPEED) {
                                drawMode = false
                                onAction(DrawingAction.OnClearCanvasClick)
                                countdown = 3
                            } else {
                                navHostController.navigate("result")
                            }
                        },
                        enabled = canSubmit,
                        modifier = Modifier.fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Success,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(5.dp, Color.White)
                    ) {
                        Text(
                            "Done".uppercase(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = if (canSubmit) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(
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