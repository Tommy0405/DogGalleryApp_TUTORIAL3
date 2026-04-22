package dam.a51560.doggalleryapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton que fornece uma instância configurada do serviço API.
 * A inicialização é lazy, ocorrendo apenas no primeiro acesso.
O singleton garante a existência de apenas uma única instância
de uma classe em toda a aplicação, fornecendo um ponto de acesso
global a ela.
 */
object RetrofitInstance {
    /** URL base da API The Dog API */
    private const val BASE_URL = "https://api.thedogapi.com/"

    /**
     * Interceptor para logging das chamadas de rede.
     * Nível BODY permite visualizar requisições e respostas completas,
     * útil para depuração durante o desenvolvimento.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /** Cliente HTTP OkHttp com o interceptor de logging configurado */
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Instância do serviço API.
     * Utiliza lazy para garantir que a configuração só ocorre quando necessário.
     */
    val api: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }
}