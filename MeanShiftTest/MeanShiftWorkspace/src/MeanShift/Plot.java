package MeanShift;
import java.io.*;
import java.util.IllegalFormatException;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Plot extends Application{
	/*
    public static void main(String[] args) throws FileNotFoundException, IOException, IllegalFormatException
    {
    	 
    }
   
    public static void Export(float arr[][])
    {
    	int rowCounter = 0;
    	int colCounter = 0;
		  int curCol = 0;	//column counter
		  XSSFWorkbook workbook = new XSSFWorkbook(); // create a book
		  XSSFSheet sheet = workbook.createSheet("Sheet1");// create a sheet
		  XSSFRow curRow = sheet.createRow(rowCounter++); // create a row
		  
		  
		  
		  for(int i=0; i<arr.length-1; i++)
		  {
			  curRow.createCell(colCounter).setCellValue(arr[rowCounter][colCounter]);
			  colCounter++;
			  curRow.createCell(colCounter).setCellValue(arr[rowCounter][colCounter]);
			  colCounter = 0;
			  curRow = sheet.createRow(rowCounter++);
		  }
		  
		  curRow = sheet.createRow(rowCounter++); // create a row
		  curCol = 0;
		  
		 
	      //save the file
	      try 
	      {
	    	  FileOutputStream fout = new FileOutputStream(".\\Data\\ExcelFile.xlsx");
	    	  //FileOutputStream fout = new FileOutputStream(new File(ProblemInfo1.outputPath + "Comparison.xlsx"));
	    	  workbook.write(fout); 
	          fout.close();
	          System.out.println("File has been successfully edited.");
	      } 
	      catch (Exception e) 
	      { 
	          e.printStackTrace(); 
	      } 
		  
	  }
    
    public static void Import()
    {
    	//Open the requested file
    			XSSFWorkbook workbook = new XSSFWorkbook();    
    			FileInputStream fis;
    			XSSFSheet sheet;
    			XSSFRow curRow;
    			int rowCounter = 0; //initial the row counter

    			//    FileInputStream fis;
    			//    InputStreamReader isr;
    			//    BufferedReader br;
    			try {
    				fis = new FileInputStream("D:\\C#\\MeanShift\\A1Data.xlsx");
    				workbook = new XSSFWorkbook(fis);
    				sheet = workbook.getSheetAt(0);
    				curRow = sheet.getRow(rowCounter); // the 2nd row is the problem data

    				//      fis = new FileInputStream(CETSPFileName);
    				//      isr = new InputStreamReader(fis);
    				//      br = new BufferedReader(isr);
    				System.out.println("READING FROM EXCEL FILE: Here's the points");
    				
//THIS CAUSES ERROR	int x = (int)curRow.getCell(0).getNumericCellValue();
				//	int y = (int)curRow.getCell(1).getNumericCellValue();
    			
    				
    			/*	for(int i = 0; i < 499; i++)
    				{
    					int x = (int)curRow.getCell(0).getNumericCellValue();
    					int y = (int)curRow.getCell(1).getNumericCellValue();
    					rowCounter++;
    					curRow = sheet.getRow(rowCounter);
    					System.out.println(x + " " + y);
    				}*/
    				
    		//	}
    		//	catch (Exception e) {
    		//		System.out.println("D:\\C#\\MeanShift\\A1Data.xlsx\" File is not present");
    		//	}
    	//}
    //*/
	@Override
	public void start(Stage stage) 
	{
       Plot(stage);
    }

	public static void main(String[] args) 
    {
    	//plot(Stage stage
        launch(args);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void Plot(Stage stage)
    {
    	stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(0, 10, 1);
        final NumberAxis yAxis = new NumberAxis(-100, 500, 100);        
        final ScatterChart<Number,Number> sc = new
            ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Age (years)");                
        yAxis.setLabel("Returns to date");
        sc.setTitle("Investment Overview");
       
		XYChart.Series series1 = new XYChart.Series();
        series1.setName("Equities");
        series1.getData().add(new XYChart.Data(4.2, 193.2));
        series1.getData().add(new XYChart.Data(2.8, 33.6));
        series1.getData().add(new XYChart.Data(6.2, 24.8));
        series1.getData().add(new XYChart.Data(1, 14));
        series1.getData().add(new XYChart.Data(1.2, 26.4));
        series1.getData().add(new XYChart.Data(4.4, 114.4));
        series1.getData().add(new XYChart.Data(8.5, 323));
        series1.getData().add(new XYChart.Data(6.9, 289.8));
        series1.getData().add(new XYChart.Data(9.9, 287.1));
        series1.getData().add(new XYChart.Data(0.9, -9));
        series1.getData().add(new XYChart.Data(3.2, 150.8));
        series1.getData().add(new XYChart.Data(4.8, 20.8));
        series1.getData().add(new XYChart.Data(7.3, -42.3));
        series1.getData().add(new XYChart.Data(1.8, 81.4));
        series1.getData().add(new XYChart.Data(7.3, 110.3));
        series1.getData().add(new XYChart.Data(2.7, 41.2));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Mutual funds");
        series2.getData().add(new XYChart.Data(5.2, 229.2));
        series2.getData().add(new XYChart.Data(2.4, 37.6));
        series2.getData().add(new XYChart.Data(3.2, 49.8));
        series2.getData().add(new XYChart.Data(1.8, 134));
        series2.getData().add(new XYChart.Data(3.2, 236.2));
        series2.getData().add(new XYChart.Data(7.4, 114.1));
        series2.getData().add(new XYChart.Data(3.5, 323));
        series2.getData().add(new XYChart.Data(9.3, 29.9));
        series2.getData().add(new XYChart.Data(8.1, 287.4));
 
        sc.getData().addAll(series1, series2);
        Scene scene  = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
 
   
    
}
	
  