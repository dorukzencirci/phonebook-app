package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.phonebookapplication.R

@Composable
fun CustomInputField() {
    var textValue by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textValue,
        onValueChange = { textValue = it },
        label = if (!isFocused) {
            { Text(stringResource(R.string.first_name)) }
        } else null,
        placeholder = if (!isFocused) {
            { Text(stringResource(R.string.first_name)) }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
    )
}

@Preview
@Composable
fun CustomInputFieldPreview() {
    CustomInputField()
}