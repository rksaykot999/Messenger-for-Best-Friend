package com.rksaykot.myapplication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rksaykot.myapplication.ui.theme.*

// Modern Message Input Component
@Composable
fun ModernMessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttachClick: () -> Unit = {},
    onEmojiClick: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Divider(color = NeutralLighter, thickness = 1.dp)
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            verticalAlignment = Alignment.Bottom
        ) {
            // Attach Button
            IconButton(
                onClick = onAttachClick,
                modifier = Modifier
                    .size(40.dp),
                enabled = !isLoading
            ) {
                Icon(
                    imageVector = Icons.Filled.AttachFile,
                    contentDescription = "Attach",
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Message Input Field
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 44.dp, max = 120.dp),
                placeholder = {
                    Text(
                        text = "Type a message...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeutralMedium
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = NeutralLighter,
                    unfocusedContainerColor = NeutralLighter,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = false,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                enabled = !isLoading
            )

            // Emoji Button
            IconButton(
                onClick = onEmojiClick,
                modifier = Modifier.size(40.dp),
                enabled = !isLoading
            ) {
                Icon(
                    imageVector = Icons.Filled.EmojiEmotions,
                    contentDescription = "Emoji",
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Send Button
            Button(
                onClick = onSend,
                modifier = Modifier
                    .size(40.dp),
                enabled = value.trim().isNotEmpty() && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    disabledContainerColor = NeutralMedium
                ),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(0.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send",
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Character Counter (optional)
        if (value.length > 200) {
            Text(
                text = "${value.length}/500",
                style = MaterialTheme.typography.labelSmall,
                color = Warning,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
    }
}

// Suggested Replies Component
@Composable
fun SuggestedReplies(
    suggestions: List<String>,
    onReplyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = suggestions.isNotEmpty(),
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NeutralLighter)
                .padding(Spacing.lg)
        ) {
            Text(
                text = "Quick replies",
                style = MaterialTheme.typography.labelMedium,
                color = NeutralMedium,
                modifier = Modifier.padding(bottom = Spacing.md)
            )

            suggestions.forEach { suggestion ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, RoundedCornerShape(8.dp))
                        .clickable { onReplyClick(suggestion) }
                        .padding(Spacing.md)
                        .border(1.dp, Primary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(Spacing.md),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = suggestion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeutralDark
                    )
                }

                if (suggestion != suggestions.last()) {
                    Spacer(modifier = Modifier.height(Spacing.sm))
                }
            }
        }
    }
}

// Voice Message Button Component
@Composable
fun VoiceMessageButton(
    isRecording: Boolean = false,
    onStartRecording: () -> Unit = {},
    onStopRecording: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = if (isRecording) onStopRecording else onStartRecording,
        modifier = modifier
            .size(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isRecording) Secondary else Primary
        ),
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
            contentDescription = if (isRecording) "Stop" else "Voice",
            tint = White,
            modifier = Modifier.size(24.dp)
        )
    }
}

// Message Composer with all features
@Composable
fun ModernMessageComposer(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isTyping: Boolean = false,
    onTypingIndicator: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LaunchedEffect(value) {
        onTypingIndicator(value.isNotEmpty())
    }

    Column(modifier = modifier.fillMaxWidth()) {
        ModernMessageInput(
            value = value,
            onValueChange = onValueChange,
            onSend = onSend
        )
    }
}
