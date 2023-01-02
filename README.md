<h1 align="center">:clock9: Writing & Reading Files • Threads-Related :clock3:</h1>
<h2 align="center"> An Object-Oriented-Programming Assignment </h2>
 
<h3> Definition: </h3>
 
_A thread in Java is the direction or path that is taken while a program is being executed. Generally, all the programs have at least one thread, known as the main thread, that is provided by the JVM or Java Virtual Machine at the starting of the program’s execution. At this point, when the main thread is provided, the main() method is invoked by the main thread._
> **_SOURCE: simplilearn_**

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

<h3> Assignment - Part A: </h3>

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
   this solution has performed the whole work by herself. Second, we solved the problem by creating a thread that was responsible to read a file that will passed
   for him, thus, this step has saved more time than the previous solution. Third, a thread pool was made and connected into our objective - which means, we managed
   to solve our problem by dedicating many threads such that each thread receives a certain file to read and provide us a result at the end, thus, for a high number
   of files, the multithreading idea would handle them files more efficiently._
   
* **U.M.L Diagram -**

   ![UML](https://user-images.githubusercontent.com/75171676/210280069-ea142d45-8d39-4679-b26b-e597cd2419ba.png)

  _Please note that, in Ex2_1.java:_
      
     **1. _createTextFiles(int, int, int) -_** _This function writes a certain amount of files such that each file has a random number of lines._
     
     **2. _getNumOfLines(String []) -_** _This function solves the problem without any threads._
    
     **3. _getNumOfLinesThreads(String []) -_** _This function solves the problem by using a thread._
     
     **4. _getNumOfLinesThreadsPool -_** _This function solves the problem by using a thread pool._
     
-------------------------------------------------------------------------------------------------------------------------------------------------------------------


