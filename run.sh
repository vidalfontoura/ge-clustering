grammarFile=/clustergrammar.bnf
database=/bbsort/tabela_resultado_arrumada_filtrada.data
evaluations=20000
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
classIncluded=true
maximumClusteringEvaluations=500 
clusteringSeed=5
seed=5
maxAlgorithmDepth=20


for (( i=1; i <= seed; i++ ))
do
  echo "Starting seed $i"
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
done

   

