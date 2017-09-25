package com.cristhianbonilla.cantantesmedellin.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.fragments.DetailsFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.FormNewGroupFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.MainFragment;
import com.cristhianbonilla.cantantesmedellin.models.Grupo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2/07/2017.
 */

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GruposViewHolder> {
   private List<Grupo> grupos;
    private Fragment fragment;
    private int contdor = 0;

    public GrupoAdapter(List<Grupo> grupos, MainFragment mainFragment) {

        this.grupos = grupos;
        this.fragment = mainFragment;

    }

    @Override
    public GruposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group,parent,false);
        GruposViewHolder holder = new GruposViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(GruposViewHolder holder, int position) {
        final Grupo grupo = grupos.get(position);
        grupo.getImagen();
        Picasso.with(this.fragment.getContext()).load(grupo.getUrlimagen()).resize(300,300).into(holder.coverImageView);
        holder.tituloGrupo.setText(grupo.getNombre());


       FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        holder.coverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String key = grupo.getKey();
                String propietario = grupo.getPropietario();
                String nombreGrupo = grupo.getNombre();
                String telefonoGrupo  = grupo.getCelular();
                bundle.putString("key",key);
                bundle.putString("propietario",propietario );
                bundle.putString("nombreGrupo",nombreGrupo);
                bundle.putString("telefonoGrupo",telefonoGrupo);
                bundle.putString("nombreGrupo",nombreGrupo);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(fragment.getFragmentManager(),"cristhian");
            }
        });

        if(usuario.getUid().equals(grupo.getPropietario())||usuario.getUid().equals("mMugUhhgaZOCUDhVSNHfr4Ax8I83")){

            holder.editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String key = grupo.getKey();
                    String nombreGrupo = grupo.getNombre();
                    String telefonoGrupo  = grupo.getCelular();
                    String categoria = grupo.getCategoria();
                    String celular = grupo.getCelular();
                    String descripcion = grupo.getDescripcion();
                    String nombreContacto = grupo.getNombreContacto();
                    String socialF = grupo.getSocialF();
                    String url = grupo.getImagen();
                    String youtube = grupo.getYouTube();
                    String fijo = grupo.getFijo();
                    String email = grupo.getEmail();
                    String propietario = grupo.getPropietario();

                    bundle.putString("key",key);
                    bundle.putString("editar","editar");
                    bundle.putString("nombreGrupo",nombreGrupo);
                    bundle.putString("categoria",categoria);
                    bundle.putString("celular",celular);
                    bundle.putString("descripcion",descripcion);
                    bundle.putString("telefonoGrupo",telefonoGrupo);
                    bundle.putString("socialF",socialF);
                    bundle.putString("url",url);
                    bundle.putString("youtube",youtube);
                    bundle.putString("fijo",fijo);
                    bundle.putString("email",email);
                    bundle.putString("nombreContacto",nombreContacto);
                    bundle.putString("propietario",propietario);

                    FormNewGroupFragment formNewGroupFragment = new FormNewGroupFragment();
                    formNewGroupFragment.setArguments(bundle);
                    formNewGroupFragment.show(fragment.getFragmentManager(),"Editar");
                }
            });
        }else{

            holder.editar.setVisibility(View.GONE);


        }





    }



    @Override
    public int getItemCount() {
        return grupos.size();
    }

    public void setFilter(List<Grupo> newList) {

        grupos = new ArrayList<>();

        grupos.addAll(newList);
        notifyDataSetChanged();



    }

    public static class GruposViewHolder extends RecyclerView.ViewHolder{

        private ImageView coverImageView,editar;
        private TextView tituloGrupo;

        public GruposViewHolder(View itemView) {
            super(itemView);
            coverImageView = (ImageView) itemView.findViewById(R.id.coverImageView);
            tituloGrupo = (TextView) itemView.findViewById(R.id.tituloGrupo);
            editar = (ImageView) itemView.findViewById(R.id.edit_grupo);
        }

    }
}
