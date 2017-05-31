# ComputerArchitecture_ScoreBoard
Building  the scoreboard to schedule the instructions.
It uses Instruction Cache and Data Cache to improve the performcance.
Instruction Cache uses direct mapping and Data Cache uses 2 way set associative.
This program takes four arguments as input.
  arg1  -  instructions_file.txt
  arg2  -  data_file.txt
  arg3  -  configuration_file.txt
  arg4  -  result.txt
  
SAMPLE result.txt
Instruction Fetch Issue Read Exec Write RAW WAW Struct
 LI R4, 260  13    14    15    16   17   N   N   N
 LI R5, 272  14    18    19    20   21   N   N   Y

You run the project directly in Eclipse IDE or through the commandline.
