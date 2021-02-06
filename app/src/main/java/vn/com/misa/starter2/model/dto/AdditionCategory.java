package vn.com.misa.starter2.model.dto;

/**
 * ‐ Lớp bổ sung item theo danh mục
 * ‐ @created_by giangpb on 2/2/2021
 */
public class AdditionCategory {

    private String additionID;

    private String additionDescription;

    public AdditionCategory() {
    }

    public AdditionCategory(String additionID, String additionDescription) {
        this.additionID = additionID;
        this.additionDescription = additionDescription;
    }

    public String getAdditionID() {
        return additionID;
    }

    public void setAdditionID(String additionID) {
        this.additionID = additionID;
    }

    public String getAdditionDescription() {
        return additionDescription;
    }

    public void setAdditionDescription(String additionDescription) {
        this.additionDescription = additionDescription;
    }
}
