package vn.com.misa.starter2.ui.setuplistitem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.OrderDetail;
import vn.com.misa.starter2.ui.additem.IAddItemModel;
import vn.com.misa.starter2.ui.chooserectaurant.SelectRestaurantFragment;

/**
 * ‐ lớp model của item sản phẩm
 * - Lấy dữ liệu từ database
 * ‐ @created_by giangpb on 1/26/2021
 */
public class ItemFoodModel extends DatabaseHelper {

    private IAddItemModel mIAddItemModel;

    private static final String TAG = "ItemFoodModel";

    private Context mContext;

    public ItemFoodModel(Context context, IAddItemModel iAddItemModel){
        super(context);
        this.mIAddItemModel = iAddItemModel;
        this.mContext  =context;
    }

    /**
     * Hàm lấy toàn bộ dữ liệu item sản phẩm
     * @return danh sách sản phẩm
     * @author: giangpb
     * @date 26/01/2021
     */
    public ArrayList<Item> getAllListItem(){
        connectSQLite();
        ArrayList<Item> listItem= new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from InventoryItem",null);
        while (cursor.moveToNext()){
            String itemID = cursor.getString(0);
            String categoryID = cursor.getString(1);
            String itemCode = cursor.getString(2);
            String itemName = cursor.getString(3);
            int price = cursor.getInt(4);
            String unitID = cursor.getString(5);
            byte[] imagePath = cursor.getBlob(6);
            int position = cursor.getInt(7);

            int quantity = cursor.getInt(9);// sửa

            Item item = new Item(itemID,categoryID, itemCode, itemName, price, unitID,imagePath, position, quantity );
            listItem.add(item);
        }
        cursor.close();
        return listItem;
    }

    /**
     * Hàm lấy danh sách item từ database
     * @param categoryId mã danh mục
     * @return danh sách item
     * @author: giangpb
     * @date: 26/01/2021
     */
    public ArrayList<Item> getAllListItem(String categoryId){
        connectSQLite();
        ArrayList<Item> listItem= new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from InventoryItem WHERE InventoryItemCategoryID = ?",new String[]{categoryId});
        while (cursor.moveToNext()){
            String itemID = cursor.getString(0);
            String categoryID = cursor.getString(1);
            String itemCode = cursor.getString(2);
            String itemName = cursor.getString(3);
            int price = cursor.getInt(4);
            String unitID = cursor.getString(5);
            byte[] imagePath = cursor.getBlob(6);
            int position = cursor.getInt(7);
            int quantity = cursor.getInt(9);// sửa

            Item item = new Item(itemID,categoryID, itemCode, itemName, price, unitID, imagePath, position, quantity);
            listItem.add(item);
        }
        cursor.close();
        return listItem;
    }

    public void resetItemQuantity(){
        connectSQLite();
        ContentValues values = new ContentValues();
        values.put("Quantity", 0);
        sqLiteDatabase.update("InventoryItem",values,null, null);
    }


