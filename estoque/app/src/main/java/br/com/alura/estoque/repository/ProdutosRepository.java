package br.com.alura.estoque.repository;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import br.com.alura.estoque.asynctask.BaseAsyncTask;
import br.com.alura.estoque.database.dao.ProdutoDAO;
import br.com.alura.estoque.model.Produto;
import br.com.alura.estoque.retrofit.EstoqueRetrofit;
import br.com.alura.estoque.retrofit.service.ProdutoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ProdutosRepository {

    private final ProdutoDAO dao;
    private final ProdutoService service;

    public ProdutosRepository(ProdutoDAO dao) {
        this.dao = dao;
        service = new EstoqueRetrofit().getProdutoService();
    }

    public void buscaProdutos(DadosCarregadosListener<List<Produto>> listener) {
        buscaProdutosInternos(listener);
    }

    private void buscaProdutosInternos(DadosCarregadosListener<List<Produto>> listener) {
        new BaseAsyncTask<>(dao::buscaTodos,
                resultado -> {
                    listener.quandoCarregados(resultado);
                    buscaProdutosAPI(listener);
                } )
                .execute();
    }

    private void buscaProdutosAPI(DadosCarregadosListener<List<Produto>> listener) {

        Call<List<Produto>> call = service.buscaTodos();
        new BaseAsyncTask<>(() -> {
            try { //se tiver internet
                Response<List<Produto>> resposta = call.execute();
                List<Produto> produtosNovos = resposta.body();
                dao.salvaTodos(produtosNovos);
            } catch (IOException e) { //se falhar a internet
                e.printStackTrace();
            }
            return dao.buscaTodos();
        },produtosNovos ->
                listener.quandoCarregados(produtosNovos))
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void salva(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        salvaProdutoAPI(produto, callBack);
    }

    private void salvaProdutoAPI(Produto produto, DadosCarregadosCallBack<Produto> callBack) {
        Call<Produto> call = service.salva(produto);
        call.enqueue(new Callback<Produto>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if(response.isSuccessful()){
                    Produto produtoSalvo = response.body();
                    if(produtoSalvo != null){
                        salvaInterno(produtoSalvo, callBack);
                    }
                }
                else {
                    callBack.quandoFalha("Resposta não sucedida");
                }
            }



            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Produto> call, Throwable t) {
                callBack.quandoFalha("Falha de comunicação: " + t.getMessage());
            }
        });
    }

    private void salvaInterno(Produto produtoSalvo, DadosCarregadosCallBack<Produto> callBack) {
        new BaseAsyncTask<>(() -> {
            long id = dao.salva(produtoSalvo);
            return dao.buscaProduto(id);
        }, callBack::quandoSucesso).execute();
    }

    public interface DadosCarregadosListener<T>{
        void quandoCarregados(T resultado);
    }

    public interface DadosCarregadosCallBack<T>{
        void quandoSucesso(T resultado);
        void quandoFalha(String erro);
    }
}
