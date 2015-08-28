package edu.ufpr.cluster.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ClusteringAlgorithm {

	private Function<ClusteringContext,Void> init;
	
	private List<Function<ClusteringContext,Void>> functions;
	
	private Function<List<Point>, Double> distanceFunction;
	
	private int k;
	
	public ClusteringAlgorithm(Function<ClusteringContext, Void> init,
			List<Function<ClusteringContext, Void>> functions, Function<List<Point>, Double> distance, int k) {
		this.init = init;
		this.functions = functions;
		this.distanceFunction = distance;
		this.k = k;
	}
	
	
	
	public void execute() {
		
		List<Point> points = new ArrayList<>();
		int maxEvaluations = 100000;
		int evaluations = 0;
		
		ClusteringContext algorithmIO = new ClusteringContext(points, distanceFunction, k);
		
		init.apply(algorithmIO);
		
		while (evaluations < maxEvaluations) {
			for(Function<ClusteringContext, Void> function: functions) {
				function.apply(algorithmIO);
			}
			evaluations++;
		}
	}


	
	
	
}