    /**
     * Hàm xoá item theo danh mục
     * @param categoryID mã danh mục
     * @return kết quả true or false
     * @author giangpb
     * @date 26/01/2021
     */
    public boolean deleteAllItem(String categoryID){
        try{
            connectSQLite();
            sqLiteDatabase.delete(SelectRestaurantFragment.TABLE_Item,"InventoryItemCategoryID = ?", new String[]{categoryID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "deleteAllItem: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm xoá item theo mã
     * @param itemID mã item
     * @return trả về đúng hoặc sai
     */
    public boolean deleteItem(String itemID){
        try{
            connectSQLite();
            sqLiteDatabase.delete(SelectRestaurantFragment.TABLE_Item,"InventoryItemID = ?", new String[]{itemID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "deleteItem: "+ex.getMessage());
        }
        return false;
    }


    /**
     * Hàm đọc ảnh từ assets
     * @param fileName đường dẫn ảnh trong assets
     * @return drawable ảnh
     * @author giangpb
     * @date 26/01/2021
     */
    private Drawable getImageFromAsset(String fileName){
        try{
            // get input stream
            InputStream ims = mContext.getAssets().open("images"+fileName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            return d;
        }
        catch (Exception ex){
            Log.d(TAG, "loadImageFromAsset: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm tính tiền hoá đơn chi tiết
     * @param data danh sách thực đơn chọn
     * @return tổng tiền
     * @author giangpb
     * @date 5/2/2021
     */
    public int tinhTienHoaDon(ArrayList<Item> data){
        int tien=0;
        for(Item item :data){
            tien += item.getQuantity()*item.getPrice();
        }

        return tien;
    }

    /**
     * Hàm lấy thông tin chi tiết hoá đơn
     * @param orderID mã hoá đơn
     * @return danh sách sản phẩm theo hoá đơn
     * @author giangpb
     * @date 06/02/2021
     */
    public ArrayList<Item> getItemInOrderDetail(String orderID){
        try{
            ArrayList<Item> lstItemInOrder = new ArrayList<>();
            connectSQLite();
            String sql = "SELECT InventoryItem.InventoryItemID, InventoryItem.InventoryItemCategoryID,InventoryItem.InventoryItemName, InventoryItem.Price,\n" +
                    "InventoryItem.UnitID, InventoryItem.Position, OrderDetail.Quantity from OrderDetail INNER JOIN InventoryItem ON OrderDetail.ItemID = InventoryItem.InventoryItemID INNER JOIN Order1 on order1.OrderID = \n" +
                    " OrderDetail.OrderID where order1.OrderID = ?";
            Cursor cursor  =sqLiteDatabase.rawQuery(sql,new String[]{orderID});
            while (cursor.moveToNext()){
                Item item = new Item();

                String itemID = cursor.getString(0);
                String categoryID = cursor.getString(1);
                String itemName = cursor.getString(2);
                int price = cursor.getInt(3);
                String unitID = cursor.getString(4);
                int position = cursor.getInt(5);
                int quantity = cursor.getInt(6);// sửa

                item.setItemID(itemID);
                item.setCategoryID(categoryID);
                item.setItemName(itemName);
                item.setPrice(price);
                item.setUnitID(unitID);
                item.setPosition(position);
                item.setQuantity(quantity);

                lstItemInOrder.add(item);
            }
            cursor.close();
            return lstItemInOrder;
        }
        catch (Exception ex){
            Log.d(TAG, "getItemInOrderDetail: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm tính tổng số lượng sản phẩm
     * @param data dữ liệu chi tiết orer
     * @return số lượng sản phẩm
     * @author giangpb
     * @date 5/02/2021
     */
    public int tongSoLuongSanPham(ArrayList<Item> data){
        int sl=0;
        for(Item item :data){
            sl+=item.getQuantity();
        }
        return sl;
    }


    /**
     * Hàm cập nhật item
     * @param item item
     * @return kết quả cập nhật
     * @author giangpb
     * @date 27/01/2021
     */
    public boolean updateItemFood(Item item){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("CategoryID", item.getCategoryID());
            values.put("ItemName", item.getItemName());
            if(item.getImage()!=null)
                values.put("ImagePath", item.getImage());
            values.put("UnitID", item.getUnitID());
            values.put("Price", item.getPrice());
            sqLiteDatabase.update(SelectRestaurantFragment.TABLE_Item,values,"ItemID = ?", new String[]{item.getItemID()});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "updateItemFood: "+ex.getMessage());
        }
        return false;
    }


    /**
     * Hàm thêm item mới
     * @param item item food
     * @return trả về kết quả thêm vào
     * @author giangpb
     * @date 27/01/2021
     */
    public boolean addItemFodd(Item item){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("InventoryItemID", item.getItemID());
            values.put("InventoryItemCategoryID", item.getCategoryID());
            values.put("InventoryItemCode", item.getItemCode());
            values.put("InventoryItemName", item.getItemCode());
            values.put("Price", item.getPrice());
            values.put("UnitID", item.getUnitID());
            if(item.getImage()!=null)
                values.put("ImagePath", item.getImage());
            values.put("Position", item.getPosition());
            values.put("Quantity", 0);
            values.put("InActive", false);
            sqLiteDatabase.insert(SelectRestaurantFragment.TABLE_Item,null,values);
            mIAddItemModel.addItemSuccess();
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "addItemFodd: "+ex.getMessage());
        }
        mIAddItemModel.addItemFalse();
        return false;
    }

    /**
     * Hàm lấy vị trí item thêm mới bởi mã danh mục
     * @param categoryID
     * @return
     */
    public int positionItemInCategory(String categoryID){
        try{
            int pos = 0;
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM InventoryItem WHERE InventoryItemCategoryID =?", new String[]{categoryID});
            if (cursor.moveToNext()){
                pos = cursor.getInt(0);
            }
            cursor.close();
            return pos;
        }
        catch (Exception ex){
            Log.d(TAG, "positionItemInCategory: "+ex.getMessage());
        }
        return -1;
    }



}
