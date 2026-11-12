package com.example.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppInstallState
import com.example.data.model.DownloadStatus
import com.example.ui.theme.IosBlue

@Composable
fun IosGetButton(
    installState: AppInstallState,
    price: String = "Free",
    inAppPurchases: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Crossfade(
            targetState = installState.status,
            label = "GetButtonCrossfade"
        ) { status ->
            when (status) {
                DownloadStatus.GET -> {
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(min = 72.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(
                                if (isDarkTheme) Color(0xFF2C2C2E) else Color(0xFFEFEFF4)
                            )
                            .clickable(
                                interactionSource = interactionSource,
                                indication = ripple(bounded = true, color = IosBlue),
                                onClick = onClick
                            )
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (price == "Free" || price.isBlank()) "GET" else price.uppercase(),
                            color = IosBlue,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.testTag("get_button")
                        )
                    }
                }
                DownloadStatus.DOWNLOADING -> {
                    // Authentic iOS Circular Progress Ring with Stop Square
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(28.dp)) {
                            val strokeWidth = 2.5.dp.toPx()
                            // Background track ring
                            drawCircle(
                                color = if (isDarkTheme) Color(0xFF3A3A3C) else Color(0xFFD1D1D6),
                                radius = size.minDimension / 2 - strokeWidth / 2,
                                style = Stroke(width = strokeWidth)
                            )
                            // Progress sweep
                            val sweepAngle = installState.progress * 360f
                            drawArc(
                                color = IosBlue,
                                startAngle = -90f,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                                size = Size(size.width - strokeWidth, size.height - strokeWidth)
                            )
                        }
                        // Centered stop square
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(1.5.dp))
                                .background(IosBlue)
                        )
                    }
                }
                DownloadStatus.INSTALLED -> {
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .widthIn(min = 72.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(
                                if (isDarkTheme) Color(0xFF2C2C2E) else Color(0xFFEFEFF4)
                            )
                            .clickable(
                                interactionSource = interactionSource,
                                indication = ripple(bounded = true, color = IosBlue),
                                onClick = onClick
                            )
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "OPEN",
                            color = IosBlue,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.testTag("open_button")
                        )
                    }
                }
            }
        }

        if (installState.status == DownloadStatus.GET && inAppPurchases) {
            Text(
                text = "In-App Purchases",
                fontSize = 8.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun SquircleAppIcon(
    emoji: String,
    gradientColors: List<Long>,
    size: Dp = 62.dp,
    customIconType: String? = null,
    modifier: Modifier = Modifier
) {
    val colors = gradientColors.map { Color(it) }
    val brush = remember(gradientColors) {
        if (colors.size >= 2) Brush.linearGradient(colors)
        else Brush.linearGradient(listOf(Color(0xFF007AFF), Color(0xFF5856D6)))
    }

    Box(
        modifier = modifier
            .size(size)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(size * 0.225f))
            .clip(RoundedCornerShape(size * 0.225f))
            .background(brush)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.25f),
                shape = RoundedCornerShape(size * 0.225f)
            ),
        contentAlignment = Alignment.Center
    ) {
        val iconKey = customIconType ?: ""
        when (iconKey) {
            "myfitnesspal" -> {
                // Exact Under Armour leaping athlete on royal blue
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0070D2)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        drawCircle(color = Color.White, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.22f))
                        val strokeW = w * 0.12f
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.15f, h * 0.42f),
                            end = Offset(w * 0.85f, h * 0.42f),
                            strokeWidth = strokeW,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.5f, h * 0.35f),
                            end = Offset(w * 0.5f, h * 0.65f),
                            strokeWidth = strokeW,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.5f, h * 0.65f),
                            end = Offset(w * 0.22f, h * 0.88f),
                            strokeWidth = strokeW,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.5f, h * 0.65f),
                            end = Offset(w * 0.78f, h * 0.88f),
                            strokeWidth = strokeW,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
            "paper" -> {
                // Exact Paper by WeTransfer colorful ribbon on white
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.7f)) {
                        val w = this.size.width
                        val h = this.size.height
                        val strokeW = w * 0.14f
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.18f, h * 0.75f)
                            cubicTo(w * 0.18f, h * 0.25f, w * 0.42f, h * 0.25f, w * 0.42f, h * 0.75f)
                            cubicTo(w * 0.42f, h * 0.25f, w * 0.68f, h * 0.25f, w * 0.68f, h * 0.75f)
                            cubicTo(w * 0.68f, h * 0.25f, w * 0.90f, h * 0.35f, w * 0.88f, h * 0.55f)
                        }
                        drawPath(
                            path = path,
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF00C9FF), Color(0xFFFFB300), Color(0xFFFF2A6D))
                            ),
                            style = Stroke(width = strokeW, cap = StrokeCap.Round)
                        )
                    }
                }
            }
            "zen" -> {
                // Exact Zen teal icon with white lowercase text
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00B4D8)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "zen",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = (size.value * 0.36f).sp,
                        letterSpacing = (-0.5).sp
                    )
                }
            }
            "tripit" -> {
                // TripIt flight arrow and travel compass on sky blue
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0072CE)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // Supersonic passenger jet
                        val jet = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.5f, h * 0.12f)
                            lineTo(w * 0.6f, h * 0.38f)
                            lineTo(w * 0.92f, h * 0.52f)
                            lineTo(w * 0.92f, h * 0.62f)
                            lineTo(w * 0.58f, h * 0.58f)
                            lineTo(w * 0.56f, h * 0.78f)
                            lineTo(w * 0.72f, h * 0.88f)
                            lineTo(w * 0.72f, h * 0.95f)
                            lineTo(w * 0.5f, h * 0.88f)
                            lineTo(w * 0.28f, h * 0.95f)
                            lineTo(w * 0.28f, h * 0.88f)
                            lineTo(w * 0.44f, h * 0.78f)
                            lineTo(w * 0.42f, h * 0.58f)
                            lineTo(w * 0.08f, h * 0.62f)
                            lineTo(w * 0.08f, h * 0.52f)
                            lineTo(w * 0.4f, h * 0.38f)
                            close()
                        }
                        drawPath(jet, color = Color.White)
                    }
                }
            }
            "heartrate" -> {
                // Heart with medical ECG heartbeat pulse graph line
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(listOf(Color(0xFFFF0844), Color(0xFFFF4E50)))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.7f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // ECG pulse line
                        val ecg = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.08f, h * 0.5f)
                            lineTo(w * 0.28f, h * 0.5f)
                            lineTo(w * 0.38f, h * 0.25f)
                            lineTo(w * 0.52f, h * 0.78f)
                            lineTo(w * 0.64f, h * 0.38f)
                            lineTo(w * 0.74f, h * 0.5f)
                            lineTo(w * 0.92f, h * 0.5f)
                        }
                        drawPath(
                            path = ecg,
                            color = Color.White,
                            style = Stroke(width = w * 0.1f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
                        )
                    }
                }
            }
            "niketraining" -> {
                // Nike Swoosh on black
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF111111)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        val swoosh = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.12f, h * 0.52f)
                            cubicTo(w * 0.32f, h * 0.68f, w * 0.46f, h * 0.74f, w * 0.58f, h * 0.72f)
                            cubicTo(w * 0.74f, h * 0.70f, w * 0.88f, h * 0.48f, w * 0.95f, h * 0.24f)
                            cubicTo(w * 0.84f, h * 0.54f, w * 0.68f, h * 0.62f, w * 0.54f, h * 0.61f)
                            cubicTo(w * 0.42f, h * 0.60f, w * 0.28f, h * 0.50f, w * 0.12f, h * 0.52f)
                            close()
                        }
                        drawPath(swoosh, color = Color.White)
                    }
                }
            }
            "spotify" -> {
                // Spotify real 3 curved sound wave arcs on green
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1ED760)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.68f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // 3 concentric sound waves
                        val stroke1 = w * 0.11f
                        val stroke2 = w * 0.10f
                        val stroke3 = w * 0.09f
                        val arcColor = Color(0xFF191414)
                        // Top Arc
                        drawArc(
                            color = arcColor,
                            startAngle = 200f,
                            sweepAngle = 72f,
                            useCenter = false,
                            style = Stroke(width = stroke1, cap = StrokeCap.Round),
                            topLeft = Offset(w * 0.08f, h * 0.16f),
                            size = Size(w * 0.84f, h * 0.84f)
                        )
                        // Middle Arc
                        drawArc(
                            color = arcColor,
                            startAngle = 200f,
                            sweepAngle = 68f,
                            useCenter = false,
                            style = Stroke(width = stroke2, cap = StrokeCap.Round),
                            topLeft = Offset(w * 0.16f, h * 0.28f),
                            size = Size(w * 0.68f, h * 0.68f)
                        )
                        // Bottom Arc
                        drawArc(
                            color = arcColor,
                            startAngle = 200f,
                            sweepAngle = 64f,
                            useCenter = false,
                            style = Stroke(width = stroke3, cap = StrokeCap.Round),
                            topLeft = Offset(w * 0.24f, h * 0.40f),
                            size = Size(w * 0.52f, h * 0.52f)
                        )
                    }
                }
            }
            "instagram" -> {
                // Real Instagram camera on sunset gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF405DE6), Color(0xFF833AB4), Color(0xFFE1306C), Color(0xFFFD1D1D), Color(0xFFF77737))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.62f)) {
                        val w = this.size.width
                        val h = this.size.height
                        val strokeW = w * 0.11f
                        // Outer rounded camera
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(strokeW / 2, strokeW / 2),
                            size = Size(w - strokeW, h - strokeW),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.26f),
                            style = Stroke(width = strokeW)
                        )
                        // Center circle lens
                        drawCircle(
                            color = Color.White,
                            radius = w * 0.22f,
                            center = Offset(w * 0.5f, h * 0.5f),
                            style = Stroke(width = strokeW)
                        )
                        // Flash dot
                        drawCircle(
                            color = Color.White,
                            radius = w * 0.055f,
                            center = Offset(w * 0.74f, h * 0.26f)
                        )
                    }
                }
            }
            "tiktok" -> {
                // Real TikTok chromatic aberration musical note on pure black
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF010101)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        val strokeW = w * 0.14f

                        fun drawNote(offset: Offset, color: Color) {
                            val path = androidx.compose.ui.graphics.Path().apply {
                                moveTo(w * 0.42f + offset.x, h * 0.20f + offset.y)
                                lineTo(w * 0.42f + offset.x, h * 0.62f + offset.y)
                                cubicTo(
                                    w * 0.42f + offset.x, h * 0.78f + offset.y,
                                    w * 0.22f + offset.x, h * 0.78f + offset.y,
                                    w * 0.22f + offset.x, h * 0.66f + offset.y
                                )
                                cubicTo(
                                    w * 0.22f + offset.x, h * 0.54f + offset.y,
                                    w * 0.38f + offset.x, h * 0.54f + offset.y,
                                    w * 0.42f + offset.x, h * 0.56f + offset.y
                                )
                            }
                            drawPath(path, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round))
                            // Right flag
                            val flag = androidx.compose.ui.graphics.Path().apply {
                                moveTo(w * 0.42f + offset.x, h * 0.22f + offset.y)
                                cubicTo(
                                    w * 0.54f + offset.x, h * 0.22f + offset.y,
                                    w * 0.72f + offset.x, h * 0.32f + offset.y,
                                    w * 0.76f + offset.x, h * 0.46f + offset.y
                                )
                            }
                            drawPath(flag, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round))
                        }

                        // Cyan shifted left
                        drawNote(Offset(-2.dp.toPx(), -1.dp.toPx()), Color(0xFF00F2FE))
                        // Magenta shifted right
                        drawNote(Offset(2.dp.toPx(), 1.dp.toPx()), Color(0xFFFE2C55))
                        // White centered
                        drawNote(Offset(0f, 0f), Color.White)
                    }
                }
            }
            "whatsapp" -> {
                // Real WhatsApp speech bubble with phone receiver
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF25D366)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.68f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // White speech circle
                        drawCircle(color = Color.White, radius = w * 0.42f, center = Offset(w * 0.5f, h * 0.48f))
                        // Tail on bottom left
                        val tail = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.22f, h * 0.65f)
                            lineTo(w * 0.12f, h * 0.88f)
                            lineTo(w * 0.36f, h * 0.80f)
                            close()
                        }
                        drawPath(tail, color = Color.White)
                        // Phone handset in WhatsApp green
                        val phone = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.38f, h * 0.32f)
                            cubicTo(w * 0.32f, h * 0.38f, w * 0.34f, h * 0.54f, w * 0.46f, h * 0.66f)
                            cubicTo(w * 0.58f, h * 0.78f, w * 0.72f, h * 0.76f, w * 0.76f, h * 0.70f)
                            lineTo(w * 0.70f, h * 0.60f)
                            lineTo(w * 0.62f, h * 0.64f)
                            cubicTo(w * 0.56f, h * 0.58f, w * 0.50f, h * 0.52f, w * 0.46f, h * 0.44f)
                            lineTo(w * 0.50f, h * 0.38f)
                            close()
                        }
                        drawPath(phone, color = Color(0xFF25D366))
                    }
                }
            }
            "chatgpt" -> {
                // Real OpenAI rosette knot on dark teal
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF10A37F)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        val strokeW = w * 0.08f
                        // 6 symmetrical interlocking loops
                        for (angle in 0 until 360 step 60) {
                            val rad = Math.toRadians(angle.toDouble())
                            val cos = Math.cos(rad).toFloat()
                            val sin = Math.sin(rad).toFloat()
                            val cx = w * 0.5f + cos * w * 0.16f
                            val cy = h * 0.5f + sin * h * 0.16f
                            drawCircle(
                                color = Color.White,
                                radius = w * 0.18f,
                                center = Offset(cx, cy),
                                style = Stroke(width = strokeW)
                            )
                        }
                    }
                }
            }
            "duolingo" -> {
                // Real Duo the Owl's face on lime green
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF58CC02)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.72f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // Left eye white
                        drawCircle(color = Color.White, radius = w * 0.22f, center = Offset(w * 0.32f, h * 0.42f))
                        // Right eye white
                        drawCircle(color = Color.White, radius = w * 0.22f, center = Offset(w * 0.68f, h * 0.42f))
                        // Left pupil
                        drawCircle(color = Color(0xFF2B4718), radius = w * 0.11f, center = Offset(w * 0.36f, h * 0.42f))
                        // Right pupil
                        drawCircle(color = Color(0xFF2B4718), radius = w * 0.11f, center = Offset(w * 0.64f, h * 0.42f))
                        // Orange triangular beak
                        val beak = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.40f, h * 0.52f)
                            lineTo(w * 0.60f, h * 0.52f)
                            lineTo(w * 0.50f, h * 0.72f)
                            close()
                        }
                        drawPath(beak, color = Color(0xFFFF9600))
                    }
                }
            }
            "roblox" -> {
                // Real Roblox tilted metallic block with square hole on carbon
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1E1E1E)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.60f)) {
                        val w = this.size.width
                        val h = this.size.height
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(w * 0.15f, h * 0.15f),
                            size = Size(w * 0.70f, h * 0.70f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.12f)
                        )
                        // Inner tilted square hole
                        drawRoundRect(
                            color = Color(0xFF1E1E1E),
                            topLeft = Offset(w * 0.38f, h * 0.38f),
                            size = Size(w * 0.24f, h * 0.24f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.04f)
                        )
                    }
                }
            }
            "clashroyale" -> {
                // Real Clash Royale King's golden crown on royal blue
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(listOf(Color(0xFF0052D4), Color(0xFF1B3B8B)))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // 3-point golden crown
                        val crown = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.15f, h * 0.75f)
                            lineTo(w * 0.12f, h * 0.35f)
                            lineTo(w * 0.35f, h * 0.52f)
                            lineTo(w * 0.50f, h * 0.24f)
                            lineTo(w * 0.65f, h * 0.52f)
                            lineTo(w * 0.88f, h * 0.35f)
                            lineTo(w * 0.85f, h * 0.75f)
                            close()
                        }
                        drawPath(crown, color = Color(0xFFFFD700))
                        // Crown base rim
                        drawRoundRect(
                            color = Color(0xFFFFB300),
                            topLeft = Offset(w * 0.14f, h * 0.72f),
                            size = Size(w * 0.72f, h * 0.12f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.04f)
                        )
                        // Sapphire blue jewel in center
                        drawCircle(color = Color(0xFF00E5FF), radius = w * 0.06f, center = Offset(w * 0.50f, h * 0.64f))
                    }
                }
            }
            "subwaysurf" -> {
                // Subway Surfers Jake red skater cap with yellow visor
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00B4D8)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.70f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // Red Cap dome
                        drawArc(
                            color = Color(0xFFE53935),
                            startAngle = 180f,
                            sweepAngle = 180f,
                            useCenter = true,
                            topLeft = Offset(w * 0.15f, h * 0.18f),
                            size = Size(w * 0.70f, h * 0.60f)
                        )
                        // Yellow curved visor
                        val visor = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.10f, h * 0.48f)
                            cubicTo(w * 0.40f, h * 0.40f, w * 0.70f, h * 0.40f, w * 0.95f, h * 0.52f)
                            lineTo(w * 0.88f, h * 0.62f)
                            cubicTo(w * 0.65f, h * 0.52f, w * 0.35f, h * 0.52f, w * 0.12f, h * 0.58f)
                            close()
                        }
                        drawPath(visor, color = Color(0xFFFFD600))
                        // Star badge on cap
                        drawCircle(color = Color.White, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.32f))
                    }
                }
            }
            "genshin" -> {
                // Genshin celestial 4-point primogem star on deep galaxy
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(listOf(Color(0xFF0D1B2A), Color(0xFF1B263B), Color(0xFF415A77)))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.68f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // Glowing star
                        val star = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.50f, h * 0.08f)
                            cubicTo(w * 0.50f, h * 0.38f, w * 0.62f, h * 0.50f, w * 0.92f, h * 0.50f)
                            cubicTo(w * 0.62f, h * 0.50f, w * 0.50f, h * 0.62f, w * 0.50f, h * 0.92f)
                            cubicTo(w * 0.50f, h * 0.62f, w * 0.38f, h * 0.50f, w * 0.08f, h * 0.50f)
                            cubicTo(w * 0.38f, h * 0.50f, w * 0.50f, h * 0.38f, w * 0.50f, h * 0.08f)
                            close()
                        }
                        drawPath(star, color = Color(0xFFFFD54F))
                        // Inner glow diamond
                        drawCircle(color = Color.White, radius = w * 0.07f, center = Offset(w * 0.5f, h * 0.5f))
                    }
                }
            }
            "sasquatch" -> {
                // Sneaky Sasquatch eyes & fur on forest green
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2E7D32)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.70f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // Brown Sasquatch head
                        drawCircle(color = Color(0xFF5D4037), radius = w * 0.40f, center = Offset(w * 0.5f, h * 0.54f))
                        // Left eye
                        drawCircle(color = Color.White, radius = w * 0.12f, center = Offset(w * 0.35f, h * 0.44f))
                        drawCircle(color = Color.Black, radius = w * 0.06f, center = Offset(w * 0.37f, h * 0.44f))
                        // Right eye
                        drawCircle(color = Color.White, radius = w * 0.12f, center = Offset(w * 0.65f, h * 0.44f))
                        drawCircle(color = Color.Black, radius = w * 0.06f, center = Offset(w * 0.63f, h * 0.44f))
                        // Cute nose
                        drawCircle(color = Color(0xFF3E2723), radius = w * 0.07f, center = Offset(w * 0.50f, h * 0.60f))
                    }
                }
            }
            "oceanhorn2" -> {
                // Oceanhorn 2 Knight Shield on azure
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0277BD)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.65f)) {
                        val w = this.size.width
                        val h = this.size.height
                        // Shield
                        val shield = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.18f, h * 0.18f)
                            lineTo(w * 0.82f, h * 0.18f)
                            lineTo(w * 0.82f, h * 0.52f)
                            cubicTo(w * 0.82f, h * 0.78f, w * 0.50f, h * 0.92f, w * 0.50f, h * 0.92f)
                            cubicTo(w * 0.50f, h * 0.92f, w * 0.18f, h * 0.78f, w * 0.18f, h * 0.52f)
                            close()
                        }
                        drawPath(shield, color = Color(0xFFFFB300))
                        // Sword in center
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.5f, h * 0.12f),
                            end = Offset(w * 0.5f, h * 0.82f),
                            strokeWidth = w * 0.08f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
            "minimotorways" -> {
                // Mini Motorways geometric roads on slate
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF263238)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(size * 0.70f)) {
                        val w = this.size.width
                        val h = this.size.height
                        val roadW = w * 0.13f
                        // Yellow curved highway
                        val yellowRoad = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.10f, h * 0.25f)
                            cubicTo(w * 0.50f, h * 0.25f, w * 0.50f, h * 0.85f, w * 0.90f, h * 0.85f)
                        }
                        drawPath(yellowRoad, color = Color(0xFFFFCA28), style = Stroke(width = roadW, cap = StrokeCap.Round))
                        // Red crossing highway
                        val redRoad = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w * 0.85f, h * 0.15f)
                            cubicTo(w * 0.40f, h * 0.50f, w * 0.60f, h * 0.75f, w * 0.15f, h * 0.80f)
                        }
                        drawPath(redRoad, color = Color(0xFFEF5350), style = Stroke(width = roadW, cap = StrokeCap.Round))
                        // Car dot
                        drawCircle(color = Color.White, radius = w * 0.06f, center = Offset(w * 0.50f, h * 0.55f))
                    }
                }
            }
            else -> {
                Text(
                    text = emoji,
                    fontSize = (size.value * 0.48f).sp
                )
            }
        }
    }
}
