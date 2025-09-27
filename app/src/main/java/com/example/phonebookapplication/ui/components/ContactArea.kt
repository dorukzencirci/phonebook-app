package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.theme.Gray900
import com.example.phonebookapplication.ui.theme.Gray950
import com.example.phonebookapplication.ui.theme.TextBlue

@Composable
fun ContactArea(
    users: List<User>,
    searchQuery: String,
    modifier: Modifier = Modifier
) {

    val filteredUsers = users.filter {
        it.firstName?.contains(searchQuery, ignoreCase = true) == true || it.lastName?.contains(searchQuery, ignoreCase = true) == true
    }

    if (users.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.default_user_profile_image),
                    contentDescription = "Default User Profile Image"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No Contacts",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray950,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Contacts you've added will appear here.",
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Create New Contact",
                    color = TextBlue
                )
            }
        }
    } else if (filteredUsers.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.no_result_image),
                    contentDescription = "Default User Profile Image"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No Results",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray950
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "The user you are looking for could not be found.",
                    color = Gray900
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(filteredUsers) { user ->
                UserItem(user)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun ContactAreaPreview() {
    ContactArea(
        users = emptyList(),
        searchQuery = "a"
    )
}
