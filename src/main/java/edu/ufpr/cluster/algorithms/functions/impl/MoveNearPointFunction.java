package edu.ufpr.cluster.algorithms.functions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import edu.ufpr.cluster.algorithm.Cluster;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;

public class MoveNearPointFunction implements Function<ClusteringContext, Object>{

	@Override
	public Object apply(ClusteringContext context) {
		

		int r = new Random().nextInt(context.getPoints().size());

		//Seleciona um ponto aleatório
		Point p = context.getPoints().get(r);
		Cluster c = p.getCluster();
		
		Cluster minCluster = c;
		double minDistance = Double.MAX_VALUE;
		
		//Verifica o cluster com menor distância para o ponto
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
		
		//Remove o ponto do seu cluster anterior e adiciona ao novo
		if(!minCluster.getPoints().contains(p)) {
			c.getPoints().remove(p);
			minCluster.getPoints().add(p);
			p.setCluster(minCluster);
		}
		
//		Utils.updateCusterCentroid(c);
//		Utils.updateCusterCentroid(minCluster);
		
		return null;
	}
	
}
