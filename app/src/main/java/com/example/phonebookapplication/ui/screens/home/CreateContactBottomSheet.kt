package com.example.phonebookapplication.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.ui.theme.Gray200
import com.example.phonebookapplication.ui.theme.Gray950
import com.example.phonebookapplication.ui.theme.TextBlue
import com.example.phonebookapplication.util.capitalizeFirstChar
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactBottomSheet(
    onDismiss: () -> Unit,
    onImageSelected: (File) -> Unit,
    onSave: (SaveUserRequest) -> Unit,
    initialProfileImageUrl: String? = null
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("+") }
    var profileImageUrl by remember { mutableStateOf(initialProfileImageUrl) }
    var showUploadImageSheet by remember { mutableStateOf(false) }

    LaunchedEffect(initialProfileImageUrl) {
        profileImageUrl = initialProfileImageUrl
    }

    val isDoneButtonEnabled = firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = TextBlue,
                    modifier = Modifier.align(Alignment.CenterStart)
                        .clickable { onDismiss() }
                )

                Text(
                    text = stringResource(R.string.new_contact),
                    color = Gray950,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(R.string.done),
                    color = if(isDoneButtonEnabled) TextBlue else Gray200,
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .clickable(enabled = (isDoneButtonEnabled)) {
                            onSave(
                                SaveUserRequest(
                                    firstName = firstName.capitalizeFirstChar(),
                                    lastName = lastName.capitalizeFirstChar(),
                                    phoneNumber = phoneNumber,
                                    profileImageUrl = profileImageUrl
                                )
                            )
                        }
                )
            }

            Spacer(Modifier.height(48.dp))
            if(profileImageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profileImageUrl)
                        .build(),
                    contentDescription = "User Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(96.dp).clip(CircleShape)
                )
            }
            else {
                Image(
                    painter = painterResource(R.drawable.default_user_profile_image),
                    contentDescription = "Default User Profile Image",
                    modifier = Modifier.size(96.dp)
                )
            }
            Spacer(Modifier.height(8.dp))

            Text(
                text = if(profileImageUrl != null) stringResource(R.string.change_photo) else stringResource(R.string.add_photo),
                color = TextBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {showUploadImageSheet = true}
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = {firstName = it},
                label = { Text(stringResource(R.string.first_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = {lastName = it},
                label = { Text(stringResource(R.string.surname)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { input ->
                    val digitsOnly = input.removePrefix("+").filter { it.isDigit() }
                    val limitedDigits = digitsOnly.take(10)
                    phoneNumber = "+$limitedDigits"
                },
                label = { Text(stringResource(R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
        }
    }

    if(showUploadImageSheet) {
        PhotoPickerBottomSheet(
            onDismiss = { showUploadImageSheet = false },
            onTakePhoto = {},
            onChooseGallery = { file ->
                onImageSelected(file)
            }
        )
    }
}