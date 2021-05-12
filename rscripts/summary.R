library(tidyverse)
library(xtable)

source('./import_data.R')

bestCandidateFitnessAverage <- mean(allMethodsRoundsDF$`Best Candidate Fitness`)
bestCandidateStandardDeviation <- mean(allMethodsRoundsDF$`Best Candidate Standard Deviation`)
meanFitness <- mean(allMethodsRoundsDF$`Mean Fitness`)
totalNumberOfGenerations <- nrow(allMethodsRoundsDF)

df <- filter(allMethodsRoundsDF, Generation == 999)
terminationByGenerationCount <- nrow(df)



ellapsedTimeInSummaryDF <- allMethodsRoundsDF %>% 
  group_by(`Method Name`, Round) %>%
  filter(Generation == max(Generation)) %>%
  arrange(Round, Generation, `Method Name`, `Elapsed Time(Milli Second)`)

ellapsedTimeInSummaryInSeconds <- sum(ellapsedTimeInSummaryDF$`Elapsed Time(Milli Second)`) / 1000

Attributes <- c('Best candidate fitness average', 'Best candidate standard deviation', 'Mean fitness', 'Total Number Of Generations' ,'Terminated by generation count', 'Elapsed time(Seconds)')
Results <- c(bestCandidateFitnessAverage, bestCandidateStandardDeviation, meanFitness, totalNumberOfGenerations, terminationByGenerationCount, ellapsedTimeInSummaryInSeconds)

summaryResultsDataFrame <- data.frame(Attributes, Results)

write.csv(summaryResultsDataFrame, '../results/summary.csv')

print(xtable(summaryResultsDataFrame, type = "latex", caption = 'Summary of the data', label = 'table:summary'), include.rownames=FALSE, file = "../results/latex/summary.tex")
