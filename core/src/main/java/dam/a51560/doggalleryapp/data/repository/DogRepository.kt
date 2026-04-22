package dam.a51560.doggalleryapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dam.a51560.doggalleryapp.data.models.CachedImage
import dam.a51560.doggalleryapp.data.models.DogImage
import dam.a51560.doggalleryapp.data.models.Favorite
import dam.a51560.doggalleryapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException

/**
 * Repositório responsável pela gestão de dados da aplicação.
 * Implementa lógica de cache offline, gestão de favoritos (FIFO com limite de 5)
 * e decisões sobre a origem dos dados (rede ou cache).
 *
 * @param context Contexto da aplicação para acesso às SharedPreferences
 */
class DogRepository(context: Context) {
    /** SharedPreferences para armazenamento persistente dos dados */
    private val prefs: SharedPreferences = context.getSharedPreferences("dog_gallery_prefs", Context.MODE_PRIVATE)
    /** Instância do Gson para serialização/deserialização JSON */
    private val gson = Gson()
    /** Serviço API para comunicação com a rede */
    private val api = RetrofitInstance.api

    /** Flow que emite a lista atual de favoritos sempre que há alterações */
    private val _favoritesFlow = MutableStateFlow<List<Favorite>>(getFavoritesFromPrefs())

    /**
     * Obtém a lista de imagens para exibição.
     * Prioriza o cache local, a menos que forceRefresh seja true.
     * Em caso de falha de rede, retorna os dados em cache se disponíveis.
     *
     * @param forceRefresh Se true, ignora o cache e obtém dados da API
     * @return Lista de objetos DogImage
     * @throws IOException Se não há rede e não há dados em cache
     */
    suspend fun getImages(forceRefresh: Boolean = false): List<DogImage> {
        val cached = getCachedImagesFromPrefs()
        if (cached.isNotEmpty() && !forceRefresh) {
            return cached.map { DogImage(it.id, it.url, it.width, it.height) }
        }

        return try {
            val response = api.getRandomDogs(limit = 10)
            saveCachedImagesToPrefs(response.map { CachedImage(it.id, it.url, it.width, it.height) })
            response
        } catch (e: Exception) {
            if (cached.isNotEmpty()) {
                cached.map { DogImage(it.id, it.url, it.width, it.height) }
            } else {
                throw IOException("No network and no cached data available", e)
            }
        }
    }

    /**
     * Obtém o Flow com a lista atual de favoritos.
     *
     * @return Flow que emite a lista de favoritos
     */
    fun getAllFavorites(): Flow<List<Favorite>> = _favoritesFlow

    /**
     * Adiciona uma imagem aos favoritos.
     * Implementa política FIFO: quando atinge o limite de 5 itens,
     * remove o mais antigo automaticamente.
     *
     * @param dogImage Imagem a adicionar aos favoritos
     */
    suspend fun addFavorite(dogImage: DogImage) {
        val current = getFavoritesFromPrefs().toMutableList()
        if (current.none { it.id == dogImage.id }) {
            current.add(Favorite(dogImage.id, dogImage.url))
            // Política FIFO: garante no máximo 5 favoritos, removendo o mais antigo
            while (current.size > 5) {
                current.removeAt(0) // Remove o primeiro (mais antigo)
            }
            saveFavoritesToPrefs(current)
            _favoritesFlow.value = current
        }
    }

    /**
     * Remove uma imagem dos favoritos.
     *
     * @param favorite Favorito a remover
     */
    suspend fun removeFavorite(favorite: Favorite) {
        val current = getFavoritesFromPrefs().toMutableList()
        current.removeAll { it.id == favorite.id }
        saveFavoritesToPrefs(current)
        _favoritesFlow.value = current
    }

    /**
     * Obtém a lista de favoritos a partir das SharedPreferences.
     *
     * @return Lista de favoritos ou lista vazia se não existir
     */
    private fun getFavoritesFromPrefs(): List<Favorite> {
        val json = prefs.getString("favorites", null) ?: return emptyList()
        val type = object : TypeToken<List<Favorite>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    /**
     * Guarda a lista de favoritos nas SharedPreferences.
     *
     * @param list Lista de favoritos a guardar
     */
    private fun saveFavoritesToPrefs(list: List<Favorite>) {
        prefs.edit().putString("favorites", gson.toJson(list)).apply()
    }

    /**
     * Obtém a lista de imagens em cache a partir das SharedPreferences.
     *
     * @return Lista de imagens em cache ou lista vazia se não existir
     */
    private fun getCachedImagesFromPrefs(): List<CachedImage> {
        val json = prefs.getString("cached_images", null) ?: return emptyList()
        val type = object : TypeToken<List<CachedImage>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    /**
     * Guarda a lista de imagens em cache nas SharedPreferences.
     *
     * @param list Lista de imagens a guardar em cache
     */
    private fun saveCachedImagesToPrefs(list: List<CachedImage>) {
        prefs.edit().putString("cached_images", gson.toJson(list)).apply()
    }
}