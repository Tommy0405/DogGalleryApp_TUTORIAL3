package dam.a51560.doggalleryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dam.a51560.doggalleryapp.data.models.Favorite
import dam.a51560.doggalleryapp.databinding.ItemFavoriteBinding

/**
 * Adaptador para o RecyclerView que exibe a lista de favoritos.
 * Cada item inclui a imagem e um botão de eliminação.
 *
 * @param onDeleteClick Callback invocado quando o utilizador clica no botão de eliminar
 */
class FavoriteAdapter(
    private val onDeleteClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    /** Lista de favoritos atualmente exibida */
    private var favorites: List<Favorite> = emptyList()

    /**
     * Atualiza a lista de favoritos exibida.
     *
     * @param newList Nova lista de favoritos
     */
    fun submitList(newList: List<Favorite>) {
        favorites = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int = favorites.size

    /**
     * ViewHolder para cada item da lista de favoritos.
     *
     * @param binding ViewBinding associada ao layout do item
     */
    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Associa um favorito ao ViewHolder.
         *
         * @param favorite Objeto Favorite a exibir
         */
        fun bind(favorite: Favorite) {
            Glide.with(binding.root.context)
                .load(favorite.url)
                .centerCrop()  // Garante que a imagem mantém proporção
                .into(binding.favoriteImageView)

            // Define o listener de clique no botão de eliminar
            binding.deleteButton.setOnClickListener {
                onDeleteClick(favorite)
            }
        }
    }
}