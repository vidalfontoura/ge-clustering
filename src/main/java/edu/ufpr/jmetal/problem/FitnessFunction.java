package edu.ufpr.jmetal.problem;

import java.util.List;
import java.util.function.Function;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;

/**
 *
 *
 * @author Vidal
 */
public class FitnessFunction implements Function<ClusteringContext, Double> {

	public Double apply(ClusteringContext clusteringContext) {

		// If no cluster is empty && and if each point belongs to only one
		// cluster
		List<Cluster> clusters = clusteringContext.getClusters();
		// clusters.stream().forEach(p -> p.printCluster());
		List<Point> pointsClustered = clusteringContext.getPoints();

		// If exists any cluster if no points it will penalize hard the solution
		for (Cluster c : clusters) {
			List<Point> points = c.getPoints();
			if (points == null || points.size() == 0) {
				return Double.MAX_VALUE;
			}
		}

		// If exists one point that is not clustered it will penalize hard the
		// solution
		for (Point p : pointsClustered) {
			if (p.getCluster() == null) {
				return Double.MAX_VALUE;
			}
		}

		// Calculate fitness
		double fitness = calculateFitness(clusters, pointsClustered);
		return fitness;

	}

	public double calculateFitness(List<Cluster> clusters, List<Point> allPoints) {

		double f = 0.0;
		for (int i = 0; i < clusters.size(); i++) {
			Point centroid = clusters.get(i).getCentroid();
			List<Point> pointsCluster = clusters.get(i).getPoints();

			double sqrtSumCoordinates = 0.0;
			for (int j = 0; j < pointsCluster.size(); j++) {

				Point point = pointsCluster.get(j);
				List<Double> coordinates = point.getCoordinates();
				double sumCoordinates = 0.0;
				for (int a = 0; a < coordinates.size(); a++) {
					Double xA = coordinates.get(a);
					Double cA = centroid.getCoordinates().get(a);
					sumCoordinates += Math.pow(xA - cA, 2);
				}
				sqrtSumCoordinates += Math.sqrt(sumCoordinates);
			}
			f += sqrtSumCoordinates;
		}
		return f;
	}

}
