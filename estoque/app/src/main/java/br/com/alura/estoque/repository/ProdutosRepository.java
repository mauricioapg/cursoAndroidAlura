package br.com.alura.estoque.repository;

import android.content.Context;

import java.util.List;

import br.com.alura.estoque.asynctask.BaseAsyncTask;
import br.com.alura.estoque.database.EstoqueDatabase;
import br.com.alura.estoque.database.dao.ProdutoDAO;
import br.com.alura.estoque.model.Produto;
import br.com.alura.estoque.retrofit.EstoqueRetrofit;
import br.com.alura.estoque.retrofit.callback.CallbackComRetorno;
import br.com.alura.estoque.retrofit.callback.CallbackSemRetorno;
import br.com.alura.estoque.retrofit.service.ProdutoService;
import retrofit2.Call;

public class ProdutosRepository {

    private final ProdutoDAO dao;
    private final ProdutoService service;

    public ProdutosRepository(Context context) {
        EstoqueDatabase db = EstoqueDatabase.getInstance(context);
        dao = db.getProdutoDAO();
        service = new EstoqueRetrofit().getProdutoService();
    }

    public void buscaProdutos(DadosCarregadosCallBack<List<Produto>> callBack) {
        buscaProdutosInternos(callBack);
    }

    private void buscaProdutosInternos(DadosCarregadosCallBack<List<Produto>> callBack) {
        new BaseAsyncTask<>(dao::buscaTodos,
                resultado -> {
                    callBack.quandoSucesso(resultado);
                    buscaProdutosAPI(callBack);
                } )
                .execute();
    }

    private void buscaProdutosAPI(DadosCarregadosCallBack<List<Produto>> callBack) {

        Call<List<Produto>> call = service.buscaTodos();
        call.enqueue(new CallbackComRetorno<>(new CallbackComRetorno.RespostaCallback<List<Produto>>() {
            @Override
            public void quandoSucesso(List<Produto> produtosNovos) {
                atualizaInterno(produtosNovos, callBack);
            }

            @Override
            public void quandoFalha(String erro) {
                callBack.quandoFalha(erro);
            }
        }));
    }

    public void salva(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        salvaProdutoAPI(produto, callBack);
    }

    private void salvaProdutoAPI(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        Call<Produto> call = service.salva(produto);
        call.enqueue(new CallbackComRetorno<>(new CallbackComRetorno.RespostaCallback<Produto>() {
            @Override
            public void quandoSucesso(Produto produtoSalvo) {
                salvaInterno(produtoSalvo, callBack);
            }

            @Override
            public void quandoFalha(String erro) {
                callBack.quandoFalha(erro);
            }
        }));
    }

    private void salvaInterno(Produto produtoSalvo, DadosCarregadosCallBack<Produto> callBack) {
        new BaseAsyncTask<>(() -> {
            long id = dao.salva(produtoSalvo);
            return dao.buscaProduto(id);
        }, callBack::quandoSucesso).execute();
    }

    private void atualizaInterno(List<Produto> produtosNovos, DadosCarregadosCallBack<List<Produto>> callBack) {
        new BaseAsyncTask<>(() -> {
            dao.salvaTodos(produtosNovos);
            return  dao.buscaTodos();
        }, callBack::quandoSucesso).execute();
    }

    private void editaInterno(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        new BaseAsyncTask<>(() -> {
            dao.atualiza(produto);
            return produto;
        }, callBack::quandoSucesso).execute();
    }

    public void edita(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        editaProdutoAPI(produto, callBack);
    }

    public void editaProdutoAPI(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        Call<Produto> call = service.edita(produto.getId(), produto);
        call.enqueue(new CallbackComRetorno<>(new CallbackComRetorno.RespostaCallback<Produto>() {
            @Override
            public void quandoSucesso(Produto produtoEditado) {
                editaInterno(produto, callBack);
            }

            @Override
            public void quandoFalha(String erro) {
                callBack.quandoFalha(erro);
            }
        }));
    }

    public void remove(Produto produtoSelecionado,
                       DadosCarregadosCallBack<Void> callBack) {

        removeProdutoAPI(produtoSelecionado, callBack);
    }

    private void removeProdutoAPI(Produto produtoSelecionado, DadosCarregadosCallBack<Void> callBack) {
        Call<Void> call = service.remove(produtoSelecionado.getId());
        call.enqueue(new CallbackSemRetorno(new CallbackSemRetorno.RespostaCallback() {
            @Override
            public void quandoSucesso() {
                removeInterno(produtoSelecionado, callBack);
            }

            @Override
            public void quandoFalha(String erro) {
                callBack.quandoFalha(erro);
            }
        }));
    }

    private void removeInterno(Produto produtoSelecionado, DadosCarregadosCallBack<Void> callBack) {
        new BaseAsyncTask<>(() -> {
            dao.remove(produtoSelecionado);
            return null;
        }, callBack::quandoSucesso)
                .execute();
    }

    public interface DadosCarregadosCallBack<T>{
        void quandoSucesso(T resultado);
        void quandoFalha(String erro);
    }
}
