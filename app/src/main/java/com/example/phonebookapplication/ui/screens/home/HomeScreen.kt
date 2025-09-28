package com.example.phonebookapplication.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.components.AppSearchBar
import com.example.phonebookapplication.ui.components.ContactArea
import com.example.phonebookapplication.ui.components.HomePageHeader
import com.example.phonebookapplication.ui.components.SuccessScreen
import com.example.phonebookapplication.ui.state.UiState
import com.example.phonebookapplication.ui.theme.AppBackgroundColor
import com.example.phonebookapplication.ui.theme.SuccessGreen

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    var searchQuery by remember { mutableStateOf("") }
    val users by viewModel.users.collectAsState(emptyList())
    var showAddContactSheet by remember { mutableStateOf(false) }
    var showContactInfoSheet by remember { mutableStateOf(false) }
    var showSuccessScreen by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }
    var isSearching by remember { mutableStateOf(false) }
    var searchHistory by remember { mutableStateOf(mutableListOf<String>()) }
    val focusManager = LocalFocusManager.current
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.fetchAllUsers()
    }

    LaunchedEffect(viewModel.uiState) {
        viewModel.uiState.collect { uiState ->
            when(uiState) {
                is UiState.Error -> {

                }
                is UiState.Loading -> {
                    isLoading = true
                }
                is UiState.Success -> {
                    isLoading = false
                    if(uiState.imageUrl != null) {
                        selectedUser?.let { selectedUser = it.copy(profileImageUrl = uiState.imageUrl)}
                        uploadedImageUrl = uiState.imageUrl
                    }

                    if(!uiState.message.isNullOrEmpty()) {
                        if(uiState.message == "User is saved!") {
                            showSuccessScreen = true
                        }
                        else {
                            snackbarHostState.showSnackbar(uiState.message)
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(horizontal = 16.dp)) { data ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.success_icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = data.visuals.message,
                            color = SuccessGreen,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            focusManager.clearFocus()
                        }
                    }
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(39.dp))
                HomePageHeader({ showAddContactSheet = true })
                Spacer(modifier = Modifier.height(26.dp))
                AppSearchBar(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        isSearching = searchQuery.isEmpty()
                    },
                    onFocusChanged = { isSearching = it && searchQuery.isEmpty() },
                    onSearchClick = {query -> if(!searchHistory.contains(query))searchHistory.add(query)}
                )
                Spacer(modifier = Modifier.height(16.dp))
                ContactArea(
                    users = users,
                    searchQuery = searchQuery,
                    onCreateClick = { showAddContactSheet = true },
                    onUserClick = {user ->
                        selectedUser = user
                        showContactInfoSheet = true
                    },
                    isSearching = isSearching,
                    searchHistory = searchHistory,
                    onDeleteSearchItem = { item ->
                        searchHistory = searchHistory.toMutableList().also { it.remove(item) }
                    },
                    onClearAllSearch = {
                        searchHistory = mutableListOf()
                    }
                )
            }
        },
        containerColor = AppBackgroundColor
    )

    if (showAddContactSheet) {
        CreateContactBottomSheet(
            onDismiss = {
                uploadedImageUrl = null
                showAddContactSheet = false
            },
            onImageSelected = { file ->
                viewModel.uploadImage(file)
            },
            onSave = { user ->
                viewModel.saveUser(
                    SaveUserRequest(
                        firstName = user.firstName,
                        lastName = user.lastName,
                        phoneNumber = user.phoneNumber,
                        profileImageUrl = uploadedImageUrl
                    )
                )
                uploadedImageUrl = null
                showAddContactSheet = false
            },
            initialProfileImageUrl = uploadedImageUrl
        )
    }

    if (showContactInfoSheet && selectedUser != null) {
        ContactInfoBottomSheet(
            user = selectedUser!!,
            onDismiss = { showContactInfoSheet = false },
            onUpdate = { saveUserRequest ->
                viewModel.updateUser(
                    saveUserRequest, selectedUser!!.id ?: ""
                )
                uploadedImageUrl = null
            },
            onDelete = {
                viewModel.deleteUser(selectedUser!!.id ?: "")
                showContactInfoSheet = false
            },
            onUploadImage = { file ->
                viewModel.uploadImage(file)
            }
        )
    }

    if(showSuccessScreen) {
        SuccessScreen(
            onDismiss = {
                showSuccessScreen = false
            }
        )
    }
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}