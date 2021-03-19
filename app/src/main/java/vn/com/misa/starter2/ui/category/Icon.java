package vn.com.misa.starter2.ui.category;

/**
 * ‐ @created_by giangpb on 3/17/2021
 */
public class Icon {
    private int type;
    private String path;

    public Icon(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public Icon() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
