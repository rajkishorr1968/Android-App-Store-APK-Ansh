package com.example.ui.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppInstallState
import com.example.data.model.FeaturedBanner
import com.example.data.model.StoreApp
import com.example.data.model.TodayStory
import com.example.ui.theme.IosBlue

@Composable
fun SectionHeader(
    title: String,
    actionText: String? = "See All",
    onActionClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        if (actionText != null) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = IosBlue,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onActionClick() }
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
    }
}

// Top Horizontal Carousel Card matching the user's screenshot
@Composable
fun FeaturedAppsCarouselCard(
    banner: FeaturedBanner,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(325.dp)
            .clickable { onCardClick() }
            .testTag("featured_banner_${banner.id}")
    ) {
        // Tag (e.g. NOW WITH SIRI in vibrant blue)
        Text(
            text = banner.tag,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.5.sp,
                color = IosBlue,
                letterSpacing = 0.6.sp
            )
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Title (TripIt: Travel Planner)
        Text(
            text = banner.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                letterSpacing = (-0.3).sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Subtitle (Quickly get flight info with Siri)
        Text(
            text = banner.subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Large Graphic Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(banner.bannerBgColor)),
            contentAlignment = Alignment.Center
        ) {
            when (banner.bannerIllustrationKey) {
                "tripit" -> {
                    // Exact illustration matching the user's screenshot:
                    // Character sitting on red map pin with suitcase, flight path lines and plane
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Background stylized flight map paths
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            // Flight route arcs
                            drawArc(
                                color = Color.White.copy(alpha = 0.25f),
                                startAngle = 180f,
                                sweepAngle = 90f,
                                useCenter = false,
                                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
                                topLeft = Offset(w * 0.1f, h * 0.1f),
                                size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.8f)
                            )
                            drawLine(
                                color = Color.White.copy(alpha = 0.25f),
                                start = Offset(w * 0.05f, h * 0.7f),
                                end = Offset(w * 0.95f, h * 0.7f),
                                strokeWidth = 1.5.dp.toPx()
                            )
                        }

                        // Airplane emblem
                        Text(
                            text = "✈️",
                            fontSize = 28.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 24.dp, top = 28.dp)
                        )

                        // Globe / destination pin
                        Text(
                            text = "🌐",
                            fontSize = 22.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 65.dp, bottom = 10.dp)
                        )

                        // Center Map Pin + Traveler + Luggage
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🧑‍💻",
                                fontSize = 48.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "🧳", fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "📍", fontSize = 54.sp)
                            }
                        }

                        // Siri / Travel prompt badge at bottom
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.4f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "🎙️ Hey Siri, show flights",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                "fitness" -> {
                    // Fitness, Workout, Heart Beat Check & Timer Graphic
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF0052D4), Color(0xFF4364F7), Color(0xFF6FB1FC))
                                )
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "⏱️", fontSize = 42.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = "💓", fontSize = 52.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = "🎵", fontSize = 42.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Workout Timer • Heart Rate BPM • Music",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "✨", fontSize = 54.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AppRowItem(
    app: StoreApp,
    installState: AppInstallState,
    onAppClick: () -> Unit,
    onGetClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    showDivider: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAppClick() }
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SquircleAppIcon(
                emoji = app.iconEmoji,
                gradientColors = app.iconGradientColors,
                size = 58.dp,
                customIconType = app.customIconType ?: app.id
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = app.description.take(40) + "...",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            IosGetButton(
                installState = installState,
                price = app.price,
                inAppPurchases = app.inAppPurchases,
                onClick = onGetClick,
                isDarkTheme = isDarkTheme
            )
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 92.dp, end = 20.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
            )
        }
    }
}

@Composable
fun RankedAppRowItem(
    rank: Int,
    app: StoreApp,
    installState: AppInstallState,
    onAppClick: () -> Unit,
    onGetClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    showDivider: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAppClick() }
                .padding(horizontal = 20.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$rank",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(28.dp)
            )

            SquircleAppIcon(
                emoji = app.iconEmoji,
                gradientColors = app.iconGradientColors,
                size = 56.dp,
                customIconType = app.customIconType ?: app.id
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = app.genre,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IosGetButton(
                installState = installState,
                price = app.price,
                inAppPurchases = app.inAppPurchases,
                onClick = onGetClick,
                isDarkTheme = isDarkTheme
            )
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 96.dp, end = 20.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
            )
        }
    }
}

@Composable
fun HeroTodayCard(
    story: TodayStory,
    app: StoreApp,
    installState: AppInstallState,
    onCardClick: () -> Unit,
    onGetClick: () -> Unit,
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bgColors = story.bgGradient.map { Color(it) }
    val brush = remember(story.bgGradient) {
        Brush.verticalGradient(bgColors)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .height(410.dp)
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(18.dp), clip = false)
            .clip(RoundedCornerShape(18.dp))
            .background(brush)
            .clickable { onCardClick() }
            .testTag("hero_today_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = story.tag,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = story.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        lineHeight = 31.sp,
                        color = Color.White
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = story.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.iconEmoji,
                    fontSize = 58.sp
                )
            }

            // Bottom Glass App Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SquircleAppIcon(
                    emoji = app.iconEmoji,
                    gradientColors = app.iconGradientColors,
                    size = 46.dp,
                    customIconType = app.customIconType ?: app.id
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = app.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = app.genre,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.75f)
                        ),
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IosGetButton(
                    installState = installState,
                    price = app.price,
                    inAppPurchases = app.inAppPurchases,
                    onClick = onGetClick,
                    isDarkTheme = true
                )
            }
        }
    }
}
