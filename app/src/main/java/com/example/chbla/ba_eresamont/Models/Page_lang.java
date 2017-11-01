package com.example.chbla.ba_eresamont.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chbla on 13.10.2017.
 */

public class Page_lang {
    private String plaintext;
    private String text;
    private String title;
    private String language;
    private String page;

    public Page_lang(String plaintext, String text, String title, String language, String page) {
        this.plaintext = plaintext;
        this.text = text;
        this.title = title;
        this.language = language;
        this.page = page;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public Page_lang() {
    }

    public String getPlaintext() {
        return plaintext;
    }
    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("plaintext",plaintext);
        result.put("text",text);
        result.put("title",title);
        result.put("language",text);
        result.put("page",title);
        return result;
    }
    public Map<String, Object> toMapMain() {
        HashMap<String, Page_lang> result = new HashMap<>();
        return (Map)result;
    }
    

}
