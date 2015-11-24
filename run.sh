grammarFile=/clustergrammar.bnf
database=/bbsort/tabela_resultado_arrumada_filtrada.data
evaluations=15000
resultFolder=result-5-bbsort

population=100
minCondons=1
maxCondons=20
threads=1

crossoverProbability=0.95
mutationProbability=0.1
pruneIndex=10
pruneProbability=0.05
duplicationProbability=0.05

datatype=Double
classIncluded=false
maximumClusteringEvaluations=500 
clusteringSeed=5
<<<<<<< HEAD
seed=5
maxAlgorithmDepth=20
=======
seed=10
>>>>>>> a3a279767ac2fee64b7b9ff893a6e0211c6af54f

java -jar target/ge-clustering-jar-with-dependencies.jar \
	-g $grammarFile 								  \
	-d $database									  \
	-m $evaluations 								  \
	-r $resultFolder 								\
	-s $seed 										\
	-p $population 									\
	-minC $minCondons 								\
	-maxC $maxCondons 								\
	-t $threads										\
	-cx $crossoverProbability 						\
	-mx $mutationProbability						\
	-pi $pruneIndex									\
	-px $pruneProbability 							\
	-dx $duplicationProbability 					\
	-cs $clusteringSeed						       \	
	-dt $datatype                                  \
	-ci $classIncluded							  \ 
	-mc $maximumClusteringEvaluations			\
	-md $maxAlgorithmDepth

   

