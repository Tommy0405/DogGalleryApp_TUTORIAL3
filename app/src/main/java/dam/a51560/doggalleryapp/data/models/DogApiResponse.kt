package dam.a51560.doggalleryapp.data.models

/**
 * Classe que representa a resposta da API The Dog API.
 * A API retorna diretamente um array de objetos DogImage, pelo que esta classe
 * estende ArrayList para facilitar a desserialização automática pelo Retrofit.
 */
class DogApiResponse : ArrayList<DogImage>()