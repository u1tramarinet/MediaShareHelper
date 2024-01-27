package com.u1tramarinet.mediasharehelper.ui

import android.net.Uri
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.u1tramarinet.mediasharehelper.R
import com.u1tramarinet.mediasharehelper.ui.screen.SettingsScreen
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
            TopScreen(
                onShare = onShare,
                onSettings = {
                    navController.navigate(MediaShareHelperRoute.SETTINGS.route())
                },
            )
        }
        composable(MediaShareHelperRoute.SETTINGS.route()) {
            SettingsScreen(
                onNavigateUp = { navController.navigateUp() },
                onHistories = {},
                onArtists = {},
                onTags = {},
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaShareHelperTopAppBar(
    title: String,
    onNavigateUp: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onNavigateUp != null) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                    )
                }
            }
        },
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaShareHelperLargeTopAppBar(
    title: String,
    onNavigateUp: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    LargeTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onNavigateUp != null) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                    )
                }
            }
        },
        actions = actions,
    )
}

object MediaShareHelperRoute {
    val TOP = RouteDef("top")
    val SETTINGS = RouteDef("settings")
    val ARTISTS = RouteDef("artists")
    val TAGS = RouteDef("tags")
    val HISTORIES = RouteDef("histories")
}

open class RouteDef(private val subdirectory: String) {
    fun route(): String {
        return subdirectory
    }
}