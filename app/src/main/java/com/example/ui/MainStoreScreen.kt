package com.example.ui

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppSection
import com.example.data.model.DownloadStatus
import com.example.data.model.StoreApp
import com.example.data.repository.AppStoreRepository
import com.example.ui.components.IosStatusBar
import com.example.ui.screens.AccountSheet
import com.example.ui.screens.AppDetailSheet
import com.example.ui.screens.AppsScreen
import com.example.ui.screens.ArcadeScreen
import com.example.ui.screens.GamesScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SimulatedAppRunner
import com.example.ui.screens.TodayScreen
import com.example.ui.theme.IosBlue

@Composable
fun MainStoreScreen(
    repository: AppStoreRepository,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedSection by remember { mutableStateOf(AppSection.APPS) } // Default to Apps to match the screenshot!
    var selectedAppForDetail by remember { mutableStateOf<StoreApp?>(null) }
    var selectedAppForSandbox by remember { mutableStateOf<StoreApp?>(null) }
    var showAccountSheet by remember { mutableStateOf(false) }
    var showDynamicIsland by remember { mutableStateOf(true) }

    val installStates by repository.installStates.collectAsState()
    val apps = repository.sampleApps
    val featuredBanners = repository.featuredAppBanners

    // Find active downloading app if any
    val activeDownloadingApp = apps.find {
        installStates[it.id]?.status == DownloadStatus.DOWNLOADING
    }
    val activeDownloadState = activeDownloadingApp?.let { installStates[it.id] }

    // System Back Button Navigation handling
    BackHandler(enabled = selectedAppForSandbox != null || selectedAppForDetail != null || showAccountSheet) {
        when {
            selectedAppForSandbox != null -> selectedAppForSandbox = null
            selectedAppForDetail != null -> selectedAppForDetail = null
            showAccountSheet -> showAccountSheet = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (showDynamicIsland) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.statusBars)
                    ) {
                        IosStatusBar(
                            activeDownloadApp = activeDownloadingApp,
                            downloadState = activeDownloadState
                        )
                    }
                }
            },
            bottomBar = {
                IosBottomNavigationBar(
                    selectedSection = selectedSection,
                    onSectionSelect = { selectedSection = it },
                    isDarkTheme = isDarkTheme
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = selectedSection,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "SectionContentTransition"
                ) { section ->
                    when (section) {
                        AppSection.TODAY -> TodayScreen(
                            apps = apps,
                            installStates = installStates,
                            onAppClick = { selectedAppForDetail = it },
                            onGetClick = { handleGetClick(repository, it, context, onOpenSimulator = { selectedAppForSandbox = it }) },
                            onProfileClick = { showAccountSheet = true },
                            isDarkTheme = isDarkTheme
                        )
                        AppSection.GAMES -> GamesScreen(
                            apps = apps,
                            installStates = installStates,
                            onAppClick = { selectedAppForDetail = it },
                            onGetClick = { handleGetClick(repository, it, context, onOpenSimulator = { selectedAppForSandbox = it }) },
                            onProfileClick = { showAccountSheet = true },
                            isDarkTheme = isDarkTheme
                        )
                        AppSection.APPS -> AppsScreen(
                            apps = apps,
                            featuredBanners = featuredBanners,
                            installStates = installStates,
                            onAppClick = { selectedAppForDetail = it },
                            onGetClick = { handleGetClick(repository, it, context, onOpenSimulator = { selectedAppForSandbox = it }) },
                            onProfileClick = { showAccountSheet = true },
                            isDarkTheme = isDarkTheme
                        )
                        AppSection.ARCADE -> ArcadeScreen(
                            apps = apps,
                            installStates = installStates,
                            onAppClick = { selectedAppForDetail = it },
                            onGetClick = { handleGetClick(repository, it, context, onOpenSimulator = { selectedAppForSandbox = it }) },
                            onProfileClick = { showAccountSheet = true },
                            isDarkTheme = isDarkTheme
                        )
                        AppSection.SEARCH -> SearchScreen(
                            apps = apps,
                            installStates = installStates,
                            onAppClick = { selectedAppForDetail = it },
                            onGetClick = { handleGetClick(repository, it, context, onOpenSimulator = { selectedAppForSandbox = it }) },
                            onProfileClick = { showAccountSheet = true },
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
            }
        }

        // App Detail Modal Sheet
        AnimatedVisibility(
            visible = selectedAppForDetail != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            selectedAppForDetail?.let { app ->
                val installState = installStates[app.id] ?: com.example.data.model.AppInstallState()
                AppDetailSheet(
                    app = app,
                    installState = installState,
                    onDismiss = { selectedAppForDetail = null },
                    onGetClick = {
                        handleGetClick(repository, app, context, onOpenSimulator = { selectedAppForSandbox = it })
                    },
                    onLaunchRealApp = { ctx, storeApp ->
                        repository.launchRealAppOrPlayStore(ctx, storeApp)
                    },
                    onLaunchSandbox = {
                        selectedAppForSandbox = it
                    },
                    isDarkTheme = isDarkTheme
                )
            }
        }

        // Simulated App Sandbox Modal (when opening an installed game/app)
        AnimatedVisibility(
            visible = selectedAppForSandbox != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            selectedAppForSandbox?.let { app ->
                SimulatedAppRunner(
                    app = app,
                    onClose = { selectedAppForSandbox = null }
                )
            }
        }

        // Account / Apple ID Modal
        AnimatedVisibility(
            visible = showAccountSheet,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            AccountSheet(
                apps = apps,
                installStates = installStates,
                isDarkTheme = isDarkTheme,
                onToggleDarkTheme = onToggleDarkTheme,
                showDynamicIsland = showDynamicIsland,
                onToggleDynamicIsland = { showDynamicIsland = it },
                onUpdateAll = { repository.updateAllApps() },
                onAppClick = {
                    showAccountSheet = false
                    selectedAppForDetail = it
                },
                onGetClick = { handleGetClick(repository, it, context, onOpenSimulator = { selectedAppForSandbox = it }) },
                onDismiss = { showAccountSheet = false }
            )
        }
    }
}

