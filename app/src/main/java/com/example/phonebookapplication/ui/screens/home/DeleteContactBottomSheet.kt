package com.example.phonebookapplication.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonebookapplication.ui.theme.Gray900
import com.example.phonebookapplication.ui.theme.Gray950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteContactBottomSheet(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        sheetState = sheetState,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Delete Contact", color = Gray900, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Are you sure you want to delete this contact?", color = Gray900, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                        .border(
                            width = 1.dp,
                            color = Gray950,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .height(56.dp)
                ) {
                    Text("No", fontSize = 16.sp, color = Gray950)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray950,
                    ),
                    modifier = Modifier.weight(1f)
                        .height(56.dp)
                ) {
                    Text("Yes", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}