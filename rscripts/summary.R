library(tidyverse)
library(xtable)

source('./import_data.R')

bestCandidateFitnessAverage <- mean(allMethodsRoundsDF$`Best Candidate Fitness`)
bestCandidateStandardDeviation <- mean(allMethodsRoundsDF$`Best Candidate Standard Deviation`)
meanFitness <- mean(allMethodsRoundsDF$`Mean Fitness`)
terminationByGenerationCount <- nrow(allMethodsRoundsDF[allMethodsRoundsDF$Generation == "998",])

ellapsedTimeInSummaryDF <- allMethodsRoundsDF %>% 
  group_by(`Method Name`, Round) %>%
  filter(Generation == max(Generation)) %>%
  arrange(Round, Generation, `Method Name`, `Elapsed Time(Milli Second)`)

ellapsedTimeInSummaryInSeconds <- sum(ellapsedTimeInSummaryDF$`Elapsed Time(Milli Second)`) / 1000

attributes <- c('Best candidate fitness average', 'Best candidate standard deviation', 'Mean fitness', 'Terminated by generation count', 'Elapsed time(Seconds)')
results <- c(bestCandidateFitnessAverage, bestCandidateStandardDeviation, meanFitness, terminationByGenerationCount, ellapsedTimeInSummaryInSeconds)

summaryResultsDataFrame <- data.frame(attributes, results)

write.csv(summaryResultsDataFrame, '../results/summary.csv')


print(xtable(summaryResultsDataFrame, type = "latex", caption = 'Summary of the data', label = 'table:summary'), file = "../results/latex/summary.tex")
