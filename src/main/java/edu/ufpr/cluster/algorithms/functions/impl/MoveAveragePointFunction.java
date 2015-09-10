package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.algorithms.functions.Function;

public class MoveAveragePointFunction implements Function<ClusteringContext> {

	@Override
	public void apply(ClusteringContext context) {

		List<Point> nonNullPoints = new ArrayList<Point>();
		
		Point p;
		do{
			int r = JMetalRandom.getInstance().nextInt(0, context.getPoints().size() - 1);
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
