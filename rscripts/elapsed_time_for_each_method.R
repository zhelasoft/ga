library(tidyverse)

# Extrat the row before last row for each method 

dataFrameByMethod <- function(df_name, methodNameVar) {
  df <- (eval(sym(df_name)))
  df <- df[nrow(df), ]
  df$methodName <- methodNameVar
  return (df)
}


dataFrameForCalculatingElapsedTime <- data.frame()

for(i in 1:nrow(fileNameAndDataFrame)) {
  row <- fileNameAndDataFrame[i,]
  
  dataFrameName <- paste(row$fileDataFrameNames, '', sep = '')
  
  methodNameVar <- paste(row$methodNames, '', sep = '')
  
  dataFrameForCalculatingElapsedTime <- rbind(dataFrameForCalculatingElapsedTime, dataFrameByMethod(dataFrameName, methodNameVar))
}

columnNames <- c('Generation','Best Candidate Fitness','Best Candidate Standard Deviation','Mean Fitness','Elapsed Time(Milli Second)','Best Candidate', 'Method Name')

colnames(dataFrameForCalculatingElapsedTime) <- columnNames


elapsedTimeBYMethod <- aggregate(as.double(dataFrameForCalculatingElapsedTime$`Elapsed Time(Milli Second)`), by = list(dataFrameForCalculatingElapsedTime$`Method Name`), FUN = sum)

columnNames <- c('Method Name','Elapsed Time(Second)')

colnames(elapsedTimeBYMethod) <- columnNames

elapsedTimeBYMethod$`Elapsed Time(Second)` <- as.double(elapsedTimeBYMethod$`Elapsed Time(Second)`) / 1000

elapsedTimeBYMethod <- rbind(elapsedTimeBYMethod, c('Total', sum(elapsedTimeBYMethod$`Elapsed Time(Second)`)))

write.csv(elapsedTimeBYMethod, '../results/elapsed_time_by_method.csv')


