CollatzRewriteSystem

A simple program that takes either a single number or a list of numbers, as well as a text file denoting rewrite rules, and outputs a csv file with the number of steps it takes for the system to terminate. This system is optimized for specifically string rewriting rules that work with Professor Scott Aaronson's Collatz Conjecture String Rewrite System, meaning it will always try to find a d rule first, then move a ternary character to the c character and convert this into binary.

A detailed explanation of Professor Aaronson's String Rewrite System will be available once the thesis tied to it is published. You may also contact me via email if you want a detailed explanation.


COMPILING THE PROGRAM:

In this directory, type:

 javac -d COMPILED_DIRECTORY src/*.java

Where COMPILED_DIRECTORY is a directory you compile the output files. It's optional to run the -d option, but strongly recommended, because otherwise, the output files will compile in the src files.


RUNNING THE REWRITE SYSTEM:

From this directory, run:
  
  java -cp COMPILED_DIRECTORY Main -inputnumber NUMBER OR -inputfile FILE_NAME [-outputdirectory DIRECTORY] [-verbose] [-aggregatefile] [-rewritesystem FILE_NAME]  

Either -inputnumber or -inputfile option is required. All other parameters are optional, but most are recommended.

  -inputfile FILE_NAME: provides an input file that has numbers of input, one per line. Format for inputfile input: Uses Java's scanner, so any number token will be read as valid input, skipping anything else.
  
  -inputnumber NUMBER: plug in one number and generate the output of it.
  
  -outputdirectory DIRECTORY: generates output into the given directory. If nothing, default will be Output in the local directory. DIRECTORY MUST EXIST, otherwise the program will not run.
  
  -verbose: prints out lines which correspond to the rewrite rules and the numbers they represent as we generate the output files. 
  
  -aggregatefile: outputs an aggregate csv file which shows input number, output number, number of rewrite steps, and number of array doubles. (Number of array doubles will be removed in a future version.)
  
  -rewritesystem FILE_NAME: takes in a text file in FILE_NAME representing a different system. If not provided, will default to BasicRewriteRules.txt, which is the default system.
    Format for rewrite input:
      -"INPUT OUTPUT" on a line denotes rule is to find a string INPUT and convert it to string OUTPUT.
      -Any line beginning with a # is a comment, and will not be included.


VERSION HISTORY:

v1.0 (8/28/18): Made this repository public.


PLANNED FUTURE EDITS:

-Create output directory if one doesn't exist.
-Remove number of array doubles parameter, as this number has never exceeded 1 in all testing run on this program so far.
-Make verbose the default option for one input number, and allow the user to disable it.
-Option to print out only the aggregatefile if a batch of numbers is printed. Also remove the aggregatefile option.

