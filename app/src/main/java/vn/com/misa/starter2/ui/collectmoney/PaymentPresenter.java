package vn.com.misa.starter2.ui.collectmoney;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.model.entity.Payment;

/**
 * ‐ thao tác với payment model
 * ‐ @created_by giangpb on 2/9/2021
 */
public class PaymentPresenter {

    private PaymentModel paymentModel;

    public PaymentPresenter(Context context){
        paymentModel = new PaymentModel(context);
    }

    /**
     * Hàm thêm payment
     * @param payment payment
     * @return kết quả
     * @author giangpb
     * @date 10/02/2021
     */
    public boolean addPayment(Payment payment){
        return paymentModel.addPayment(payment);
    }

    /**
     * Hàm lấy toàn bộ danh sách payment
     * @return danh sách
     * @author giangpb
     * @date 12/02/2021
     */
    public ArrayList<Payment> getAllPayment(){
        return paymentModel.getAllPayment();
    }

    /**
     * Hàm lấy toàn bộ danh sách payment theo ngày
     * @param date ngày hiện tại
     * @return danh sách
     * @author giangpb
     * @date 14/02/2021
     */
    public ArrayList<Payment> getAllPayment(String date){
        return paymentModel.getAllPayment(date);
    }

    /**
     * Hàm tính tổng tiền theo danh sách payment
     * @param data danh sách payment
     * @return tổng tiền theo danh sách
     * @author giangpb
     * @date 14/02/2021
     */
    public int totalPrice(ArrayList<Payment> data){
        int amount =0;
        for(Payment payment :data){
            amount += payment.getAmount();
        }
        return amount;
    }

}
