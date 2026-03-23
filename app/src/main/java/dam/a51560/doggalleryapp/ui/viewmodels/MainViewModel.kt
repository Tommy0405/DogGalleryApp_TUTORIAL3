package dam.a51560.doggalleryapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dam.a51560.doggalleryapp.data.models.DogImage
import dam.a51560.doggalleryapp.data.models.UiState
import dam.a51560.doggalleryapp.data.repository.DogRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para o ecrã principal da aplicação.
 * Responsável por gerir o carregamento de imagens e o estado da interface.
 *
 * @param application Contexto da aplicação
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    /** Repositório para acesso aos dados */
    private val repository: DogRepository

    /** LiveData que representa o estado atual da UI (loading, success, error) */
    private val _uiState = MutableLiveData<UiState<List<DogImage>>>()
    val uiState: LiveData<UiState<List<DogImage>>> = _uiState

    init {
        repository = DogRepository(application)
        // Carrega as imagens automaticamente quando o ViewModel é criado
        loadImages()
    }

    /**
     * Carrega as imagens da API ou do cache.
     * Atualiza o uiState conforme o progresso da operação.
     *
     * @param forceRefresh Se true, ignora o cache e força nova chamada à API
     */
    fun loadImages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            // Define o estado como Loading
            _uiState.value = UiState.Loading
            try {
                val images = repository.getImages(forceRefresh)
                // Define o estado como Success com os dados obtidos
                _uiState.value = UiState.Success(images)
            } catch (e: Exception) {
                // Define o estado como Error com a mensagem da exceção
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}