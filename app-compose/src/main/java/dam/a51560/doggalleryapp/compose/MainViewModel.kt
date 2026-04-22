package dam.a51560.doggalleryapp.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51560.doggalleryapp.data.models.DogImage
import dam.a51560.doggalleryapp.data.models.UiState
import dam.a51560.doggalleryapp.data.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<DogImage>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<DogImage>>> = _uiState.asStateFlow()

    init {
        loadDogImages()
    }

    fun loadDogImages() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val images = repository.getImages(forceRefresh = true)
                _uiState.value = UiState.Success(images)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}
