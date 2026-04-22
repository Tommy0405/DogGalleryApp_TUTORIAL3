package dam.a51560.doggalleryapp.data.models

/**
 * Representa uma imagem marcada como favorita pelo utilizador.
 * Segue uma política FIFO com limite máximo de 5 itens.
 *
 * @property id Identificador único da imagem
 * @property url Endereço URL da imagem
 * @property addedAt Timestamp do momento em que foi adicionada aos favoritos
 */
data class Favorite(
    val id: String,
    val url: String,
    val addedAt: Long = System.currentTimeMillis()
)