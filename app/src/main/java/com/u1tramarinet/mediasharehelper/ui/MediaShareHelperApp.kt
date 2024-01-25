package com.u1tramarinet.mediasharehelper.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.u1tramarinet.mediasharehelper.ui.screen.TopScreen

@Composable
fun MediaShareHelperApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MediaShareHelperRoute.TOP.route(),
    onShare: (text: String?, imageUri: Uri?) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(MediaShareHelperRoute.TOP.route()) {
            TopScreen(onShare = onShare)
        }
    }
}

object MediaShareHelperRoute {
    val TOP = RouteDef("top")
    val ARTIST_LIST = RouteDef("artistList")
    val TAG_LIST = RouteDef("tagList")
    val HISTORY_LIST = RouteDef("historyList")
}

open class RouteDef(private val subdirectory: String) {
    fun route(): String {
        return subdirectory
    }
}