package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.AppStoreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("App Store", appName)
  }

  @Test
  fun `verify catalog contains popular apps from reference screenshot`() {
    val repository = AppStoreRepository()
    val apps = repository.sampleApps
    val appIds = apps.map { it.id }

    assertTrue("Should contain MyFitnessPal", appIds.contains("myfitnesspal"))
    assertTrue("Should contain Paper by WeTransfer", appIds.contains("paper"))
    assertTrue("Should contain Zen", appIds.contains("zen"))
    assertTrue("Should contain TripIt", appIds.contains("tripit"))
    assertTrue("Should contain Heart Rate monitor", appIds.contains("heartrate"))
    assertTrue("Should contain Spotify", appIds.contains("spotify"))
  }

  @Test
  fun `verify search filtering`() {
    val repository = AppStoreRepository()
    val results = repository.filterApps("Fitness")
    assertTrue("Should find fitness apps", results.isNotEmpty())
    assertNotNull(results.find { it.name.contains("Fitness", ignoreCase = true) })
  }
}
