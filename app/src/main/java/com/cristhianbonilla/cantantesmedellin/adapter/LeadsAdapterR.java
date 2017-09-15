package com.cristhianbonilla.cantantesmedellin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.fragments.DetallesLeads;
import com.cristhianbonilla.cantantesmedellin.fragments.LeadListFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.LeadListFragmentR;
import com.cristhianbonilla.cantantesmedellin.models.Lead;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cali1 on 13/09/2017.
 */




public class LeadsAdapterR extends RecyclerView.Adapter<LeadsAdapterR.LeadViewAdapter> {



    private List<Lead> listaLeads;
    private Fragment fragment;



    public LeadsAdapterR(List<Lead> leads, LeadListFragmentR leadListFragmentR) {

        this.listaLeads = leads;
        this.fragment = leadListFragmentR;
    }


    @Override
    public LeadViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_leads, parent, false);
        LeadViewAdapter holder = new LeadViewAdapter(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LeadViewAdapter holder, int position) {
        final Lead lead = listaLeads.get(position);
        holder.categoriaEvento.setText(lead.getCategoriaEvento());
        holder.fechaEvento.setText(lead.getFecha());
        Picasso.with(fragment.getContext()).load(lead.getImagenPerfil()).resize(200,200).into( holder.imagenPerfil);
        holder.descripcionEvento.setText(lead.getDescripcion());
        holder.leadDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String key = lead.getKey();
                String categoria = lead.getCategoriaEvento();
                String ciudad = lead.getCiudad();
                String fecha = lead.getFecha();
                String hora = lead.getHora();
                String direccion = lead.getDireccion();
                String telefono = lead.getTelefono();
                String telefonoGrupo = lead.getTelefonoGrupo();
                String nombreGrupo = lead.getNombreGrupo();
                String idUsuario = lead.getIdUsuario();
                bundle.putString("idUsuario",idUsuario);
                bundle.putString("categoria",categoria);
                bundle.putString("key",key);
                bundle.putString("ciudad",ciudad);
                bundle.putString("telefonoGrupo",telefonoGrupo);
                bundle.putString("fecha",fecha);
                bundle.putString("hora",hora);
                bundle.putString("direccion",direccion);
                bundle.putString("telefono",telefono);
                bundle.putString("nombreGrupo",nombreGrupo);

                DetallesLeads detallesLeads = new DetallesLeads();
                detallesLeads.setArguments(bundle);
                detallesLeads.show(fragment.getFragmentManager(),"DetallesLeads");


            }
        });


    }

    @Override
    public int getItemCount() {
        return listaLeads.size();
    }

    public static class LeadViewAdapter extends RecyclerView.ViewHolder{


        private TextView categoriaEvento;
        private TextView fechaEvento;
        private TextView descripcionEvento;
        private ImageView imagenPerfil;
        private RelativeLayout leadDetalle;
        public LeadViewAdapter(View itemView) {
            super(itemView);

            categoriaEvento= (TextView) itemView.findViewById(R.id.categoria_del_evento);
            fechaEvento = (TextView) itemView.findViewById(R.id.fecha_evento);
            descripcionEvento =(TextView) itemView.findViewById(R.id.descripcion_evento);
            leadDetalle = (RelativeLayout) itemView.findViewById(R.id.lead_detalle);
            imagenPerfil = (ImageView) itemView.findViewById(R.id.img_perfil_lead);
        }
    }
}

