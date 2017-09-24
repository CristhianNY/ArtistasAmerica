package com.cristhianbonilla.cantantesmedellin.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cristhianbonilla.cantantesmedellin.MyFirebaseMessagingService;
import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.ComentariosAdapter;
import com.cristhianbonilla.cantantesmedellin.models.Comentario;
import com.cristhianbonilla.cantantesmedellin.models.Lead;
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

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends DialogFragment implements View.OnClickListener {
    private FirebaseUser usuario;
    Communicator communicator;
    private Button btnCotizacion;
    private Spinner categoriaEvento;
    private TimePicker hora;
    private EditText ciudad;
    private EditText direccion;
    private EditText telefono;
    private EditText descripcion;
    private String key;
    private TextView tituloDetalles,errorCategoria,fechaError;
    private DatePicker fecha;
    private String telefonoGrupo;
    private String nombreGrupo;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        communicator = (Communicator) activity;
    }

    public BookingFragment() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking, null);
        key = this.getArguments().getString("key");
        nombreGrupo = this.getArguments().getString("nombreGrupo");
        telefonoGrupo = this.getArguments().getString("telefonoGrupo");
        btnCotizacion = (Button) v.findViewById(R.id.bnt_cotizacion);
        hora = (TimePicker) v.findViewById(R.id.hora_evento);
        fecha = (DatePicker) v.findViewById(R.id.fecha_del_evento);
        categoriaEvento = (Spinner) v.findViewById(R.id.categoria_evento);
        ciudad = (EditText) v.findViewById(R.id.input_ciudad);
        direccion = (EditText) v.findViewById(R.id.address);
        telefono = (EditText) v.findViewById(R.id.input_telefono_event);
        descripcion = (EditText) v.findViewById(R.id.input_decripcion);
        tituloDetalles = (TextView) v.findViewById(R.id.tituloDetalles);
        errorCategoria = (TextView) v.findViewById(R.id.seleccione_categoria);
        fechaError = (TextView) v.findViewById(R.id.fecha_evento_error);
        tituloDetalles.setText(nombreGrupo);

        btnCotizacion.setOnClickListener(this);






        return v;
    }


    public  String imagenDePerfil (){

        String profileImageUrl = "nada";

        return profileImageUrl;
    }

    private boolean validate(){
        boolean valid = true;

        if(direccion.getText().length()== 0){

            valid = false;
            direccion.setError("No olvides poner la dirección");
        }

        if(categoriaEvento.getSelectedItem().toString() == "Seleccione la categoria"){


            errorCategoria.setError("Por Favor Seleccione una Categoria");
            valid = false;
        }

        String fechaDate = fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear();

        if(fechaDate.length()== 0 || fecha.getYear()< Calendar.getInstance().get(Calendar.YEAR)
                || fecha.getMonth()< Calendar.getInstance().get(Calendar.MONTH)
              ){


            fechaError.setError("La Fecha es invalidad");
            valid = false;


        }

        if(ciudad.getText().length()== 0){

            ciudad.setError("Por favor diganos la ciudad");
            valid = false;
        }

        if(telefono.getText().length() == 0 || telefono.getText().length()<10 || telefono.getText().length()>10){
            telefono.setError("Necesitamos el telefono de 10 digitos ");

            valid = false;
        }


        return valid;

    }

    private void insertarPedidoCotizacion(final String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference ref = database.getReference(References.LEADS + "/" + usuario.getUid());
        final DatabaseReference ref2 = database.getReference(References.LEADSR );

        //adapterComentarios = new ComentariosAdapter(comentarios,this);
        Toast.makeText(getContext(),key ,Toast.LENGTH_LONG).show();

      //  recyclerView.setAdapter(adapterComentarios);
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
                                            String strDateTime = hora.getCurrentHour() + ":" + hora.getCurrentMinute();
                                            String fechaDate = fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear();
                                            Lead lead = new Lead(categoriaEvento.getSelectedItem().toString(),ciudad.getText().toString(),
                                                    descripcion.getText().toString(),direccion.getText().toString(),strDateTime,key,telefono.getText().toString(),usuario.getUid(),me.optString("name"),fechaDate

                                            ,nombreGrupo,telefonoGrupo , profileImageUrl);
                                                  //  me.optString("name"), new Date().toString(),comentariokey,usuario.getUid(),contectReview.getText().toString(),key,"4");


                                            //   contectReview.setText("");
                                          //  getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
                                            ref.push().setValue(lead);
                                            ref2.push().setValue(lead);



                                         //   onDestroyView();
                                            llamarLeads();


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

    public void llamarLeads(){

       // MainFragment mainFragment = new MainFragment();

       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
               // .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
        dismiss();
       // LeadListFragment leadListFragment = new LeadListFragment();


       //MainFragment frag = MainFragment.newInstance();
       /** FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.container, leadListFragment);
        ft.addToBackStack(null);
        ft.commit();
        DetailsFragment detailsFragment = new DetailsFragment();
      //  detailsFragment.cerarFragment();**/


    }
    @Override
    public void onDestroyView() {


        FragmentManager mFragmentMgr= getFragmentManager();

        FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();

        Fragment childFragment =mFragmentMgr.findFragmentByTag("lead");



        mTransaction.remove(childFragment);
        mTransaction.commit();
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.bnt_cotizacion){

            validate();

            if(!validate()){
                Toast.makeText(getActivity().getApplicationContext(),"Por favor conrrige los siguientes campos ", Toast.LENGTH_LONG).show();
            }else{

                insertarPedidoCotizacion(key);
            }

            communicator.CerrarYCambiar();

        }else{
            Toast.makeText(getActivity(),"Nada ",Toast.LENGTH_LONG).show();
        }

    }

   public  interface Communicator{
        public void CerrarYCambiar();

    }
}
