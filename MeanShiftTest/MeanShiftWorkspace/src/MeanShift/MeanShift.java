
package MeanShift;
import java.util.*;
import java.util.Arrays;
import java.lang.Math;
import java.util.Vector;
public class MeanShift {
		
	public static void main(String[] args) 
	{
	}
	
	public static void Add(int i, float x, float y, float q, float a1[][])
// Column guide for array a1/arr || Column: 1:x  2:y    3:was in current cluster window(0/1)    4:was ever in cluster?(0/1)   5:cluster number	6: Weight of point												
	{
		
		int col = 0;
		
		a1[i][col] = x;
		
		col++;
		a1[i][col] = y;
		
		col = 5;
		a1[i][col] = q;
		
		
//		System.out.println("COORD at row " + i + " is " + x + ", " +y);
	} // end Add function
	
	
	
	
	public static void addProbPoint(float x, float y, int otherCluster, float cluster[][], float arr[][], float problemPoints[][])
	{
		for(int row = 0; row < problemPoints.length; row++) // look at each row
		{
			if(problemPoints[row][0] == x && problemPoints[row][1] == y) //does the point exists already?
			{
				for(int col = 2; col < problemPoints.length; col++) // yes it does! go through columns, look for clusternumber
				{
					if(problemPoints[row][col] == otherCluster)
					{
						break; // that problem point already has a problem with that specific cluster
					}
					else if(problemPoints[row][col] == 0) // if that clusterNumber wasn't found, and there's an empty slot
					{
						problemPoints[row][col] = otherCluster;
						break; //adds point, break out
					}
				}
				break; // the point has been found, break out of forloop whether cluster number is added or not
			}
			if(problemPoints[row][0] == 0 && problemPoints[row][1] == 0 ) // if the point wasn't found above but an empty spot is found
			{
				problemPoints[row][0] = x;
				problemPoints[row][1] = y;
				problemPoints[row][2] = otherCluster;
				break;	//added the point of conflict and the cluster it is in conflict with
			}
		}
	} // end addProbPoint function
	
