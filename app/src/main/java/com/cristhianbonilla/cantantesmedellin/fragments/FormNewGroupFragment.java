package com.cristhianbonilla.cantantesmedellin.fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.PicturesAdapter;
import com.cristhianbonilla.cantantesmedellin.models.Grupo;
import com.cristhianbonilla.cantantesmedellin.models.ImageUploaded;
import com.cristhianbonilla.cantantesmedellin.models.Images;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormNewGroupFragment extends DialogFragment {


    private ImageButton btncamera;
    private Button subirFotos;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference refGroup;
    private DatabaseReference refGroupImage;
    private ImageView imagen;
    private PicturesAdapter adapter;
    public static final  String FB_STORAGE_PATH = "image/";
    public static final  String FB_DATABASE_PATH = "image";
    public static final  String GRUPO = "grupos";
    public static final  int REQUEST_CODE = 1234;
    private List<Images> imagenes ;
    private List<Images> imagenesAgregadas ;
    private List<ImageUploaded> imgs;
    private Uri imgUri;
    private FirebaseUser usuario;
    private RecyclerView recyclerImagenes;
    private int contador=0;
    private String editar;
    private String clave;
    private EditText nombre ,celular,fijo, email ,  url, youtube,socialf , descripcion,contactName;
    private String nombreSt ,celularSt,fijoSt, emailSt , urlSt, youtubeSt,socialfSt , descripcionSt,contactNameSt
            ,categoriaSt,propietario;

    private TextView muchasImagenes,errorCategoria;
    private Spinner categoria;


    public FormNewGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        editar = this.getArguments().getString("editar");
        celularSt = this.getArguments().getString("celular");
        fijoSt = this.getArguments().getString("fijo");
        emailSt = this.getArguments().getString("email");
        urlSt = this.getArguments().getString("url");
        nombreSt = this.getArguments().getString("nombreGrupo");
        socialfSt = this.getArguments().getString("socialF");
        descripcionSt = this.getArguments().getString("descripcion");
        contactNameSt = this.getArguments().getString("nombreContacto");
        categoriaSt = this.getArguments().getString("categoria");
        clave = this.getArguments().getString("key");
        propietario = this.getArguments().getString("propietario");
        urlSt = this.getArguments().getString("url");
        youtubeSt = this.getArguments().getString("youtube");





       //  mAwesomeValidation.setColor(Color.YELLOW);
        // Inflate the layout for this fragment
   View v =  inflater.inflate(R.layout.fragment_form_new_group, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        recyclerImagenes = (RecyclerView)v.findViewById(R.id.recyclerPictures);

        recyclerImagenes.setLayoutManager(new GridLayoutManager(getActivity(),2));

        muchasImagenes=(TextView) v.findViewById(R.id.muchas_imagenes);
        errorCategoria = (TextView) v.findViewById(R.id.error_categoria);
        nombre = (EditText) v.findViewById(R.id.input_name);
        email = (EditText) v.findViewById(R.id.input_email);
        celular  = (EditText) v.findViewById(R.id.input_telfono);
        fijo  = (EditText) v.findViewById(R.id.input_telefono_fijo);
        socialf = (EditText) v.findViewById(R.id.input_socialF);
        youtube = (EditText) v.findViewById(R.id.input_youtube);
        url = (EditText) v.findViewById(R.id.input_url);
        descripcion= (EditText) v.findViewById(R.id.input_decripcion);
        contactName = (EditText) v.findViewById(R.id.input_contact_name);
        categoria = (Spinner) v.findViewById(R.id.list_category);




        descripcion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;

            }
        });
        if(nombreSt != null){

            nombre.setText(nombreSt);
        }

        if(contactNameSt != null){

            contactName.setText(contactNameSt);
        }

        if(youtubeSt != null){

            youtube.setText(youtubeSt);
        }
        if(socialfSt != null){

            socialf.setText(socialfSt);
        }

        if(celularSt != null){

            celular.setText(celularSt);
        }
        if(descripcionSt != null){

            descripcion.setText(descripcionSt);
        }
        if(emailSt != null){

            email.setText(emailSt);
        }
        if(fijoSt != null){

            fijo.setText(fijoSt);
        }

        if(urlSt != null){

            url.setText(urlSt);
        }



        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cargarImagenesEnImagenes(clave);

        imagenes = new ArrayList<>();
        imagenesAgregadas = new ArrayList<>();

        imgs = new ArrayList<>();

        imagen = (ImageView) v.findViewById(R.id.imagen);

        subirFotos = (Button) v.findViewById(R.id.subir_fotos);


        subirFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpload_Click(v);
            }
        });
        btncamera = (ImageButton) v.findViewById(R.id.btn_camera);
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBrowse_click(v);
            }
        });



        return v;
    }



    private void cargarImagenesEnImagenes(final String clave) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(References.IMAGES+"/"+clave);



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {




                    ImageUploaded imageUploaded = snapshot.getValue(ImageUploaded.class);

                    try {
                        URL url = new URL(imageUploaded.getUrl());
                        adapter = new PicturesAdapter(FormNewGroupFragment.this, imagenes, editar);
                        recyclerImagenes.setAdapter(adapter);
                        Uri uri =  Uri.parse(url.toString());
                      //  Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                       Images img = new Images(uri, clave,imageUploaded.getKey() );

                        imagenes.add(img);
                       // imagenesAgregadas.add(img);
                        adapter.notifyDataSetChanged();

                    } catch(IOException e) {
                        System.out.println(e);
                    }
                    //Images img = new Images(bm, imgUri);

                    System.out.print(imageUploaded.getUrl());

                   // imagenes.add(imageUploaded)


// estamos cargando las imagenes para editar
                    //&    imagenes.add(imageUploaded);


                    // arrayListDeImagenes.add(imageUploaded.getUrl());
                }



                //adapter = new SliderAdapter(getContext(),key, IMAGES);


               // viewPager.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.print("error al traer imagenes ");
            }
        });

    }
    private void startCropImageActivity() {
        CropImage.activity()
                .start(getActivity());
    }


    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(getActivity());
    }

    public void btnBrowse_click(View v){

        Intent intent = new Intent();
        intent.setType("image/*");

        if(editar == "editar"){

            //imagenes.clear();
        }

    intent.setAction(intent.ACTION_GET_CONTENT);

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
       // intent.putExtra(MediaStore.EXTRA_OUTPUT, uriWhereToStore);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent.createChooser(intent,"select image"),REQUEST_CODE);
        } catch (ActivityNotFoundException e) {}






    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(editar == "editar"){

            //imagenes.clear();



        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {




                adapter = new PicturesAdapter(FormNewGroupFragment.this, imagenes, editar);
                recyclerImagenes.setAdapter(adapter);
                imgUri = data.getData();
                      CropImage.activity(imgUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity());


            //    Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
            //  imagen.setImageBitmap(bm);
            // imagenes.add(bm);

            //    for (int i = 0; i < bm.getByteCount(); i++) {


            //  }
            Images img = new Images(imgUri,clave,clave);
            imagenesAgregadas.add(img);
            imagenes.add(img);
            adapter.notifyDataSetChanged();


        } else {


                if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getClipData() != null) {


                    adapter = new PicturesAdapter(FormNewGroupFragment.this, imagenes, editar);
                    recyclerImagenes.setAdapter(adapter);


                    try {
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {

                            ClipData.Item item = data.getClipData().getItemAt(i);
                            Uri imgUri = item.getUri();
                            Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                            Images img = new Images(imgUri,clave,clave);
                            imagenes.add(img);
                        }

                        // imagen.setImageBitmap(bm);
                        // imagenes.add(bm);


                        adapter.notifyDataSetChanged();


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print("por aca paso ");
            }
        }

        else{

            if ( imagenes.size() > 5) {
                // Toast.makeText(getActivity(), "Muchas imagenes ", Toast.LENGTH_LONG);
                muchasImagenes.setText("Solo se permiten 5 miagenes");


            } else {



                if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {



                    adapter = new PicturesAdapter(FormNewGroupFragment.this, imagenes, editar);
                    recyclerImagenes.setAdapter(adapter);
                    imgUri = data.getData();

                    Images img = new Images(imgUri,clave,clave);
                    imagenes.add(img);
                    adapter.notifyDataSetChanged();

                } else {


                    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getClipData() != null) {


                        adapter = new PicturesAdapter(FormNewGroupFragment.this, imagenes, editar);
                        recyclerImagenes.setAdapter(adapter);


                        try {
                            for (int i = 0; i < data.getClipData().getItemCount(); i++) {

                                ClipData.Item item = data.getClipData().getItemAt(i);
                                Uri imgUri = item.getUri();
                                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                                Images img = new Images(imgUri,clave,clave);
                                imagenes.add(img);
                            }

                            // imagen.setImageBitmap(bm);
                            // imagenes.add(bm);


                            adapter.notifyDataSetChanged();


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print("por aca paso ");
                }
            }

        }


        }


    public String getImageExt(Uri uri){

        ContentResolver  contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void btnUpload_Click(View v) {

        if (imagenes.size() != 0) {


            inizialize();

            if(!validate()){
                Toast.makeText(getActivity().getApplicationContext(),"Por favor conrrige los siguientes campos ", Toast.LENGTH_LONG).show();
                
            }else{

                if(editar == "editar"){

                    editarTodasLasFotos();

                }else{
                    subirTodasLasFotos();
                }


            }



            //get the storage reference


    }
        else{

            Toast.makeText(getContext(),"Por favor Seleccione una imagen", Toast.LENGTH_LONG).show();

        }

    }

    private boolean validate() {

        boolean valid = true;

        if(nombreSt.isEmpty()|| nombreSt.length()>32){
            valid = false;
            nombre.setError("Por favor inserte un nombre valido");
        }

        if(descripcionSt.isEmpty()|| descripcionSt.length()>528){
            valid = false;
            descripcion.setError("Por favor agregue una descripcion de 528 caracteres ");
        }
        if(contactNameSt.isEmpty()|| contactNameSt.length()>31){
            contactName.setError("Por favor insertar ");
            valid = false;
        }

        if(emailSt.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(emailSt).matches()){

            email.setError("El Correo no es valido");
            valid = false;
        }

        if(celularSt.isEmpty()||!Patterns.PHONE.matcher(celularSt).matches()||celularSt.length()<10){

            celular.setError("Telefono invalido ");
            valid = false;
        }
        if(fijoSt.isEmpty()||!Patterns.PHONE.matcher(fijoSt).matches()||fijoSt.length()<10){

            fijo.setError("Telefono invalido ");
            valid = false;
        }
        if(urlSt.isEmpty()||!Patterns.WEB_URL.matcher(urlSt).matches()){

            url.setError("Url ivalidad ");
            valid = false;
        }
        if(socialfSt.isEmpty()||!Patterns.WEB_URL.matcher(socialfSt).matches()){

            socialf.setError("Url de facebook no valida  ");
            valid = false;
        }

        if(youtubeSt.isEmpty()||!Patterns.WEB_URL.matcher(youtubeSt).matches()){

            youtube.setError("Telefono invalido ");
            valid = false;
        }



        if(categoriaSt.isEmpty()|| categoriaSt.equals("Seleccione la categoria")){

            errorCategoria.setText("Por favor seleccione una categoria");
            valid = false;
        }
        return valid;
    }

    private void inizialize() {


        nombreSt = nombre.getText().toString().trim();
        emailSt = email.getText().toString().trim();
        celularSt  = celular.getText().toString().trim();
        fijoSt  = fijo.getText().toString().trim();
        socialfSt = socialf.getText().toString().trim();
        youtubeSt = youtube.getText().toString().trim();
        urlSt = url.getText().toString().trim();
        descripcionSt= descripcion.getText().toString().trim();
        contactNameSt = contactName.getText().toString().trim();
        categoriaSt = categoria.getSelectedItem().toString().trim();


    }
    private void editarTodasLasFotos() {
        usuario = FirebaseAuth.getInstance().getCurrentUser();


        final ProgressDialog dialog = new ProgressDialog(getContext());



        if(editar == "editar"){



            /// subirmos todas las imagenes7
            refGroup = FirebaseDatabase.getInstance().getReference(GRUPO);
            refGroupImage = FirebaseDatabase.getInstance().getReference(GRUPO+"/"+clave+"/urlimagen");

            final String keyGroup =  refGroup.push().getKey();


            if(imagenesAgregadas.size()== 0){

                refGroupImage.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        //mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH+"/"+clave);
                        //final String newKey = mDatabaseRef.push().getKey();
                        //ImageUploaded imageUploaded = new ImageUploaded(dataSnapshot.getValue().toString(),newKey,keyGroup);
                        // save image info in to firebase database
                        //  String uploadId = mDatabaseRef.getKey();

                        contador = contador+1;
                        //  imgs.add(imageUploaded);
                        if(contador == 1){
                            Grupo grupo = new Grupo(nombre.getText().toString(),contactName.getText().toString(),
                                    email.getText().toString(),celular.getText().toString(),fijo.getText().toString(),
                                    socialf.getText().toString(),youtube.getText().toString(),url.getText().toString()
                                    ,descripcion.getText().toString(),categoria.getSelectedItem().toString(),clave,dataSnapshot.getValue().toString(),usuario.getUid());



                            refGroup.child(clave).setValue(grupo);
                          dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }else{
                dialog.setTitle("Uploading image");
                dialog.show();

                for (Images img : imagenesAgregadas) {






                    String variable = FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(img.getImgUri());

                    StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + img.getImgUri());





                    ref.putFile(img.getImgUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            // Display Success Toast

                            //    Toast.makeText(getActivity().getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG);
                            mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH+"/"+clave);
                            final String newKey = mDatabaseRef.push().getKey();
                            ImageUploaded imageUploaded = new ImageUploaded(taskSnapshot.getDownloadUrl().toString(),newKey,clave);
                            // save image info in to firebase database
                            //  String uploadId = mDatabaseRef.getKey();

                            contador = contador+1;
                            imgs.add(imageUploaded);
                            if(contador == 1){
                                Grupo grupo = new Grupo(nombre.getText().toString(),contactName.getText().toString(),
                                        email.getText().toString(),celular.getText().toString(),fijo.getText().toString(),
                                        socialf.getText().toString(),youtube.getText().toString(),url.getText().toString()
                                        ,descripcion.getText().toString(),categoria.getSelectedItem().toString(),clave,taskSnapshot.getDownloadUrl().toString(),usuario.getUid());



                                refGroup.child(clave).setValue(grupo);
                            }







                            mDatabaseRef.child(newKey).setValue(imageUploaded);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();

                            // Display Error Toast

                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            //show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            dialog.setMessage("uploaded" + (int) progress + "%");

                            if(progress == 100){
                                dialog.dismiss();
                                dismiss();

                            }
                        }
                    });
                }
            }


            //imagenes.clear();
        }else{

            dialog.setTitle("Uploading image");
            dialog.show();


            /// subirmos todas las imagenes7
            refGroup = FirebaseDatabase.getInstance().getReference(GRUPO);
          //  final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(References.CLAVEGRUPO + "/" + usuario.getUid());
            refGroupImage = FirebaseDatabase.getInstance().getReference(GRUPO+"/"+clave+"/urlimagen");

            final String keyGroup =  refGroup.push().getKey();

            for (Images img : imagenes) {






                String variable = FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(img.getImgUri());

                StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + img.getImgUri());





                ref.putFile(img.getImgUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        // Display Success Toast

                        //    Toast.makeText(getActivity().getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG);
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH+"/"+clave);
                        final String newKey = mDatabaseRef.push().getKey();
                        ImageUploaded imageUploaded = new ImageUploaded(taskSnapshot.getDownloadUrl().toString(),newKey,keyGroup);
                        // save image info in to firebase database
                        //  String uploadId = mDatabaseRef.getKey();

                        contador = contador+1;
                        imgs.add(imageUploaded);
                        if(contador == 1){
                            Grupo grupo = new Grupo(nombre.getText().toString(),contactName.getText().toString(),
                                    email.getText().toString(),celular.getText().toString(),fijo.getText().toString(),
                                    socialf.getText().toString(),youtube.getText().toString(),url.getText().toString()
                                    ,descripcion.getText().toString(),categoria.getSelectedItem().toString(),clave,taskSnapshot.getDownloadUrl().toString(),usuario.getUid());



                            refGroup.child(newKey).push().setValue(grupo);


                        }






                        mDatabaseRef.child(newKey).setValue(imageUploaded);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();

                        // Display Error Toast

                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        //show upload progress

                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        dialog.setMessage("uploaded" + (int) progress + "%");

                        if(progress == 100){
                            dialog.dismiss();
                            dismiss();

                        }
                    }
                });
            }

        }


    }
    private void subirTodasLasFotos() {
        usuario = FirebaseAuth.getInstance().getCurrentUser();


        final ProgressDialog dialog = new ProgressDialog(getContext());


        dialog.setTitle("Uploading image");
        dialog.show();


        /// subirmos todas las imagenes7
        refGroup = FirebaseDatabase.getInstance().getReference(GRUPO);


       final String keyGroup =  refGroup.push().getKey();


        if(editar == "editar"){
            for (Images img : imagenesAgregadas) {





                StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(img.getImgUri()));






                ref.putFile(img.getImgUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        // Display Success Toast

                        //    Toast.makeText(getActivity().getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG);
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH+"/"+keyGroup);
                        final String newKey = mDatabaseRef.push().getKey();
                        ImageUploaded imageUploaded = new ImageUploaded(taskSnapshot.getDownloadUrl().toString(),newKey,keyGroup);
                        // save image info in to firebase database
                        //  String uploadId = mDatabaseRef.getKey();

                        contador = contador+1;
                        imgs.add(imageUploaded);
                        if(contador == 1){
                            Grupo grupo = new Grupo(nombre.getText().toString(),contactName.getText().toString(),
                                    email.getText().toString(),celular.getText().toString(),fijo.getText().toString(),
                                    socialf.getText().toString(),youtube.getText().toString(),url.getText().toString()
                                    ,descripcion.getText().toString(),categoria.getSelectedItem().toString(),keyGroup,taskSnapshot.getDownloadUrl().toString(),usuario.getUid());



                            refGroup.child(keyGroup).setValue(grupo);
                        }








                        mDatabaseRef.child(newKey).setValue(imageUploaded);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();

                        // Display Error Toast

                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        //show upload progress

                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        dialog.setMessage("uploaded" + (int) progress + "%");

                        if(progress == 100){
                            dialog.dismiss();
                            dismiss();

                        }
                    }
                });
            }

        }else{
            for (Images img : imagenes) {





                StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(img.getImgUri()));





                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(References.CLAVEGRUPO + "/" + usuario.getUid());
                ref.putFile(img.getImgUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        // Display Success Toast

                        //    Toast.makeText(getActivity().getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG);
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH+"/"+keyGroup);
                        final String newKey = mDatabaseRef.push().getKey();
                        ImageUploaded imageUploaded = new ImageUploaded(taskSnapshot.getDownloadUrl().toString(),newKey,keyGroup);
                        // save image info in to firebase database
                        //  String uploadId = mDatabaseRef.getKey();

                        contador = contador+1;
                        imgs.add(imageUploaded);
                        if(contador == 1){
                            Grupo grupo = new Grupo(nombre.getText().toString(),contactName.getText().toString(),
                                    email.getText().toString(),celular.getText().toString(),fijo.getText().toString(),
                                    socialf.getText().toString(),youtube.getText().toString(),url.getText().toString()
                                    ,descripcion.getText().toString(),categoria.getSelectedItem().toString(),keyGroup,taskSnapshot.getDownloadUrl().toString(),usuario.getUid());



                            refGroup.child(keyGroup).setValue(grupo);
                            ref2.setValue(keyGroup);
                        }







                        mDatabaseRef.child(newKey).setValue(imageUploaded);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();

                        // Display Error Toast

                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        //show upload progress

                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        dialog.setMessage("uploaded" + (int) progress + "%");

                        if(progress == 100){
                            dialog.dismiss();
                            dismiss();

                        }
                    }
                });
            }

        }



    }

    public static FormNewGroupFragment newInstance() {
        FormNewGroupFragment f = new FormNewGroupFragment();
        return f;
    }
}
