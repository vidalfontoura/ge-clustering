package edu.ufpr.cluster.algorithms.functions;

import java.util.List;
import java.util.function.Function;

import edu.ufpr.cluster.algorithm.Point;

public class EucledianDistanceFunction implements Function<List<Point>,Double> {

	@Override
	public Double apply(List<Point> points) {
		
		//\mathrm{d}(\mathbf{p},\mathbf{q})=\sqrt{(q_1-p_1)^2 + (q_2-p_2)^2}.
		
		if(points.size() > 2) {
			throw new IllegalArgumentException("Isn't possible to calculate the distance for more than 2 points: "+points.size());
		}
		
		Point p = points.get(0);
		Point q = points.get(1);
		
		return Math.sqrt(Math.pow(q.getX() - p.getX(),2) + Math.pow(q.getY() - p.getY(),2));
		
	}
	
	
	

}
