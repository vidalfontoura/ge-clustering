package edu.ufpr.cluster.algorithm;

import java.util.List;

import edu.ufpr.cluster.algorithms.functions.Function;
import edu.ufpr.cluster.algorithms.functions.InitializiationFunction;

public class ClusteringAlgorithm {

	private InitializiationFunction initilization;

	private List<Function<ClusteringContext>> functions;

	private ClusteringContext clusteringContext;

	private java.util.function.Function<List<Point>, Double> distanceFunction;

	// TODO: Obtain this points from somewhere. Currently the point are being
	// set by a setMethod
	private List<Point> points;

	public ClusteringAlgorithm(InitializiationFunction initialization, List<Function<ClusteringContext>> functions,
			java.util.function.Function<List<Point>, Double> distanceFunction) {
		this.initilization = initialization;
		this.functions = functions;
		this.distanceFunction = distanceFunction;

	}

	public ClusteringContext execute() {

		this.clusteringContext = new ClusteringContext(points, distanceFunction);
		// TODO: check from where this parameters should be given
		int maxEvaluations = 100000;
		int evaluations = 0;

		// Call to the initialization functions of the centroids
		initilization.apply(clusteringContext);

		// Run sequentially the functions until the maxEvaluations value
		while (evaluations < maxEvaluations) {
			for (Function<ClusteringContext> function : functions) {
				function.apply(clusteringContext);
			}
			evaluations++;

		}

		// Finished the algorithm returning the cluster list
		return clusteringContext;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public InitializiationFunction getInitilization() {
		return initilization;
	}

	public void setInitilization(InitializiationFunction initilization) {
		this.initilization = initilization;
	}

	public List<Function<ClusteringContext>> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function<ClusteringContext>> functions) {
		this.functions = functions;
	}

	public ClusteringContext getClusteringContext() {
		return clusteringContext;
	}

	public void setClusteringContext(ClusteringContext clusteringContext) {
		this.clusteringContext = clusteringContext;
	}

	public java.util.function.Function<List<Point>, Double> getDistanceFunction() {
		return distanceFunction;
	}

	public void setDistanceFunction(java.util.function.Function<List<Point>, Double> distanceFunction) {
		this.distanceFunction = distanceFunction;
	}

	public int getInitialK() {
		return this.getInitilization().getInitialK();
	}

}
