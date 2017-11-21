package com.example.chbla.ba_eresamont.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chbla on 13.10.2017.
 */

public class Pages_lang {
    private long id;
    private long language;
    private long page;
    private String plaintext;
    private String title;
    private String translate;

    public Pages_lang() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLanguage() {
        return language;
    }

    public void setLanguage(long language) {
        this.language = language;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Pages_lang(long id, long language, long page, String plaintext, String text, String translate) {
        this.id = id;
        this.language = language;
        this.page = page;
        this.plaintext = plaintext;
        this.title = text;
        this.translate = translate;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("plaintext",plaintext);
        result.put("title", title);
        result.put("translate",translate);
        result.put("language", title);
        return result;
    }
    public Map<String, Object> toMapMain() {
        HashMap<String, Pages_lang> result = new HashMap<>();
        return (Map)result;
    }

}
