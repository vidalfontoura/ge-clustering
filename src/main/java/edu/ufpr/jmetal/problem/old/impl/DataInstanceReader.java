package edu.ufpr.jmetal.problem.old.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithm.Point;

public class DataInstanceReader {

	// TODO: PLEASE REFACTOR THIS METHOD ASAP
	public static List<Point> readPoints(String fileName) throws FileNotFoundException, IOException {
		List<Point> points = new ArrayList<Point>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(DataInstanceReader.class.getResourceAsStream(fileName)))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				Point point = new Point();
				// TODO: This will need to change since the databases files are
				// not in the same format
				for (int i = 0; i < values.length; i++) {
					if (i != values.length - 1) {
						point.getCoordinates().add(Double.valueOf(values[i]));
					} else {
						// TODO: Do nothing this is the class for the
						// pima-indians database
					}
				}
				points.add(point);
			}
		}
		return points;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Point> readPoints = DataInstanceReader.readPoints("/prima-indians-diabetes.data");
		for (Point point : readPoints) {

			List<Double> coordinates = point.getCoordinates();
			System.out.println(coordinates.toString());
			System.out.println(point.getCluster());
		}
	}
}
