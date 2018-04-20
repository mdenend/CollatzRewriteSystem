# CollatzRewriteSystem
A simple program that takes either a single number or a list of numbers, as well as a text file denoting rewrite rules, and outputs a csv file with the number of steps it takes for the system to terminate. This system is optimized for specifically string rewriting rules that work with Prof. Scott Aaronson's system, meaning it will always try to find a d rule first, then move a ternary character to the c character and convert this into binary.

How to run: From the CollatzRewriteSystem directory, run:
  
  java -cp out/production/CollatzRewriteSystem/ Main -inputnumber NUMBER OR -inputfile FILE_NAME [-outputdirectory DIRECTORY] [-verbose] [-aggregatefile] [-rewritesystem FILE_NAME]  

Either -inputnumber or -inputfile option is required. All other parameters are optional, but most are recommended.

  -inputfile FILE_NAME: provides an input file that has numbers of input.
    Format for inputfile input: Uses Java's scanner, so any number token will be read as valid input, skipping anything else.
  
  -inputnumber NUMBER: plug in one number and generate the output of it.
  
  -outputdirectory DIRECTORY: generates output into the given directory. If nothing, default will be Output in the local directory. If directory doesn't exist, gives an error.
  
  -verbose: prints out lines which correspond to the rewrite rules and the numbers they represent as we generate the output files.
  
  -aggregatefile: outputs an aggregate csv file which shows input number, output number, number of rewrite steps, and number of array doubles. (Number of array doubles will be removed in a future version.)
  
  -rewritesystem FILE_NAME: takes in a text file in FILE_NAME representing a different system. If not provided, will default to BasicRewriteRules.txt, which is the default system.
    Format for rewrite input:
      -"INPUT OUTPUT" on a line denotes rule is to find a string INPUT and convert it to string OUTPUT.
      -Any line beginning with a # is a comment, and will not be included.
