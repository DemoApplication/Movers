package model;

/**
 * Created by user1 on 13-Sep-16.
 */
public class NavDrawerItem {
    private int  image_id;
    private String title;

    public NavDrawerItem() {

    }

    public NavDrawerItem(int imageid, String title) {
        this.image_id = imageid;
        this.title = title;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImageId(int imageid) {
        this.image_id = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
