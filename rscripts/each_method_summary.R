library(tidyverse)
library(xtable)
library(dplyr)

source('./import_data.R')


eachMethodSummary <- function(methodName, df) {
  bestCandidateFitnessAverage <- mean(df$`Best Candidate Fitness`)
  bestCandidateStandardDeviation <- mean(df$`Best Candidate Standard Deviation`)
  meanFitness <- mean(df$`Mean Fitness`)
  
  terminationByGenerationCount <- nrow(filter(df, Generation == 999))
  
  totalGenerations <- nrow(df)
  
  dfMinAndMax <- filter(df, `Mean Fitness` == 10)
  
  generationDfMin <- dfMinAndMax %>% 
    group_by(Round) %>%
    filter(Generation == min(Generation))
  
  minGeneration <- -1
  if(nrow(generationDfMin) > 0) {
    minGeneration <- min(generationDfMin$Generation)  
  } 
  
  generationDfMax <- dfMinAndMax %>% 
    group_by(Round) %>%
    filter(Generation == max(Generation))
  
  maxGeneration <- -1
  if(nrow(generationDfMax) > 0) {
    maxGeneration <- max(generationDfMax$Generation)  
  } 
  
  
  ellapsedTimeInSummaryDF <- df %>% 
    group_by(`Method Name`, Round) %>%
    filter(Generation == max(Generation)) %>%
    arrange(Round, Generation, `Method Name`, `Elapsed Time(Milli Second)`)
  
  ellapsedTimeInSummaryInSeconds <- sum(ellapsedTimeInSummaryDF$`Elapsed Time(Milli Second)`) / 1000
  
  Results <- data.frame(c(methodName), c(bestCandidateFitnessAverage), c(bestCandidateStandardDeviation), c(meanFitness), c(terminationByGenerationCount), c(ellapsedTimeInSummaryInSeconds), c(totalGenerations), c(minGeneration), c(maxGeneration))
  
  return (Results)
}


methodNames <- unique(allMethodsRoundsDF$`Method Name`)

summaryResultsEachMethodDataFrame <- data.frame()

for (methodName in methodNames) {
  
  df <- filter(allMethodsRoundsDF, `Method Name` == methodName)
  
  summaryResultsEachMethodDataFrame <- rbind(summaryResultsEachMethodDataFrame, eachMethodSummary(methodName, df))
}

colnames(summaryResultsEachMethodDataFrame) <- c('Method Name', 'Best candidate fitness average', 'Best candidate standard deviation', 'Mean fitness', 'Terminated by generation count', 'Elapsed time(Seconds)', 'Total Generations', 'Min Generation', 'Max Generation')

write.csv(summaryResultsEachMethodDataFrame , '../results/each_method_summary.csv')


summaryResultsEachMethodDataFrameAttributes <- c(2:ncol(summaryResultsEachMethodDataFrame))

for (i in summaryResultsEachMethodDataFrameAttributes) {
  df <- summaryResultsEachMethodDataFrame[, c(1, i)]
  print(xtable(df , type = "latex", caption = 'Summary of each method in data', label = paste('table:each_method_summary', i)), include.rownames=FALSE, file = paste("../results/latex/each_method_summary_", i, '.tex' sep = ''))
}
