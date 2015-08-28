package edu.ufpr.cluster.algorithms.functions;

import java.util.List;
import java.util.function.Function;

import edu.ufpr.cluster.algorithm.Point;

public class ManhattanDistanceFunction implements Function<List<Point>,Double> {

	@Override
	public Double apply(List<Point> points) {
		
		//(p_1,p_2) and (q_1,q_2) is | p_1 - q_1 | + | p_2 - q_2 |.
		if(points.size() > 2) {
			throw new IllegalArgumentException("Isn't possible to calculate the distance for more than 2 points: "+points.size());
		}
		
		
		Point p = points.get(0);
		Point q = points.get(1);
		
		return Math.abs(p.getX() - q.getX()) + Math.abs(p.getY() - q.getY());
	}
	
	
	

}
