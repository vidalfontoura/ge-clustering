package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.function.Function;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import com.google.common.collect.Lists;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;

public class JoinClustersFunction implements Function<ClusteringContext, Void>{

	@Override
	public Void apply(ClusteringContext context) {

		int r = JMetalRandom.getInstance().nextInt(0, context.getClusters().size()-1);
		
		Cluster cluster1 = context.getClusters().get(r);
		Cluster cluster2 = context.getClusters().get(0);
		
		double minDist = Double.MAX_VALUE;
		for (Cluster c : context.getClusters()) {			
			if(c != cluster1) {
				double dist = context.getDistanceFunction().apply(Lists.newArrayList(cluster1.getCentroid(), c.getCentroid()));
				if(dist < minDist) {
					minDist = dist;
					cluster2 = c;
				}
			}
		}
		
		cluster1.getPoints().addAll(cluster2.getPoints());
		cluster1.updateCentroid();
		context.getClusters().remove(cluster2);
		
		return null;
	}

}
