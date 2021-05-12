library(dplyr)
library(tidyverse)
library(stringr) 

csvFileNames <- list.files(path = '../exports', pattern = "*.csv")
csvFileNames <- sort(csvFileNames)

beautifyFileName <- function(fileName) {
  fileName <- str_remove(fileName, 'ga_')
  fileName <- str_replace(fileName, '_round_', 'Round')
  fileName <- str_replace(fileName, 'Round10', 'RoundLast')
  
  
  indexOfUnderScore <- unlist(gregexpr(pattern ='_', fileName))[[1]]
  return (substr(fileName, 0, indexOfUnderScore - 1))
}

methodName <- function(beautifiedFileName) {
  indexOfRound <- unlist(gregexpr(pattern ='Round', beautifiedFileName))[[1]]
  return (substr(beautifiedFileName, 0, indexOfRound - 1))
}

roundNumber <- function(beautifiedFileName) {
  beautifiedFileName <- str_replace(beautifiedFileName, 'RoundLast', 'Round10')
  
  indexOfRound <- unlist(gregexpr(pattern ='Round', beautifiedFileName))[[1]]
  return (as.numeric(substr(beautifiedFileName, indexOfRound + 5, str_length(beautifiedFileName))))
}

fileNames <- c()
fileDataFrameNames <- c()
methodNames <- c()
rounds <- c()

for (fileName in csvFileNames) {
  fileNames <- c(fileNames, fileName)
  fileDataFrameNames <- c(fileDataFrameNames, beautifyFileName(fileName))
  methodNames <- c(methodNames, methodName(beautifyFileName(fileName)))
  rounds <- c(rounds, roundNumber(beautifyFileName(fileName)))
}

fileNameAndDataFrame <- data.frame(fileNames, fileDataFrameNames, methodNames, rounds)
fileNameAndDataFrame <-fileNameAndDataFrame[order(fileNameAndDataFrame$fileDataFrameNames),]

methodRoundColumnNames <- c('Round', 'Generation','Best Candidate Fitness','Best Candidate Standard Deviation','Mean Fitness','Elapsed Time(Milli Second)','Best Candidate', 'Method Name')

getRounds <- function(df, methodNameVar, roundVar) {
  df <- df[3:nrow(df), ]
  df <- cbind(roundVar, df)
  df$methodName <- methodNameVar
  # df$roundVar <- roundVar
  
  colnames(df) <- methodRoundColumnNames
  
  return(df) 
}

methodRoundMetaColumnNames <- c('Class', 'Method Name', 'Return Type', 'Parameter Types')
getMeta <- function(df) {
  df <- df[1, 1:4]
  colnames(df) <- methodRoundMetaColumnNames
  return(df) 
}

allMethodsMetadataDF <- data.frame()
allMethodsRoundsDF <- data.frame()

for(i in 1:nrow(fileNameAndDataFrame)) {
  row <- fileNameAndDataFrame[i,]
  
  filePath <- paste('../exports/', row$fileNames, sep = '')
  methodNameVar <- paste(row$methodNames, '', sep = '')
  roundVar <- paste(row$rounds, '', sep = '')
  
  dataFrameName <- paste(row$fileDataFrameNames)
  csvFile <- read.csv(filePath)
  
  methodRoundsDataFrame <- getRounds(csvFile, methodNameVar, roundVar)
  assign(dataFrameName, methodRoundsDataFrame)
  allMethodsRoundsDF <- rbind(allMethodsRoundsDF, methodRoundsDataFrame)
  
  methodMetaDataFrame <- getMeta(csvFile)
  assign(paste(dataFrameName, 'Meta', sep = ''), methodMetaDataFrame)
  allMethodsMetadataDF <- rbind(allMethodsMetadataDF, methodMetaDataFrame)
}

allMethodsMetadataDF <- unique(allMethodsMetadataDF)

write.csv(allMethodsMetadataDF, '../results/list_of_methods_metadata.csv')

# Convert the data
allMethodsRoundsDF$Round <- as.integer(allMethodsRoundsDF$Round)
allMethodsRoundsDF$Generation <- as.integer(allMethodsRoundsDF$Generation)
allMethodsRoundsDF$`Best Candidate Fitness` <- as.double(as.character(allMethodsRoundsDF$`Best Candidate Fitness`))
allMethodsRoundsDF$`Best Candidate Standard Deviation` <- as.double(as.character(allMethodsRoundsDF$`Best Candidate Standard Deviation`))
allMethodsRoundsDF$`Mean Fitness` <- as.double(as.character(allMethodsRoundsDF$`Mean Fitness`))
allMethodsRoundsDF$`Elapsed Time(Milli Second)` <- as.double(as.character(allMethodsRoundsDF$`Elapsed Time(Milli Second)`))

write.csv(allMethodsRoundsDF, '../results/list_of_methods_rounds.csv')




