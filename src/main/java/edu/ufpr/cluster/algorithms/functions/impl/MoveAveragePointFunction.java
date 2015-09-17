package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.Function;
import edu.ufpr.cluster.random.ClusteringRandom;

public class MoveAveragePointFunction implements Function<ClusteringContext> {

	@Override
	public void apply(ClusteringContext context) {

		List<Point> nonClusterizedPoints = new ArrayList<Point>();
		
		for(Point p : context.getPoints()) {
			if(p.getCluster() == null) nonClusterizedPoints.add(p);
		}
		
		int r = ClusteringRandom.getInstance().nextInt(0, nonClusterizedPoints.size() - 1);
		Point p = context.getPoints().get(r);
		
		Cluster minCluster = context.getClusters().get(0);
		double minDistance = Double.MAX_VALUE;

		for (Cluster cluster : context.getClusters()) {			
			double sum = 0.0;
			for (Point q : cluster.getPoints()) {
				sum += context.getDistanceFunction().apply(Lists.newArrayList(p, q));
			}
			sum = sum / cluster.getPoints().size();
			if (sum < minDistance) {
				minDistance = sum;
				minCluster = cluster;
			}
		}
		minCluster.addPoint(p);
		minCluster.updateCentroid();
	}

	@Override
	public String toString() {
		return "MoveAveragePointFunction";
	}

}
