package com.example.data.model

enum class AppSection {
    TODAY, GAMES, APPS, ARCADE, SEARCH
}

enum class AppCategory(val displayName: String) {
    GAMES("Games"),
    APPS("Apps"),
    ARCADE("Arcade"),
    ACTION("Action"),
    SOCIAL("Social Networking"),
    ENTERTAINMENT("Entertainment"),
    MUSIC("Music"),
    PRODUCTIVITY("Productivity"),
    PHOTO_VIDEO("Photo & Video"),
    EDUCATION("Education"),
    HEALTH_FITNESS("Health & Fitness"),
    UTILITIES("Utilities")
}

data class CustomerReview(
    val id: String,
    val userName: String,
    val rating: Int,
    val date: String,
    val title: String,
    val body: String
)

data class ScreenshotModel(
    val title: String,
    val subtitle: String,
    val bgGradient: List<Long>,
    val accentIcon: String
)

data class TodayStory(
    val id: String,
    val tag: String,
    val title: String,
    val subtitle: String,
    val appId: String,
    val bgGradient: List<Long>,
    val storyText: String,
    val quote: String? = null
)

data class FeaturedBanner(
    val id: String,
    val tag: String,
    val title: String,
    val subtitle: String,
    val appId: String,
    val bannerBgColor: Long = 0xFF007AFF,
    val bannerIllustrationKey: String = "tripit"
)

data class StoreApp(
    val id: String,
    val name: String,
    val developer: String,
    val category: AppCategory,
    val genre: String,
    val rating: Float,
    val ratingCount: String,
    val ageRating: String,
    val chartRank: String?,
    val iconGradientColors: List<Long>,
    val iconEmoji: String,
    val description: String,
    val whatsNew: String,
    val version: String,
    val size: String,
    val inAppPurchases: Boolean = true,
    val isArcade: Boolean = false,
    val price: String = "Free",
    val packageName: String,
    val webFallbackUrl: String,
    val screenshots: List<ScreenshotModel>,
    val reviews: List<CustomerReview>,
    val heroStory: TodayStory? = null,
    val customIconType: String? = null
)

enum class DownloadStatus {
    GET,
    DOWNLOADING,
    INSTALLED
}

data class AppInstallState(
    val status: DownloadStatus = DownloadStatus.GET,
    val progress: Float = 0f,
    val downloadedText: String = ""
)
