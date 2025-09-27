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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.ui.components.CustomInputField
import com.example.phonebookapplication.ui.theme.AppBackgroundColor
import com.example.phonebookapplication.ui.theme.Gray200
import com.example.phonebookapplication.ui.theme.Gray950
import com.example.phonebookapplication.ui.theme.TextBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactBottomSheet(
    onDismiss: () -> Unit,
    onSave: (User) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppBackgroundColor,
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
                    text = "Cancel",
                    color = TextBlue,
                    modifier = Modifier.align(Alignment.CenterStart).clickable { onDismiss() }
                )

                Text(
                    text = "New Contact",
                    color = Gray950,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Done",
                    color = Gray200,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            Spacer(Modifier.height(48.dp))

            Image(
                painter = painterResource(R.drawable.default_user_profile_image),
                contentDescription = "Default User Profile Image",
                modifier = Modifier.size(96.dp)
            )
            Spacer(Modifier.height(8.dp))

            Text(
                "Add Photo",
                color = TextBlue,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(32.dp))

            CustomInputField()
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}