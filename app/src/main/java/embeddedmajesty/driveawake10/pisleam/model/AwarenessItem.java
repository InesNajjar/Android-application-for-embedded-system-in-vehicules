package embeddedmajesty.driveawake10.pisleam.model;

public class AwarenessItem {
    private  int id;
    private  int img;
    private String desc;

    public AwarenessItem(int id, int img, String desc) {
        this.id = id;
        this.img = img;
        this.desc = desc;
    }

    public AwarenessItem(int img, String desc) {
        this.img = img;
        this.desc = desc;
    }

    public AwarenessItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
