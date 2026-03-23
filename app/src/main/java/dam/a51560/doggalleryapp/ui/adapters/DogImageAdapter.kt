package dam.a51560.doggalleryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dam.a51560.doggalleryapp.data.models.DogImage
import dam.a51560.doggalleryapp.databinding.ItemDogImageBinding

/**
 * Adaptador para o RecyclerView que exibe o grid de imagens de cães.
 * Utiliza Glide para carregamento eficiente das imagens.
 *
 * @param onClick Callback invocado quando o utilizador clica numa imagem
 */
class DogImageAdapter(
    private val onClick: (DogImage) -> Unit
) : RecyclerView.Adapter<DogImageAdapter.DogImageViewHolder>() {

    /** Lista de imagens atualmente exibidas */
    private var images: List<DogImage> = emptyList()

    /**
     * Atualiza a lista de imagens exibidas.
     *
     * @param newImages Nova lista de imagens
     */
    fun submitList(newImages: List<DogImage>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogImageViewHolder {
        val binding = ItemDogImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    /**
     * ViewHolder para cada item do grid.
     * Responsável por carregar e exibir a imagem utilizando Glide.
     *
     * @param binding ViewBinding associada ao layout do item
     */
    inner class DogImageViewHolder(private val binding: ItemDogImageBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Associa uma imagem ao ViewHolder.
         *
         * @param dogImage Objeto DogImage a exibir
         */
        fun bind(dogImage: DogImage) {
            Glide.with(binding.root.context)
                .load(dogImage.url)
                .centerCrop()  // Garante que a imagem preenche o espaço uniformemente
                .into(binding.dogImageView)

            // Define o listener de clique no item
            binding.root.setOnClickListener {
                onClick(dogImage)
            }
        }
    }
}