package com.rksaykot.myapplication.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rksaykot.myapplication.ui.theme.Primary
import com.rksaykot.myapplication.ui.theme.Secondary
import com.rksaykot.myapplication.viewmodel.ChatViewModel

// Navigation Item Data Class
data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val badgeCount: Int = 0
)

// Modern Bottom Navigation Bar
@Composable
fun ModernBottomNavigationBar(
    navController: NavHostController,
    items: List<NavigationItem>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route?.substringBefore("/")

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Box {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (item.badgeCount > 0) {
                            Badge(
                                containerColor = Secondary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(
                                    text = if (item.badgeCount > 99) "99+" else item.badgeCount.toString(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    indicatorColor = Primary.copy(alpha = 0.1f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

// Navigation structure for the app
sealed class BottomNavRoute(val route: String, val label: String) {
    object Chats : BottomNavRoute("chats", "Chats")
    object Contacts : BottomNavRoute("contacts", "Contacts")
    object Calls : BottomNavRoute("calls", "Calls")
    object Settings : BottomNavRoute("settings", "Settings")
}

// Main app navigation with bottom bar
@Composable
fun AppNavigationWithBottomBar(
    viewModel: ChatViewModel,
    onChatClick: (String, String) -> Unit,
    onLogout: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val navigationItems = listOf(
        NavigationItem(
            route = BottomNavRoute.Chats.route,
            label = "Chats",
            icon = Icons.Outlined.Chat,
            selectedIcon = Icons.Filled.Chat,
            badgeCount = viewModel.unreadRooms.size
        ),
        NavigationItem(
            route = BottomNavRoute.Contacts.route,
            label = "Contacts",
            icon = Icons.Outlined.People,
            selectedIcon = Icons.Filled.People,
            badgeCount = 0
        ),
        NavigationItem(
            route = BottomNavRoute.Calls.route,
            label = "Calls",
            icon = Icons.Outlined.Call,
            selectedIcon = Icons.Filled.Call,
            badgeCount = 0
        ),
        NavigationItem(
            route = BottomNavRoute.Settings.route,
            label = "Settings",
            icon = Icons.Outlined.Settings,
            selectedIcon = Icons.Filled.Settings,
            badgeCount = 0
        )
    )

    Scaffold(
        bottomBar = {
            // Only show bottom nav on main screens
            if (navBackStackEntry.value?.destination?.route?.substringBefore("/") in 
                listOf(BottomNavRoute.Chats.route, BottomNavRoute.Contacts.route, 
                       BottomNavRoute.Calls.route, BottomNavRoute.Settings.route)
            ) {
                ModernBottomNavigationBar(
                    navController = navController,
                    items = navigationItems
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomNavRoute.Chats.route
            ) {
                composable(BottomNavRoute.Chats.route) {
                    // Chats screen content - placeholder for now
                    Box(modifier = Modifier.fillMaxSize())
                }

                composable(BottomNavRoute.Contacts.route) {
                    // Contacts screen content - placeholder for now
                    Box(modifier = Modifier.fillMaxSize())
                }

                composable(BottomNavRoute.Calls.route) {
                    // Calls screen content - placeholder for now
                    Box(modifier = Modifier.fillMaxSize())
                }

                composable(BottomNavRoute.Settings.route) {
                    // Settings screen content - placeholder for now
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
