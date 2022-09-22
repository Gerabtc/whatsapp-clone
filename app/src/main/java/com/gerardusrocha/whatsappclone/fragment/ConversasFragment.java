package com.gerardusrocha.whatsappclone.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.gerardusrocha.whatsappclone.R;
import com.gerardusrocha.whatsappclone.activity.ChatActivity;
import com.gerardusrocha.whatsappclone.adapter.ConversasAdapter;
import com.gerardusrocha.whatsappclone.config.ConfiguracaoFirebase;
import com.gerardusrocha.whatsappclone.helper.RecyclerItemClickListener;
import com.gerardusrocha.whatsappclone.helper.UsuarioFirebase;
import com.gerardusrocha.whatsappclone.model.Conversa;
import com.gerardusrocha.whatsappclone.model.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ConversasFragment extends Fragment {

    private RecyclerView recyclerViewListaConversas;
    private ConversasAdapter adapter;
    private List<Conversa> listaConversas = new ArrayList<>();
    private DatabaseReference database;
    private DatabaseReference conversasRef;
    private ChildEventListener childEventListenerConversas;

    public ConversasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerViewListaConversas = view.findViewById(R.id.recyclerViewListaConversas);

        adapter = new ConversasAdapter(listaConversas, getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewListaConversas.setLayoutManager(layoutManager);
        recyclerViewListaConversas.setHasFixedSize(true);
        recyclerViewListaConversas.setAdapter(adapter);

        recyclerViewListaConversas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewListaConversas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Conversa conversaSelecionada = listaConversas.get(position);

                                Intent i = new Intent(getActivity(), ChatActivity.class);
                                i.putExtra("chatContato", conversaSelecionada.getUsuarioExibicao());
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                ));

        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        database = ConfiguracaoFirebase.getFirebaseDatabase();
        conversasRef = database.child("conversas")
                .child(identificadorUsuario);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarConversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        conversasRef.removeEventListener(childEventListenerConversas);
    }

    public void recuperarConversas() {

        childEventListenerConversas = conversasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Conversa conversa = snapshot.getValue(Conversa.class);
                listaConversas.add(conversa);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}