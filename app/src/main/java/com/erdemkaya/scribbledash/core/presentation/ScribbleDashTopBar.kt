package com.erdemkaya.scribbledash.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScribbleDashTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showIcon: Boolean = false,
    onClickBack: () -> Unit = {},
    homeScreen: Boolean = false
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    TopAppBar(
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = title,
                    style = if (homeScreen) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.labelLarge.copy(
                        fontSize = 24.sp,
                        lineHeight = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            if (showIcon) {
                IconButton(
                    onClick = {
                        onClickBack()
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.close_circle),
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        })
}


@Preview
@Composable
fun MyTopAppBarPreview() {
    ScribbleDashTheme {
        ScribbleDashTopBar(title = "ScribbleDash", showIcon = true)
    }
}