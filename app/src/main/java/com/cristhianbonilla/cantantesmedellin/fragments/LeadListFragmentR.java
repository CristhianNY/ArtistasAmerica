package com.cristhianbonilla.cantantesmedellin.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.adapter.LeadsAdapter;
import com.cristhianbonilla.cantantesmedellin.adapter.LeadsAdapterR;
import com.cristhianbonilla.cantantesmedellin.models.Lead;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class LeadListFragmentR extends Fragment {

    private RecyclerView recyclerView;

    private List<Lead> leads;
    private LeadsAdapterR adapterLeads;

    FirebaseUser usuario;

    private String variable;

    public LeadListFragmentR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lead_list, container, false);




        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerleads);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        leads = new ArrayList<>();
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        cargarLeads();

        return v;



    }



    private void cargarLeads() {
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usuario.getUid();



        final DatabaseReference ref2 = database.getReference(References.CLAVEGRUPO);

        final DatabaseReference ref = database.getReference(References.LEADSR);

        adapterLeads = new LeadsAdapterR(leads, this);

        recyclerView.setAdapter(adapterLeads);

        ref2.child(usuario.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                if(dataSnapshot2.getValue()!=null) {


                    ref.orderByChild("key").equalTo(dataSnapshot2.getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            leads.removeAll(leads);
                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()
                                    ) {

                                Lead lead = snapshot.getValue(Lead.class);

                                leads.add(lead);

                            }
                            recyclerView.smoothScrollToPosition(adapterLeads.getItemCount());
                            adapterLeads.notifyDataSetChanged();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}
