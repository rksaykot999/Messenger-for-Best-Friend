package com.rksaykot.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rksaykot.myapplication.ui.theme.*

// ============ ONLINE STATUS COMPONENTS ============

@Composable
fun UserStatusBadge(
    status: UserStatus,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = status.backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(status.color)
            )
            Text(
                text = status.label,
                style = MaterialTheme.typography.labelSmall,
                color = status.color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun OnlineStatusDot(
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    size: Int = 12
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(size / 2))
            .background(
                color = if (isOnline) Success else NeutralMedium
            )
            .border(
                width = 2.dp,
                color = White,
                shape = RoundedCornerShape(size / 2)
            )
    )
}

// User Status Type
sealed class UserStatus(
    val label: String,
    val color: androidx.compose.ui.graphics.Color,
    val backgroundColor: androidx.compose.ui.graphics.Color
) {
    object Online : UserStatus("Online", Success, Success.copy(alpha = 0.1f))
    object Away : UserStatus("Away", Warning, Warning.copy(alpha = 0.1f))
    object Offline : UserStatus("Offline", NeutralMedium, NeutralMedium.copy(alpha = 0.1f))
    object DoNotDisturb : UserStatus("DND", Error, Error.copy(alpha = 0.1f))
}

// ============ PRESENCE INDICATORS ============

@Composable
fun PresenceIndicator(
    name: String,
    status: String,
    lastSeen: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = NeutralDark
            )
        }

        Text(
            text = when {
                status == "online" -> "Online now"
                status == "typing" -> "typing..."
                lastSeen.isNotEmpty() -> "Last seen $lastSeen"
                else -> "Offline"
            },
            style = MaterialTheme.typography.labelSmall,
            color = NeutralMedium,
            fontWeight = if (status == "typing") FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ============ TYPING INDICATOR COMPONENTS ============

@Composable
fun TypingIndicatorCard(
    userName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.sm)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            modifier = Modifier.padding(Spacing.md)
        ) {
            ModernAvatar(
                name = userName,
                imageUrl = null,
                size = 32
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.labelMedium,
                    color = NeutralDark,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .background(NeutralLighter, RoundedCornerShape(12.dp))
                        .padding(horizontal = Spacing.md, vertical = Spacing.sm)
                ) {
                    TypingIndicator()
                }
            }
        }
    }
}

// ============ STATUS HEADER ============

@Composable
fun ChatHeaderStatus(
    name: String,
    isOnline: Boolean,
    lastSeen: String = "",
    isTyping: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            color = NeutralDark,
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            OnlineStatusDot(
                isOnline = isOnline,
                size = 10
            )

            Text(
                text = when {
                    isTyping -> "typing..."
                    isOnline -> "Online"
                    lastSeen.isNotEmpty() -> "Last seen $lastSeen"
                    else -> "Offline"
                },
                style = MaterialTheme.typography.bodySmall,
                color = NeutralMedium
            )
        }
    }
}

// ============ ACTIVITY INDICATORS ============

@Composable
fun UserActivityCard(
    name: String,
    activity: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Info.copy(alpha = 0.1f), RoundedCornerShape(Dimensions.cornerRadius))
            .padding(Spacing.md)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Info,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelMedium,
                    color = Info,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = activity,
                    style = MaterialTheme.typography.labelSmall,
                    color = Info.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// ============ BORDER EXTENSION ============

fun androidx.compose.foundation.BorderStroke(
    width: androidx.compose.ui.unit.Dp,
    color: androidx.compose.ui.graphics.Color
): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(width, color)
}

// Extension for Box to add border
fun Modifier.border(
    width: androidx.compose.ui.unit.Dp,
    color: androidx.compose.ui.graphics.Color,
    shape: androidx.compose.ui.graphics.Shape
): Modifier {
    return this.border(
        width = width,
        color = color,
        shape = shape
    )
}
