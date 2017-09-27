package com.cristhianbonilla.cantantesmedellin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.cristhianbonilla.cantantesmedellin.fragments.BookingFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.FormNewGroupFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.LeadListFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.LeadListFragmentR;
import com.cristhianbonilla.cantantesmedellin.fragments.MainFragment;
import com.cristhianbonilla.cantantesmedellin.fragments.Principal;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  BookingFragment.Communicator   {
    private DatabaseReference refGroup;
    private ImageView  imagenDePerfil;
    public static final  String GRUPO = "grupos";
    private FirebaseUser usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usuario = FirebaseAuth.getInstance().getCurrentUser();

        refGroup = FirebaseDatabase.getInstance().getReference(GRUPO);

        if (usuario.getEmail().equals("cali1708@hotmail.com")) {
            //final View actionB = findViewById(R.id.action_b);



            final FloatingActionsMenu multiple = (FloatingActionsMenu) findViewById(R.id.multiple_buton);
          //  final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setIcon(R.drawable.agregargrupo);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("editar","no editar");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    FormNewGroupFragment dialog = FormNewGroupFragment.newInstance();
                    dialog.setArguments(bundle);
                    dialog.show(ft, "Tag");
                    fab.setVisibility(View.VISIBLE);
                    multiple.setVisibility(View.VISIBLE);

                }
            });



        } else {


            final FloatingActionsMenu multiple = (FloatingActionsMenu) findViewById(R.id.multiple_buton);
        refGroup.orderByChild("propietario").equalTo(usuario.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

                    fab.setVisibility(View.GONE);
                    multiple.setVisibility(View.GONE);


                } else {
                    final Bundle bundle = new Bundle();
                    bundle.putString("editar","no editar");
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            FormNewGroupFragment dialog = FormNewGroupFragment.newInstance();
                            dialog.setArguments(bundle);
                            dialog.show(ft, "Tag");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

      //  imagenDePerfil = (ImageView) findViewById(R.id.imagen_de_perfil);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        imagenDePerfil = (ImageView) hView.findViewById(R.id.imagen_de_perfil);
        if (AccessToken.getCurrentAccessToken() != null) {

            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {

                            if (AccessToken.getCurrentAccessToken() != null) {

                                if (me != null) {


                                    String profileImageUrl = ImageRequest.getProfilePictureUri(me.optString("id"), 100, 100).toString();

                                    Picasso.with(getApplicationContext()).load(profileImageUrl).resize(200,200).into(imagenDePerfil);


                                }
                            }
                        }
                    });
            GraphRequest.executeBatchAsync(request);
        }

        Bundle bundle = new Bundle();
        String categoria = "Mariachi";
        bundle.putString("categoria",categoria);
        Principal principalFragment = new Principal();
     //  MainFragment mainFragment = new MainFragment();
       // principalFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, principalFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Estas seguro que quieres salir?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "Saliendo ",Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if(id == R.id.home){
            Bundle bundle = new Bundle();
            String categoria = "Mariachi";
            bundle.putString("categoria",categoria);
            Principal principalFragment = new Principal();
            //  MainFragment mainFragment = new MainFragment();
            // principalFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, principalFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.agrupaciones) {
            setActionBarTitle("Agrupaciones");
            Bundle bundle = new Bundle();
            String categoria = "Agrupaciones";
            bundle.putString("Agrupaciones",categoria);
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


           ///  formNewGroupFragment.show();
            // Handle the camera action


        } else if (id == R.id.conjunto_vallenatos) {
            setActionBarTitle("Vallenatos");
            Bundle bundle = new Bundle();
            String categoria = "Conjuntos vallenatos";
            bundle.putString("coros",categoria);
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

        } else if (id == R.id.coros) {
            setActionBarTitle("Coros");
            Bundle bundle = new Bundle();
            String categoria = "Coros";
            bundle.putString("coros",categoria);
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

        } else if (id == R.id.cantantes_de_reggaeton) {
            setActionBarTitle("Reggaeton");
            Bundle bundle = new Bundle();
            String categoria = "Cantantes de reggaeton";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();



        } else if (id == R.id.duetos_trios_cuartetos) {
            setActionBarTitle("Trios y Duetos");
            Bundle bundle = new Bundle();
            String categoria = "Duetos y Trios";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.cotizaciones){
            setActionBarTitle("Cotizaciones");
            LeadListFragment leadListFragment = new LeadListFragment();


            getSupportFragmentManager().beginTransaction().replace(R.id.container, leadListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
        }else if(id==R.id.cotizacionesEnviadas){
            setActionBarTitle("Cotizaciones enviadas");
            LeadListFragmentR leadListFragment = new LeadListFragmentR();


            getSupportFragmentManager().beginTransaction().replace(R.id.container, leadListFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
        }
        else if (id == R.id.bailarines) {
            setActionBarTitle("Bailarines");
            Bundle bundle = new Bundle();
            String categoria = "Bailarines";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }
        else if (id == R.id.escuela_de_musica) {
            setActionBarTitle("Escuela de musica");
            Bundle bundle = new Bundle();
            String categoria = "Escuelas de música";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.instrumentales) {
            setActionBarTitle("Intrumentales");
            Bundle bundle = new Bundle();
            String categoria = "Instrumentales";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.musica_infantil) {
            setActionBarTitle("Musica Infantil");
            Bundle bundle = new Bundle();
            String categoria = "Música infantil";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.mariachis) {
            setActionBarTitle("Mariachis");
            Bundle bundle = new Bundle();
            String categoria = "Mariachi";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.minitecas) {
            setActionBarTitle("Minitecas");
            Bundle bundle = new Bundle();
            String categoria = "Minitecas";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.orquestas) {
            setActionBarTitle("Orquestas");
            Bundle bundle = new Bundle();
            String categoria = "Orquestas";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.papayeras) {
            setActionBarTitle("Papayeras");
            Bundle bundle = new Bundle();
            String categoria = "Papayeras";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.solistas) {
            setActionBarTitle("Solistas");
            Bundle bundle = new Bundle();
            String categoria = "Solistas";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.trovadores) {
            setActionBarTitle("Trovadores");
            Bundle bundle = new Bundle();
            String categoria = "Trovadores";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();




        }else if (id == R.id.duetos_trios_cuartetos) {
            setActionBarTitle("Duetos y trios");
            Bundle bundle = new Bundle();
            String categoria = "Duetos y Trios";
            bundle.putString("categoria",categoria);
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();


        }else if (id == R.id.registrarse) {
            setActionBarTitle("Registro");
            Bundle bundle = new Bundle();
            String categoria = "registarse";
            bundle.putString("categoria",categoria);

        }else if (id == R.id.terminos_y_condiciones) {
            setActionBarTitle("Terminos Y Condiciones");
            Bundle bundle = new Bundle();
            String categoria = "TerminosYcondiciones";
            bundle.putString("categoria",categoria);

        }else if (id == R.id.contactenos) {
            setActionBarTitle("Contactenos");
            Bundle bundle = new Bundle();
            String categoria = "contantenos";
            bundle.putString("categoria",categoria);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void CerrarYCambiar() {


        LeadListFragment leadListFragment = new LeadListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, leadListFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
      //  detailsFragment.cerrar();
    }


}
