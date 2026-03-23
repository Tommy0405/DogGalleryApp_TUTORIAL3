package dam.a51560.doggalleryapp.data.models

/**
 * Classe selada que representa os possíveis estados da interface de utilizador.
 * Utilizada pelos ViewModels para comunicar alterações de estado às Activities.
 *
 * @param T Tipo de dados associado ao estado de sucesso
 */
sealed class UiState<out T> {
    /** Estado de carregamento - indica que uma operação assíncrona está em curso */
    object Loading : UiState<Nothing>()

    /** Estado de sucesso - contém os dados carregados */
    data class Success<T>(val data: T) : UiState<T>()

    /** Estado de erro - contém a mensagem de erro para apresentar ao utilizador */
    data class Error(val message: String) : UiState<Nothing>()
}