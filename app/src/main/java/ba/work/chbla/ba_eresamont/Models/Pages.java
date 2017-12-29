package ba.work.chbla.ba_eresamont.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chbla on 11.10.2017.
 * this classes stand for the item of the pages and it holds the pages-lang classes
 */

public class Pages {
    String icon;
    Long id;
    boolean unactivated;
    Long updated;
    boolean deleted;
    Long position;
    Long template;
    boolean has_own_style;
    ArrayList<Pages_lang> pages_lang;
    Long parent_id;

    public Pages(String icon, Long id, boolean unactived, Long updated, boolean deleted,
                 Long position, Long template, boolean has_own_style, ArrayList<Pages_lang> pages_lang) {
        this.icon = icon;
        this.id = id;
        this.unactivated = unactived;
        this.updated = updated;
        this.deleted = deleted;
        this.position = position;
        this.template = template;
        this.has_own_style = has_own_style;
        this.pages_lang = pages_lang;
    }

    public Pages() {
    }
    public Long getParent_id() {
        return parent_id;    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getUnactivated() {
        return unactivated;
    }

    public void setUnactivated(boolean unactivated) {
        this.unactivated = unactivated;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getTemplate() {
        return template;
    }

    public void setTemplate(Long template) {
        this.template = template;
    }

    public boolean getHas_own_style() {
        return has_own_style;
    }

    public void setHas_own_style(boolean has_own_style) {
        this.has_own_style = has_own_style;
    }

    public ArrayList<Pages_lang> getPages_lang() {
        return pages_lang;
    }

    public void setPages_lang(ArrayList<Pages_lang> pages_lang) {
        this.pages_lang = pages_lang;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("deleted", deleted);
        result.put("has_own_style", has_own_style);
        result.put("position", position);
        result.put("template", template);
        result.put("unactivated", unactivated);
        result.put("updated", updated);
        return result;
    }

}