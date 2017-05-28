# JPICL
**Java/Python Interpreted Compiled Langued**, An Interpreted/"Compiled" language that was supposed to be like python but turned out to look something a bit similar to ruby
# Tutorial
## Syntax
methods do not have parentheses around the parameters
so far there is only one builtin method, the `print` function which takes an unlimited number of parameters
```
print "hello"
print "I can use multiple" "parameters"
print "That's more than" 1
print "I can do addition 6*7 = " 6 * 7
```
### Variables
Variables are defined the same way as they are in python and ruby
`variable = value`
```
variable = 1
print variable
variable = variable + 1
print variable
```
#### Dynamic typing
Variables can have their type reasigned automagically
```
a = 1
print a
a = "string"
print a
```
### Usage
```java -jar JPICL.jar [--java] code.txt```
the --java parameter outputs the Java equivalent of your code to STODUT
without this, the interpreter will attempt to execute your code
