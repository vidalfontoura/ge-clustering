package edu.ufpr.jmetal.problem;

import java.util.ArrayList;
import java.util.List;

import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithm.ClusteringContext;
import edu.ufpr.cluster.algorithm.Point;
import edu.ufpr.cluster.random.ClusteringRandom;
import edu.ufpr.ge.mapper.impl.ClusteringExpressionGrammarMapper;
import edu.ufpr.jmetal.solution.impl.VariableIntegerSolution;

public class ClusteringProblem extends AbstractGrammaticalEvolutionProblem {

    private static final long serialVersionUID = 1L;

    private int minCondons;
    private int maxCondons;

    private FitnessFunction fitnessFunction;

    private List<Point> points;
    private int clusteringExecutionSeed;

    private int evaluationCount = 0;

    private int generationsCount = 0;

    private double bestFitnessPerGen = -1.1;

    private double bestFitness = -1.1;

    private String bestIndividual = "";

    private int bestPerGenK = -1;

    private int bestK = -1;

    private int populationSize;

    public ClusteringProblem(String grammarFile, List<Point> points, int minCondons, int maxCondons,
        int clusteringExecutionSeed, FitnessFunction fitnessFunction, int populationSize) {

        super(new ClusteringExpressionGrammarMapper(), grammarFile);
        this.maxCondons = maxCondons;
        this.minCondons = minCondons;
        this.points = points;
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

        if (fitness > bestFitnessPerGen) {
            // System.out.println("Swap from " + bestIndividualFitnessPerGen + "
            // to " + fitness);
            bestFitnessPerGen = fitness;
            bestIndividual = solutionStr.toString();
            bestPerGenK = clusteringContext.getClusters().size();
        }

        if (fitness > bestFitness) {
            bestFitness = fitness;
            bestK = clusteringContext.getClusters().size();
        }

        if (evaluationCount % populationSize == 0) {
            System.out.print("Generation: " + generationsCount + "; ");
            System.out.println(bestFitnessPerGen + "; k: " + bestPerGenK + "; ");
            // System.out.println("Individual: " + bestIndividual);
            System.out.println("Best fitness found so far: " + bestFitness + "; k: " + bestK);

            bestIndividual = "";
            bestFitnessPerGen = -1.1;
            bestPerGenK = -1;
            generationsCount++;
        }
        solution.setObjective(0, fitness * -1);
        evaluationCount++;

    }

    @Override
    public VariableIntegerSolution createSolution() {

        return new VariableIntegerSolution(this, minCondons, maxCondons);
    }

}
