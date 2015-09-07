package edu.ufpr.jmetal.problem.old.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithm.Point;

public class DataInstanceReader {

	public static List<ClassifiedPoint> readPoints(String fileName) throws FileNotFoundException, IOException {
		List<ClassifiedPoint> points = new ArrayList<ClassifiedPoint>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				ClassifiedPoint classifiedPoint = new ClassifiedPoint();
				Point point = new Point();
				// TODO: This will need to change since the databases files are
				// not in the same format
				for (int i = 0; i < values.length; i++) {
					if (i == values.length - 1) {
						classifiedPoint.setClassification(values[i]);
					} else {
						point.setCluster(null);
						point.getCoordinates().add(Double.valueOf(values[i]));
					}
				}
				classifiedPoint.setPoint(point);
				points.add(classifiedPoint);
			}
		}
		return points;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<ClassifiedPoint> readPoints = DataInstanceReader
				.readPoints("src/main/resources/prima-indians-diabetes.data");
		for (ClassifiedPoint classifiedPoint : readPoints) {
			Point point = classifiedPoint.getPoint();

			List<Double> coordinates = point.getCoordinates();
			System.out.println(coordinates.toString());
			System.out.println(point.getCluster());
		}
	}
}
