package dam.a51560.doggalleryapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dam.a51560.doggalleryapp.data.models.UiState
import dam.a51560.doggalleryapp.databinding.ActivityMainBinding
import dam.a51560.doggalleryapp.ui.adapters.DogImageAdapter
import dam.a51560.doggalleryapp.ui.viewmodels.MainViewModel
import kotlin.properties.ReadOnlyProperty


/**
 * Activity principal da aplicação.
 * Exibe um grid de imagens de cães e permite a navegação para detalhes e favoritos.
 */
class MainActivity : AppCompatActivity() {
    /** ViewBinding para acesso aos elementos do layout */
    private lateinit var binding: ActivityMainBinding

    /** ViewModel responsável pela lógica do ecrã principal */
    private lateinit var viewModel: MainViewModel

    /** Adaptador para o grid de imagens */
    private lateinit var adapter: DogImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    /**
     * Configura o RecyclerView com GridLayoutManager de 2 colunas.
     * Define o comportamento de clique para navegar para os detalhes.
     */
    private fun setupRecyclerView() {
        adapter = DogImageAdapter { dogImage ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("id", dogImage.id)
                putExtra("url", dogImage.url)
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Configura os listeners de interação do utilizador.
     * - SwipeRefresh: força atualização das imagens
     * - FloatingActionButton: navega para o ecrã de favoritos
     */
    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadImages(forceRefresh = true)
        }

        binding.fabFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    /**
     * Configura os observadores do ViewModel.
     * Atualiza a UI conforme o estado (loading, success, error).
     */
    private fun setupObservers() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Mostra o ProgressBar apenas se não for um refresh por swipe
                    if (!binding.swipeRefresh.isRefreshing) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
                is UiState.Success -> {
                    // Esconde indicadores de carregamento e atualiza o adaptador
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    adapter.submitList(state.data)
                }
                is UiState.Error -> {
                    // Esconde indicadores e exibe mensagem de erro
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}