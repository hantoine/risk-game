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
	- Try not too have too many nested conditions:
	  What we should not do:
		```java
		void function(DataObject dataObject) {
			if(check1(dataObject)) {
				if(check2(dataObject)) {
					if(check3(dataObject)) {
						doThings(dataObject);
					} else {
						System.out.println("Check 3 failed");
					}
				} else {
					System.out.println("Check 2 failed");
				}
			} else {
				System.out.println("Check 1 failed");
			}
		}
		```
	  What we should do:
		```java
		void function(DataObject dataObject) {
			if(!check1(dataObject)) {
				System.out.println("Check 1 failed");
				return;
			}	
			if(!check2(dataObject)) {
				System.out.println("Check 2 failed");
				return;
			}
			if(check3(dataObject)) {
				System.out.println("Check 3 failed");
				return;
			}
			
			doThings(dataObject);
		}
		```
- After refactoring, lets try to  always do peer programming
- Trye to ensure the source code in master should always be running. Always work on a dedicated branch.

- Naming conventions:
	- class name:		should start with uppercase letter and be a noun e.g. String, Color, Button, System, Thread etc.
	- interface name:	should start with uppercase letter and be an adjective e.g. Runnable, Remote, ActionListener etc.
	- method name:		should start with lowercase letter and be a verb e.g. actionPerformed(), main(), print(), println() etc.
	- variable name:	should start with lowercase letter e.g. firstName, orderNumber etc.
	- package name:		should be in lowercase letter e.g. java, lang, sql, util etc.
	- constants name:	should be in uppercase letter. e.g. RED, YELLOW, MAX_PRIORITY etc.


- A method should not be longer than 50 lines.
- We should commit as frequently as possible. It is considered possible to commit when an atomic modification of the source code as been completed.
- Let's try not to commit code without comments
	- Each methods should be commented using JavaDoc format with a brief description of the function, a description of each of its parameters and a description of what it returns 
	- more comments can be added inside the function if necessary when the function can be hard to understand 
- A variable should be declared in the deepest block using it 
- Variable names should be meaningful, we should not use generic variable names like tmp or aux.
