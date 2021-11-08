package br.com.alura.estoque.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.estoque.R;
import br.com.alura.estoque.model.Produto;
import br.com.alura.estoque.repository.ProdutosRepository;
import br.com.alura.estoque.ui.dialog.EditaProdutoDialog;
import br.com.alura.estoque.ui.dialog.SalvaProdutoDialog;
import br.com.alura.estoque.ui.recyclerview.adapter.ListaProdutosAdapter;

public class ListaProdutosActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lista de produtos";
    private static final String MENSAGEM_ERRO_BUSCA_PRODUTOS = "Não foi possível carregar os produtos novos";
    private static final String MENSAGEM_ERRO_REMOCAO_PRODUTO = "Não foi possível remover o produto";
    private static final String MENSAGEM_ERRO_INSERCAO_PRODUTO = "Não foi possível salvar o produto";
    private static final String MENSAGEM_ERRO_EDICAO_PRODUTO = "Não foi possível editar o produto";
    private ListaProdutosAdapter adapter;
    private ProdutosRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        setTitle(TITULO_APPBAR);

        configuraListaProdutos();
        configuraFabSalvaProduto();

        repository = new ProdutosRepository(this);
        buscaProdutos();
    }

    private void buscaProdutos() {
        extracted();
    }

    private void extracted() {
        repository.buscaProdutos(new ProdutosRepository.DadosCarregadosCallBack<List<Produto>>() {
            @Override
            public void quandoSucesso(List<Produto> produtosNovos) {
                adapter.atualiza(produtosNovos);
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this,
                        MENSAGEM_ERRO_BUSCA_PRODUTOS,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void configuraListaProdutos() {
        RecyclerView listaProdutos = findViewById(R.id.activity_lista_produtos_lista);
        adapter = new ListaProdutosAdapter(this, this::abreFormularioEditaProduto);
        listaProdutos.setAdapter(adapter);
        adapter.setOnItemClickRemoveContextMenuListener(
                ((posicao, produtoSelecionado) ->
                        removeProduto(posicao, produtoSelecionado))
        );
    }

    private void removeProduto(int posicao, Produto produtoSelecionado) {
        repository.remove(produtoSelecionado, new ProdutosRepository.DadosCarregadosCallBack<Void>() {
            @Override
            public void quandoSucesso(Void resultado) {
                adapter.remove(posicao);
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this,
                        MENSAGEM_ERRO_REMOCAO_PRODUTO,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void configuraFabSalvaProduto() {
        FloatingActionButton fabAdicionaProduto = findViewById(R.id.activity_lista_produtos_fab_adiciona_produto);
        fabAdicionaProduto.setOnClickListener(v -> abreFormularioSalvaProduto());
    }

    private void abreFormularioSalvaProduto() {
        new SalvaProdutoDialog(this, produto -> {
            salvaProduto(produto);
        }).mostra();
    }

    private void salvaProduto(Produto produto) {
        repository.salva(produto, new ProdutosRepository.DadosCarregadosCallBack<Produto>() {
            @Override
            public void quandoSucesso(Produto produtoSalvo) {
                adapter.adiciona(produtoSalvo);
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this,
                        MENSAGEM_ERRO_INSERCAO_PRODUTO,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abreFormularioEditaProduto(int posicao, Produto produto) {
        new EditaProdutoDialog(this, produto,
                produtoCriado -> editaProduto(posicao, produtoCriado))
                .mostra();
    }

    private void editaProduto(int posicao, Produto produtoCriado) {
        repository.edita(produtoCriado,
                new ProdutosRepository.DadosCarregadosCallBack<Produto>() {
                    @Override
                    public void quandoSucesso(Produto produtoEditado) {
                        adapter.edita(posicao, produtoEditado);
                    }

                    @Override
                    public void quandoFalha(String erro) {
                        Toast.makeText(ListaProdutosActivity.this,
                                MENSAGEM_ERRO_EDICAO_PRODUTO,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
