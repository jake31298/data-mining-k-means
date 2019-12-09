package MeanShift;
import java.io.*;
import java.util.*;
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
	static ArrayList<ArrayList<Float> > centList = new ArrayList<ArrayList<Float> >();
	static ArrayList<ArrayList<Float> > arrList = new ArrayList<ArrayList<Float> >();
	
	public static void getArray(float arr[][], float[][] centroids)
	{
		for(int i = 0; i < arr.length; i++)
		{
			arrList.add(new ArrayList<Float>());
			arrList.get(i).add(0, arr[i][0]);
			arrList.get(i).add(1, arr[i][1]);
			arrList.get(i).add(2, arr[i][4]);
		}
		
		for(int i = 0; i < centroids.length; i++)
		{
			centList.add(new ArrayList<Float>());
			centList.get(i).add(0, centroids[i][2]);
			System.out.println("Centroid number " + i + " has a number of " + centroids[i][2]);
		}
		
	}
	public static void starting(Stage stage) {
       Plot(stage);
    }
	
	public static void startCall()
	{
		Stage stage = new Stage();
		starting(stage);
	}
 
	
    public static void main(String[] args) {
    	//plot(Stage stage
    	System.out.println("In main");
        launch();
    }
    
    public static void t()
    {
    	launch();
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void Plot(Stage stage)
    {
    	stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(0, 50, 1);
        final NumberAxis yAxis = new NumberAxis(0, 50, 10);        
        final ScatterChart<Number,Number> sc = new
            ScatterChart<Number,Number>(xAxis,yAxis);
        //xAxis.setLabel("Age (years)");                
        //yAxis.setLabel("Returns to date");
        sc.setTitle("Modified K-Means Clusters");
       
        XYChart.Series series1 = new XYChart.Series();
      //  series1.setName("Equities");
      //  series1.getData().add(new XYChart.Data(4.2, 193.2));
        /*
        for(int i = 0; i < arrList.size(); i++)
        {
        	series1.getData().add(new XYChart.Data(arrList.get(i).get(0), arrList.get(i).get(1)));
        }
        */
        /*
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Mutual funds");
        series2.getData().add(new XYChart.Data(20, 20));
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Jake's shit");
        series3.getData().add(new XYChart.Data(42.42, 42.42));
        
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Test");
        */
        
        String temp;
        int tempNum;
        float clusterNum = 0;
        Scene scene  = new Scene(sc, 500, 400);
        
        Vector <XYChart.Series> vecClusters = new Vector<XYChart.Series>();
        System.out.println("centList size is " + centList.size());
        for(int i = 0; i < centList.size(); i++)
        {
        	tempNum = i + 1;
        	temp = "cluster";
        	temp = temp + tempNum;
        	XYChart.Series a = new XYChart.Series();
        	a.setName(temp);
        	vecClusters.add(a);        	
        }
        System.out.println("centList size is now " + centList.size());
        // ADD instances of vectors to a list, find out how to assign points to vectors
        
        System.out.println("arrList size is " + arrList.size());
        for(int i = 0; i < arrList.size(); i++)
        {
        	clusterNum = arrList.get(i).get(2);
        	System.out.println("[index: " + i + "] clusterNum is " + clusterNum);
        	vecClusters.get((int)clusterNum-1).getData().add(new XYChart.Data(arrList.get(i).get(0), arrList.get(i).get(1)));
        }
        
        
        
        for(int i = 0; i < vecClusters.size(); i++)
        {
        	sc.getData().add(vecClusters.get(i));
        }
        
        //temp.getData().add(newXYChart.Data(50, 50));
        
        
       // sc.getData().addAll(series1, series2);
        
        //Scene scene  = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    
	@Override
	public void start(Stage stage) throws Exception {
		//startCall();
		System.out.println("In start");
		Plot(stage);
		
	}
 
   
    
}
	
  