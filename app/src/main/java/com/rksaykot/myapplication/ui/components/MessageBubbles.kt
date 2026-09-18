package com.rksaykot.myapplication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rksaykot.myapplication.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

// ============ MESSAGE BUBBLES ============

// Message Bubble for received messages
@Composable
fun ReceivedMessageBubble(
    text: String,
    timestamp: Long,
    senderName: String = "",
    showTimestamp: Boolean = false,
    isTyping: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.Start
    ) {
        // Avatar
        ModernAvatar(
            name = senderName,
            imageUrl = null,
            size = 32,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(end = Spacing.md)
        )

        // Message Bubble
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .background(NeutralLighter)
                .padding(Spacing.md)
        ) {
            Column {
                if (isTyping) {
                    TypingIndicator()
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeutralDark
                    )
                }

                if (showTimestamp && !isTyping) {
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = formatTime(timestamp),
                        style = MaterialTheme.typography.labelSmall,
                        color = NeutralMedium,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

// Message Bubble for sent messages
@Composable
fun SentMessageBubble(
    text: String,
    timestamp: Long,
    isRead: Boolean = false,
    isSending: Boolean = false,
    showTimestamp: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.End
    ) {
        // Message Bubble
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 4.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .background(Primary)
                .padding(Spacing.md)
        ) {
            Column {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = White
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = Spacing.xs),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showTimestamp) {
                        Text(
                            text = formatTime(timestamp),
                            style = MaterialTheme.typography.labelSmall,
                            color = White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    // Read Receipt Indicator
                    AnimatedVisibility(
                        visible = !isSending,
                        enter = scaleIn() + fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            imageVector = if (isRead) Icons.Filled.DoneAll else Icons.Filled.Check,
                            contentDescription = if (isRead) "Read" else "Sent",
                            modifier = Modifier.size(16.dp),
                            tint = if (isRead) SecondaryLight else White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

// System Message (date separator, etc)
@Composable
fun SystemMessageBubble(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.lg),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(NeutralLighter, RoundedCornerShape(Dimensions.cornerRadius))
                .padding(horizontal = Spacing.md, vertical = Spacing.sm),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = NeutralMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ============ INDICATORS ============

// Typing Indicator Animation
@Composable
fun TypingIndicator() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(NeutralMedium)
            )
        }
    }
}

// Message Read Status Indicator
@Composable
fun MessageStatusIndicator(
    isSending: Boolean = false,
    isRead: Boolean = false,
    modifier: Modifier = Modifier
) {
    when {
        isSending -> {
            CircularProgressIndicator(
                modifier = modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = NeutralMedium
            )
        }
        isRead -> {
            Icon(
                imageVector = Icons.Filled.DoneAll,
                contentDescription = "Read",
                modifier = modifier.size(18.dp),
                tint = Info
            )
        }
        else -> {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Sent",
                modifier = modifier.size(18.dp),
                tint = NeutralMedium
            )
        }
    }
}

// Online Status Indicator
@Composable
fun OnlineStatusIndicator(
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(12.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (isOnline) Success else NeutralMedium)
    )
}

// ============ HELPER FUNCTIONS ============

private fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

private fun formatDateSeparator(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
