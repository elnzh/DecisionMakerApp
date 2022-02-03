

# A Decision-Maker App
 
Making decisions can be difficult. This project will be a decision-maker application which its goal is to help indecisive people determining the primary task to accomplish. It also works as a to-do list for people who want to increase their productivity.  
  
   
##### This project will have the following features:


- Users can input a list of tasks, cross things off, restore an item they crossed off or erase everything. 

- Users can get a random choice based on the list of tasks they input. 

I'm an indecisive person myself. I need such a decision-maker application to help me with planning my day but as well as
adding a little uncertainty. Having a bunch of things waiting to be done can be anxious to an indecisive people, so this app will be a minimalist app that only contains simple functions, and refreshing GUI designs to reduce the anxious feeling. 





### User Stories 
- As a user, I want to be able to add a task to my choices.
- As a user, I want to be able to delete a task from my choices
- As a user, I want to be able to delete the whole list of choices
- As a user, I want to be able to get a random result from all my choices
- As a user, I want to be able to save my list of choices to file
- As a user, I want to be able to be able to load my list of choices from file 



### Phase 4: Task 2
- Choice class in model package have robust design now. When constructing a Choice object with value null or setting the content of Choice object to null,
  the constructor or the setChoice method will throw NullChoiceException.
- ListOfChoices class in model package also have robust design now. Its removeChoice() method, setChoice() method and getString() method each takes an index as a parameter
  and use the index to locate the position of Choice object within the list. If index is less than zero or equal or greater than the size of list, unchecked exception
  IllegalArgumentException will be throw. Now, if the index is out of bounds, these methods will throw InvalidIndexException to prevent the program from crashing. 
 
 
### Phase 4: Task 3
- I will refactor my code to improve cohesion and dependencies. AppFrame class in ui package is the Frame of the app, but it has to keep track of the
  ListOfChoice object which is not its responsibility. All of FirstPanel, SecondPanel, ModifyChoicePanel and DecisionPanel classes take the actual appFrame object
  as an argument, which is a lot of dependencies. AppFrame object depends on many classes, which can be refactored to be more independent. Also, ListOfChoices object
  is passing into AppFrame, FirstPanel,  SecondPanel, ModifyChoicePanel and DecisionPanel classes, which is unnecessary. FirstPanel need ListOfChoices object to 
  read JSon file and store the choices, ModifyChoicePanel need ListOfChoices object to modify it, and DecisionPanel needs it to generate random decision, so decrease five dependencies
  to three will improve the code's coupling.
  