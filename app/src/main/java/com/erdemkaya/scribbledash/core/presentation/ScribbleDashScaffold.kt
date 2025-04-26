package com.erdemkaya.scribbledash.core.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScribbleDashScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold (topBar = topAppBar, modifier = modifier, bottomBar = bottomBar) { paddingValues ->
        content(paddingValues)
    }
}