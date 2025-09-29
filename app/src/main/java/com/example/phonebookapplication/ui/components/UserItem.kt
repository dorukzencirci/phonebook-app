package com.example.phonebookapplication.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.theme.Blue50
import com.example.phonebookapplication.ui.theme.Blue500
import com.example.phonebookapplication.ui.theme.Gray500
import com.example.phonebookapplication.ui.theme.Gray900
import com.example.phonebookapplication.ui.theme.TextBlue
import kotlin.math.roundToInt

@Composable
fun UserItem(
    user: User,
    onClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {},
    onEditClick: (User) -> Unit = {}
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "")
    val maxSwipeDistance = -300f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.White),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (offsetX < 0) {
                Row {
                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .fillMaxHeight()
                            .clickable {
                                onEditClick(user)
                                offsetX = 0f
                            }
                            .background(Blue500),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit_icon),
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 20.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .fillMaxHeight()
                            .clickable {
                                onDeleteClick(user)
                                offsetX = 0f
                            }
                            .background(Color.Red),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.delete_icon),
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 20.dp)
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                        },
                        onDragEnd = {
                            offsetX = if (offsetX < maxSwipeDistance / 2) {
                                maxSwipeDistance
                            } else {
                                0f
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = offsetX + dragAmount
                            offsetX = newOffset.coerceIn(maxSwipeDistance, 0f)
                        }
                    )
                }
                .clickable(
                    enabled = offsetX == 0f,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClick(user)
                }
        ) {
            if (!user.profileImageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.profileImageUrl)
                        .build(),
                    contentDescription = "User Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Blue50)
                ) {
                    Text(
                        text = user.firstName?.firstOrNull()?.uppercase() ?: "?",
                        color = TextBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    color = Gray900,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
                Text(
                    text = user.phoneNumber ?: "",
                    color = Gray500,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    val user = User(
        id = "1",
        createdAt = "10",
        firstName = "Doruk",
        lastName = "Zencirci",
        phoneNumber = "+905346626533",
        profileImageUrl = ""
    )
    UserItem(user)
}