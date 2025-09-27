package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.theme.Gray300
import com.example.phonebookapplication.ui.theme.Gray50
import com.example.phonebookapplication.ui.theme.Gray900
import com.example.phonebookapplication.ui.theme.Gray950
import com.example.phonebookapplication.ui.theme.TextBlue

@Composable
fun ContactArea(
    users: List<User>,
    searchQuery: String,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit = {},
    onCreateClick: () -> Unit = {}
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
                    color = TextBlue,
                    modifier = Modifier.clickable { onCreateClick() }
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
        val groupedUsers = users.sortedBy { it.firstName }.groupBy { it.firstName?.firstOrNull()?.uppercaseChar() ?: '#' }
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            groupedUsers.forEach { (letter, usersForLetter) ->

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column {
                            Text(
                                text = letter.toString(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp, top = 7.dp),
                                color = Gray300
                            )

                            usersForLetter.forEachIndexed { index, user ->
                                UserItem(
                                    user = user,
                                    onClick = onUserClick
                                )
                                if (index != usersForLetter.lastIndex) {
                                    HorizontalDivider(
                                        color = Gray50,
                                        thickness = 1.dp,
                                        modifier = Modifier.padding(horizontal = 12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
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
