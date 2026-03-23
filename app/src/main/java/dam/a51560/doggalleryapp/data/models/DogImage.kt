package dam.a51560.doggalleryapp.data.models

/**
 * Modelo principal que representa uma imagem de cão obtida da API.
 *
 * @property id Identificador único da imagem
 * @property url Endereço URL da imagem
 * @property width Largura da imagem em pixels
 * @property height Altura da imagem em pixels
 */
data class DogImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
