package edu.ufpr.cluster.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

	
	private Point centroid;
	
	private List<Point> points;
	

	public Cluster(Point centroid) {
		this.centroid = centroid;
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public List<Point> getPoints() {
		if (points == null) {
			return new ArrayList<Point>();
		}
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	
	
}
