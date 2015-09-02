package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;

public class MoveNearPointFunction implements Function<ClusteringContext, Void>{

	@Override
	public Void apply(ClusteringContext context) {
		
		int r = JMetalRandom.getInstance().nextInt(0, context.getPoints().size()-1);
		Point p = context.getPoints().get(r);
		Cluster c = p.getCluster();
		
		Cluster minCluster = context.getClusters().get(0);
		double minDistance = Double.MAX_VALUE;
		
		for (Cluster cluster : context.getClusters()) {
			if(cluster != c) {
				
				List<Point> points = new ArrayList<Point>();
				points.add(p);
				points.add(cluster.getCentroid());
				
				double dist = context.getDistanceFunction().apply(points);
				
				if(dist < minDistance) {
					minDistance = dist;
					minCluster = cluster;
				}
			}
		}
		
		if(!minCluster.getPoints().contains(p)) {
			c.getPoints().remove(p);
			minCluster.addPoint(p);
			
			minCluster.updateCentroid();
			if(!c.isEmpty()) c.updateCentroid();
			else context.getClusters().remove(c);
		}
		
		return null;
	}	
}
