package com.example.phonebookapplication.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageRequest
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.repository.UserRepository
import com.example.phonebookapplication.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class HomeViewModel(val repository: UserRepository): ViewModel() {

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState = _uiState.asSharedFlow()
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun fetchAllUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getAllUsers()
                _users.value = response.data ?: emptyList()
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "fetchAllUsers: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }

        }
    }

    fun saveUser(request: SaveUserRequest) {
        viewModelScope.launch {
            try {
                val response = repository.saveUser(request)
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "saveUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.deleteUser(id)
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "deleteUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }

        }
    }

    fun updateUser(request: UpdateUserRequest, id: String) {
        viewModelScope.launch {
            try {
                val response = repository.updateUser(id, request)
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "updateUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }

    }

    fun uploadImage(request: UploadImageRequest) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(request)
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "updateUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }
    }
}