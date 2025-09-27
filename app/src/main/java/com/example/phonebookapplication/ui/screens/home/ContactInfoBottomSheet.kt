package com.example.phonebookapplication.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageRequest
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.theme.Blue50
import com.example.phonebookapplication.ui.theme.Gray100
import com.example.phonebookapplication.ui.theme.Gray50
import com.example.phonebookapplication.ui.theme.Gray500
import com.example.phonebookapplication.ui.theme.Gray950
import com.example.phonebookapplication.ui.theme.TextBlue
import com.example.phonebookapplication.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactInfoBottomSheet(
    user: User,
    onDismiss: () -> Unit,
    onUpdate: (UpdateUserRequest) -> Unit,
    onUploadImage: (UploadImageRequest) -> Unit,
    onDelete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var profileImageUrl by remember { mutableStateOf(user.profileImageUrl) }
    var isContactSaved by remember { mutableStateOf(user.savedToPhone) }
    var expanded by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var showUploadImageSheet by remember { mutableStateOf(false) }
    var showDeleteContactSheet by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        modifier = Modifier.fillMaxHeight().padding(top = 42.dp),
        dragHandle = null
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            if (isEditMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = TextBlue,
                        modifier = Modifier.align(Alignment.CenterStart)
                            .clickable { onDismiss() }
                    )

                    Text(
                        text = "Edit Contact",
                        color = Gray950,
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Done",
                        color = TextBlue,
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .clickable {
                                onUpdate(
                                    UpdateUserRequest(
                                        firstName = firstName ?: "",
                                        lastName = lastName ?: "",
                                        phoneNumber = phoneNumber ?: "",
                                        profileImageUrl = profileImageUrl ?: ""
                                    )
                                )
                                onDismiss()
                            }
                    )
                }
            }
            else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box {
                        Image(
                            painter = painterResource(R.drawable.contact_info_menu_icon),
                            contentDescription = "Contact Info Menu Icon",
                            modifier = Modifier.clickable { expanded = true }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            offset = DpOffset(x = 0.dp, y = (-10).dp),
                            containerColor = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.edit_icon),
                                        contentDescription = "Edit Icon"
                                    )
                                },
                                onClick = {
                                    expanded = false
                                    isEditMode = true
                                },
                                modifier = Modifier.height(40.dp)
                            )
                            HorizontalDivider(
                                color = Gray50,
                                thickness = 1.dp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            DropdownMenuItem(
                                text = { Text("Delete", color = Color.Red) },
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.delete_icon),
                                        contentDescription = "Edit Icon"
                                    )
                                },
                                onClick = {
                                    expanded = false
                                    showDeleteContactSheet = true
                                },
                                modifier = Modifier.height(40.dp)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(49.dp))

            if (!profileImageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profileImageUrl)
                        .build(),
                    contentDescription = "User Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Blue50)
                ) {
                    Text(
                        text = user.firstName?.firstOrNull()?.uppercase() ?: "?",
                        color = TextBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 33.sp,
                        lineHeight = 33.sp
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            Text(
                "Change Photo",
                color = TextBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {showUploadImageSheet = true}
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = firstName ?: "",
                onValueChange = {firstName = it},
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = lastName ?: "",
                onValueChange = {lastName = it},
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = phoneNumber ?: "",
                onValueChange = {phoneNumber = it},
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(48.dp))

            TextButton(
                onClick = {
                    user.savedToPhone = true
                    isContactSaved = true
                },
                enabled = !isContactSaved,
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if(isContactSaved) Gray100 else Gray950,
                        shape = RoundedCornerShape(64.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.save_icon),
                        contentDescription = null,
                        colorFilter = if(isContactSaved) ColorFilter.tint(Gray100) else ColorFilter.tint(Gray950),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.save_to_my_phone_text), color = if(isContactSaved) Gray100 else Gray950)
                }
            }
            if(isContactSaved) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.info_icon),
                        contentDescription = "info icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("This contact is already saved to your phone.", color = Gray500, fontSize = 12.sp)
                }
            }
        }
    }

    if(showDeleteContactSheet) {
        DeleteContactBottomSheet(
            onDismiss = {
                showDeleteContactSheet = false
            },
            onDelete = onDelete
        )

    }
    if(showUploadImageSheet) {
        PhotoPickerBottomSheet(
            onDismiss = { showUploadImageSheet = false },
            onTakePhoto = {},
            onChooseGallery = { uriString ->
                profileImageUrl = uriString
                onUploadImage(
                    UploadImageRequest(
                        image = uriString
                    )
                )
            }
        )
    }

}