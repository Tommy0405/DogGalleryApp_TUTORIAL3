package dam.a51560.doggalleryapp.data.remote

import dam.a51560.doggalleryapp.data.models.DogImage
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface que define os endpoints da API The Dog API.
 * Utilizada pelo Retrofit para gerar a implementação das chamadas de rede.
 */
interface DogApiService {
    /**
     * Obtém uma lista de imagens aleatórias de cães.
     *
     * @param limit Número máximo de imagens a retornar (padrão: 10)
     * @return Lista de objetos DogImage
     */
    @GET("v1/images/search")
    suspend fun getRandomDogs(
        @Query("limit") limit: Int = 10
    ): List<DogImage>
}