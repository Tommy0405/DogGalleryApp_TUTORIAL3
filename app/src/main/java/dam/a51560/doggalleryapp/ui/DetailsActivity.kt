package dam.a51560.doggalleryapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dam.a51560.doggalleryapp.data.models.DogImage
import dam.a51560.doggalleryapp.databinding.ActivityDetailsBinding
import dam.a51560.doggalleryapp.ui.viewmodels.DetailsViewModel
import kotlin.properties.ReadOnlyProperty

/**
 * Activity que exibe os detalhes de uma imagem específica.
 * Permite ao utilizador adicionar a imagem aos favoritos.
 */
class DetailsActivity : AppCompatActivity() {
    /** ViewBinding para acesso aos elementos do layout */
    private lateinit var binding: ActivityDetailsBinding

    /** ViewModel responsável pela lógica do ecrã de detalhes */
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        // Obtém os dados passados pela Intent
        val id = intent.getStringExtra("id") ?: ""
        val url = intent.getStringExtra("url") ?: ""

        // Configura o TextView com o identificador
        binding.dogIdTextView.text = "ID: $id"

        // Carrega a imagem utilizando Glide com fitCenter para manter proporções
        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(binding.detailImageView)

        // Configura o botão de favoritos
        binding.btnFavorite.setOnClickListener {
            viewModel.addFavorite(DogImage(id, url, 0, 0))
        }

        // Observa o estado de adição de favorito para exibir mensagem de confirmação
        viewModel.isFavoriteAdded.observe(this) { added ->
            if (added) {
                Toast.makeText(this, "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}