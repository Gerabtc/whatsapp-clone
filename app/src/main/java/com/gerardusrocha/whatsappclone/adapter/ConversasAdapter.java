package com.gerardusrocha.whatsappclone.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gerardusrocha.whatsappclone.R;
import com.gerardusrocha.whatsappclone.model.Conversa;
import com.gerardusrocha.whatsappclone.model.Usuario;

import java.util.List;
import java.util.PrimitiveIterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversasAdapter extends RecyclerView.Adapter<ConversasAdapter.MyViewHolder> {

    private List<Conversa> conversas;
    private Context context;

    public ConversasAdapter(List<Conversa> lista, Context c) {
        this.conversas = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.adapter_contatos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Conversa conversa = conversas.get(position);
        holder.ultimaMensagem.setText(conversa.getUltimaMensagem());

        Usuario usuario = conversa.getUsuarioExibicao();
        holder.nome.setText(usuario.getNome());

        if (usuario.getFoto() != null) {
            Uri uri = Uri.parse(usuario.getFoto());
            Glide.with(context).load(uri).into(holder.foto);
        }else {
            holder.foto.setImageResource(R.drawable.padrao);
        }

    }

    @Override
    public int getItemCount() {
        return conversas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView foto;
        TextView nome, ultimaMensagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           foto = itemView.findViewById(R.id.imageViewFotoContato);
           nome = itemView.findViewById(R.id.textNomeContato);
           ultimaMensagem = itemView.findViewById(R.id.textEmailContato);

        }
    }
}