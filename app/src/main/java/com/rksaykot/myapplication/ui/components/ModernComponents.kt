package com.rksaykot.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rksaykot.myapplication.ui.theme.*

// ============ MODERN BUTTON COMPONENTS ============

@Composable
fun ModernPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(Dimensions.buttonHeight)
            .fillMaxWidth(),
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = White,
            disabledContainerColor = NeutralMedium,
            disabledContentColor = White
        ),
        shape = RoundedCornerShape(Dimensions.cornerRadius)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = White,
                strokeWidth = 2.dp
            )
        } else {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimensions.iconSize)
                        .padding(end = Spacing.sm)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ModernSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(Dimensions.smallButtonHeight)
            .fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Primary,
            disabledContentColor = NeutralMedium
        ),
        border = ButtonDefaults.outlinedButtonBorder(enabled),
        shape = RoundedCornerShape(Dimensions.cornerRadius)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ============ MODERN INPUT FIELD ============

@Composable
fun ModernSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    icon: ImageVector = Icons.Default.Search
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = NeutralMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeutralMedium,
                modifier = Modifier.size(20.dp)
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,
            unfocusedContainerColor = NeutralLighter,
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = NeutralLighter,
            disabledIndicatorColor = NeutralLighter
        ),
        shape = RoundedCornerShape(Dimensions.cornerRadius),
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

// ============ MODERN CARD COMPONENTS ============

@Composable
fun ModernCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(Dimensions.cornerRadius))
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            ),
        shape = RoundedCornerShape(Dimensions.cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        content()
    }
}

@Composable
fun ModernContactCard(
    name: String,
    email: String,
    profileImageUrl: String?,
    isOnline: Boolean = false,
    unreadCount: Int = 0,
    onClick: () -> Unit
) {
    ModernCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.lg),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Profile Image with Online Status
            Box {
                if (profileImageUrl != null) {
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = name,
                        modifier = Modifier
                            .size(Dimensions.profileImageSize)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(Dimensions.profileImageSize)
                            .clip(CircleShape)
                            .background(Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name.firstOrNull()?.uppercase() ?: "U",
                            color = White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(Success)
                            .border(2.dp, White, CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }
            }

            // Contact Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NeutralDark
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodySmall,
                    color = NeutralMedium
                )
            }

            // Unread Badge
            if (unreadCount > 0) {
                Badge(
                    containerColor = Secondary,
                    contentColor = White,
                    modifier = Modifier.padding(start = Spacing.sm)
                ) {
                    Text(unreadCount.toString())
                }
            }
        }
    }
}

// ============ MODERN AVATAR ============

@Composable
fun ModernAvatar(
    name: String,
    imageUrl: String?,
    size: Int = 40,
    isOnline: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(size.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(size.dp)
                    .clip(CircleShape)
                    .background(Primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.firstOrNull()?.uppercase() ?: "U",
                    color = White,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (isOnline) {
            Box(
                modifier = Modifier
                    .size((size / 4).dp)
                    .clip(CircleShape)
                    .background(Success)
                    .border(2.dp, White, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

// ============ MODERN STATUS CHIP ============

@Composable
fun ModernStatusChip(
    text: String,
    status: ChipStatus = ChipStatus.DEFAULT,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        ChipStatus.SUCCESS -> Success.copy(alpha = 0.1f)
        ChipStatus.ERROR -> Error.copy(alpha = 0.1f)
        ChipStatus.WARNING -> Warning.copy(alpha = 0.1f)
        ChipStatus.INFO -> Info.copy(alpha = 0.1f)
        ChipStatus.DEFAULT -> NeutralLighter
    }

    val textColor = when (status) {
        ChipStatus.SUCCESS -> Success
        ChipStatus.ERROR -> Error
        ChipStatus.WARNING -> Warning
        ChipStatus.INFO -> Info
        ChipStatus.DEFAULT -> NeutralDark
    }

    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(Dimensions.cornerRadius))
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

enum class ChipStatus {
    DEFAULT, SUCCESS, ERROR, WARNING, INFO
}

// ============ MODERN EMPTY STATE ============

@Composable
fun ModernEmptyState(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = NeutralMedium
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = NeutralDark
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = NeutralMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        if (action != null) {
            Spacer(modifier = Modifier.height(Spacing.lg))
            action()
        }
    }
}

// ============ MODERN LOADING STATE ============

@Composable
fun ModernLoadingState(
    message: String = "Loading..."
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = Primary,
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = NeutralMedium
        )
    }
}
