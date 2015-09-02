package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.function.Function;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import com.google.common.collect.Lists;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;

public class SplitClustersFunction implements Function<ClusteringContext, Void> {

	@Override
	public Void apply(ClusteringContext context) {
		
		Cluster cluster = context.getClusters().get(0);
		
		double maxAvg = 0.0;
		for (Cluster c : context.getClusters()) {
			double avg = 0.0;
			for (Point point : c.getPoints()) {
				avg += context.getDistanceFunction().apply(Lists.newArrayList(point, c.getCentroid()));
			}
			
			avg = avg / cluster.getPoints().size();
			
			if(avg > maxAvg) {
				maxAvg = avg;
				cluster = c;
			}
		}
		
		Cluster cluster1 = new Cluster();
		Cluster cluster2 = new Cluster();
		
		for (Point point : cluster.getPoints()) {
			double dist = context.getDistanceFunction().apply(Lists.newArrayList(point, cluster.getCentroid()));
			if (dist <= maxAvg) cluster1.addPoint(point);
			else cluster2.addPoint(point);
		}
		cluster1.updateCentroid();
		cluster2.updateCentroid();
		context.getClusters().add(cluster1);
		context.getClusters().add(cluster2);
		cluster.getPoints().clear();
		context.getClusters().remove(cluster);
		
		return null;
	}
	
}
