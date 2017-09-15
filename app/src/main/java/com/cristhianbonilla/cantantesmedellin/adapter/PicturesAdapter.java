package com.cristhianbonilla.cantantesmedellin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristhianbonilla.cantantesmedellin.LoginActivity;
import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.fragments.FormNewGroupFragment;
import com.cristhianbonilla.cantantesmedellin.models.ImageUploaded;
import com.cristhianbonilla.cantantesmedellin.models.Images;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ASUS on 5/07/2017.
 */

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {


    List<Images> pictures;
    private Fragment fragment;
    private DatabaseReference mDatabaseRef;
    public static final  String GRUPO = "grupos";
    public static final  String FB_DATABASE_PATH = "image";
    private  String editar;

    private DatabaseReference refGroup;

    public PicturesAdapter(FormNewGroupFragment context, List<Images> imagenes, String editar) {
        this.pictures = imagenes;
        this.fragment = context;
        this.editar = editar;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_upload_pictures,parent,false);

        PictureViewHolder holder = new PictureViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, final int position) {

      //  Bitmap listPicture = pictures.get(position);

        final Images listaDeImagenes = pictures.get(position);

        ImageView imagenMiniatura = holder.imagen;
      //  imagenMiniatura.setImageBitmap(listaDeImagenes.getImagen());
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH+"/"+listaDeImagenes.getKey());
        refGroup = FirebaseDatabase.getInstance().getReference(GRUPO+"/"+listaDeImagenes.getKey()+"/"+"urlimagen");
        Picasso.with(fragment.getContext()).load(listaDeImagenes.getImgUri()).resize(200,200).into(imagenMiniatura);




        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editar != "editar"){

                    pictures.remove(pictures.get(position));
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, pictures.size());
                }else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(fragment.getContext());
                    builder1.setMessage("Estas apunto de eliminar esta imagen de la base de datos, seguro?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();





                                    mDatabaseRef.child(listaDeImagenes.getKeyGroup()).removeValue();

                                    pictures.remove(pictures.get(position));

                                    notifyItemRemoved(position);


// Iterate in reverse.
                                    for (int i = 0; i < pictures.size(); i++) {



                                        if (i == (pictures.size() - 1)) {
                                            refGroup.setValue(pictures.get(i).getImgUri().toString());

                                            pictures.get(i).getImgUri();


                                        }





                                    }

                                    notifyItemRangeChanged(position, pictures.size());

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public ImageView eliminar;

        public PictureViewHolder(View itemView) {
            super(itemView);

            imagen = (ImageView) itemView.findViewById(R.id.upload_imagen);
            eliminar = (ImageView) itemView.findViewById(R.id.delete_image);
        }
    }
}
