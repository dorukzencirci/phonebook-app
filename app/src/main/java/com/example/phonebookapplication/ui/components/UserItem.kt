package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.theme.Blue50
import com.example.phonebookapplication.ui.theme.Gray500
import com.example.phonebookapplication.ui.theme.Gray900
import com.example.phonebookapplication.ui.theme.TextBlue

@Composable
fun UserItem(
    user: User,
    onClick: (User) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp)
            .clickable {onClick(user)}
    ) {
        if (!user.profileImageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = user.profileImageUrl,
                contentDescription = "User Profile Image",
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