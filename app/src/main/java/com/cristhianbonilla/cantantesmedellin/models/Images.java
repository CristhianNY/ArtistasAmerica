package com.cristhianbonilla.cantantesmedellin.models;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by ASUS on 5/07/2017.
 */

public class Images {


  //  private Bitmap imagen;
    private Uri imgUri;
    private String key;
    private String keyGroup;

    public Uri getImgUri() {
        return imgUri;
    }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public Images(Uri imgUri, String key, String keyGroup) {
      this.imgUri = imgUri;
      this.key = key;
      this.keyGroup = keyGroup;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }
    /**public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }**/
}
