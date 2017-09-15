package com.cristhianbonilla.cantantesmedellin.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cristhianbonilla.cantantesmedellin.R;
import com.cristhianbonilla.cantantesmedellin.References.References;
import com.cristhianbonilla.cantantesmedellin.models.ImageUploaded;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 13/07/2017.
 */

public class SliderAdapter extends PagerAdapter {
    private int[] imgs = {R.drawable.chichen_itza,R.drawable.christ_the_redeemer,R.drawable.colosseum};



    private List<String> imagenes;
    private ArrayList<String> IMAGES = new ArrayList<>();

    private LayoutInflater inflater;
    private Context ctx;
    private String key;
    private FirebaseDatabase database;
    private int contador = 0;

    public SliderAdapter(Context ctx, String key, ArrayList<String> imagenes){
        this.ctx=ctx;
        this.key = key;
        this.IMAGES = imagenes;


    }
    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.swipe, container, false);
        ImageView img= (ImageView)v.findViewById(R.id.imagen_grupo);
        TextView tv = (TextView) v.findViewById(R.id.tituloDetalles);
      //  Picasso.with(this.fragment.getContext()).load(grupo.getUrlimagen()).resize(300,300).into(holder.coverImageView);
   //     Picasso.with(context).load(url).fit().into(imageView);
        Picasso.with(ctx)
                .load(IMAGES.get(position)).resize(600, 600).into(img);

        //img.setImageResource(imgs[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();

    }
}
