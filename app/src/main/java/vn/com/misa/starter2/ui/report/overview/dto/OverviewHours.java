package vn.com.misa.starter2.ui.report.overview.dto;

/**
 * ‐ @created_by giangpb on 2/23/2021
 */
public class OverviewHours {
    private int hour;
    private int money;

    public OverviewHours() {
    }

    public OverviewHours(int hour, int money) {
        this.hour = hour;
        this.money = money;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
