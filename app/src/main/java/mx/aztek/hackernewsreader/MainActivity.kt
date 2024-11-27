@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)

package mx.aztek.hackernewsreader

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.aztek.hackernewsreader.ui.theme.HackerNewsReaderTheme
import mx.aztek.hackernewsreader.utils.timeAgo
import mx.aztek.hackernewsreader.viewmodel.HackerNewsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackerNewsReaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListDetailLayout(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun ListDetailLayout(
    modifier: Modifier = Modifier,
    viewModel: HackerNewsViewModel = viewModel<HackerNewsViewModel>(),
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    val hits by viewModel.hits.collectAsState()
    val onRefresh: () -> Unit = {
        coroutineScope.launch {
            isRefreshing = true
            viewModel.refreshNews()
            delay(1000L)
            isRefreshing = false
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        indicator = {
            Indicator(
                isRefreshing = isRefreshing,
                state = state,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        },
    ) {
        NavigableListDetailPaneScaffold(
            modifier = modifier,
            navigator = navigator,
            listPane = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier =
                        Modifier
                            .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(
                        items = hits,
                        key = { it.objectID.toString() },
                    ) {
                        SwipeToDeleteContainer(
                            item = it,
                            onDelete = {
                                viewModel.deleteNews(it.objectID.toString())
                            },
                        ) { hit ->
                            ElevatedCard(
                                elevation =
                                    CardDefaults.cardElevation(
                                        defaultElevation = 6.dp,
                                    ),
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navigator.navigateTo(
                                                pane = ListDetailPaneScaffoldRole.Detail,
                                                content = it.storyUrl ?: it.url,
                                            )
                                        },
                            ) {
                                Text(
                                    text = hit.storyTitle ?: hit.title ?: "",
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.background)
                                            .padding(8.dp),
                                )
                                Text(
                                    text = "${ hit.author } - ${ hit.createdAt?.timeAgo() }",
                                    fontWeight = FontWeight.Light,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.background)
                                            .padding(8.dp),
                                )
                            }
                        }
                    }
                }
            },
            detailPane = {
                val url = navigator.currentDestination?.content?.toString() ?: "www.google.com"
                AnimatedPane {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f),
                        ) {
                            TopAppBar(
                                title = { Text(text = "Back") },
                                navigationIcon = {
                                    IconButton(onClick = { navigator.navigateBack() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Back",
                                        )
                                    }
                                },
                            )
                        }
                        Box(
                            modifier =
                                Modifier
                                    .weight(9f)
                                    .padding(16.dp),
                        ) {
                            AndroidView(
                                factory = {
                                    WebView(it).apply {}
                                },
                                update = {
                                    it.webViewClient = WebViewClient()
                                    it.loadUrl(url)
                                },
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit,
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state =
        rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                if (value == SwipeToDismissBoxValue.EndToStart) {
                    isRemoved = true
                    true
                } else {
                    false
                }
            },
        )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit =
            shrinkVertically(
                animationSpec = tween(durationMillis = animationDuration),
                shrinkTowards = Alignment.Top,
            ) + fadeOut(),
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeDismissState = state)
            },
            content = { content(item) },
            enableDismissFromEndToStart = true,
        )
    }
}

@Composable
fun DeleteBackground(swipeDismissState: SwipeToDismissBoxState) {
    val color =
        if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            Color.Red
        } else {
            Color.Transparent
        }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color)
                .padding(16.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            text = "Delete",
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}
