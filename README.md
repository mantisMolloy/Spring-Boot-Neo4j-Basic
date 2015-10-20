# Spring-Boot-Neo4j-Basic
Basic Spring Boot Neo4j access using an embedded Neo4j instance. Person entities are modeled and then 
relationships created between person objects.

###Sample Output
```
Lookup each person by name...
John's teammates include
	- George
	- Paul
	- Ringo

Paul's teammates include
	- John
	- George
	- Ringo

Ringo's teammates include
	- John
	- Paul
	- George

George's teammates include
	- Ringo
	- Paul
	- John

Looking up who works with Paul...
John's teammates include
	- Paul
	- Ringo
	- George

Ringo's teammates include
	- Paul
	- George
	- John

George's teammates include
	- Ringo
	- Paul
	- John
````

