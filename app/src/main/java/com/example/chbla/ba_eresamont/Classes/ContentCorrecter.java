package com.example.chbla.ba_eresamont.Classes;

import android.support.annotation.NonNull;

/**
 * Created by chbla on 15.12.2017.
 */

public class ContentCorrecter {
    String content;
    String output;

    public ContentCorrecter(String content) {
        this.content = content;
    }

    @NonNull
    public String setCorrectContent() {
        output=content.replaceAll("style=", "style=\"").toString();
        output=output.replaceAll(";>", ";\">").toString();
        output=output.replaceAll("src=", "src=\"").toString();
        output=output.replaceAll("alt", "\" alt").toString();
        return output;
    }
    public String setDefaultWidth()
    {
        output=content.replaceAll("style=\"width: 100%;\"", "style=\"width:100%;\"");
        return output;
    }
}
