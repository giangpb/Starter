package vn.com.misa.starter2.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import vn.com.misa.starter2.R;

/**
 * ‐ @created_by giangpb on 3/18/2021
 */
public class GIANGUtils {

    private static  GIANGUtils giangUtils;

    public static GIANGUtils getInstance(){
        if (giangUtils ==null)
            giangUtils = new GIANGUtils();
        return giangUtils;
    }

    /**
     * Hàm Lấy icon xml drawable từ 1 đường dẫn
     * @param c màn hình truyền vào
     * @param ImageName đường dẫn
     * @return trả về 1 drawble
     * @author: giangpb
     * @date 28/01/2021
     */
    public Drawable getMyDrawable(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    /**
     * Hàm show toast cuttom
     * @param context màn hình cần show
     * @param messagse nội dung
     * @param time 0 short,1 long
     * @author giangpb
     * @date 19/03/2021
     */
    public void showMessage(Context context, String messagse, int time){
        StyleableToast.makeText(context,messagse,time, R.style.mytoast).show();
    }
}
