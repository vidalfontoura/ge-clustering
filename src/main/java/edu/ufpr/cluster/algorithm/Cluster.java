package edu.ufpr.cluster.algorithm;

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
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	
	
}
