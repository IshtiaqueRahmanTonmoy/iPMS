package patient.patientmanagement.pms.entity;


import java.util.ArrayList;

public class CardItem {

    private int mTextResource;
    private int mTitleResource;

    private String districtName;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public CardItem(String districtName) {
        this.districtName = districtName;
    }



   /*
    public CardItem(String title) {
        mTextResource = title;
    }
    */

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getText() {
        return mTextResource;

    }

    public int getTitle() {
        return mTitleResource;
    }
}
