package edu.ufpr.cluster.algorithm;

import java.util.List;

import edu.ufpr.cluster.algorithms.functions.Function;

public class ClusteringAlgorithm {

	private Function<ClusteringContext> initilization;
	
	private List<Function<ClusteringContext>> functions;
	
	private ClusteringContext clusteringContext;
	
	private java.util.function.Function<List<Point>, Double> distanceFunction;
	
	private int k;
	
	
	//TODO: Obtain this points from somewhere. Currently the point are being set by a setMethod
	private List<Point> points;
	
	public ClusteringAlgorithm(Function<ClusteringContext> init,
			List<Function<ClusteringContext>> functions, java.util.function.Function<List<Point>, Double> distanceFunction, int k) {
		this.initilization = init;
		this.functions = functions;
		this.distanceFunction = distanceFunction;
		this.k = k;

	}
	
	
	
	public ClusteringContext execute() {
		
		this.clusteringContext = new ClusteringContext(points, distanceFunction, k);
		//TODO: check from where this parameters should be given
		int maxEvaluations = 100000;
		int evaluations = 0;
		
		//Call to the initialization functions of the centroids
		initilization.apply(clusteringContext);
		
		//Run sequentially the functions until the maxEvaluations value
		while (evaluations < maxEvaluations) {
//			for(Function<ClusteringContext, Void> function: functions) {
//				function.apply(clusteringContext);
//			}
			evaluations++;
		}
		
		//Finished the algorithm returning the cluster list
		return clusteringContext;
	}



	public List<Point> getPoints() {
		return points;
	}



	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	


	
	
	
}
