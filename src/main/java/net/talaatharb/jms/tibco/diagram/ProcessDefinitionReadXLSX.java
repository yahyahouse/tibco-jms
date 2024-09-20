package net.talaatharb.jms.tibco.diagram;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProcessDefinitionReadXLSX{
    /****** START SET/GET METHOD, DO NOT MODIFY *****/
    protected String inputFile = "";
    protected String sheet = "";
    protected int rowOffset = 0;
    protected int colOffset = 0;
    protected int rowCount = 0;
    protected int colCount = 0;
    protected boolean normalizeOffset = false;
    protected boolean returnNullValues = false;
    protected Object[] rows = null;
    protected boolean error = false;
    protected String errorMessage = "";
    public String getinputFile() {
        return inputFile;
    }
    public void setinputFile(String val) {
        inputFile = val;
    }
    public String getsheet() {
        return sheet;
    }
    public void setsheet(String val) {
        sheet = val;
    }
    public int getrowOffset() {
        return rowOffset;
    }
    public void setrowOffset(int val) {
        rowOffset = val;
    }
    public int getcolOffset() {
        return colOffset;
    }
    public void setcolOffset(int val) {
        colOffset = val;
    }
    public int getrowCount() {
        return rowCount;
    }
    public void setrowCount(int val) {
        rowCount = val;
    }
    public int getcolCount() {
        return colCount;
    }
    public void setcolCount(int val) {
        colCount = val;
    }
    public boolean getnormalizeOffset() {
        return normalizeOffset;
    }
    public void setnormalizeOffset(boolean val) {
        normalizeOffset = val;
    }
    public boolean getreturnNullValues() {
        return returnNullValues;
    }
    public void setreturnNullValues(boolean val) {
        returnNullValues = val;
    }
    public Object[] getrows() {
        return rows;
    }
    public void setrows(Object[] val) {
        rows = val;
    }
    public boolean geterror() {
        return error;
    }
    public void seterror(boolean val) {
        error = val;
    }
    public String geterrorMessage() {
        return errorMessage;
    }
    public void seterrorMessage(String val) {
        errorMessage = val;
    }
    /****** END SET/GET METHOD, DO NOT MODIFY *****/
    public void ReadXLSReadXLSX() {
    }

    class Val
    {
        public Val( int _row, int _col, String _value)
        {
            row = _row;
            col = _col;
            value = _value;

        }
        int row;
        int col;
        String value;
    }

    public void invoke() throws Exception {
/* Available Variables: DO NOT MODIFY
	In  : String inputFile
	In  : String sheet
	In  : int rowOffset
	In  : int colOffset
	In  : int rowCount
	In  : int colCount
	In  : boolean normalizeOffset
	In  : boolean returnNullValues
	Out : Object[] rows
	Out : boolean error
	Out : String errorMessage
* Available Variables: DO NOT MODIFY *****/
        error = false;
        errorMessage = "";
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream( inputFile ) );
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if ( wb == null )
        {
            error = true;
            errorMessage =  "Accessing workbook: " + inputFile + " returned null" ;
            return;
        }
        XSSFSheet ws = wb.getSheet( sheet );
        if ( ws == null )
        {
            error = true;
            errorMessage =  "Accessing sheet: " + sheet + " returned null" ;
            return;
        }
        //System.out.println( returnNullValues );
        ArrayList<Val> cells = new ArrayList<Val>(100);
        int toRow = rowOffset+rowCount;
        int toCol =colOffset+colCount;

        String value = null;
        //Determine end of data
        if ( rowCount == -1 )
        {
            toRow = (ws.getLastRowNum() +1);

            boolean foundData = false;
            while ( !foundData )
            {
                XSSFRow _row = ws.getRow( toRow-1 ) ;
                if ( _row != null )
                {
                    XSSFCell cell = null;
                    cell = _row.getCell( colOffset ) ;

                    if ( cell != null )
                        if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC )
                            value = "" +cell.getRawValue();
                        else
                            value =  cell.getStringCellValue();

                    if  ( value != null && !value.equals( "" ) )
                        foundData = true;
                }
                if ( !foundData ) toRow--;
            }

            rowCount = toRow - rowOffset;
        }

        rows = new Object[rowCount];
        for( int row= rowOffset; row<toRow; row++ )
        {
            XSSFRow _row = ws.getRow( row ) ;
            if ( _row == null )
                System.out.println( "Accessing row: " + row + " returned null" );

            if ( colCount == -1 && row == rowOffset && _row != null )
            {
                toCol = (_row.getLastCellNum() +1);
                colCount = toCol - colOffset;
            }

            String[] vals = new String[colCount];
            for( int col= colOffset; col<toCol; col++ )
            {
                XSSFCell cell = null;
                value = "";
                if ( _row != null )
                    cell = _row.getCell( col ) ;


                if ( cell != null )

                    if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ){
                        if (DateUtil.isCellDateFormatted((Cell)cell))
                            value = cell.getDateCellValue()!=null?formatter.format(cell.getDateCellValue()):"";
                        else
                            value =""+cell.getRawValue();
                    }else if(cell.getCellType() ==XSSFCell.CELL_TYPE_BOOLEAN){
                        value =  String.valueOf(cell.getBooleanCellValue());
                    }else
                        value =  cell.getStringCellValue();

                //if ( value != null && value.length() == 0 )
                //	value = null;

                //vals[col-colOffset] = new Val( row-rowOffset, col-colOffset, value  );
                vals[col-colOffset] = value;
            }
            rows[row-rowOffset] = vals;
        }

    }
}
