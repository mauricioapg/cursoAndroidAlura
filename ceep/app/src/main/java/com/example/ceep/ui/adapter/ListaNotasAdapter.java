package com.example.ceep.ui.adapter;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.listener.OnItemClickListener;
import com.example.ceep.ui.adapter.listener.OnLongClickListener;

import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final List<Nota> notas;
    private Context context;
    private int qtdViewCriado = 0;
    private OnItemClickListener onItemClickListener;
    private OnLongClickListener onLongClickListener;

    public ListaNotasAdapter(Context context, List<Nota> listaNotas) {
        this.context = context;
        this.notas = listaNotas;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        qtdViewCriado++;
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, parent, false);
        Log.i("adapter", "quantidade view: " + qtdViewCriado);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyItemRemoved(posicao); //remoção mais fluida
    }

    public void trocar(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal); //troca mais fluida
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(nota, getAdapterPosition());
                }
            });

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    Toast.makeText(context, "Clique longo", Toast.LENGTH_SHORT).show();
//                    return true; //isso impede que execute também a ação de clique simples
//                }
//            });
        }

        public void vincula(Nota nota){
            this.nota = nota;
            preencherCampos(nota);
        }

        private void preencherCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void adicionarBanco(Nota nota){
        notas.add(nota);
        notifyDataSetChanged();
    }

    public void alterarBanco(int posicao, Nota nota) {
        notas.set(posicao, nota);
        notifyDataSetChanged();
    }

    public void removerBanco(int posicao) {
        notas.remove(posicao);
        notifyDataSetChanged();
    }
}
