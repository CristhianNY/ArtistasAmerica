package com.cristhianbonilla.cantantesmedellin.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.cristhianbonilla.cantantesmedellin.MainActivity;
import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.GrupoAdapter;
import com.cristhianbonilla.cantantesmedellin.adapter.PrincipalAdapter;
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
public class Principal extends Fragment {

    private Spinner categoria;
    private RecyclerView recyclerView;
    private PrincipalAdapter adapter;
    List<Grupo> grupos;
    private  int contador =0;
    private String key;

    public Principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_principal, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclergroupos_destacados);
        categoria = (Spinner) v.findViewById(R.id.lista_categoria);
        ((MainActivity) getActivity())
                .setActionBarTitle("Cantantes Medellín");
        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equals("Mariachi")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Mariachis");
                    Bundle bundle = new Bundle();
                    String categoria = "Mariachi";
                    bundle.putString("categoria", categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

                }else if (selectedItem.equals("Agrupaciones")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Agrupaciones");
                   // setActionBarTitle("Agrupaciones");
                    Bundle bundle = new Bundle();
                    String categoria = "Agrupaciones";
                    bundle.putString("Agrupaciones",categoria);
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                    ///  formNewGroupFragment.show();
                    // Handle the camera action


                } else if (selectedItem.equals("Conjustos vallenatos")) {
                   // setActionBarTitle("Vallenatos");
                    ((MainActivity) getActivity())
                            .setActionBarTitle("COnjuntos Vallenatos");
                    Bundle bundle = new Bundle();
                    String categoria = "Conjuntos vallenatos";
                    bundle.putString("coros",categoria);
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

                } else if (selectedItem.equals("Coros")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Coros");
                    //setActionBarTitle("Coros");
                    Bundle bundle = new Bundle();
                    String categoria = "Coros";
                    bundle.putString("coros",categoria);
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

                } else if (selectedItem.equals("Cantantes de reggaeton")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Cantantes de reggaeton");
                   // setActionBarTitle("Reggaeton");
                    Bundle bundle = new Bundle();
                    String categoria = "Cantantes de reggaeton";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();



                } else if (selectedItem.equals("Duetos y Trios")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Duetos y Trios");
                   // setActionBarTitle("Trios y Duetos");
                    Bundle bundle = new Bundle();
                    String categoria = "Duetos y Trios";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }
                else if (selectedItem.equals("Bailarines")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Bailarines");
                    //setActionBarTitle("Bailarines");
                    Bundle bundle = new Bundle();
                    String categoria = "Bailarines";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }
                else if (selectedItem.equals("Escuelas de música")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Escuela de música");
                  //  setActionBarTitle("Escuela de musica");
                    Bundle bundle = new Bundle();
                    String categoria = "Escuelas de música";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if (selectedItem.equals("Instrumentales")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Instrumentales");
                  //  setActionBarTitle("Intrumentales");
                    Bundle bundle = new Bundle();
                    String categoria = "Instrumentales";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if (selectedItem.equals("Música infantil")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Música infantil");
                    //setActionBarTitle("Musica Infantil");
                    Bundle bundle = new Bundle();
                    String categoria = "Música infantil";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if (selectedItem.equals("Minitecas")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Minitecas");
                   // setActionBarTitle("Minitecas");
                    Bundle bundle = new Bundle();
                    String categoria = "Minitecas";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if (selectedItem.equals("Orquestas")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Orquestas");
                  //  setActionBarTitle("Orquestas");
                    Bundle bundle = new Bundle();
                    String categoria = "Orquestas";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if (selectedItem.equals("Papayeras")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Papayeras");
                   // setActionBarTitle("Papayeras");
                    Bundle bundle = new Bundle();
                    String categoria = "Papayeras";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if (selectedItem.equals("Solistas")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Solistas");
                  //  setActionBarTitle("Solistas");
                    Bundle bundle = new Bundle();
                    String categoria = "Solistas";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }else if(selectedItem.equals("Trovadores")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Trovadores");
                   // setActionBarTitle("Trovadores");
                    Bundle bundle = new Bundle();
                    String categoria = "Trovadores";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();




                }else if (selectedItem.equals("Duetos Y Trios")) {
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Duetos Y Trios");
                  //  setActionBarTitle("Duetos y trios");
                    Bundle bundle = new Bundle();
                    String categoria = "Duetos y Trios";
                    bundle.putString("categoria",categoria);
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(bundle);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


                }

                GridLayoutManager layoutManager = new GridLayoutManager(getContext(),6);
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        // 5 is the sum of items in one repeated section
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
                });
                recyclerView.setLayoutManager(layoutManager);

                grupos = new ArrayList<>();


                cargarTodosLosGrupos();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return v;
    }

    private void cargarTodosLosGrupos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.GRUPOS);
        adapter = new PrincipalAdapter(grupos, this);

        recyclerView.setAdapter(adapter);

        ref.orderByChild("destacado").equalTo("si").addValueEventListener(new ValueEventListener() {
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

}
