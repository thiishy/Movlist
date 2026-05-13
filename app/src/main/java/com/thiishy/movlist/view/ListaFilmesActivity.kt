package com.thiishy.movlist.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thiishy.movlist.adapter.FilmeAdapter
import com.thiishy.movlist.databinding.ActivityListaFilmesBinding
import com.thiishy.movlist.viewmodel.FilmeViewModel

class ListaFilmesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaFilmesBinding

    private val viewModel: FilmeViewModel by viewModels()

    private lateinit var filmeAdapter: FilmeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaFilmesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerviewFilmes.layoutManager = LinearLayoutManager(this)

        filmeAdapter = FilmeAdapter(
            { filme, isChecked -> viewModel.alterWatchedStatusByUid(filme, isChecked) },
            { filme -> viewModel.removeMovie(filme) }
        )

        binding.recyclerviewFilmes.adapter = filmeAdapter

        viewModel.filmes.observe(this) { filmes ->
            filmeAdapter.submitList(filmes)
        }

        binding.fabVoltar.setOnClickListener {
            finish()
        }
    }
}