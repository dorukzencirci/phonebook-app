package com.example.phonebookapplication.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.ui.theme.AppBackgroundColor
import com.example.phonebookapplication.ui.theme.Gray950
import com.example.phonebookapplication.ui.theme.TextBlue
import com.example.phonebookapplication.util.Constants
import com.example.phonebookapplication.util.copyUriToFile
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoPickerBottomSheet(
    onDismiss: () -> Unit,
    onTakePhoto: (File) -> Unit,
    onChooseGallery: (File) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val mimeType = context.contentResolver.getType(uri)
            val allowedExtensions = Constants.ACCEPTED_IMAGE_TYPES
            if (mimeType != null && allowedExtensions.contains(mimeType.lowercase())) {
                val extension = when (mimeType.lowercase()) {
                    "image/png" -> "png"
                    "image/jpeg" -> "jpg"
                    else -> "jpg"
                }
                val file = context.copyUriToFile(uri,extension)
                onChooseGallery(file)
                onDismiss()
            }
        }
    }
    val photoFile = remember { File(context.cacheDir, "temp_photo.jpg") }
    val photoUri = androidx.core.content.FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onTakePhoto(photoFile)
            onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppBackgroundColor,
        dragHandle = null
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 33.dp, end = 32.dp, top = 29.dp, bottom = 8.dp)
        ) {
            TextButton(
                onClick = {
                    takePhotoLauncher.launch(photoUri)
                },
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Gray950,
                        shape = RoundedCornerShape(64.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.camera_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Camera", color = Gray950)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Gray950,
                        shape = RoundedCornerShape(64.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gallery_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Gallery", color = Gray950)
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text("Cancel",
                color = TextBlue,
                modifier = Modifier.clickable { onDismiss() }
            )
        }
    }
}