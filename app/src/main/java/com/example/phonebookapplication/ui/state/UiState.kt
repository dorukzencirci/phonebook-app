package com.example.phonebookapplication.ui.state

sealed class UiState() {
    object Loading : UiState()
    data class Success(val message: String?, val imageUrl: String? = null) : UiState()
    data class Error(val message: String?) : UiState()
}
