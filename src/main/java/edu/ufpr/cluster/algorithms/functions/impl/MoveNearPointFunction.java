package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.Function;
import edu.ufpr.cluster.random.ClusteringRandom;

public class MoveNearPointFunction implements Function<ClusteringContext> {

	@Override
	public void apply(ClusteringContext context) {

		List<Point> nonNullPoints = new ArrayList<Point>();
		
		Point p;
		do{
			int r = ClusteringRandom.getInstance().nextInt(0, context.getPoints().size() - 1);
			p = context.getPoints().get(r);
			if(p.getCluster() != null && !nonNullPoints.contains(p)) {
				nonNullPoints.add(p);
				if(nonNullPoints.size() == context.getPoints().size()) {
					return;
				}
			}
		}while(p.getCluster() != null);
		
		Cluster minCluster = context.getClusters().get(0);
		double minDistance = Double.MAX_VALUE;

		for (Cluster cluster : context.getClusters()) {
			double dist = context.getDistanceFunction().apply(Lists.newArrayList(p, cluster.getCentroid()));
			if (dist < minDistance) {
				minDistance = dist;
				minCluster = cluster;
			}
		}

		minCluster.addPoint(p);
		minCluster.updateCentroid();
	}

	@Override
	public String toString() {
		return "MoveNearPointFunction";
	}

}
