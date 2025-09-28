package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.ui.theme.Gray700


@Composable
fun SearchItem(
    searchedName: String,
    onClick: () -> Unit = {},
    onDeleteClick: (String) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp)
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painterResource(R.drawable.delete_search_item),
            contentDescription = "delete icon",
            modifier = Modifier.clickable {
                onDeleteClick(searchedName)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(searchedName, color = Gray700, fontSize = 14.sp)
    }
}

@Preview
@Composable
fun SearchItemPreview() {
    SearchItem("Doruk")
}