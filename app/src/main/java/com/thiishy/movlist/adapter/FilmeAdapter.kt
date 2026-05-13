package com.thiishy.movlist.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thiishy.movlist.R
import com.thiishy.movlist.databinding.ItemFilmeBinding
import com.thiishy.movlist.model.Filme
import com.thiishy.movlist.view.WebviewActivity

class FilmeAdapter(
    private val assistido: (Filme, Boolean) -> Unit,
    private val excluir: (Filme) -> Unit
) : ListAdapter<Filme, FilmeAdapter.ViewHolder>(FilmeDiffCallback()) {

    class ViewHolder(val binding: ItemFilmeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val filme = getItem(position)
        val binding = holder.binding

        Glide.with(holder.itemView.context)
            .load(filme.poster)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.outline_broken_image_24)
            .into(binding.imvPoster)

        binding.swtAssistido.setOnCheckedChangeListener(null)

        binding.txvFilme.text = context.getString(R.string.titulo_com_valor, filme.titulo)
        binding.txvGenero.text = context.getString(R.string.genero_com_valor, filme.genero)

        binding.swtAssistido.isChecked = filme.assistido

        binding.swtAssistido.setOnCheckedChangeListener { _, isChecked ->
            assistido(filme, isChecked)
        }

        binding.imvApagar.setOnClickListener {
            excluir(filme)
        }

        binding.btnAssistir.setOnClickListener {
            val intent = Intent(context, WebviewActivity::class.java).apply {
                putExtra("URL_FILME", filme.link)
            }

            context.startActivity(intent)
        }
    }
}