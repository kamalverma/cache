package eu.com.cache.Models;

/**
 * Created by Kamal on 3/17/16.
 */
public class GameItem {

    private String itemTag;
    private String itemImageUrl;
    private String itemText;
    private boolean blankItem;
    private boolean openTemp;

    public boolean isOpenTemp() {
        return openTemp;
    }

    public void setOpenTemp(boolean openTemp) {
        this.openTemp = openTemp;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private boolean checked;

    public String getItemTag() {
        return itemTag;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isBlankItem() {
        return blankItem;
    }

    public void setBlankItem(boolean blankItem) {
        this.blankItem = blankItem;
    }
}
