package com.cristhianbonilla.cantantesmedellin.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.ComentariosAdapter;
import com.cristhianbonilla.cantantesmedellin.adapter.SliderAdapter;
import com.cristhianbonilla.cantantesmedellin.models.Comentario;
import com.cristhianbonilla.cantantesmedellin.models.Grupo;
import com.cristhianbonilla.cantantesmedellin.models.ImageUploaded;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends DialogFragment implements BookingFragment.Communicator {

    SliderAdapter adapter;
    ViewPager viewPager;
    private ArrayList<String> IMAGES = new ArrayList<>();
    private ArrayList<String> arrayListDeImagenes;
    private RatingBar rb, ratingBarEdit,ratingBarEdit2;
    private FirebaseDatabase database;
    private RecyclerView recyclerView, recyclerView2;
    private ComentariosAdapter adapterComentarios;
    private List<Comentario> comentarios;
    private Button btnInsertarReview;
    private EditText contectReview,contenidoreview;
    private FirebaseUser usuario;
    private ImageButton btnYoutube;
    private TextView tituloDetalles;
    private Float calificacion = Float.parseFloat("0");
    private int contador2 = 0;
    private TextView contentDescripcion , numeroCelular;
    private Button btnCotizar,verComentarios, insertComentario;
    private String nombreGrupo;
    private String telefonoGrupo, propietario;
    private ImageButton rightNav, leftNav;



    private String key;
    public DetailsFragment() {
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
      View v = inflater.inflate(R.layout.fragment_details, container, false);
        key = this.getArguments().getString("key");
        nombreGrupo = this.getArguments().getString("nombreGrupo");
        telefonoGrupo = this.getArguments().getString("telefonoGrupo");
        propietario = this.getArguments().getString("propietario");

        usuario = FirebaseAuth.getInstance().getCurrentUser();



        rightNav = (ImageButton) v.findViewById(R.id.right_nav);
        leftNav = (ImageButton) v.findViewById(R.id.left_nav);

        viewPager =(ViewPager) v.findViewById(R.id.view_pager);
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });

        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_comentarios);
        rb = (RatingBar) v.findViewById(R.id.rating);
        ratingBarEdit = (RatingBar) v.findViewById(R.id.idRating);
        btnYoutube = (ImageButton) v.findViewById(R.id.btn_youtube);
        btnCotizar = (Button) v.findViewById(R.id.btn_cotizar);

        verComentarios = (Button) v.findViewById(R.id.ver_comentarios);
        numeroCelular = (TextView) v.findViewById(R.id.numero_celular);
        PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber pn = null;
        String numberStr = "8885551234";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(telefonoGrupo, "US");
            //Since you know the country you can format it as follows:
            System.out.println(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
            numeroCelular.setText(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        try {
            pn = pnu.parse(telefonoGrupo, "US");
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        String pnE164 = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
        //   numeroCelular.setText(pnE164);

        verComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.comentarios_alert);
                recyclerView2 = (RecyclerView) dialog.findViewById(R.id.rv_comentarios2);
                recyclerView2.setHasFixedSize(true);
                recyclerView2.setNestedScrollingEnabled(false);
                LinearLayoutManager mManager = new LinearLayoutManager(getActivity());

                recyclerView2.setLayoutManager(mManager);
                mManager.setReverseLayout(true);
                mManager.setStackFromEnd(true);

                recyclerView2.setNestedScrollingEnabled(false);
                cargarTodosLosReviews(key);

                dialog.show();
            }
        });

        btnCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
               // String key = grupo.getKey();

                bundle.putString("key",key);
                bundle.putString("nombreGrupo",nombreGrupo);
                bundle.putString("telefonoGrupo",telefonoGrupo);



                BookingFragment bookingFragment = new BookingFragment();
                bookingFragment.show(getFragmentManager(),"lead");
                bookingFragment.setArguments(bundle);
                dismiss();

                //Fragment.show(v.get.getFragmentManager(),"cristhian");

            }
        });


        tituloDetalles = (TextView) v.findViewById(R.id.tituloDetalles);
        contentDescripcion = (TextView) v.findViewById(R.id.content_descripcion);


      btnInsertarReview = (Button) v.findViewById(R.id.btn_enviar_review);
        if(usuario.getUid().equals(propietario)){


            btnInsertarReview.setVisibility(View.GONE);
            btnCotizar.setVisibility(View.GONE);

        }
        btnInsertarReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.new_review);

                contenidoreview = (EditText) dialog.findViewById(R.id.content_review);
                ratingBarEdit2 = (RatingBar) dialog.findViewById(R.id.idRating);

                insertComentario = (Button) dialog.findViewById(R.id.insertComentario);

                insertComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(contenidoreview.getText().length() != 0 && ratingBarEdit2.getRating()!=0){
                            insertarReview(key, contenidoreview.getText().toString(),ratingBarEdit2.getRating());

                            dialog.dismiss();

                        }else{

                            if(contenidoreview.getText().length() <= 0){
                                contenidoreview.setError("Por favor escribe un comentario");

                            }

                            if(ratingBarEdit2.getRating() <= 0){

                                TextView errorEstrellas = (TextView) dialog.findViewById(R.id.error_estrellas);
                                errorEstrellas.setError("Selecciona una calificacion por favor ");

                            }
                        }

                    }
                });


                dialog.show();


            }
        });

        contectReview = (EditText) v.findViewById(R.id.content_review);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mManager);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        recyclerView.setNestedScrollingEnabled(false);
        comentarios = new ArrayList<>();
        cargarReviews(key);
        cargarInformacion(key);
        comentarios = new ArrayList<>();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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


   /**     btnInsertarReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // insertarReview(key);
                insertarReview(key);
            }
        });**/



        return v;
    }




    private void insertarReview(final String key, final String contenido, final Float calificacion) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference ref = database.getReference(References.COMENTARIOS + "/" + key);
        adapterComentarios = new ComentariosAdapter(comentarios,this);
        Toast.makeText(getContext(),key ,Toast.LENGTH_LONG).show();

        recyclerView.setAdapter(adapterComentarios);
        if (AccessToken.getCurrentAccessToken() != null) {

            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {

                            if (AccessToken.getCurrentAccessToken() != null) {

                                if (me != null) {

                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            String profileImageUrl = ImageRequest.getProfilePictureUri(me.optString("id"), 500, 500).toString();
                                            // Log.i(LOG_TAG, profileImageUrl);
                                            String comentariokey = ref.push().getKey();

                                            String valorCalificacion = Float.toString(calificacion);

                                            Comentario comment = new Comentario(profileImageUrl,
                                                    me.optString("name"), new Date().toString(),comentariokey,usuario.getUid(),contenido,key,valorCalificacion);

                                         //   contectReview.setText("");
                                            ref.child(usuario.getUid()).setValue(comment);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });





                                }
                            }
                        }
                    });
            GraphRequest.executeBatchAsync(request);
        }
    }

    private void cargarInformacion(String key) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.GRUPOS);
        ref.orderByChild("key").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {

                    final Grupo infoGrupo = snapshot.getValue(Grupo.class);

               // System.out.print(infoGrupo.getCelular());
                   tituloDetalles.setText(infoGrupo.getNombre());
                    contentDescripcion.setText(infoGrupo.getDescripcion());
                    btnYoutube.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        Boolean validaUrl =    URLUtil.isValidUrl(infoGrupo.getYouTube());

                            if(validaUrl == true){
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(infoGrupo.getYouTube())));
                            }else{

                                Toast.makeText(getActivity(),"Error en la url del video",Toast.LENGTH_LONG).show();
                            }



                        }
                    });



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


      //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ya8SuUknLhM")));
    }

    @Override
    public void onResume() {



        System.out.print("si entra al on resume");
        super.onResume();
    }

    public void cerrar(){

        getDialog().dismiss();
    }

    private void cargarReviews2(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.COMENTARIOS);





        ref.child(key).orderByKey().limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                comentarios.removeAll(comentarios);

                for (DataSnapshot snapshop:
                        dataSnapshot.getChildren()
                        ) {

                    contador2 = contador2 +1;
                    Comentario comentario = snapshop.getValue(Comentario.class);

                    //        comentario.getRating();
                    calificacion =  calificacion + Float.parseFloat(comentario.getRating());

                    comentarios.add(comentario);
                    adapterComentarios = new ComentariosAdapter(comentarios,DetailsFragment.this);
                    recyclerView2.setAdapter(adapterComentarios);

                    //   adapter.notifyDataSetChanged();
                    rb.setRating(calificacion/contador2);

                    recyclerView2.smoothScrollToPosition(adapterComentarios.getItemCount());
                    adapterComentarios.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void cargarTodosLosReviews(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.COMENTARIOS);





        ref.child(key).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                comentarios.removeAll(comentarios);

                for (DataSnapshot snapshop:
                        dataSnapshot.getChildren()
                        ) {

                    contador2 = contador2 +1;
                    Comentario comentario = snapshop.getValue(Comentario.class);

                    //        comentario.getRating();
                    calificacion =  calificacion + Float.parseFloat(comentario.getRating());

                    comentarios.add(comentario);
                    adapterComentarios = new ComentariosAdapter(comentarios,DetailsFragment.this);
                    recyclerView2.setAdapter(adapterComentarios);

                    //   adapter.notifyDataSetChanged();
                    rb.setRating(calificacion/contador2);

                    recyclerView2.smoothScrollToPosition(adapterComentarios.getItemCount());
                    adapterComentarios.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void cargarReviews(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.COMENTARIOS);





        ref.child(key).orderByKey().limitToLast(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                comentarios.removeAll(comentarios);

                for (DataSnapshot snapshop:
                        dataSnapshot.getChildren()
                     ) {

                    contador2 = contador2 +1;
                    Comentario comentario = snapshop.getValue(Comentario.class);

            //        comentario.getRating();
                     calificacion =  calificacion + Float.parseFloat(comentario.getRating());

                    comentarios.add(comentario);
                    adapterComentarios = new ComentariosAdapter(comentarios,DetailsFragment.this);
                    recyclerView.setAdapter(adapterComentarios);

                 //   adapter.notifyDataSetChanged();


                    rb.setRating(calificacion/contador2);

                    recyclerView.smoothScrollToPosition(adapterComentarios.getItemCount());
                    adapterComentarios.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void CerrarYCambiar() {

        Toast.makeText(getActivity(),"probamos ",Toast.LENGTH_LONG).show();

    }
}
