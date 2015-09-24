package edu.ufpr.jmetal.problem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.random.ClusteringRandom;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.problem.old.impl.DataInstanceReader;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;
import edu.ufpr.math.utils.MathUtils;

public class ClusteringProblem extends AbstractGrammaticalEvolutionProblem {

    private static final long serialVersionUID = 1L;

    private int minCondons;
    private int maxCondons;

    private FitnessFunction fitnessFunction;

    private List<Point> points;
    private int clusteringExecutionSeed;

    private int evaluationCount = 0;

    private double bestIndividualFitnessPerGen = -1.1;

    private String bestIndividual = "";

    private int bestIndividualK = -1;

    private int populationSize;

    public ClusteringProblem(String grammarFile, String dataSetFile, int minCondons, int maxCondons,
        int clusteringExecutionSeed, FitnessFunction fitnessFunction, int populationSize)
            throws FileNotFoundException, IOException {

        super(new ClusteringExpressionGrammarMapper(), grammarFile);
        this.maxCondons = maxCondons;
        this.minCondons = minCondons;
        this.points = MathUtils.normalizeData(DataInstanceReader.readPoints(dataSetFile));
        this.fitnessFunction = fitnessFunction;
        this.clusteringExecutionSeed = clusteringExecutionSeed;
        this.populationSize = populationSize;

    }

    @Override
    public int getNumberOfVariables() {

        return 0;
    }

    @Override
    public int getNumberOfObjectives() {

        return 1;
    }

    @Override
    public int getNumberOfConstraints() {

        return 0;
    }

    @Override
    public String getName() {

        return "Clustering GE Integer Problem";
    }

    public void buildClusteringAlgorithm() {

    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {

        int numberOfVariables = solution.getNumberOfVariables();

        List<Integer> clusteringSolution = new ArrayList<>();
        StringBuilder solutionStr = new StringBuilder();
        for (int i = 0; i < numberOfVariables; i++) {

            solutionStr.append(solution.getVariableValue(i)).append(",");
            clusteringSolution.add(solution.getVariableValue(i));
        }
        solutionStr.deleteCharAt(solutionStr.length() - 1);

        ClusteringRandom.getNewInstance().setSeed(clusteringExecutionSeed);

        ClusteringAlgorithm clusteringAlgorithm = (ClusteringAlgorithm) mapper.interpret(clusteringSolution);
        // Clean up points before the execution
        points.stream().forEach(p -> {
            p.clearCluster();
        });
        clusteringAlgorithm.setPoints(points);
        ClusteringContext clusteringContext = clusteringAlgorithm.execute();

        Double fitness = this.fitnessFunction.apply(clusteringContext);

        if (fitness > bestIndividualFitnessPerGen) {
            // System.out.println("Swap from " + bestIndividualFitnessPerGen + "
            // to " + fitness);
            bestIndividualFitnessPerGen = fitness;
            bestIndividual = solutionStr.toString();
            bestIndividualK = clusteringContext.getClusters().size();
        }

        if (evaluationCount % populationSize == 0) {
            System.out.print("Generation: " + evaluationCount + "; ");
            System.out.print("Fitness: " + bestIndividualFitnessPerGen + "; k: " + bestIndividualK + "; ");
            System.out.println("Individual: " + bestIndividual);
            bestIndividual = "";
            bestIndividualFitnessPerGen = -1.1;
            bestIndividualK = -1;
        }
        solution.setObjective(0, fitness * -1);
        evaluationCount++;

    }

    @Override
    public VariableIntegerSolution createSolution() {

        return new VariableIntegerSolution(this, minCondons, maxCondons);
    }

}
