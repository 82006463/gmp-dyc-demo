import com.zhanlu.excel.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/5/1.
 */
public class Test {
    public static void main(String[] args) {
        /*MongoClient outClient = new MongoClient("101.200.36.213", 27017);
        MongoDbFactory outFactory = new SimpleMongoDbFactory(outClient, "dyc_uc");
        DB outDB = outFactory.getDb();

        MongoClient inClient = new MongoClient("127.0.0.1", 27017);
        MongoDbFactory inFactory = new SimpleMongoDbFactory(inClient, "db_zhanlu");
        DB inDB = inFactory.getDb();

        Set<String> outNames = outDB.getCollectionNames();
        for (String outName : outNames) {
            DBCollection outCollection = outDB.getCollection(outName);
            DBCursor outCursor = outCollection.find(new BasicDBObject());
            List<DBObject> outList = outCursor.toArray();
            outCursor.close();

            DBCollection inCollection = inDB.getCollection(outName);
            for (DBObject entity : outList) {
                inCollection.insert(entity);
            }
        }
        outClient.close();
        inClient.close();*/


        //ExcelUtils table = ExcelUtils.getInstance();
        //table.setComp("公司名：","aaaaa");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        CellRangeAddress address = new CellRangeAddress(0,0,0,0);
    }
}
