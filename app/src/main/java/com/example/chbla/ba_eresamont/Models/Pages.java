package com.example.chbla.ba_eresamont.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chbla on 11.10.2017.
 */

public class Pages {
    String color;
    String deleted;
    String has_own_style;
    String partid;
    String position;
    String template;
    String text_color;
    String unactived;
    String updated;

    public void setColor(String color) {
        this.color = color;
    }
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
    public void setHas_own_style(String has_own_style) {
        this.has_own_style = has_own_style;
    }
    public void setPartid(String partid) {
        this.partid = partid;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
    public void setText_color(String text_color) {
        this.text_color = text_color;
    }
    public void setUnactived(String unactived) {    this.unactived = unactived;    }
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getColor() {
        return color;
    }
    public String getDeleted() {
        return deleted;
    }
    public String getHas_own_style() {
        return has_own_style;
    }
    public String getPartid() { return partid; }
    public String getPosition() {
        return position;
    }
    public String getTemplate() {
        return template;
    }
    public String getText_color() {
        return text_color;
    }
    public String getUnactived() {
        return unactived;
    }
    public String getUpdated() {
        return updated;
    }

    public Map<String, Boolean> stars = new HashMap<>();

    public Pages() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Pages(String color, String deleted, String has_own_style, String partid,
                 String position, String template, String text_color,String unactived, String updated) {
        this.color = color;
        this.deleted = deleted;
        this.has_own_style = has_own_style;
        this.partid = partid;
        this.position = position;
        this.template = template;
        this.text_color=text_color;
        this.unactived = unactived;
        this.updated = updated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("color", color);
        result.put("deleted", deleted);
        result.put("has_own_style", has_own_style);
        result.put("partid", partid);
        result.put("position", position);
        result.put("template", template);
        result.put("text_color", text_color);
        result.put("unactived", unactived);
        result.put("updated", updated);
        return result;
    }

}