	public static void pickClusterForPoint(float arr[][], Vector v1, Vector v2, float problemPoints[][])
	{
//		System.out.println(" -=-=-=-=-=- STARTING pickClusterForPoint =-=---==--==-=");
		float mainCluster = 0;
		float comparisonCluster = 0;
		int column = 2; //column number starts with 2 (index 0 and 1 are the X and Y of the problem point
						//index 2 is the first "ClusterNumber" it has a problem with, hence start at 2 below
		
		for(int row = 0; row < problemPoints.length; row++) //go through each problem point in array
		{
//			System.out.println(" ");
			float getPointsClusterX; //we need to find the problem points main cluster, it isnt stored in problemPoints
			float getPointsClusterY;
			column = 2;
			v1.clear();
			
			getPointsClusterX = problemPoints[row][0];	// assigning X and Y to find it's main Cluster in arr/a1
			getPointsClusterY = problemPoints[row][1];
			
			if(getPointsClusterX == 0 && getPointsClusterY == 0)
			{
				break; //we've reached no more points in the problemPoints list, break out
			}
			
			for(int i = 0; i < arr.length; i++) // going through arr looking for that point
			{
				if(arr[i][0] == getPointsClusterX && arr[i][1] == getPointsClusterY) //nice, we found the match
				{
					mainCluster = arr[i][4]; //remember, column 5 (index 4) stores that point's main cluster number
					break; // we found that point's clusterNumber from arr, break out, we're done
				}
			}
//			System.out.println("New point in question is " + getPointsClusterX + " " + getPointsClusterY + " with cluster " + mainCluster);
			for(int i = 0; i < arr.length; i++)
			{
				if(arr[i][4] == mainCluster)
				{
					v1.add(arr[i][4]);	//v1 will be for our mainCluster
				}
			}
			
			while(true)
			{
				v2.clear();
				comparisonCluster = problemPoints[row][column]; 
//				System.out.println("In While Loop for point " + getPointsClusterX + " " + getPointsClusterY + " looking at cluster " + comparisonCluster);
				if(comparisonCluster == 0)
				{
//					System.out.println("This cluster number is 0, break out");
					column = 2;
					break; //we're done with the funciton if the comparisonCluster is now 0
							// in other words there is no more clusters to compare in that row (for that specific point)
				}
				
				
				for(int i = 0; i < arr.length; i++)
				{
					if(arr[i][4] == comparisonCluster)
					{
						v2.add(arr[i][4]); //v2 will be for our cluster that we are comparing to our main cluster
					}
				}
				
				
				if(v2.size() > v1.size()) //if the comparison cluster is larger, make the point's "main cluster" this one
				{
//					System.out.println("Main cluster " + mainCluster + " is smaller than cluster " + comparisonCluster);
					for(int i = 0; i < arr.length; i++) // going through arr looking for that point
					{
						if(arr[i][0] == getPointsClusterX && arr[i][1] == getPointsClusterY) //nice, we found the match
						{
//							System.out.println("Cluster " + mainCluster + " is now becoming " + comparisonCluster);
							arr[i][4] = comparisonCluster; // assigning that point's cluster to the comparison one
							problemPoints[row][column] = -1; //that point no longer has a problem with that cluster, assigning -1 to the tag
							column++; //go to the next cluster that point has a problem with, while loop continues
							break; // We assigned that point to the cluster with the highest points
						}
					}
					
				}
				else
				{
//					System.out.println("Main cluster(" + v1.size() + ") is larger than comparison cluster(" + v2.size() + ") ");
					//The comparisonCluster didn't have more points, the point kept it's original cluster
					problemPoints[row][column] = -1; //that point no longer has a problem with that cluster, assigning -1 to the tag
					column++; //go to the next cluster that point has a problem with, while loop continues
					
				}
				
			}// end while loop
			
		}// end going through each point(row) in problemPoints
		
	} // end pickClusterForPoint function
	
	
	
	public static void mergeCluster(float cluster1, float cluster2, float arr[][], float problemPoints[][], float centroids[][])
	{
		int pointCounter1 = 0;
		int pointCounter2 = 0;
		float winningCluster = 0;
		float losingCluster = 0;
		
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i][4] == cluster1)
			{
				pointCounter1++;
			}
			if(arr[i][4] == cluster2)
			{
				pointCounter2++;
			}
		}
		
//		System.out.println(" -=-= FOR Clusters " + cluster1 + " and " + cluster2 + "-=--=-");
//		System.out.println("Cluster" + cluster1 + " has " + pointCounter1 + " points. ");
//		System.out.println("Cluster" + cluster2 + " has " + pointCounter2 + " points. ");
		
		if(pointCounter1 >= pointCounter2)
		{
//			System.out.println("Cluster" + cluster2 + " is merging into Cluster " + cluster1);
			winningCluster = cluster1;
			losingCluster = cluster2;
		}
		else
		{
//			System.out.println("Cluster" + cluster1 + " is merging into Cluster " + cluster2);
			winningCluster = cluster2;
			losingCluster = cluster1;
		}
		
		for(int i = 0; i < centroids.length; i++) //updates the losing centroid
		{
			if(centroids[i][2] == losingCluster) // Losing cluster's centroid is removed from centroid list
			{
				centroids[i][0] = -1;
				centroids[i][1] = -1;
				centroids[i][2] = -1;
				break;
			}
		}
		
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i][4] == losingCluster) //if a point in arr1 is assigned to the losing cluster
			{
				for(int row = 0; row < problemPoints.length; row++) // look at each row in problem points
				{
					if(problemPoints[row][0] == arr[i][0] && problemPoints[row][1] == arr[i][1]) //find the point
					{
						for(int col = 2; col < problemPoints.length; col++) // go through columns, look for "winning cluster"
						{
							if(problemPoints[row][col] == winningCluster)
							{
//								System.out.println("Point " + problemPoints[row][0] + " " + problemPoints[row][1] + " no longer has a problem with cluster " + winningCluster);
								problemPoints[row][col] = -1;
								break; // that problem point no longer has a problem with that cluster
							}
							else if(problemPoints[row][col] == 0)
							{
								break; //reached the end of cluster points it has a problem with
							}
						}
						break; // the point has been found, break out of forloop whether cluster number is removed or not
					}
				} // that cluster was taken off of the point in the "problem point" array
 				arr[i][4] = winningCluster;
//				System.out.println("Point " + arr[i][0] + " " + arr[i][1] + " was moved into cluster " + arr[i][4]);
			}
		}
