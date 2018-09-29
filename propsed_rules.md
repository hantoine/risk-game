# Coding conventions

- Let's try not to commit code without tests
- All committed code should be properly formated. The function Source/Format should be used on every modified file before commiting.
	- Indentation: the curly bracket at the beginning of a block (function, if, while, etc) should be on the same line as the previous instruction. (arbitrary choice)
	  	
		Example:
		```java
		void function() {
			//function content
		}
		```
		Not:
		```java
		void function() 
		{
			//function content
		}
		```

- After refactoring, lets try to  always do peer programming
- Code in master should always be running. Always work on a dedicated branch. (The branch can stay local if only one laptop is working on it)

- naming conventions:
	- class name:		should start with uppercase letter and be a noun e.g. String, Color, Button, System, Thread etc.
	- interface name:	should start with uppercase letter and be an adjective e.g. Runnable, Remote, ActionListener etc.
	- method name:		should start with lowercase letter and be a verb e.g. actionPerformed(), main(), print(), println() etc.
	- variable name:	should start with lowercase letter e.g. firstName, orderNumber etc.
	- package name:		should be in lowercase letter e.g. java, lang, sql, util etc.
	- constants name:	should be in uppercase letter. e.g. RED, YELLOW, MAX_PRIORITY etc.


- A method should not be longer than 30 lines.
- we should commit as frequently as possible. It is considered possible to commit when an atomic modification of the source code as been completed.
- Let's try not to commit code without comments
	- Each methods should be commented using JavaDoc format with a brief description of the function, a description of each of its parameters and a description of what it returns 
	- more comments can be added inside the function if necessary when the function can be hard to understand 
- a variable should be declared in the deepest block using it 