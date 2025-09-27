package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.theme.Gray500
import com.example.phonebookapplication.ui.theme.Gray900

@Composable
fun UserItem(user: User) {
    Row {
        AsyncImage(
            model = user.profileImageUrl,
            contentDescription = "User Profile Image",
            modifier = Modifier.width(40.dp).height(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(user.firstName + " " + user.lastName, color = Gray900)
            Spacer(modifier = Modifier.height(4.dp))
            Text(user.phoneNumber ?: "", color = Gray500)
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