//		System.out.println(" ");
		
		
		
	} // end mergeCluster function
	
	
	
	
	public static void checkCentroidWindow(float centroids[][], float arr[][], float problemPoints[][], int windowSize)
	{
		//int windowSize = 15;
//		System.out.println("======= STARTING to check Centroids =======");
//		System.out.println(" ");
		for(int i = 0; i < centroids.length; i++) // go through centroid list
		{
			if(centroids[i][0] != -1 && centroids[i][1] != -1 ) // if that row has a centroid point
			{	
				float x = centroids[i][0]; //centroid x thats being compared to the other centroids
				float y = centroids[i][1]; //centroid y ^^^
				//System.out.println("Centroid " + x + " " + y + " is at row " + i);
				float x2; // each centroid point
				float y2;
				
				if(i != (centroids.length-1)) //if i is not at the very last element of the list, fixes out of bounds error
				{
					if((centroids[i+1][0] != 0 && centroids[i+1][1] != 0) || (centroids[i+1][0] != -1 && centroids[i+1][1] != -1) ) // does the initial comparison point == 0,0 ?
					{	
						for(int j = i+1; j < centroids.length; j++)   // PROBLEM, ASSIGNING x2 and y2 TO 0 ?????
						{
							x2 = centroids[j][0];
							y2 = centroids[j][1];
							
							if(x2 != 0 && y2 != 0) // checks if each continuous point after == 0,0
							{
								double distance = Eu(x, y, x2, y2);
								if(distance <= windowSize)
								{
//									System.out.print("===Clusters: " + (centroids[i][2]) + " and " + (centroids[j][2]) + "-> ");
//									System.out.println("Centroid point " + x + " " + y + " and " + x2 + " " + y2 + " are in each others scopes");
									//add something that puts all points of one cluster into another
									mergeCluster(centroids[i][2], centroids[j][2], arr, problemPoints, centroids);
								}
								else
								{
								}
							}
							else
							{
								//System.out.println("Next value is 0,0 " + centroids[j][0] + " " + centroids[j][1]);
								break; // if a point does end up == 0,0 then break, you've gone through the centroid list
							}	
						}
					}
					else if(centroids[i+1][0] == -1 && centroids[i+1][1] == -1)
					{
						continue;
					}
					else
					{
						//System.out.println("Next initial value is 0,0 " + centroids[i+1][0] + " " + centroids[i+1][1]);
						break; // if i == last point, then i+1 will just be a 0,0 point, hence, break
					}
				
				}
				
			}
			else // if the current row doens't have a centroid point
			{
				break; //there's no more points, break
			}
				
		}
	} //end checkCentroidWindow function
	
	
	
	public static double Eu(float x, float y, float x2, float y2)  //x and y is centroid, x2 and y2 is the point in question
	{
		double distance = Math.sqrt(Math.pow(x-x2,2) + Math.pow(y-y2,2));
		return distance;
	} // end Eu function
	
	public static void Cluster(float x, float y, float arr[][], int n, float cluster[][], int clusterNumber, float centroids[][], int stop[], float fixClusters1[][], float fixClusters2[][], float problemPoints[][], int windowSize, int Q)
	{
// Column guide for array a1/arr || Column: 1:x  2:y    3:was in current cluster window(0/1)    4:was ever in cluster?(0/1)   5:cluster number   6: weight of point
		
		
		int start = 1;
		float x2;			// x2 and y2 is the point in question when comparing to centroid
		float y2;
		float newX = x;		//newX and newY is the centroid
		float newY = y;
		float distanceTotalX = 0;		// Total x values
		float distanceMeanX = 0;		// Mean for x values
		float distanceTotalY = 0;		// Total y values
		float distanceMeanY = 0;		// mean for y values
		float nextClusterX = 0;			// new centroid for the new cluster
		float nextClusterY = 0;			// new centroid for the new cluster
		boolean changeCentroid = false;  // if the original NewX / NewY changed by the end, then mean needs reevaluated
		int numberOfClusterPoints = 0;   // number of points in the cluster window
		//float windowSize = 15;			 // size of cluster window
		int recallFunction = 0;			// number that is returned to clusterNumber in CETSP
		recallFunction = clusterNumber;  //Gets reassigned later
		
//		System.out.println(" ENTERED CLUSTER FUNCTION HERE!!!!!");
		
			for(int row = 0; row < n; row++)
			{
				x2 = arr[row][0];      // Sets x2 and y2 to a coord within array1
				y2 = arr[row][1];
				
				double distance = Eu(x, y, x2, y2);   //Finds the distance between center
													  // and that specific point
				
				
				
				if(distance <= windowSize)	//if point is close enough
				{
					arr[row][2] = 1;
					//System.out.println(" DISTANCE IS <= windowSize !!!!!!! at row " + row);
					
				//	if(arr[row][5]+centroids[clusterNumber-1][3] <= Q)
				//	{
					/**
					 * SOMETHING WRONG WITH WEIGHTS, works when taking this restraint out ^^
					 */
	
						arr[row][2] = 1;        // set column 3 to 1 (point is now in current cluster being made's window tag)
						arr[row][3] = 1;		// set column 4 to 1 (point has now been added to a cluster previously tag)
						
						if(arr[row][4] != 0 && arr[row][4] != clusterNumber) // it already has been assigned a cluster, but is in new cluster's window
						{
							addProbPoint(x2, y2, clusterNumber, cluster, arr, problemPoints); //point becomes a "problem point"
						}
						else // hasn't been assigned a cluster yet
						{
							arr[row][4] = clusterNumber; //set column 5 to the clusterNumber it's in
							centroids[clusterNumber-1][3] = centroids[clusterNumber-1][3] + arr[row][5];
							//System.out.println(x2 + " " + y2 + " have index 3 set to 1 for cluster # " + clusterNumber);
						}
				//	}
					
					
				}  // ends if statement, checking if point is in cluster window
					
				
			} // ends for loop checking distance of each point
			
			
		
		
		for(int row = 0; row < n; row++)
		{
			if(arr[row][2] != 0)     // Counts how many points are in the cluster window
			{
				numberOfClusterPoints++;
			}
		}
		
		for(int row = 0; row < n; row++)
		{
			if(arr[row][2] == 1) //&& arr[row][4] == clusterNumber)     // uses points in cluster window to find potential new mean
			{
				
				x2 = arr[row][0];
				y2 = arr[row][1];			//use each point within the cluster window to calculate new mean
			
				distanceTotalX = distanceTotalX + x2;
				distanceTotalY = distanceTotalY + y2;
			}
		}
		
		for(int row = 0; row < n; row++)
		{
			if(arr[row][2] == 1)
			{
				arr[row][2] = 0;	//set all "in current cluster window" tags back to 0	
				
			}
		}
		
		distanceMeanX = distanceTotalX/numberOfClusterPoints;   //Calculate all relevant points' x and y means
//		System.out.println(" Distance mean X was calculated from " + distanceTotalX + " divded by " + numberOfClusterPoints);
		
		distanceMeanY = distanceTotalY/numberOfClusterPoints;
//		System.out.println(" Distance mean Y was calculated from " + distanceTotalY + " divded by " + numberOfClusterPoints);
		
		
		
		if(distanceMeanX != x)     // if the distance mean for x has changed, update X and update Centroid
		{
			newX = distanceMeanX;	//sets the new midpoint x 
			changeCentroid = true;
		}
		
		if(distanceMeanY != y)     // same concept, except for y
		{
			newY = distanceMeanY; 	//sets the new midpoint y
			changeCentroid = true;
		}
		
		
		
		if(changeCentroid == true)  // if X or Y changed, centroid needs to be recalculated
		{
//			System.out.println("CENTROID IS NOW " + newX + " and " + newY);
//			System.out.println("CLUSTER IS SHIFTING to NEW CENTROID !!!!!!!!!!");
//			System.out.println(" ");
			
			changeCentroid = false;
			Cluster(newX, newY, arr, n, cluster, clusterNumber, centroids, stop, fixClusters1, fixClusters2, problemPoints, windowSize, Q);   //calls the same function with new x and y centroid
			
		}
		else	//If the centroid isn't being changed, do this else statement
		{
//			System.out.println("X and Y DIDNT CHANGE!! WE CAN MAKE A NEW CLUSTER!!!");			
			
			for(int k = 0; k < n; k++)
			{
				if(centroids[k][0] == 0 && centroids[k][1] == 0)  // finds next available spot to add centroid in the centroid list
				{
					centroids[k][0] = x;
					centroids[k][1] = y;
					centroids[k][2] = clusterNumber;
//					System.out.println("Added point " + x + " " + y + " at row " + k);
					break;
				}
				else if(centroids[k][0] != 0 && centroids[k][1] != 0 && k == n) // this "else if" is just a "making sure it works correctly" check
				{
//					System.out.println("There is no more room for new centroids!!"); // this SHOULDNT be possible
//					System.out.println("We can't add points " + x + " " + y);		//this array size is the same as number of points
//					System.out.println(" ");
				}
			}
		
			for(int row = 0; row < n; row++)
			{
				
				if(arr[row][3] == 1 && row == (n-1)) // if there isn't another unClustered point
				{
//					System.out.println("No points remain!! All have been clustered!!");
					//System.out.println("REASSIGNING clsuterNumber to -99!");
					//clusterNumber = -99;
					//System.out.println("REASSIGNING clsuterNumber to -99!  "  + clusterNumber);
					
					recallFunction = -1;  //in other words, this triggers the end of the CETSP forloop, look below
					break;
					
				}
				else if(arr[row][3] == 0)	//Finds the next point that hasn't been added to a cluster
				{
					//System.out.println("APPARENTLY index 3 == 0, Index 3 actually == " + arr[row][3]);
					
					nextClusterX = arr[row][0];		//Sets that point as the new clusterpoint
					nextClusterY = arr[row][1];
					
					// =------==-=-=--==-=- ADDED THIS 11/29/18
					arr[row][3] = 1;
					arr[row][4] = (clusterNumber+1);
					// -=-=-=-==--===-=--=-=-=
					
					
//					System.out.println("The new X is " + nextClusterX + " and the new Y is " + nextClusterY + " for the new cluster. ");
//					System.out.println("");
//					System.out.println("");
					
					cluster[0][0] = nextClusterX;    //assigns next X and Y to cluster
					cluster[0][1] = nextClusterY;   // mainly did this because i can't return multiple values(new x, new y, and cluster number)
					
					for(int i = 0; i < n; i++)
					{
						arr[i][2] = 0;	 //Reset column 3 to 0, all points are now able to be put into new cluster, already did this
					}
					
					int test = clusterNumber + 1;
//					System.out.println("The Clusternumber is " + clusterNumber + " Moving onto cluster " + test);
//					System.out.println(" ");
					
					break;    //A new cluster point has been found, break out of for loop, then return value at bottom
					
					
				}   // ends for loop looking for next unClustered point to be assigned as the new cluster centroid
				
				
			} //ends loop looking for an un-assigned point to become the new cluster point
			
		} // ends the "Cluster hasn't changed, make a new cluster!" block
		
		if(recallFunction == -1)  //recallFunction == -1 ( ending the loop in CETSP )
		{
			PrintUnClusteredPoints(arr, n); // if there is somehow unclustered points, print those points
			stop[0] = 1;
		}
		
	} // end Cluster function
	
	
	
	/*
	public static void ClusterPrint(float arr[][], int clusterNumber)
	{
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i][4] == clusterNumber)
			{
			System.out.println("Cluster #: " + clusterNumber + " Row " + i + " is " + arr[i][0] + " " + arr[i][1]);
			}
		}
	}
	
	public static void ClusterReset(float cluster[][])
	{
		for(int i = 0; i < cluster.length; i++)
		{
			if(cluster[i][0] != 0 && cluster[i][1] != 0)
			{
				cluster[i][0] = 0;
				cluster[i][1] = 0;
			}
			else
			{
				break;  //reached the last point in the array/list, break out
			}
		}
		System.out.println("Cluster has been reset.");
	} // end ClusterReset Function
	*/
	
	
	public static void ProbPointPrint(float problemPoints[][])
	{
//		System.out.println("======= PRINTING Problem points and cluster numbers =======");
//		System.out.println(" ");
		
		for(int i = 0; i < problemPoints.length; i++)
		{
			if(problemPoints[i][0] != 0 && problemPoints[i][1] != 0) //if there's a "problem point"
			{
				float x = problemPoints[i][0];
				float y = problemPoints[i][1];
//				System.out.print(i + ": Point " + x + " " + y + " conflicts with cluster: " );
				
				for(int j = 2; j < problemPoints.length; j++) //until cluster == 0, keep printing clusters, there will never be a cluster 0
				{ //cluster == 0 indicates there's no more conflicting clusters the rest of the way
					if(problemPoints[i][j] != 0) // if the cluster is anything but 0, print it
					{
//						System.out.print(problemPoints[i][j] + " ");
					}
					else // if that column is 0, there's no more clusters left to print
					{
						break; //break out
					}
				}
				
//				System.out.println(" ");
			}
			else // no more "problem points left"
			{
				break; //function is over, break out
			}
		}
	} //end ProbPrintPoint function
	
	public static void PrintUnClusteredPoints(float arr[][], int n)
	{
//		System.out.println("======= PRINTING unClustered Points =======");
//		System.out.println(" ");
		for(int row = 0; row < n; row++)
		{
			if(arr[row][3] == 0)
			{
				float x = arr[row][0];
				float y = arr[row][1];
				
//				System.out.println("Row: " + row + " is " + x + " " + y);
			}
		}
	} // end printUnClusteredPoints function
	
	public static void CentroidPrint(float centroid[][], int n)
	{
		System.out.println("======= PRINTING Centroids =======");
		System.out.println("            X           Y      Centroid Number");
		System.out.println(" ");
		
		for(int row = 0; row < centroid.length; row++)
		{
			if(centroid[row][0] != 0 && centroid[row][1] != 0)
			{
				float x = centroid[row][0];
				float y = centroid[row][1];
				
				System.out.println("Row: " + row + " is " + x + "  " + y  + "  " + centroid[row][2]);
			}
		}
		
	} //end CentroidPrint function
	
	public static void updateWeights(float centroid[][], float a1[][])
	{
		float x;
		float y;
		float clusterNumber;
		float q; //demand of the point
		float totalWeight; //totalWeight for current cluster 
		
		for(int row = 0; row < centroid.length; row++)
		{
			if(centroid[row][0] == -1 && centroid[row][1] == -1)
			{
				continue;
			}
			else
			{
				totalWeight = 0; //reset totalWeight for next cluster
				clusterNumber = centroid[row][2];
				if(clusterNumber == 0)
				{
					break; //already updated the last point, break out
				}
				
				for(int i = 0; i < a1.length; i++)
				{
					if(a1[i][4] == clusterNumber)
					{
						totalWeight = totalWeight + a1[row][5];
					}
				}
				
				centroid[row][3] = totalWeight;
			}
		}
	}
	
	public static void Print(float a1[][], int windowSize)  //n number of customers  Prints main array -> a1
	{
		
//		System.out.println(" -=-=-=--= PRINTING ARRAY -=-=-=-=-=-=");
//		System.out.println("        X   Y   Cluster Number   Point Weight");
		
		for(int i = 0; i < a1.length; i++)
		{
//			System.out.print("Row " + i + " ");
			for(int j = 0; j < 2; j++)
			{
				System.out.print(a1[i][j] + " ");
			}
//			System.out.print(a1[i][4] + " " + a1[i][5]);
//			System.out.println("");
		}
//		System.out.println(" -=-=-=--= DONE -=-=-=-=-=-=");	
	} // end Print function


	public static int CentroidCount(float[][] centroids) {
		int count  = 0;
		
		for(int i = 0; i < centroids.length; i++) {
			if ( centroids[i][0] > 0 && centroids[i][1] > 0)
			count++;
		}
		
		return count;
	}


	public static void FinalCentroids(float[][] centroids, float[][] finalCentroids) {
		
		for(int i = 0; i < centroids.length; i++) {
			if( centroids[i][0] > 0 && centroids[i][1] > 0) {
				for(int j = 0; j < finalCentroids.length;j++) {
					if( finalCentroids[j][0] == 0 && finalCentroids[j][1] == 0) {
						finalCentroids[j][0] = centroids[i][0];
						finalCentroids[j][1] = centroids[i][1];
						finalCentroids[j][2] = centroids[i][2];
						finalCentroids[j][3] = centroids[i][3];
						break;
					}
				}
				
				
			}
		}
		

		
	}


	public static void FindEdges(float[][] a1, float[][] finalCentroids, float[][] edgeArray, float[][] edgePoints, int numofClusters) {
		
		int lastElemental = (finalCentroids.length) * 2;
		int iteration = 0;
		double distance = 0;
		double minDistance = 100;
		int count  = 0;
		int tracker = 0;
		float clusternum = 0;
		
		
		while (edgePoints[lastElemental][0] == 0) {
			
			
			
			
			for(iteration = 0; iteration < finalCentroids.length;iteration++) {
				
				for (int j = 0; j < a1.length;j++) {
					if(a1[j][4] != finalCentroids[iteration][2] && edgeArray[iteration][3] == 0) {
					distance = Eu(finalCentroids[iteration][0], finalCentroids[iteration][1], a1[j][0], a1[j][1]);
					if ( distance < minDistance) {
						minDistance = distance;
						tracker = j;
						clusternum = a1[j][4];
					}
					
				}
				}
				edgePoints[count][0] = a1[tracker][0];
				edgePoints[count][1] = a1[tracker][1];
				count++;
				edgeArray[(int) clusternum][1] += 1;
				
				
				for(int k = 0; k < a1.length; k++) {
					if(a1[k][4] == finalCentroids[iteration][2]) {
						distance = Eu(edgePoints[count][0], edgePoints[iteration][1], a1[k][0], a1[k][1]);
						if ( distance < minDistance) {
							minDistance = distance;
							tracker = k;
							clusternum = a1[k][4];
						}
						
						
					}
				}
				edgePoints[count][0] = a1[tracker][0];
				edgePoints[count][1] = a1[tracker][1];
				edgeArray[(int) clusternum][1] += 1;
				
				count++;
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}

		
		
		
		
		
		
	}
	
	
} //ends class
