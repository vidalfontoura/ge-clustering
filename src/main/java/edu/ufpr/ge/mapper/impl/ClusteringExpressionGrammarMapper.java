package edu.ufpr.ge.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import edu.ufpr.cluster.algorithm.ClusteringAlgorithm;
import edu.ufpr.cluster.algorithms.functions.DistanceFunction;
import edu.ufpr.cluster.algorithms.functions.InitilizationFunction;
import edu.ufpr.cluster.algorithms.functions.impl.ChebyshevDistanceFunction;
import edu.ufpr.cluster.algorithms.functions.impl.EucledianDistanceFunction;
import edu.ufpr.cluster.algorithms.functions.impl.ManhattanDistanceFunction;
import edu.ufpr.cluster.algorithms.functions.impl.RandomCentroidInitilization;
import edu.ufpr.cluster.algorithms.functions.impl.UniformCentroidInitilization;
import edu.ufpr.ge.mapper.AbstractGrammarMapper;
import edu.ufpr.ge.representation.Expression;
import edu.ufpr.ge.representation.Node;

public class ClusteringExpressionGrammarMapper extends AbstractGrammarMapper<ClusteringAlgorithm> {

    protected int currentIndex;
    protected int numberOfWraps;
    protected int currentDepth;
    protected List<Node> visitedNodes;

    protected int maxDepth;
    
    public ClusteringExpressionGrammarMapper(String grammarFile) {
        loadGrammar(grammarFile);
        maxDepth = 100;
    }

    public ClusteringExpressionGrammarMapper() {
        //TODO: check where this should be configured
        maxDepth = 100;
    }

    public ClusteringExpressionGrammarMapper(Node rootNode) {
        super(rootNode);
    }

    public ClusteringExpressionGrammarMapper(int maxNumberOfSelfLoops) {
        this.maxDepth = maxNumberOfSelfLoops;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getNumberOfWraps() {
        return numberOfWraps;
    }

    @Override
    public ClusteringAlgorithm interpret(List<Integer> grammarInstance) {
        currentIndex = 0;
        numberOfWraps = 0;
        currentDepth = 1;
        visitedNodes = new ArrayList<>();
        String[] result = getNodeValue(rootNode, grammarInstance).split(" ");
        
        String initializationValue = result[0];
        InitilizationFunction initializationFunction = null;
        switch (initializationValue) {
		 case "uniform":
			initializationFunction = new UniformCentroidInitilization();
			break;
		 case "random":
			 initializationFunction = new RandomCentroidInitilization();
			 break;
		 default:
			throw new RuntimeException("Initialization "+initializationValue+ " not supported");
        }	
        
        int k = Integer.valueOf(result[1]);
        
        String distance = result[2];
        
        DistanceFunction distanceFunction = null;
        switch (distance) {
        case "manhathan":
			distanceFunction = new ManhattanDistanceFunction();
			break;
        case "eucledian":
       	 distanceFunction = new EucledianDistanceFunction();
       	 break;
        case "chebyshev":
       	 distanceFunction = new ChebyshevDistanceFunction();
       	 break;
        default:
			throw new RuntimeException("Distance function "+distanceFunction+ " not supported");
        }
        //TODO: create the functions here
        for(int i=3; i<result.length; i++) {
       	 
        }
        ClusteringAlgorithm clusteringAlgorithm = new ClusteringAlgorithm(initializationFunction, null, distanceFunction, k);

        return clusteringAlgorithm;
        
    }

    private String getNodeValue(Node node, List<Integer> grammarInstance) {
    	 if (node.isTerminal()) {
             return node.getName();
         } else {
             visitedNodes.add(node);
             
             int expressionsSize = node.getExpressions().size();
             if (expressionsSize > 1) {
                 currentIndex++;
                 if (currentIndex >= grammarInstance.size()) {
                     currentIndex = 0;
                     numberOfWraps++;
                 }
             }

             int indexToGet = grammarInstance.get(currentIndex) % expressionsSize;
             Expression expression = node.getExpressions().get(indexToGet);
             for (Node childNode : expression.getNodes()) {
                 if (visitedNodes.contains(childNode)) {
                     currentDepth++;
                     break;
                 }
             }
             while (currentDepth > maxDepth) {
                 currentDepth--;
                 grammarInstance.set(currentIndex, grammarInstance.get(currentIndex) + 1);
                 indexToGet = grammarInstance.get(currentIndex) % expressionsSize;
                 expression = node.getExpressions().get(indexToGet);
                 for (Node childNode : expression.getNodes()) {
                     if (visitedNodes.contains(childNode)) {
                         currentDepth++;
                         break;
                     }
                 }
             }

             String result = expression
                     .getNodes()
                     .stream()
                     .map(childNode -> getNodeValue(childNode, grammarInstance))
                     .collect(Collectors.joining(" "));

             visitedNodes.remove(node);

             return result;
         }
        
    }
    
    public static void main(String[] args) {
        ClusteringExpressionGrammarMapper mapper = new ClusteringExpressionGrammarMapper();
        mapper.loadGrammar("src/main/resources/clustergrammar.bnf");

        List<Integer> integerList = new ArrayList<>();
        
        
        for(int j=0; j<30; j++) {
        	JMetalRandom random = JMetalRandom.getInstance();
        	integerList = new ArrayList<>();
        	int maxBound = random.nextInt(20, 300);
            for (int i = 0; i < maxBound; i++) {
                integerList.add(random.nextInt(0, 1000));
            }
//            System.out.println(integerList.toString());
            System.out.println(mapper.interpret(integerList));

        }
    }


}
