package edu.ufpr.cluster.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import edu.ufpr.jmetal.problem.DataInstanceReader;

public class ClusteringAlgorithm {

	private Function<ClusteringContext,Void> init;
	
	private List<Function<ClusteringContext,Void>> functions;
	
	private ClusteringContext clusteringContext;
	
	private Function<List<Point>, Double> distanceFunction;
	
	private int k;
	
	
	
	//TODO: Obtain this points from somewhere. Currently the point are being set by a setMethod
	private List<Point> points;
	
	public ClusteringAlgorithm(Function<ClusteringContext, Void> init,
			List<Function<ClusteringContext, Void>> functions, Function<List<Point>, Double> distanceFunction, int k) {
		this.init = init;
		this.functions = functions;
		this.distanceFunction = distanceFunction;
		this.k = k;

	}
	
	
	
	public List<Cluster> execute() {
		
		this.clusteringContext = new ClusteringContext(points, distanceFunction, k);
		//TODO: check from where this parameters should be given
		int maxEvaluations = 100000;
		int evaluations = 0;
		
		//Call to the initialization functions of the centroids
		init.apply(clusteringContext);
		
		//Run sequentially the functions until the maxEvaluations value
		while (evaluations < maxEvaluations) {
//			for(Function<ClusteringContext, Void> function: functions) {
//				function.apply(clusteringContext);
//			}
			evaluations++;
		}
		
		//Finished the algorithm returning the cluster list
		return clusteringContext.getClusters();
	}



	public List<Point> getPoints() {
		return points;
	}



	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	


	
	
	
}
