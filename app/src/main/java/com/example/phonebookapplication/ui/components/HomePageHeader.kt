package com.example.phonebookapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonebookapplication.R
import com.example.phonebookapplication.ui.theme.Gray950

@Composable
fun HomePageHeader(onAddClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            stringResource(R.string.contacts),
            color = Gray950,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = "Add Button",
            modifier = Modifier.clickable { onAddClick() }
        )
    }
}

@Preview
@Composable
fun HomePageHeaderPreview() {
    HomePageHeader({})
}