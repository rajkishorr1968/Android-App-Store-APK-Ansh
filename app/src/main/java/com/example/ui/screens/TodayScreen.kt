package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppInstallState
import com.example.data.model.StoreApp
import com.example.data.model.TodayStory
import com.example.ui.components.AppRowItem
import com.example.ui.components.HeroTodayCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.IosBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodayScreen(
    apps: List<StoreApp>,
    installStates: Map<String, AppInstallState>,
    onAppClick: (StoreApp) -> Unit,
    onGetClick: (StoreApp) -> Unit,
    onProfileClick: () -> Unit,
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier
) {
    val todayDateFormatted = remember {
        val sdf = SimpleDateFormat("EEEE, MMMM d", Locale.US)
        sdf.format(Date()).uppercase()
    }

    val stories = remember(apps) {
        apps.mapNotNull { it.heroStory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("today_screen")
    ) {
        // Today Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = todayDateFormatted,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 0.5.sp
                        )
                    )
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 34.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Apple ID Profile Avatar
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(IosBlue.copy(alpha = 0.15f))
                        .clickable { onProfileClick() }
                        .testTag("profile_avatar_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🍎",
                        fontSize = 20.sp
                    )
                }
            }
        }

        // Stories Feed
        items(stories) { story ->
            val storyApp = apps.find { it.id == story.appId }
            if (storyApp != null) {
                val installState = installStates[storyApp.id] ?: AppInstallState()
                HeroTodayCard(
                    story = story,
                    app = storyApp,
                    installState = installState,
                    onCardClick = { onAppClick(storyApp) },
                    onGetClick = { onGetClick(storyApp) },
                    isDarkTheme = isDarkTheme
                )
            }
        }

        // "Now Trending on iOS" Collection Card
        item {
            SectionHeader(
                title = "Trending Now",
                actionText = "See All",
                modifier = Modifier.padding(top = 10.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    val trendingSample = apps.take(3)
                    trendingSample.forEachIndexed { index, app ->
                        val installState = installStates[app.id] ?: AppInstallState()
                        AppRowItem(
                            app = app,
                            installState = installState,
                            onAppClick = { onAppClick(app) },
                            onGetClick = { onGetClick(app) },
                            isDarkTheme = isDarkTheme,
                            showDivider = index < trendingSample.size - 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
