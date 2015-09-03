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
		
		int r1, r2;
		r1= JMetalRandom.getInstance().nextInt(0, cluster.getPoints().size()-1);
		do {
			r2 = JMetalRandom.getInstance().nextInt(0, cluster.getPoints().size()-1);
		} while(r1 == r2);
		
		Cluster cluster1 = new Cluster(cluster.getPoints().get(r1));
		Cluster cluster2 = new Cluster(cluster.getPoints().get(r2));
			
		for (Point point : cluster.getPoints()) {
			double d1 = context.getDistanceFunction().apply(Lists.newArrayList(point, cluster1.getCentroid()));
			double d2 = context.getDistanceFunction().apply(Lists.newArrayList(point, cluster2.getCentroid()));; 
			if(d1 <= d2) cluster1.addPoint(point);
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
