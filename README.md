<h1 align="center">:clock9: Multithreading • Java :clock3:</h1>
 
<h2 align="center"> An Object-Oriented-Programming Assignment </h2>
 
<h3> Introduction: </h3>
 
* _The **Object-Oriented Programming** (OOP) term is a computer programming model that organizes software design around data, or objects, rather than functions and logic. An object can be defined as a data field that has unique attributes and behavior._

   > **_SOURCE: TechTarget_**

* _The **Multithreading** term means that you have multiple threads of execution inside the same application. A **Thread** is like a separate CPU executing your application. Thus, a multithreaded application is like an application that has multiple CPUs executing different parts of the code at the same time._

   > **_SOURCE: Jenkov.com_**

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

<h3> Chapter 1 - Writting & Reading Files: </h3>

* **Objective -** _Writing a certain amount of text files. Then, counting the accumulated number of lines of the whole files. This objective was implemented in 3 different solutions:_
 
     :heavy_check_mark: _Once, while NOT using a thread._
     
     :heavy_check_mark: _Once, while using a thead._
     
     :heavy_check_mark: _Once, while using a thread pool._
     
    _Each solution has performed its own functionality right after the files were successfully written._
     
* **Runtime Analysis -** _A runtime analysis was successfully done by passing to each function 1,000 written files such that each file has a random number of lines that is up to 10,000. The files had their own allocated package that were written into - called "WrittenFiles". A temporary output was built in order to clarify each function's performance. The following figure shows:_

   ![RuntimeOutput](https://user-images.githubusercontent.com/75171676/210275919-b909c12d-c50a-4684-ac02-882f5003bb1e.PNG)
   
   _Each runtime output was calculated by using System.nanoTime() command as the following:_
    
    <pre><i>
    <b>1.</b> long start_time_of_func = System.nanoTime();
    <b>2.</b> // Here we put some program or calling a function...
    <b>3.</b> long end_time_of_func = System.nanoTime() - start_time_of_func;
    <b>4.</b> System.out.println(end_time_of_func);
    </pre></i>
   
   _The whole solutions have counted the same amount of accumulated lines: 125,860. However, the speed of each solution varies:_
   
   | Solution                    | Speed           | # of files   | # of lines for each file   |
   |:---------------------------:|:---------------:|:------------:|:--------------------------:|
   | **NOT using a thread**      | _1009 ms_       | _1,000_      | _RANDOM - Up to 10,000_    |
   | **Using a thread**          | _318 ms_        | _1,000_      | _RANDOM - Up to 10,000_    |
   | **Using a thread pool**     | _154 ms_        | _1,000_      | _RANDOM - Up to 10,000_    |
   
   > **_NOTE: The speed is measured with milliseconds._**
   
   _To conclude, threads are basically providing us efficiency. Because, we tend to divide some parts of a program and hand it to some thread to do it for us.
   Therefore, we save time and that program ends faster. First, we accomplished the objective without using any threads, thus, the function that was specified for
   this solution has performed the whole work by itself. Second, we solved the problem by creating a thread that was responsible to read a file that will be passed
   for him, thus, this step has saved more time than the previous solution. Third, a thread pool was made and connected into our objective - which means, we managed
   to solve our problem by dedicating many threads such that each thread receives a certain file to read and provide us a result at the end, thus, for a high number
   of files, the multithreading idea would handle them files more efficiently._
   
   _Feel free to have a glance about the written files content  [HERE](https://github.com/osamaghaliah/OOP_Ex2/tree/master/src/Part_A/WrittenFiles)_.
   
* **U.M.L Diagram -**

   ![UML](https://user-images.githubusercontent.com/75171676/210280069-ea142d45-8d39-4679-b26b-e597cd2419ba.png)

  _Please note that, in Ex2_1.java:_
      
     **1. _createTextFiles(int, int, int) -_** _This function writes a certain amount of files such that each file has a random number of lines._
     
     **2. _getNumOfLines(String []) -_** _This function solves the problem without any threads._
    
     **3. _getNumOfLinesThreads(String []) -_** _This function solves the problem by using a thread._
     
     **4. _getNumOfLinesThreadPool(String []) -_** _This function solves the problem by using a thread pool._
     
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

<h3> Chapter 2 - Concurrency Customization: </h3>

* **Objective -** _Unfortunately, in Java, thread pools don't manage tasks according by their priorities. However, it supports priorities for the threads who are responsible to handle the submitted tasks. Thus, this chapter has came to implement the following:._

   :heavy_check_mark: Implementing a generic class that represents an asynchronous operation.
   
   :heavy_check_mark: Implementing a custom thread pool that supports the custom tasks to be handled according to their priorities.
   
   _Each one of the above implementations were developed by implementing Java's Callable <T> interface & extending ThreadPoolExecutor class._
   
* **Design Patterns Used -** _In order to achieve the objective mentioned above, we have implemented various design patterns that contribute in wrapping our solution as a whole. Design patterns are building blocks of a system's architecture which are recurring solution to design problems we face. They tend to identify and specify abstractions that are above the level of single classes and instances. Design Patterns are classified into 3 categories: Creational, Behavioral & Structural. Now, let's introduce the design patterns we used that all together present us our final solution:_
   
   - _**Thread Pool:** The Thread Pool pattern is a design pattern, used in software engineering to organise the processing of a large number of queued tasks through a smaller/limited number of threads. Results can also be queued. When a thread finishes it's task it requests another. If none are available, the thread can wait or terminate. Such pattern was used in our solution to provide a customized thread pool that progresses tasks according to their priorities and not according to its threads._
   
   - _**Active Object:** The Active Object pattern decouples method execution from method invocation for objects that each reside in their own thread of control. The goal is to introduce concurrency, by using asynchronous method invocation and a scheduler for handling requests. This pattern stems of the Thread Pool pattern and it was used in our solution to provide multiple tasks to be executed in our custom thread pool._
   
   - _**Adapter:** The Adapter pattern lets classes work together that couldn't due to incompatible interfaces. For instance, converting an interface of a class into another interface clients. Such pattern was used in our solution to make our thread pool to accept our customized tasks instead of runnables. Thus, Adapter pattern belongs to the Structural category._
   
   - _**Factory Method:** The Factory Method pattern defines an interface for creating an object of particular abstract type. However, it lets subclasses decide which concrete class to instantiate. Generally, it is used by many modern frameworks and APIs. Such pattern was used in our solution in order to create customized tasks. Thus, Factory Method pattern belongs to the Creational category._
   
   - _**Chain Of Responsibility:** The Chain Of Responsibility pattern is used to achieve loose coupling in software design where a request from client is passed to a chain of objects to process them. Then the object in the chain will decide themselves who will be processing the request and whether the request is required to be sent to the next object in the chain or not. Such pattern was used in our solution to provide execptions-handling in a lot of functions. Thus, Chain Of Responsibility patterns belongs to the Behavioral category._
   
  _To conclude, let's illustrate how the above 5 mentioned patterns ACTUALLY related into our solution:_
   
   | **Design Pattern**          | **Category**    | **How was it used**                                                                       |
   |:---------------------------:|:---------------:|:----------------------------------------------------------------------------------------  |
   | **Thread Pool**             | _Concurrency_   | _Providing a customized thread pool that progresses tasks according to their priorities._ |
   | **Active Object**           | _Concurrency_   | _Providing multiple tasks to be executed in our custom thread pool._                      |
   | **Adapter**                 | _Structural_    | _Making our thread pool accept our customized callable tasks instead of runnables._       |
   | **Factory Method**          | _Creational_    | _Creating a static method that creates our asynchronous tasks._                           |
   | **Chain Of Responsibility** | _Behavioral_    | _Providing execptions-handling._                                                          |
   

* **U.M.L Diagram -**

   ![UML](https://user-images.githubusercontent.com/75171676/211866856-e2ffe569-94c7-417d-b39e-83b2f3a4a73a.png)
   
   _**Clarification:**_
   
    - _**Task** is a class that implements Java's Callable <T> interface. It represents our custom asynchronous task._
    - _**TaskType** is an enum that represents the type of an asynchronous task and their priorities._
    - _**CustomExecutor** is a class that extends from Java's ThreadPoolExecutor class. It represents a custom thread pool that progresses the submitted asynchronous tasks according to their priorities._
    - _**ComparablePriorityAdapter** is a class that extends from Java's FutureTask <T> & implements Java's Comparable interface._
    - _**Tests** is a JUNIT5 class that tests the correctness of our complete solution._
     
       ![Capture](https://user-images.githubusercontent.com/75171676/211877062-2ad72086-6ec6-4275-880a-df65e98b68ec.PNG)
 
    - _**Other** classes were clarrified in Chapter 1._
