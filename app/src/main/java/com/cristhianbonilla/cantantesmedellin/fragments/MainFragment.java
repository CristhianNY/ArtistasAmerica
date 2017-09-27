package com.cristhianbonilla.cantantesmedellin.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cristhianbonilla.cantantesmedellin.MainActivity;
import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.GrupoAdapter;
import com.cristhianbonilla.cantantesmedellin.models.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private GrupoAdapter adapter;
    private  int contador =0;
    private String key;
    List<Grupo> grupos;
    private String categoria;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v =  inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclergroupos);

        categoria = this.getArguments().getString("categoria");
        recyclerView.setHasFixedSize(true);


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);


      /**layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (position % 5) {
                    // first two items span 3 columns each
                    case 0:
                    case 1:
                        return 3;
                    // next 3 items span 2 columns each
                    case 2:
                    case 3:
                    case 4:
                        return 2;
                }
                throw new IllegalStateException("internal error");
            }

        });*/
       // LinearLayoutManager mManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
     //   mManager.setStackFromEnd(true);

        grupos = new ArrayList<>();

        cargarTodosLosGrupos(categoria);


                return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_buscar, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

    }
    private void cargarTodosLosGrupos(String categoria) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.GRUPOS);

        adapter = new GrupoAdapter(grupos, this);

        recyclerView.setAdapter(adapter);
        ref.orderByChild("categoria").equalTo(categoria).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                grupos.removeAll(grupos);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    // System.out.print(snapshot.getValue());


                    contador++;
                    Grupo grupo = snapshot.getValue(Grupo.class);
                    if(contador == 1){
                        System.out.print(snapshot.getKey());
                        key = snapshot.getKey();

                    }

                    grupos.add(grupo);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<Grupo> newList = new ArrayList<>();

        for (Grupo grupo2 : grupos){
            String titulo = grupo2.getNombre().toLowerCase();

            if(titulo.contains(newText)){
                newList.add(grupo2);
            }

            adapter.setFilter(newList);

        }



        return true;
    }
}
