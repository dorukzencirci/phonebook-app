package com.example.phonebookapplication.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phonebookapplication.ui.components.AppSearchBar
import com.example.phonebookapplication.ui.components.ContactArea
import com.example.phonebookapplication.ui.components.HomePageHeader
import com.example.phonebookapplication.ui.theme.AppBackgroundColor

@Composable
fun HomeScreen(viewModel: HomeViewModel?) {
    //val users by viewModel?.users?.collectAsState(emptyList())
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel?.fetchAllUsers()
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(39.dp))
                HomePageHeader({ showBottomSheet = true })
                Spacer(modifier = Modifier.height(26.dp))
                AppSearchBar()
                ContactArea(
                    users = listOf(),
                    searchQuery = "",
                )
            }
        },
        containerColor = AppBackgroundColor
    )

    if (showBottomSheet) {
        CreateContactBottomSheet(
            onDismiss = { showBottomSheet = false },
            onSave = { user ->
                showBottomSheet = false
            }
        )
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(null)
}