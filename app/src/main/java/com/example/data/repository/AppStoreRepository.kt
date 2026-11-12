package com.example.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.data.model.AppCategory
import com.example.data.model.AppInstallState
import com.example.data.model.CustomerReview
import com.example.data.model.DownloadStatus
import com.example.data.model.FeaturedBanner
import com.example.data.model.ScreenshotModel
import com.example.data.model.StoreApp
import com.example.data.model.TodayStory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppStoreRepository {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _installStates = MutableStateFlow<Map<String, AppInstallState>>(emptyMap())
    val installStates: StateFlow<Map<String, AppInstallState>> = _installStates.asStateFlow()

    private val _purchasedAppIds = MutableStateFlow<Set<String>>(setOf("spotify", "myfitnesspal"))
    val purchasedAppIds: StateFlow<Set<String>> = _purchasedAppIds.asStateFlow()

    init {
        // Preset installed state for instant testing
        _installStates.value = mapOf(
            "spotify" to AppInstallState(DownloadStatus.INSTALLED, 1f, "Installed"),
            "myfitnesspal" to AppInstallState(DownloadStatus.INSTALLED, 1f, "Installed")
        )
    }

    val featuredAppBanners: List<FeaturedBanner> = listOf(
        FeaturedBanner(
            id = "banner_tripit",
            tag = "NOW WITH SIRI",
            title = "TripIt: Travel Planner",
            subtitle = "Quickly get flight info with Siri",
            appId = "tripit",
            bannerBgColor = 0xFF007AFF,
            bannerIllustrationKey = "tripit"
        ),
        FeaturedBanner(
            id = "banner_fitness",
            tag = "FITNESS & HEART BEAT",
            title = "MyFitnessPal & Cardio Pulse",
            subtitle = "Timer recording, heart beat check & mobile control",
            appId = "myfitnesspal",
            bannerBgColor = 0xFF1976D2,
            bannerIllustrationKey = "fitness"
        ),
        FeaturedBanner(
            id = "banner_paper",
            tag = "CREATIVE ESSENTIAL",
            title = "Paper by WeTransfer",
            subtitle = "Sketch with confidence",
            appId = "paper",
            bannerBgColor = 0xFFFFA000,
            bannerIllustrationKey = "paper"
        )
    )

    val sampleApps: List<StoreApp> = listOf(
        // Exact screenshot apps
        StoreApp(
            id = "myfitnesspal",
            name = "MyFitnessPal",
            developer = "MyFitnessPal, Inc.",
            category = AppCategory.HEALTH_FITNESS,
            genre = "Health & Fitness",
            rating = 4.7f,
            ratingCount = "1.8M Ratings",
            ageRating = "4+",
            chartRank = "#1 in Health & Fitness",
            iconGradientColors = listOf(0xFF0066CC, 0xFF0052A3),
            iconEmoji = "🏃",
            description = "Track progress toward your nutrition, fitness, workout, and weight loss goals with MyFitnessPal. This all-in-one food tracker, calorie counter, macro tracker, and fitness planner is like having a nutrition coach, meal planner, workout timer, and food diary with you at all times.",
            whatsNew = "Version 24.18 - Heart rate sync with Apple Watch & Health, timer recording for high intensity interval training (HIIT), and audio workout cues with music integration.",
            version = "24.18.2",
            size = "92 MB",
            inAppPurchases = true,
            packageName = "com.myfitnesspal.android",
            webFallbackUrl = "https://www.myfitnesspal.com",
            customIconType = "myfitnesspal",
            screenshots = listOf(
                ScreenshotModel("Calorie & Macro Tracker", "Log over 14 million foods instantly", listOf(0xFF0066CC, 0xFF003366), "🥗"),
                ScreenshotModel("Workout Timer & Recording", "Log reps, cardio and fitness heart beat", listOf(0xFF0288D1, 0xFF01579B), "⏱️"),
                ScreenshotModel("Fitness Goals & Control", "Track progress with adaptive charts", listOf(0xFF0097A7, 0xFF006064), "🎯")
            ),
            reviews = listOf(
                CustomerReview("mfp1", "RunnerGirl99", 5, "Yesterday", "Lost 15 lbs with ease", "The barcode scanner and fitness timer recording keep me consistent every single day!"),
                CustomerReview("mfp2", "CardioPro", 5, "3 days ago", "Best fitness and meal tracker", "Simple, elegant interface and pairs nicely with my heart rate monitor.")
            ),
            heroStory = TodayStory(
                id = "story_mfp",
                tag = "FITNESS ESSENTIAL",
                title = "Reach Your Fitness Goals in 2024",
                subtitle = "How MyFitnessPal helps you balance workouts, nutrition, and heart health.",
                appId = "myfitnesspal",
                bgGradient = listOf(0xFF0052D4, 0xFF4364F7, 0xFF6FB1FC),
                storyText = "Staying fit requires consistency, clarity, and rhythm. MyFitnessPal combines intelligent food logging with workout interval timers and cardio recording so you can achieve peak fitness safely.",
                quote = "Track your food, measure your heart rate, and celebrate every goal."
            )
        ),
        StoreApp(
            id = "paper",
            name = "Paper by WeTransfer",
            developer = "WeTransfer",
            category = AppCategory.PRODUCTIVITY,
            genre = "Productivity",
            rating = 4.6f,
            ratingCount = "320K Ratings",
            ageRating = "4+",
            chartRank = "#3 in Productivity",
            iconGradientColors = listOf(0xFFFFFFFF, 0xFFF5F5F7),
            iconEmoji = "🎨",
            description = "Paper is the immersive sketching app used by over 30 million people worldwide. Capture ideas anywhere with beautifully tuned sketch, draw, diagram, color, and write tools.",
            whatsNew = "Version 6.2 - Added fresh watercolor blending brushes, unlimited canvas zoom, and instant iCloud sync.",
            version = "6.2.0",
            size = "118 MB",
            inAppPurchases = true,
            packageName = "com.wetransfer.paper",
            webFallbackUrl = "https://paper.bywetransfer.com",
            customIconType = "paper",
            screenshots = listOf(
                ScreenshotModel("Sketch With Confidence", "Expressive brushstrokes and precision inks", listOf(0xFFFFA000, 0xFFFF6F00), "🖌️"),
                ScreenshotModel("Color & Diagram", "Effortless color palettes and 3D perspectives", listOf(0xFFE91E63, 0xFFC2185B), "📐")
            ),
            reviews = listOf(
                CustomerReview("p1", "ArchitectAlex", 5, "Nov 10", "The purest digital sketchbook", "Feels as natural as paper and pencil on an iPad or Android tablet.")
            )
        ),
        StoreApp(
            id = "zen",
            name = "Zen: Meditation & Sleep",
            developer = "Move ideais",
            category = AppCategory.HEALTH_FITNESS,
            genre = "Health & Fitness",
            rating = 4.8f,
            ratingCount = "540K Ratings",
            ageRating = "4+",
            chartRank = "#2 in Health & Fitness",
            iconGradientColors = listOf(0xFF00B4D8, 0xFF0077B6),
            iconEmoji = "🧘",
            description = "Looking to sleep better, lower stress, reduce anxiety, and feel happier? Zen offers guided meditations, relaxing binaural beats, sleep stories, breathing exercises, and soothing soundscapes.",
            whatsNew = "Version 4.5 - Sleep timer recording with gentle fade out, heart coherence breathing, and new nature white noise loops.",
            version = "4.5.1",
            size = "86 MB",
            inAppPurchases = true,
            packageName = "com.zenapp.meditation",
            webFallbackUrl = "https://zenapp.me",
            customIconType = "zen",
            screenshots = listOf(
                ScreenshotModel("Deep Sleep Stories", "Fall asleep within 10 minutes", listOf(0xFF03045E, 0xFF023E8A), "🌙"),
                ScreenshotModel("Breathing & Heart Rate", "Regulate heartbeat and lower cortisol", listOf(0xFF0077B6, 0xFF0096C7), "🌊")
            ),
            reviews = listOf(
                CustomerReview("z1", "PeacefulMind", 5, "Nov 08", "Cured my insomnia", "The binaural soundscapes with the gentle sleep timer put me right to sleep.")
            )
        ),
        StoreApp(
            id = "tripit",
            name = "TripIt: Travel Planner",
            developer = "TripIt, Inc.",
            category = AppCategory.APPS,
            genre = "Travel",
            rating = 4.8f,
            ratingCount = "780K Ratings",
            ageRating = "4+",
            chartRank = "#1 in Travel",
            iconGradientColors = listOf(0xFF0288D1, 0xFF01579B),
            iconEmoji = "✈️",
            description = "Unlike other travel apps, TripIt can organize your travel plans no matter where you book. Simply forward your confirmation emails to plans@tripit.com and in seconds, TripIt creates a master itinerary.",
            whatsNew = "Version 16.4 - Siri Shortcuts integration, terminal maps, flight delay push notifications, and luggage tracker sync.",
            version = "16.4.0",
            size = "68 MB",
            inAppPurchases = true,
            packageName = "com.tripit",
            webFallbackUrl = "https://www.tripit.com",
            customIconType = "tripit",
            screenshots = listOf(
                ScreenshotModel("Master Itinerary", "All flights, hotels, and rentals in one place", listOf(0xFF0288D1, 0xFF01579B), "📋"),
                ScreenshotModel("Now with Siri", "Ask Siri: What is my gate number?", listOf(0xFF00BCD4, 0xFF0097A7), "🎙️")
            ),
            reviews = listOf(
                CustomerReview("t1", "FrequentFlyer", 5, "Nov 06", "Cannot travel without this", "Saves me from digging through hundreds of airline emails.")
            )
        ),
        StoreApp(
            id = "heartrate",
            name = "Heart Rate & Beat Pulse",
            developer = "Cardio Health Lab",
            category = AppCategory.HEALTH_FITNESS,
            genre = "Health & Fitness",
            rating = 4.9f,
            ratingCount = "620K Ratings",
            ageRating = "4+",
            chartRank = "#4 in Health & Fitness",
            iconGradientColors = listOf(0xFFFF3366, 0xFFFF0844),
            iconEmoji = "❤️",
            description = "Check your heartbeat and measure your pulse instantly using your phone camera and mobile sensors. Accurate BPM recording, fitness cardio zones, workout timers, and detailed health graphs.",
            whatsNew = "Version 3.8 - Added workout timer recording, high/low heart beat alerts, mobile control dashboard, and fitness cool-down metrics.",
            version = "3.8.2",
            size = "45 MB",
            inAppPurchases = true,
            packageName = "com.cardiotech.heartrate",
            webFallbackUrl = "https://heartrate.app",
            customIconType = "heartrate",
            screenshots = listOf(
                ScreenshotModel("Instant Heart Beat Check", "Measure pulse BPM with camera in 10s", listOf(0xFFFF0844, 0xFFFFB199), "💓"),
                ScreenshotModel("Fitness Zones & Timer", "Optimize cardio burn and cool-down", listOf(0xFF990033, 0xFF660022), "⏱️")
            ),
            reviews = listOf(
                CustomerReview("hr1", "GymAthlete", 5, "Yesterday", "Spot-on BPM accuracy", "Matches my chest strap monitor perfectly. Great for post-run recovery checks.")
            )
        ),
        StoreApp(
            id = "niketraining",
            name = "Nike Training Club",
            developer = "Nike, Inc.",
            category = AppCategory.HEALTH_FITNESS,
            genre = "Health & Fitness",
            rating = 4.9f,
            ratingCount = "1.2M Ratings",
            ageRating = "4+",
            chartRank = "#3 in Health & Fitness",
            iconGradientColors = listOf(0xFF111111, 0xFF222222),
            iconEmoji = "⚡",
            description = "Reach your fitness goals with Nike Training Club (NTC). From home workouts to gym strength training, yoga, and HIIT, NTC provides professional workouts with built-in timers, music playlists, and goal tracking.",
            whatsNew = "Version 7.12 - Fitness cool-down guidance, mobile audio coach, custom interval timer, and heart rate monitor sync.",
            version = "7.12.0",
            size = "135 MB",
            inAppPurchases = false,
            packageName = "com.nike.ntc",
            webFallbackUrl = "https://www.nike.com/ntc-app",
            customIconType = "niketraining",
            screenshots = listOf(
                ScreenshotModel("200+ Free Workouts", "Yoga, strength, HIIT & mobility", listOf(0xFF111111, 0xFF333333), "🏋️"),
                ScreenshotModel("Audio Coach & Music", "Stay pumped with fitness beats", listOf(0xFFE50914, 0xFFB81D24), "🎵")
            ),
            reviews = listOf(
                CustomerReview("ntc1", "FitLifeSam", 5, "Nov 11", "Completely free & elite quality", "The workout timers and trainer videos are unmatched.")
            )
        ),
        StoreApp(
            id = "spotify",
            name = "Spotify: Fitness & Beats",
            developer = "Spotify AB",
            category = AppCategory.APPS,
            genre = "Music & Audio",
            rating = 4.8f,
            ratingCount = "31M Ratings",
            ageRating = "12+",
            chartRank = "#1 in Music",
            iconGradientColors = listOf(0xFF1DB954, 0xFF191414),
            iconEmoji = "🎵",
            description = "Power your workout with upbeat running music, cardio playlists, fitness motivation podcasts, and relaxing cool-down tracks on Spotify. Match your running cadence to song BPM!",
            whatsNew = "Version 8.9.84 - Workout mode with BPM cadence matching, timer recording integration, and offline fitness tracks.",
            version = "8.9.84",
            size = "98 MB",
            inAppPurchases = true,
            packageName = "com.spotify.music",
            webFallbackUrl = "https://open.spotify.com",
            customIconType = "spotify",
            screenshots = listOf(
                ScreenshotModel("Fitness & Workout Music", "Running BPM matching & gym motivation", listOf(0xFF1DB954, 0xFF121212), "🎧"),
                ScreenshotModel("Cardio Cool-down", "Ambient recovery soundscapes", listOf(0xFF0072B5, 0xFF000000), "🧘")
            ),
            reviews = listOf(
                CustomerReview("s1", "MarathonRunner", 5, "Nov 11", "Best running playlists", "The BPM cadence playlists helped me crush my 10k personal record.")
            )
        ),
        // Games & Originals
        StoreApp(
            id = "subwaysurf",
            name = "Subway Surfers",
            developer = "SYBO Games",
            category = AppCategory.GAMES,
            genre = "Endless Runner",
            rating = 4.7f,
            ratingCount = "38M Ratings",
            ageRating = "9+",
            chartRank = "#1 in Casual Games",
            iconGradientColors = listOf(0xFFF12711, 0xFFF5AF19),
            iconEmoji = "🛹",
            description = "Dash as fast as you can! Dodge oncoming trains! Help Jake, Tricky & Fresh escape from the grumpy Inspector and his dog.",
            whatsNew = "Version 3.32 - The World Tour visits Tokyo! Surf through neon cherry blossom gardens.",
            version = "3.32.0",
            size = "172 MB",
            inAppPurchases = true,
            packageName = "com.kiloo.subwaysurf",
            webFallbackUrl = "https://subwaysurfers.com",
            screenshots = listOf(
                ScreenshotModel("Tokyo World Tour", "Dodge trains under neon billboards", listOf(0xFFF12711, 0xFFF5AF19), "🚄")
            ),
            reviews = listOf(
                CustomerReview("5", "SpeedyRunner", 5, "Yesterday", "Timeless classic", "Over 10 years and still pure fun.")
            )
        ),
        StoreApp(
            id = "genshin",
            name = "Genshin Impact",
            developer = "Cognosphere PTE. LTD.",
            category = AppCategory.GAMES,
            genre = "Action RPG",
            rating = 4.8f,
            ratingCount = "4.2M Ratings",
            ageRating = "12+",
            chartRank = "#1 in Role Playing",
            iconGradientColors = listOf(0xFF2C3E50, 0xFF4CA1AF),
            iconEmoji = "⚔️",
            description = "Step into Teyvat, a vast fantasy world teeming with life and elemental energy.",
            whatsNew = "Version 5.2 - New Natlan expansion regions and 5-star character Chasca.",
            version = "5.2.0",
            size = "2.8 GB",
            inAppPurchases = true,
            packageName = "com.miHoYo.GenshinImpact",
            webFallbackUrl = "https://genshin.hoyoverse.com",
            screenshots = listOf(
                ScreenshotModel("Vast Open World", "Explore the realm of Teyvat", listOf(0xFF1F4068, 0xFF162447), "🏔️")
            ),
            reviews = listOf(
                CustomerReview("1", "AetherTraveller", 5, "Yesterday", "Console Quality", "Unbelievable graphics.")
            )
        ),
        StoreApp(
            id = "chatgpt",
            name = "ChatGPT",
            developer = "OpenAI",
            category = AppCategory.APPS,
            genre = "Productivity & AI",
            rating = 4.9f,
            ratingCount = "3.9M Ratings",
            ageRating = "12+",
            chartRank = "#1 in Productivity",
            iconGradientColors = listOf(0xFF10A37F, 0xFF007A5A),
            iconEmoji = "🧠",
            description = "Get instant answers, tailored advice, and creative inspiration with ChatGPT.",
            whatsNew = "Version 1.2024 - Advanced Voice Mode upgrades and vision photo analysis.",
            version = "1.2024.318",
            size = "84.5 MB",
            inAppPurchases = true,
            packageName = "com.openai.chatgpt",
            webFallbackUrl = "https://chatgpt.com",
            screenshots = listOf(
                ScreenshotModel("Instant Answers", "Code, writing, and advice", listOf(0xFF10A37F, 0xFF1A1A1A), "💬")
            ),
            reviews = listOf(
                CustomerReview("3", "DevGuru", 5, "Nov 10", "Indispensable", "Saves hours every week.")
            )
        ),
        StoreApp(
            id = "sasquatch",
            name = "Sneaky Sasquatch",
            developer = "RAC7 Games",
            category = AppCategory.ARCADE,
            genre = "Adventure & Comedy",
            rating = 4.9f,
            ratingCount = "380K Ratings",
            ageRating = "4+",
            chartRank = "#1 in Apple Arcade",
            iconGradientColors = listOf(0xFF4A772F, 0xFF2B4C1A),
            iconEmoji = "🏕️",
            description = "Live the life of a Sasquatch and sneak around campsites, golf courses, and town halls.",
            whatsNew = "Version 2.1 - The Mayor Update! Run for mayor and build public parks.",
            version = "2.1.0",
            size = "340 MB",
            inAppPurchases = false,
            isArcade = true,
            packageName = "com.rac7.sneakysasquatch",
            webFallbackUrl = "https://rac7.com",
            screenshots = listOf(
                ScreenshotModel("Wilderness Sandbox", "Sneak, disguise, and explore", listOf(0xFF4A772F, 0xFF2B4C1A), "🌭")
            ),
            reviews = listOf(
                CustomerReview("13", "CampFan", 5, "Yesterday", "Charming", "Pure fun for all ages.")
            )
        ),
        StoreApp(
            id = "instagram",
            name = "Instagram",
            developer = "Meta Platforms, Inc.",
            category = AppCategory.SOCIAL,
            genre = "Photo & Video",
            rating = 4.7f,
            ratingCount = "24M Ratings",
            ageRating = "12+",
            chartRank = "#1 in Photo & Video",
            iconGradientColors = listOf(0xFF833AB4, 0xFFFD1D1D, 0xFFFCB045),
            iconEmoji = "📸",
            description = "Bringing you closer to the people and things you love. Share photos, reels, and stories with friends.",
            whatsNew = "Version 355.0 - Reel audio beat sync and broadcast channels.",
            version = "355.0.0",
            size = "132 MB",
            inAppPurchases = true,
            packageName = "com.instagram.android",
            webFallbackUrl = "https://instagram.com",
            screenshots = listOf(
                ScreenshotModel("Share Moments", "Stories, Reels & Messaging", listOf(0xFF833AB4, 0xFFFD1D1D), "✨")
            ),
            reviews = listOf(
                CustomerReview("ig1", "PhotoFan", 5, "2 days ago", "Essential", "Great photo filters and reels.")
            )
        ),
        StoreApp(
            id = "tiktok",
            name = "TikTok",
            developer = "TikTok Pte. Ltd.",
            category = AppCategory.ENTERTAINMENT,
            genre = "Entertainment",
            rating = 4.8f,
            ratingCount = "52M Ratings",
            ageRating = "12+",
            chartRank = "#1 in Entertainment",
            iconGradientColors = listOf(0xFF000000, 0xFF00F2FE, 0xFF4FACFE),
            iconEmoji = "🎵",
            description = "Short-form mobile video with endless entertainment, creators, comedy, and music.",
            whatsNew = "Version 37.1 - High-res photo carousels and enhanced video editor.",
            version = "37.1.0",
            size = "142 MB",
            inAppPurchases = true,
            packageName = "com.zhiliaoapp.musically",
            webFallbackUrl = "https://www.tiktok.com",
            screenshots = listOf(
                ScreenshotModel("Endless Feed", "Personalized video stream", listOf(0xFF000000, 0xFF232526), "📱")
            ),
            reviews = listOf(
                CustomerReview("tt1", "Streamer", 5, "Nov 12", "Addictive", "Best short video platform.")
            )
        ),
        StoreApp(
            id = "whatsapp",
            name = "WhatsApp Messenger",
            developer = "WhatsApp LLC",
            category = AppCategory.SOCIAL,
            genre = "Social Networking",
            rating = 4.7f,
            ratingCount = "175M Ratings",
            ageRating = "4+",
            chartRank = "#1 in Social Networking",
            iconGradientColors = listOf(0xFF25D366, 0xFF128C7E),
            iconEmoji = "💬",
            description = "Simple, reliable, private messaging and calling worldwide with end-to-end encryption.",
            whatsNew = "Version 2.24 - Passkey support and HD media sharing.",
            version = "2.24.23",
            size = "64 MB",
            inAppPurchases = false,
            packageName = "com.whatsapp",
            webFallbackUrl = "https://web.whatsapp.com",
            screenshots = listOf(
                ScreenshotModel("End-to-End Encrypted", "Private messages & crystal clear calls", listOf(0xFF128C7E, 0xFF075E54), "🔒")
            ),
            reviews = listOf(
                CustomerReview("wa1", "Chatter", 5, "Nov 09", "Flawless", "Fast and secure everywhere.")
            )
        ),
        StoreApp(
            id = "roblox",
            name = "Roblox",
            developer = "Roblox Corporation",
            category = AppCategory.GAMES,
            genre = "Adventure & Metaverse",
            rating = 4.6f,
            ratingCount = "18M Ratings",
            ageRating = "12+",
            chartRank = "#1 in Adventure",
            iconGradientColors = listOf(0xFF000000, 0xFF434343),
            iconEmoji = "🧱",
            description = "The ultimate virtual universe that lets you create, share experiences, and be anything you can imagine.",
            whatsNew = "Version 2.650 - Improved avatar physics and ultra-smooth 120 FPS.",
            version = "2.650.0",
            size = "210 MB",
            inAppPurchases = true,
            packageName = "com.roblox.client",
            webFallbackUrl = "https://www.roblox.com",
            screenshots = listOf(
                ScreenshotModel("Infinite Experiences", "Millions of games to explore", listOf(0xFF232526, 0xFF414345), "🌌")
            ),
            reviews = listOf(
                CustomerReview("rb1", "BloxFan", 5, "Nov 09", "Endless fun", "Always something new to play.")
            )
        ),
        StoreApp(
            id = "clashroyale",
            name = "Clash Royale",
            developer = "Supercell",
            category = AppCategory.GAMES,
            genre = "Strategy",
            rating = 4.7f,
            ratingCount = "34M Ratings",
            ageRating = "9+",
            chartRank = "#2 in Strategy",
            iconGradientColors = listOf(0xFF0052D4, 0xFF4364F7, 0xFF6FB1FC),
            iconEmoji = "👑",
            description = "Fast real-time PvP battle game starring your favorite Clash characters. Build your Battle Deck and outsmart rivals.",
            whatsNew = "Version 7.256 - Goblin Queen Tower Troop and new Evolution cards.",
            version = "7.256.0",
            size = "290 MB",
            inAppPurchases = true,
            packageName = "com.supercell.clashroyale",
            webFallbackUrl = "https://clashroyale.com",
            screenshots = listOf(
                ScreenshotModel("Real-Time Duels", "3-minute intense tactical combat", listOf(0xFF0052D4, 0xFF4364F7), "⚔️")
            ),
            reviews = listOf(
                CustomerReview("cr1", "Clasher", 5, "Nov 07", "Pure strategy", "Intense PvP duels.")
            )
        ),
        StoreApp(
            id = "duolingo",
            name = "Duolingo: Language Lessons",
            developer = "Duolingo",
            category = AppCategory.EDUCATION,
            genre = "Education",
            rating = 4.8f,
            ratingCount = "19M Ratings",
            ageRating = "4+",
            chartRank = "#1 in Education",
            iconGradientColors = listOf(0xFF58CC02, 0xFF2B9E00),
            iconEmoji = "🦉",
            description = "Learn 40+ languages through bite-sized, gamified daily lessons. Practice speaking, reading, listening, and writing.",
            whatsNew = "Version 7.15 - Math and Music interactive courses.",
            version = "7.15.2",
            size = "76 MB",
            inAppPurchases = true,
            packageName = "com.duolingo",
            webFallbackUrl = "https://www.duolingo.com",
            screenshots = listOf(
                ScreenshotModel("Bite-Sized Lessons", "5 minutes a day for fluency", listOf(0xFF58CC02, 0xFF1E824C), "🎯")
            ),
            reviews = listOf(
                CustomerReview("dl1", "Learner", 5, "Nov 05", "Great motivation", "Keeps me practicing every day.")
            )
        ),
        StoreApp(
            id = "oceanhorn2",
            name = "Oceanhorn 2: Knights",
            developer = "Cornfox & Brothers Ltd.",
            category = AppCategory.ARCADE,
            genre = "Action Adventure",
            rating = 4.8f,
            ratingCount = "150K Ratings",
            ageRating = "9+",
            chartRank = "#2 in Apple Arcade",
            iconGradientColors = listOf(0xFF0052D4, 0xFF4364F7),
            iconEmoji = "🛡️",
            description = "Embark on an epic quest through the vast realm of Gaia with console-quality visuals and puzzles.",
            whatsNew = "Golden Edition with 60 FPS graphics and extra dungeons.",
            version = "3.0.1",
            size = "2.4 GB",
            inAppPurchases = false,
            isArcade = true,
            packageName = "com.cornfox.oceanhorn2",
            webFallbackUrl = "https://oceanhorn.com",
            screenshots = listOf(
                ScreenshotModel("Epic Quest", "Explore Gaia with companions", listOf(0xFF1CB5E0, 0xFF000046), "🗺️")
            ),
            reviews = listOf(
                CustomerReview("oh1", "Gamer", 5, "Nov 03", "Stunning", "Zelda-like adventure on mobile.")
            )
        ),
        StoreApp(
            id = "minimotorways",
            name = "Mini Motorways",
            developer = "Dinosaur Polo Club",
            category = AppCategory.ARCADE,
            genre = "Strategy & Puzzle",
            rating = 4.9f,
            ratingCount = "210K Ratings",
            ageRating = "4+",
            chartRank = "#3 in Apple Arcade",
            iconGradientColors = listOf(0xFFE94057, 0xFF8A2387),
            iconEmoji = "🚗",
            description = "Draw roads that drive a growing city. Keep traffic flowing and avoid bottlenecks in bustling metropolises.",
            whatsNew = "Version 1.8 - The Iceland Update with geothermal bridges.",
            version = "1.8.0",
            size = "260 MB",
            inAppPurchases = false,
            isArcade = true,
            packageName = "com.dinopoloclub.minimotorways",
            webFallbackUrl = "https://dinopoloclub.com",
            screenshots = listOf(
                ScreenshotModel("Design Roads", "Keep city traffic moving", listOf(0xFF283048, 0xFF859398), "🛣️")
            ),
            reviews = listOf(
                CustomerReview("mm1", "Planner", 5, "Nov 01", "Satisfying", "So relaxing and clever.")
            )
        )
    )

    fun getAppById(id: String): StoreApp? {
        return sampleApps.find { it.id == id }
    }

    fun startDownload(appId: String, onProgressUpdate: (Float) -> Unit = {}, onFinish: () -> Unit = {}) {
        val app = getAppById(appId) ?: return
        scope.launch {
            _installStates.value = _installStates.value.toMutableMap().apply {
                put(appId, AppInstallState(DownloadStatus.DOWNLOADING, 0.05f, "Connecting..."))
            }

            val steps = 18
            for (i in 1..steps) {
                delay(100)
                val progress = i / steps.toFloat()
                val currentMb = (progress * (app.size.replace(" MB", "").replace(" GB", "").toFloatOrNull() ?: 100f)).toInt()
                _installStates.value = _installStates.value.toMutableMap().apply {
                    put(appId, AppInstallState(DownloadStatus.DOWNLOADING, progress, "$currentMb MB / ${app.size}"))
                }
                onProgressUpdate(progress)
            }

            _installStates.value = _installStates.value.toMutableMap().apply {
                put(appId, AppInstallState(DownloadStatus.INSTALLED, 1f, "Installed"))
            }
            _purchasedAppIds.value = _purchasedAppIds.value + appId
            onFinish()
        }
    }

    fun isAppInstalledOnDevice(context: Context, packageName: String): Boolean {
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            intent != null
        } catch (e: Exception) {
            false
        }
    }

    fun launchRealAppOrPlayStore(context: Context, app: StoreApp): Boolean {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(app.packageName)
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
            return true
        }

        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${app.packageName}")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(marketIntent)
            return true
        } catch (e: Exception) {
            try {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${app.packageName}")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(webIntent)
                return true
            } catch (ex: Exception) {
                return false
            }
        }
    }

    fun filterApps(query: String, category: AppCategory? = null): List<StoreApp> {
        return sampleApps.filter { app ->
            val matchesQuery = query.isBlank() ||
                    app.name.contains(query, ignoreCase = true) ||
                    app.developer.contains(query, ignoreCase = true) ||
                    app.genre.contains(query, ignoreCase = true) ||
                    app.description.contains(query, ignoreCase = true)

            val matchesCategory = category == null || app.category == category
            matchesQuery && matchesCategory
        }
    }

    fun updateAllApps(onFinish: () -> Unit = {}) {
        scope.launch {
            sampleApps.forEach { app ->
                startDownload(app.id)
            }
            delay(2200)
            onFinish()
        }
    }
}
