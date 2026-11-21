package com.krishnalabs.roboticsai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnalabs.roboticsai.navigation.Screen
import com.krishnalabs.roboticsai.ui.theme.DarkOnPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurface
import com.krishnalabs.roboticsai.ui.theme.DarkOnSurfaceVariant
import com.krishnalabs.roboticsai.ui.theme.DarkOutlineVariant
import com.krishnalabs.roboticsai.ui.theme.DarkPrimary
import com.krishnalabs.roboticsai.ui.theme.DarkPrimaryContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurface
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainer
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerHigh
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLow
import com.krishnalabs.roboticsai.ui.theme.DarkSurfaceContainerLowest
import com.krishnalabs.roboticsai.ui.theme.SemanticSuccess

data class NavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun TabletScaffold(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    robotName: String = "Aria-01",
    companionMode: String = "Companion Mode",
    content: @Composable () -> Unit
) {
    val navItems = listOf(
        NavItem("Home", Screen.Home.route, Icons.Default.Home),
        NavItem("AI Assistant", Screen.Assistant.route, Icons.Default.Forum),
        NavItem("Robot Face", Screen.Face.route, Icons.Default.Mood),
        NavItem("Vision", Screen.Vision.route, Icons.Default.Visibility),
        NavItem("Controls", Screen.Controls.route, Icons.Default.Tune),
        NavItem("Automations", Screen.Automations.route, Icons.Default.Bolt),
        NavItem("Activity", Screen.Activity.route, Icons.Default.History),
        NavItem("Settings", Screen.Settings.route, Icons.Default.Settings)
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkSurfaceContainerLowest)
    ) {
        // Left Tablet Sidebar (220dp fixed landscape width)
        Column(
            modifier = Modifier
                .width(220.dp)
                .fillMaxHeight()
                .background(DarkSurfaceContainerLow)
                .padding(vertical = 16.dp, horizontal = 12.dp)
        ) {
            // App Header Branding
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "KL",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Krishna Labs",
                        color = DarkOnSurface,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Robotics AI",
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Links
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                navItems.forEach { item ->
                    val isSelected = currentRoute == item.route || 
                        (item.route == Screen.Settings.route && currentRoute.startsWith("settings_"))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isSelected) DarkPrimaryContainer.copy(alpha = 0.25f)
                                else Color.Transparent
                            )
                            .clickable { onNavigate(item.route) }
                            .padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected) DarkPrimary else DarkOnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.title,
                            color = if (isSelected) DarkPrimary else DarkOnSurface,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // Bottom Profile / Status
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkSurfaceContainerHigh)
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SemanticSuccess)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = robotName,
                            color = DarkOnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = companionMode,
                            color = DarkOnSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        VerticalDivider(
            color = DarkOutlineVariant.copy(alpha = 0.4f),
            thickness = 1.dp
        )

        // Main Content Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(DarkSurface)
        ) {
            content()
        }
    }
}
