package com.example.ceep.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.dao.NotaDAO;
import com.example.ceep.ui.adapter.ListaNotasAdapter;

public class NotaItemtouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapter adapter;

    public NotaItemtouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoesArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesArrastar, marcacoesDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocarNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocarNotas(int posicaoInicial, int posicaoFinal) {
        new NotaDAO().trocar(posicaoInicial, posicaoFinal);
        adapter.trocar(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoNotaDeslizada = viewHolder.getAdapterPosition();
        removerNotas(posicaoNotaDeslizada);
    }

    private void removerNotas(int posicaoNotaDeslizada) {
        new NotaDAO().remover(posicaoNotaDeslizada);
        adapter.remove(posicaoNotaDeslizada);
    }
}
