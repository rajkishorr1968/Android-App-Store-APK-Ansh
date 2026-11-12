package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StoreApp
import com.example.ui.components.SquircleAppIcon
import com.example.ui.theme.IosBlue
import kotlinx.coroutines.delay

@Composable
fun SimulatedAppRunner(
    app: StoreApp,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF121212)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // iOS Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onClose() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Exit to App Store",
                        tint = IosBlue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "App Store",
                        color = IosBlue,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "iOS Simulator",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF34C759),
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF34C759).copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
            }

            // Dynamic Container based on the app
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when {
                    app.id == "subwaysurf" || app.genre.contains("Runner", ignoreCase = true) -> {
                        SubwaySurferMiniGame(app = app)
                    }
                    app.id == "spotify" || app.genre.contains("Music", ignoreCase = true) -> {
                        SpotifyMusicMiniPlayer(app = app)
                    }
                    app.id == "chatgpt" || app.name.contains("ChatGPT", ignoreCase = true) -> {
                        ChatGptMiniSimulator(app = app)
                    }
                    app.id == "instagram" || app.genre.contains("Photo", ignoreCase = true) -> {
                        InstagramMiniFeed(app = app)
                    }
                    app.id == "sasquatch" || app.isArcade -> {
                        ArcadeAdventureMiniGame(app = app)
                    }
                    else -> {
                        GenericAppSimulator(app = app)
                    }
                }
            }

            // iOS Home Indicator Gesture Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(vertical = 12.dp)
                    .clickable { onClose() },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(5.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Swipe up or tap to exit to App Store",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// 1. Subway Surfers Endless Runner Sandbox Game
@Composable
private fun SubwaySurferMiniGame(app: StoreApp) {
    var playerLane by remember { mutableIntStateOf(1) } // 0: Left, 1: Center, 2: Right
    var score by remember { mutableIntStateOf(1280) }
    var coins by remember { mutableIntStateOf(145) }
    var isJumping by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(150)
            score += 10
            if (score % 50 == 0) coins += 1
        }
    }

    LaunchedEffect(isJumping) {
        if (isJumping) {
            delay(600)
            isJumping = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Stats bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SCORE: $score",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )
            )
            Text(
                text = "🪙 $coins COINS",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        // 3 Subway Tracks Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (lane in 0..2) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .background(
                                if (playerLane == lane) Color.White.copy(alpha = 0.15f)
                                else Color.Black.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "🚊",
                                fontSize = 42.sp,
                                modifier = Modifier.padding(top = 24.dp)
                            )

                            if (playerLane == lane) {
                                Text(
                                    text = if (isJumping) "🚀" else "🛹",
                                    fontSize = 54.sp,
                                    modifier = Modifier.padding(bottom = if (isJumping) 60.dp else 16.dp)
                                )
                            } else {
                                Spacer(modifier = Modifier.height(1.dp))
                            }
                        }
                    }
                }
            }
        }

        // Action controls
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { if (playerLane > 0) playerLane-- },
                    colors = ButtonDefaults.buttonColors(containerColor = IosBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("⬅️ Lane Left", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { isJumping = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9500)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🦘 Super Jump", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { if (playerLane < 2) playerLane++ },
                    colors = ButtonDefaults.buttonColors(containerColor = IosBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Lane Right ➡️", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// 2. Spotify Mini Player Simulator
@Composable
private fun SpotifyMusicMiniPlayer(app: StoreApp) {
    var isPlaying by remember { mutableStateOf(true) }
    var trackProgress by remember { mutableFloatStateOf(0.35f) }
    var currentSongIndex by remember { mutableIntStateOf(0) }

    val songs = listOf(
        "Blinding Lights" to "The Weeknd",
        "Starboy" to "The Weeknd ft. Daft Punk",
        "As It Was" to "Harry Styles",
        "Flowers" to "Miley Cyrus"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "VinylSpin")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "VinylAngle"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF1DB954).copy(alpha = 0.8f), Color(0xFF121212)))
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "PLAYING FROM PLAYLIST",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
        )
        Text(
            text = "Today's Top Hits",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )

        // Vinyl Record
        Box(
            modifier = Modifier
                .size(220.dp)
                .rotate(if (isPlaying) angle else 0f)
                .clip(CircleShape)
                .background(Color.Black)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1DB954)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }
        }

        // Song Title and Artist
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = songs[currentSongIndex].first,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = songs[currentSongIndex].second,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.LightGray
                )
            )
        }

        // Playback Bar
        Column(modifier = Modifier.fillMaxWidth()) {
            Slider(
                value = trackProgress,
                onValueChange = { trackProgress = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("1:14", fontSize = 11.sp, color = Color.Gray)
                Text("3:20", fontSize = 11.sp, color = Color.Gray)
            }
        }

        // Media Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                currentSongIndex = if (currentSongIndex > 0) currentSongIndex - 1 else songs.size - 1
            }) {
                Icon(
                    imageVector = Icons.Default.FastRewind,
                    contentDescription = "Prev",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { isPlaying = !isPlaying },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }

            IconButton(onClick = {
                currentSongIndex = (currentSongIndex + 1) % songs.size
            }) {
                Icon(
                    imageVector = Icons.Default.FastForward,
                    contentDescription = "Next",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

// 3. ChatGPT Mini Simulator
@Composable
private fun ChatGptMiniSimulator(app: StoreApp) {
    var inputText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "assistant" to "Hello! I am ChatGPT running on your device. How can I assist you today?",
            "user" to "What is special about the iOS App Store?",
            "assistant" to "The iOS App Store is famous for its editorial Today stories, curated charts, seamless design, and trusted app ecosystem!"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202123))
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { (sender, text) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (sender == "user") Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (sender == "user") IosBlue else Color(0xFF343541))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = text,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Message ChatGPT...", color = Color.Gray) },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = IosBlue,
                    unfocusedBorderColor = Color.DarkGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        val query = inputText
                        messages.add("user" to query)
                        inputText = ""
                        messages.add("assistant" to "Great query! Here is tailored information about \"$query\": Everything is optimized and working smoothly in your iOS sandbox environment.")
                    }
                },
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF10A37F))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

