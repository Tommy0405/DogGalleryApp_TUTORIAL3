package dam.a51560.doggalleryapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dam.a51560.doggalleryapp.data.models.DogImage
import dam.a51560.doggalleryapp.data.repository.DogRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para o ecrã de detalhes da imagem.
 * Responsável por gerir a operação de adicionar aos favoritos.
 *
 * @param application Contexto da aplicação
 */
class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    /** Repositório para acesso aos dados */
    private val repository: DogRepository

    /** LiveData que indica se o favorito foi adicionado com sucesso */
    private val _isFavoriteAdded = MutableLiveData<Boolean>()
    val isFavoriteAdded: LiveData<Boolean> = _isFavoriteAdded

    init {
        repository = DogRepository(application)
    }

    /**
     * Adiciona uma imagem aos favoritos.
     * A operação é executada numa corrotina para não bloquear a thread principal.
     *
     * @param dogImage Imagem a adicionar aos favoritos
     */
    fun addFavorite(dogImage: DogImage) {
        viewModelScope.launch {
            repository.addFavorite(dogImage)
            _isFavoriteAdded.value = true
        }
    }
}
