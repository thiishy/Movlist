package com.thiishy.movlist.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.thiishy.movlist.R
import com.thiishy.movlist.databinding.ActivityRegistroBinding
import com.thiishy.movlist.model.Filme
import com.thiishy.movlist.viewmodel.FilmeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: FilmeViewModel by viewModels()

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) viewModel.setUriPoster(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.uriPoster.observe(this) { uri ->
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.outline_broken_image_24)
                .into(binding.imvAdicionarImagem)
        }

        binding.imvAdicionarImagem.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnRegistrarFilme.setOnClickListener {
            val titulo = binding.edtTituloFilme.text.toString()
            val genero = binding.edtGeneroFilme.text.toString()
            val link = binding.edtLinkFilme.text.toString()
            val assistido = binding.swtJaAssisti.isChecked

            val campos = listOf(
                binding.edtTituloFilme to "O título do(a) filme/série é obrigatório.",
                binding.edtGeneroFilme to "O gênero do(a) filme/série é obrigatório.",
                binding.edtLinkFilme to "O link do filme/série é obrigatório."
            )

            val todosValidos = campos.all { (edit, mensagem) ->
                if (edit.text.isBlank()) {
                    edit.error = mensagem
                    false
                } else true
            }

            if (todosValidos) {
                val flagReadOnly = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val uri = viewModel.uriPoster.value

                if (uri != null) {
                    try {
                        applicationContext.contentResolver.takePersistableUriPermission(
                            uri,
                            flagReadOnly
                        )
                    } catch (e: SecurityException) {
                        Log.e("PhotoPicker", "Erro ao persistir permissão", e)
                    }

                }

                val filme = Filme(0, titulo, genero, assistido, link, uri.toString())

                viewModel.insertMovie(filme)

                listOf(
                    binding.edtTituloFilme,
                    binding.edtGeneroFilme,
                    binding.edtLinkFilme
                ).forEach { it.text.clear() }

                Toast.makeText(this, "Filme/série registrada com sucesso!", Toast.LENGTH_SHORT)
                    .show()

                viewModel.setUriPoster(null)
                binding.imvAdicionarImagem.setImageResource(R.drawable.baseline_image_24)
            }
        }

        binding.btnListarFilmes.setOnClickListener {
            val intent = Intent(this, ListaFilmesActivity::class.java)
            startActivity(intent)
        }
    }
}