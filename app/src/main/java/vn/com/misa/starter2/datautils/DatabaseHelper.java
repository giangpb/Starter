package vn.com.misa.starter2.datautils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ‐ Class chuyen xu ly, tuong tac sqlite
 * - sao chep sqlite vao he thong
 * ‐ @created_by giangpb on 1/25/2021
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    protected SQLiteDatabase  sqLiteDatabase;

    // ten databale
    public static final String DATABASE_NAME="db_cukcuk_finally.db";

    // duong dan database
    private static final String DB_PATH_SUFFIX = "/databases/";

    private Context mContext;



    // khoi tao
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, 1);
        this.mContext = context;
    }

    public void connectSQLite(){
        sqLiteDatabase = mContext.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
    }

    /**
     * Hàm thực hiện sao chép databse
     * @author: giangpb
     * @date: 25/01/2021
     */
    public void processCopy() {
        File dbFile = mContext.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try{
                CopyDataBaseFromAsset();
                Toast.makeText(mContext, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * hàm sao chép database từ assest vào bộ nhớ local
     * @author: giangpb
     * @date: 25/01/2021
     */
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = mContext.getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = getDatabasePath();
            // if the path doesn't exist first, create it
            File f = new File(mContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();// Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);// transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Hàm lấy đường dẫn
     * @return trả về đường dẫn tới bộ nhớ local
     * @author: giangpb
     * @date: 25/01/2021
     */
    private String getDatabasePath() {
        return mContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public synchronized void close() {

        if (sqLiteDatabase != null)
            sqLiteDatabase.close();

        super.close();

    }

}
