package com.ggg.testapi.data;

/**
 * Created by Administrator on 2017-02-04.
 */
public class ItemData {
    String item;
    int selected;

    public ItemData(String item, int selected){
        super();
        this.item = item;
        this.selected = selected;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
