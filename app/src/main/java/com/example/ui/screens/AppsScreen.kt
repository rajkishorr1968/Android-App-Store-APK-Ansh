package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppCategory
import com.example.data.model.AppInstallState
import com.example.data.model.FeaturedBanner
import com.example.data.model.StoreApp
import com.example.ui.components.AppRowItem
import com.example.ui.components.FeaturedAppsCarouselCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.IosBlue

@Composable
fun AppsScreen(
    apps: List<StoreApp>,
    featuredBanners: List<FeaturedBanner>,
    installStates: Map<String, AppInstallState>,
    onAppClick: (StoreApp) -> Unit,
    onGetClick: (StoreApp) -> Unit,
    onProfileClick: () -> Unit,
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier
) {
    var allAppsFilter by remember { mutableStateOf("All") }

    // Exact popular apps matching user's image
    val primaryPopularApps = listOfNotNull(
        apps.find { it.id == "myfitnesspal" },
        apps.find { it.id == "paper" },
        apps.find { it.id == "zen" }
    )

    val secondaryPopularApps = listOfNotNull(
        apps.find { it.id == "heartrate" },
        apps.find { it.id == "niketraining" },
        apps.find { it.id == "spotify" }
    )

    val thirdPopularApps = listOfNotNull(
        apps.find { it.id == "chatgpt" },
        apps.find { it.id == "tripit" },
        apps.find { it.id == "whatsapp" }
    )

    val columnPages = listOf(primaryPopularApps, secondaryPopularApps, thirdPopularApps)

    val filterCategories = listOf("All (${apps.size})", "Popular", "Health & Fitness", "Games", "Social", "Productivity", "Arcade")

    val filteredAllApps = remember(allAppsFilter, apps) {
        when {
            allAppsFilter.startsWith("All") -> apps
            allAppsFilter == "Popular" -> primaryPopularApps + secondaryPopularApps + thirdPopularApps
            allAppsFilter == "Health & Fitness" -> apps.filter { it.category == AppCategory.HEALTH_FITNESS }
            allAppsFilter == "Games" -> apps.filter { it.category == AppCategory.GAMES }
            allAppsFilter == "Social" -> apps.filter { it.category == AppCategory.SOCIAL }
            allAppsFilter == "Productivity" -> apps.filter { it.category == AppCategory.PRODUCTIVITY || it.id == "chatgpt" || it.id == "tripit" }
            allAppsFilter == "Arcade" -> apps.filter { it.isArcade || it.category == AppCategory.ARCADE }
            else -> apps
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("apps_screen")
    ) {
        // Apps Header: "Apps" title and profile picture on the right
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Apps",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        letterSpacing = 0.35.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Profile Avatar from screenshot (circular avatar)
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE2B29F))
                        .clickable { onProfileClick() }
                        .testTag("apps_profile_avatar"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👩‍💼",
                        fontSize = 20.sp
                    )
                }
            }
        }

        // Horizontal Carousel (Now With Siri - TripIt, etc.)
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(featuredBanners) { banner ->
                    FeaturedAppsCarouselCard(
                        banner = banner,
                        onCardClick = {
                            val app = apps.find { it.id == banner.appId }
                            if (app != null) onAppClick(app)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Popular Apps Section Header: "Popular Apps" & "See All"
        item {
            SectionHeader(
                title = "Popular Apps",
                actionText = "See All",
                onActionClick = { allAppsFilter = "Popular" }
            )
        }

        // Popular Apps Carousel with 3-stacked rows per page (matching screenshot)
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(columnPages) { pageApps ->
                    Column(
                        modifier = Modifier
                            .width(320.dp)
                    ) {
                        pageApps.forEachIndexed { index, app ->
                            val installState = installStates[app.id] ?: AppInstallState()
                            AppRowItem(
                                app = app,
                                installState = installState,
                                onAppClick = { onAppClick(app) },
                                onGetClick = { onGetClick(app) },
                                isDarkTheme = isDarkTheme,
                                showDivider = index < pageApps.size - 1
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Fitness, Heart Rate & Workout Section
        item {
            SectionHeader(
                title = "Fitness, Health & Goals",
                actionText = "See All",
                onActionClick = { allAppsFilter = "Health & Fitness" }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    val fitnessApps = apps.filter {
                        it.id in listOf("myfitnesspal", "heartrate", "niketraining", "spotify")
                    }
                    fitnessApps.forEachIndexed { index, app ->
                        val installState = installStates[app.id] ?: AppInstallState()
                        AppRowItem(
                            app = app,
                            installState = installState,
                            onAppClick = { onAppClick(app) },
                            onGetClick = { onGetClick(app) },
                            isDarkTheme = isDarkTheme,
                            showDivider = index < fitnessApps.size - 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // All Apps & Games in Store Section
        item {
            SectionHeader(
                title = "All Apps in Store",
                actionText = null
            )

            // Category Filter Pills
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filterCategories) { category ->
                    val isSelected = (category.startsWith("All") && allAppsFilter.startsWith("All")) || category == allAppsFilter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) IosBlue
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable {
                                allAppsFilter = if (category.startsWith("All")) "All" else category
                            }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    filteredAllApps.forEachIndexed { index, app ->
                        val installState = installStates[app.id] ?: AppInstallState()
                        AppRowItem(
                            app = app,
                            installState = installState,
                            onAppClick = { onAppClick(app) },
                            onGetClick = { onGetClick(app) },
                            isDarkTheme = isDarkTheme,
                            showDivider = index < filteredAllApps.size - 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
