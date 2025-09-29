package com.example.phonebookapplication.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.repository.UserRepository
import com.example.phonebookapplication.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File


class HomeViewModel(val repository: UserRepository): ViewModel() {

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState = _uiState.asSharedFlow()
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    fun fetchAllUsers() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            try {
                val response = repository.getAllUsers()
                _users.value = response.data?.users ?: emptyList()
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "fetchAllUsers: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }

        }
    }

    fun getUserById(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getUserById(id)
                _user.value = response.data ?: User()
                _uiState.emit(UiState.Success(response.messages?.first() ?: ""))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "getUserById: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }
    }

    fun saveUser(request: SaveUserRequest) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            try {
                val response = repository.saveUser(request)
                fetchAllUsers()
                _uiState.emit(UiState.Success(response.messages?.first() ?: "User is saved!"))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "saveUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            try {
                val response = repository.deleteUser(id)
                fetchAllUsers()
                _uiState.emit(UiState.Success(response.messages?.first() ?: "User is Deleted!"))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "deleteUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }

        }
    }

    fun updateUser(request: UpdateUserRequest, id: String) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            try {
                val response = repository.updateUser(id, request)
                fetchAllUsers()
                _uiState.emit(UiState.Success(response.messages?.first() ?: "User is updated!"))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "updateUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }

    }

    fun uploadImage(file: File) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            try {
                val response = repository.uploadImage(file)
                _uiState.emit(UiState.Success(response.messages?.first() ?: "", response.data?.imageUrl))
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "updateUser: ", ex)
                _uiState.emit(UiState.Error(ex.message))
            }
        }
    }
}