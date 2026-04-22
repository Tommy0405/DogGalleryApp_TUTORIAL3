package dam.a51560.doggalleryapp.data.models

/**
 * Representa uma imagem armazenada em cache localmente.
 * Utilizada para permitir acesso offline às imagens previamente carregadas.
 *
 * @property id Identificador único da imagem
 * @property url Endereço URL da imagem
 * @property width Largura da imagem em pixels
 * @property height Altura da imagem em pixels
 * @property cachedAt Timestamp do momento em que a imagem foi guardada em cache
 */
data class CachedImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val cachedAt: Long = System.currentTimeMillis()
)