private fun handleGetClick(
    repository: AppStoreRepository,
    app: StoreApp,
    context: Context,
    onOpenSimulator: (StoreApp) -> Unit
) {
    val currentState = repository.installStates.value[app.id]?.status ?: DownloadStatus.GET
    when (currentState) {
        DownloadStatus.GET -> {
            repository.startDownload(app.id)
        }
        DownloadStatus.DOWNLOADING -> {
            // Already downloading
        }
        DownloadStatus.INSTALLED -> {
            // Launch real app if present, or open simulator
            val launched = repository.isAppInstalledOnDevice(context, app.packageName)
            if (launched) {
                repository.launchRealAppOrPlayStore(context, app)
            } else {
                onOpenSimulator(app)
            }
        }
    }
}

// Authentic iOS 5-Tab Bottom Navigation Bar matching the uploaded image
@Composable
private fun IosBottomNavigationBar(
    selectedSection: AppSection,
    onSectionSelect: (AppSection) -> Unit,
    isDarkTheme: Boolean
) {
    val barBg = if (isDarkTheme) Color(0xFF161618).copy(alpha = 0.94f) else Color(0xFFF9F9F9).copy(alpha = 0.94f)
    val inactiveTint = Color(0xFF8E8E93)
    val activeTint = IosBlue

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(barBg)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = if (isDarkTheme) Color(0xFF38383A) else Color(0xFFD1D1D6)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IosTabItem(
                title = "Today",
                icon = Icons.Default.Article,
                isSelected = selectedSection == AppSection.TODAY,
                activeTint = activeTint,
                inactiveTint = inactiveTint,
                onClick = { onSectionSelect(AppSection.TODAY) }
            )

            IosTabItem(
                title = "Games",
                icon = Icons.Default.RocketLaunch,
                isSelected = selectedSection == AppSection.GAMES,
                activeTint = activeTint,
                inactiveTint = inactiveTint,
                onClick = { onSectionSelect(AppSection.GAMES) }
            )

            IosTabItem(
                title = "Apps",
                icon = Icons.Default.Layers,
                isSelected = selectedSection == AppSection.APPS,
                activeTint = activeTint,
                inactiveTint = inactiveTint,
                onClick = { onSectionSelect(AppSection.APPS) }
            )

            IosTabItem(
                title = "Arcade",
                icon = Icons.Default.SportsEsports,
                isSelected = selectedSection == AppSection.ARCADE,
                activeTint = activeTint,
                inactiveTint = inactiveTint,
                onClick = { onSectionSelect(AppSection.ARCADE) }
            )

            IosTabItem(
                title = "Search",
                icon = Icons.Default.Search,
                isSelected = selectedSection == AppSection.SEARCH,
                activeTint = activeTint,
                inactiveTint = inactiveTint,
                onClick = { onSectionSelect(AppSection.SEARCH) }
            )
        }

        // iOS Home Indicator Gesture Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp, top = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(134.dp)
                    .height(4.5.dp)
                    .clip(CircleShape)
                    .background(
                        if (isDarkTheme) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.7f)
                    )
            )
        }
    }
}

@Composable
private fun IosTabItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    activeTint: Color,
    inactiveTint: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .testTag("tab_${title.lowercase()}")
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) activeTint else inactiveTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) activeTint else inactiveTint
        )
    }
}
