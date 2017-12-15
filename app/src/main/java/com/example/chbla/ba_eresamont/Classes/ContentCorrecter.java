package com.example.chbla.ba_eresamont.Classes;

import android.support.annotation.NonNull;

import com.example.chbla.ba_eresamont.Models.Pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

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
    public String removeComments()
    {
        String output=content;
        String[] comments={"//rouge", "//vert", "//jaune"};

        for (int i=0;i<comments.length;i++){
            output=output.replaceAll(comments[i], "");
        }
        return output;

    }
    public String contentEscapeProcessing(){
        //escaping
        Document doc = Jsoup.parse(content);
        doc.outputSettings().escapeMode(Entities.EscapeMode.base);
        doc.outputSettings().charset("ASCII");
        return doc.toString();
    }
}