// 4. Instagram Mini Feed Simulator
@Composable
private fun InstagramMiniFeed(app: StoreApp) {
    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(1429) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(14.dp)
    ) {
        // Story row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(bottom = 14.dp)
        ) {
            items(listOf("Your Story" to "👤", "nature" to "🌿", "tech" to "💻", "games" to "🎮", "travel" to "✈️")) { (name, emoji) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF833AB4), Color(0xFFFD1D1D), Color(0xFFFCB045))
                                )
                            )
                            .padding(2.5.dp)
                            .clip(CircleShape)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = name, fontSize = 10.sp, color = Color.White)
                }
            }
        }

        // Post Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📸", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "apple_appstore",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF007AFF), Color(0xFF5856D6))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✨", fontSize = 48.sp)
                        Text(
                            text = "iOS Experience on Android",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            if (isLiked) likesCount++ else likesCount--
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color.Red else Color.White
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(
                    text = "$likesCount likes",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Text(
                    text = "Exploring the sleekest iOS design system right on Android! #AppleStore #JetpackCompose",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// 5. Arcade Adventure Mini Game
@Composable
private fun ArcadeAdventureMiniGame(app: StoreApp) {
    var disguiseLevel by remember { mutableIntStateOf(100) }
    var foodCollected by remember { mutableIntStateOf(3) }
    var statusText by remember { mutableStateOf("Ranger is looking away... Sneak now!") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E7D32))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "🏕️ SNEAKY SASQUATCH ADVENTURE",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1B5E20))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🦍", fontSize = 72.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = statusText,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text("🌭 Stolen Hot Dogs: $foodCollected", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Disguise: $disguiseLevel%", color = Color.White, fontSize = 12.sp)
                LinearProgressIndicator(
                    progress = { disguiseLevel / 100f },
                    modifier = Modifier
                        .width(180.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFF00E676),
                    trackColor = Color.Black
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    foodCollected++
                    statusText = "Sneak successful! Grabbed hot dog from cooler."
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9500))
            ) {
                Text("🌭 Steal Food")
            }

            Button(
                onClick = {
                    disguiseLevel = 100
                    statusText = "Put on Ranger Hat. You look like a regular human!"
                },
                colors = ButtonDefaults.buttonColors(containerColor = IosBlue)
            ) {
                Text("🎩 Put Disguise")
            }
        }
    }
}

// 6. Generic App Simulator
@Composable
private fun GenericAppSimulator(app: StoreApp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SquircleAppIcon(
            emoji = app.iconEmoji,
            gradientColors = app.iconGradientColors,
            size = 96.dp,
            customIconType = app.customIconType ?: app.id
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = app.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Text(
            text = app.developer,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "App Sandbox Active",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF34C759)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "This application is running inside your simulated iPhone environment. All features and cache are safely isolated.",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}
