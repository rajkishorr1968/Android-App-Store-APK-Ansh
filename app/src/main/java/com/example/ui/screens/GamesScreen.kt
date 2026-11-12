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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppCategory
import com.example.data.model.AppInstallState
import com.example.data.model.StoreApp
import com.example.ui.components.RankedAppRowItem
import com.example.ui.components.SectionHeader
import com.example.ui.theme.IosBlue

@Composable
fun GamesScreen(
    apps: List<StoreApp>,
    installStates: Map<String, AppInstallState>,
    onAppClick: (StoreApp) -> Unit,
    onGetClick: (StoreApp) -> Unit,
    onProfileClick: () -> Unit,
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier
) {
    val gameCategories = listOf("All Games", "Action", "Endless Runner", "Role Playing", "Casual", "Strategy")
    var selectedCategory by remember { mutableStateOf("All Games") }

    val gamesList = remember(apps, selectedCategory) {
        val allGames = apps.filter { it.category == AppCategory.GAMES || it.category == AppCategory.ARCADE }
        if (selectedCategory == "All Games") allGames
        else allGames.filter { it.genre.contains(selectedCategory, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("games_screen")
    ) {
        // Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Games",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE2B29F))
                        .clickable { onProfileClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👩‍💼", fontSize = 20.sp)
                }
            }
        }

        // Category Filter Pills
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(gameCategories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) IosBlue
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable { selectedCategory = category }
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
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Featured Games Hero Banner
        item {
            val featuredGame = gamesList.firstOrNull() ?: apps.first()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121))
                        )
                    )
                    .clickable { onAppClick(featuredGame) }
                    .padding(18.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = "NEW RELEASE SPOTLIGHT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.8f),
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = featuredGame.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = featuredGame.genre,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        // Top Free Games Ranking
        item {
            SectionHeader(
                title = "Top Free Games",
                actionText = "See All",
                onActionClick = {}
            )
        }

        itemsIndexed(gamesList) { index, game ->
            val installState = installStates[game.id] ?: AppInstallState()
            RankedAppRowItem(
                rank = index + 1,
                app = game,
                installState = installState,
                onAppClick = { onAppClick(game) },
                onGetClick = { onGetClick(game) },
                isDarkTheme = isDarkTheme,
                showDivider = index < gamesList.size - 1
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
