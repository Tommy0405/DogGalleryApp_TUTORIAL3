package dam.a51560.doggalleryapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dam.a51560.doggalleryapp.data.models.Favorite
import dam.a51560.doggalleryapp.data.repository.DogRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para o ecrã de favoritos.
 * Responsável por gerir a lista de imagens favoritas e permitir a sua remoção.
 *
 * @param application Contexto da aplicação
 */
class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    /** Repositório para acesso aos dados */
    private val repository: DogRepository

    /** LiveData com a lista atual de favoritos */
    val favorites: LiveData<List<Favorite>>

    init {
        repository = DogRepository(application)
        // Converte o Flow do repositório em LiveData para observação na Activity
        favorites = repository.getAllFavorites().asLiveData(viewModelScope.coroutineContext)
    }

    /**
     * Remove um favorito da lista.
     * A operação é executada numa corrotina para não bloquear a thread principal.
     *
     * @param favorite Favorito a remover
     */
    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }
}