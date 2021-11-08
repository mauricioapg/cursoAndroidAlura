package br.com.alura.estoque.retrofit.callback;

import static br.com.alura.estoque.retrofit.callback.MensagensCallback.MENSAGEM_ERRO_FALHA_COMUNICACAO;
import static br.com.alura.estoque.retrofit.callback.MensagensCallback.MENSAGEM_ERRO_RESPOSTA_NAO_SUCEDIDA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackSemRetorno implements Callback<Void> {

    private final RespostaCallback callback;

    public CallbackSemRetorno(RespostaCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.isSuccessful()){
            callback.quandoSucesso();
        }
        else {
            callback.quandoFalha(MENSAGEM_ERRO_RESPOSTA_NAO_SUCEDIDA);
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        callback.quandoFalha(MENSAGEM_ERRO_FALHA_COMUNICACAO + t.getMessage());
    }

    public interface RespostaCallback{
        void quandoSucesso();
        void quandoFalha(String erro);
    }
}
