package br.com.alura.estoque.retrofit.callback;

import static br.com.alura.estoque.retrofit.callback.MensagensCallback.MENSAGEM_ERRO_FALHA_COMUNICACAO;
import static br.com.alura.estoque.retrofit.callback.MensagensCallback.MENSAGEM_ERRO_RESPOSTA_NAO_SUCEDIDA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackComRetorno<T> implements Callback<T> {


    private final RespostaCallback callback;

    public CallbackComRetorno(RespostaCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T resultado = response.body();
        if(resultado != null){
            callback.quandoSucesso(resultado);
        }
        else {
            callback.quandoFalha(MENSAGEM_ERRO_RESPOSTA_NAO_SUCEDIDA);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha(MENSAGEM_ERRO_FALHA_COMUNICACAO + t.getMessage());
    }

    public interface RespostaCallback<T>{
        void quandoSucesso(T resultado);
        void quandoFalha(String erro);
    }
}
