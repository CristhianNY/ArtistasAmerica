package com.cristhianbonilla.cantantesmedellin.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.SliderAdapter;
import com.cristhianbonilla.cantantesmedellin.models.ImageUploaded;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetallesLeads extends DialogFragment {


    private String key;
    private String ciudad;
    private String categoriaEvento;
    private String fecha;
    private String hora;
    private String direccion;
    private String telefono;
    private String nombreGroup;
    private TextView ciudadEvent,categoriaEvent,fechaEvent,horaEvent,direccionevent,telefonoEvent,nombreGrupo, tituloGrupo;
    private ImageButton llamarBtn;
    private String idUsuario;
    private FirebaseUser usuario;
    private String telefonoGrupo;
    private ViewPager viewPager;

    SliderAdapter adapter;
    private ArrayList<String> IMAGES = new ArrayList<>();


    public DetallesLeads() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_detalles_leads, container, false);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        key = this.getArguments().getString("key");
        categoriaEvento =  this.getArguments().getString("categoria");
        ciudad = this.getArguments().getString("ciudad");
        fecha = this.getArguments().getString("fecha");
        hora = this.getArguments().getString("hora");
        direccion = this.getArguments().getString("direccion");
        telefono = this.getArguments().getString("telefono");
        telefonoGrupo = this.getArguments().getString("telefonoGrupo");
        nombreGroup = this.getArguments().getString("nombreGrupo");
        idUsuario = this.getArguments().getString("idUsuario");




        viewPager =(ViewPager) v.findViewById(R.id.view_pager);
        tituloGrupo = (TextView) v.findViewById(R.id.tituloDetalles);
        categoriaEvent = (TextView) v.findViewById(R.id.categoria_Lead);
        ciudadEvent = (TextView) v.findViewById(R.id.ciudad_lead);
        fechaEvent = (TextView) v.findViewById(R.id.fecha_lead);
        horaEvent = (TextView) v.findViewById(R.id.hora_lead);
        direccionevent = (TextView) v.findViewById(R.id.direccion_lead);
        telefonoEvent = (TextView) v.findViewById(R.id.telefono_lead);

        llamarBtn = (ImageButton) v.findViewById(R.id.llamar_grupo);

        if(idUsuario.equals(usuario.getUid())){
            telefonoEvent.setText("Telefono de Contacto :"+ telefono);


        }else{

            telefonoEvent.setText("Telefono de contacto : "+telefonoGrupo);

        }

        tituloGrupo.setText(nombreGroup);
        categoriaEvent.setText("Categoria: "+categoriaEvento);
        ciudadEvent.setText("Ciurdad: "+ciudad);
        fechaEvent.setText("Fecha: "+fecha);
        horaEvent.setText("Hora de otra: "+hora);
        direccionevent.setText("Dirección: "+direccion);



        llamarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idUsuario.equals(usuario.getUid())){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telefono));
                    startActivity(callIntent);



                }else{
                    Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                    callIntent2.setData(Uri.parse("tel:"+telefonoGrupo));
                    startActivity(callIntent2);

                }

            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.IMAGES+"/"+key);



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {




                    ImageUploaded imageUploaded = snapshot.getValue(ImageUploaded.class);

                    System.out.print(imageUploaded.getUrl());



                    //&    imagenes.add(imageUploaded);
                    IMAGES.add(imageUploaded.getUrl());

                    // arrayListDeImagenes.add(imageUploaded.getUrl());
                }



                adapter = new SliderAdapter(getContext(),key, IMAGES);


                viewPager.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.print("error al traer imagenes ");
            }
        });







        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
