package dam.a51560.doggalleryapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dam.a51560.doggalleryapp.databinding.ActivityFavoritesBinding
import dam.a51560.doggalleryapp.ui.adapters.FavoriteAdapter
import dam.a51560.doggalleryapp.ui.viewmodels.DetailsViewModel
import dam.a51560.doggalleryapp.ui.viewmodels.FavoritesViewModel
import kotlin.properties.ReadOnlyProperty

/**
 * Activity que exibe a lista de imagens favoritas do utilizador.
 * Permite ao utilizador remover imagens da lista de favoritos.
 */
class FavoritesActivity : AppCompatActivity() {
    /** ViewBinding para acesso aos elementos do layout */
    private lateinit var binding: ActivityFavoritesBinding

    /** ViewModel responsável pela lógica do ecrã de favoritos */
    private lateinit var viewModel: FavoritesViewModel

    /** Adaptador para a lista de favoritos */
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        setupRecyclerView()
        setupObservers()
    }

    /**
     * Configura o RecyclerView e o adaptador.
     * Define o layout manager como LinearLayout vertical.
     */
    private fun setupRecyclerView() {
        adapter = FavoriteAdapter { favorite ->
            // Remove o favorito quando o utilizador clica no botão de eliminar
            viewModel.removeFavorite(favorite)
        }
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favoritesRecyclerView.adapter = adapter
    }

    /**
     * Configura os observadores do ViewModel.
     * Atualiza o adaptador e exibe/esconde a mensagem de lista vazia conforme necessário.
     */
    private fun setupObservers() {
        viewModel.favorites.observe(this) { list ->
            adapter.submitList(list)
            // Exibe a mensagem de lista vazia se não houver favoritos
            if (list.isEmpty()) {
                binding.emptyStateTextView.visibility = View.VISIBLE
            } else {
                binding.emptyStateTextView.visibility = View.GONE
            }
        }
    }
}