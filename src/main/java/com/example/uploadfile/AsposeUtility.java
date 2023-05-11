package com.example.uploadfile;

import com.aspose.cells.Cells;
import com.aspose.cells.ColumnCollection;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class AsposeUtility {

    private static final Logger logger = LoggerFactory.getLogger(AsposeUtility.class);

    public List<HiddenData> findHiddenData(File file) throws Exception {

        List<Worksheet> worksheets = null;
        List<HiddenData> hiddenDataList = new ArrayList<>();

        if(file != null){
            String extension = FilenameUtils.getExtension(file.getName());
            if ( "xlsx".equalsIgnoreCase(extension) || "xlsm".equalsIgnoreCase(extension)) {
                InputStream excelFileIs = null;

                excelFileIs = FileUtils.openInputStream(file);

                Workbook workbook = new Workbook(excelFileIs);

                for (Object worksheetObj : workbook.getWorksheets()) {
                    Worksheet ws = (Worksheet) worksheetObj;
                    if (!ws.isVisible()) {
                        logger.info("Hidden sheet found {} ", ws.getName());
                    }
                    Cells cells = ws.getCells();
                    HiddenData hiddenData = null;
                    // parse through all rows to check hidden rows
                    RowCollection rows = cells.getRows();
                    for (Object row : rows) {
                        com.aspose.cells.Row rowObj = (com.aspose.cells.Row) row;
                        if( rowObj.isHidden() ) {
                            logger.info("Hidden row found on sheet {} index {} ",ws.getName(), rowObj.getIndex());
                            hiddenData = new HiddenData(ws.getName(), "Row"+rowObj.getIndex());
                            hiddenDataList.add(hiddenData);
                        }
                    }
                    // parse through all columns to check hidden columns
                    ColumnCollection columnCollection = cells.getColumns();
                    for (Object column : columnCollection) {
                        com.aspose.cells.Column col = (com.aspose.cells.Column) column;
                        if (col.isHidden()) {
                            logger.info("Hidden column found on sheet {} index {} ", ws.getName(), col.getIndex());
                            hiddenData = new HiddenData(ws.getName(), "Column"+col.getIndex());
                            hiddenDataList.add(hiddenData);
                        }
                    }
                }

            }
        }

        return hiddenDataList;
    }
}
