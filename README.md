<h1 align="center">:clock9: Writing & Reading Files • Threads-Related :clock3:</h1>
<h2 align="center"> An Object-Oriented-Programming Assignment </h2>
 
<h3> Definition: </h3>
 
_Object-oriented programming (OOP) is a computer programming model that organizes software design around data, or objects, rather than functions and logic. An object can be defined as a data field that has unique attributes and behavior._
> **_SOURCE: TechTarget_**

<h3> Assignment - Part A: </h3>

 * **Objective -** _Writing a certain amount of text files. Then, counting the accumulated number of lines of the whole files. This objective was implemented in 3 different solutions:_
 
     - [x] _Once, while NOT using a thread._
     - [x] _Once, while using a thead._
     - [x] _Once, while using a thread pool._
     
    _Each solution has performed its own functionality right after the files were successfully written._
     
* **Runtime Analysis -** _A runtime analysis was successfully done by passing to each function 1,000 written files such that each file has a random number of lines that is up to 10,000. The files had their own allocated package that were written into - called "WrittenFiles". A temporary output was built in order to clarify each function's performance. The following figure shows:_

   ![RuntimeOutput](https://user-images.githubusercontent.com/75171676/210275919-b909c12d-c50a-4684-ac02-882f5003bb1e.PNG)
   
   _Each runtime output was calculated by using System.nanoTime() command as the following:_
    
    <pre><b><i>
    > long start_time_of_func = System.nanoTime();
    // Here we put some program or calling a function...
    long end_time_of_func = System.nanoTime() - start_time_of_func;
    System.out.println(end_time_of_func);
    </pre></b></i>
   
   _The whole solutions have counted the same amount of accumulated lines: 125,860. However, the speed of each solution varies:_
   
   | Solution                    | Speed           | # of files   | # of lines for each file   |
   |:---------------------------:|:---------------:|:------------:|:--------------------------:|
   | **NOT using a thread**      | _1009 ms_       | _1,000_      | _RANDOM - Up to 10,000_    |
   | **Using a thread**          | _318 ms_        | _1,000_      | _RANDOM - Up to 10,000_    |
   | **Using a thread pool**     | _154 ms_        | _1,000_      | _RANDOM - Up to 10,000_    |
   
   > **_NOTE: The speed is measured with milliseconds._**
   
   **_To conclude,  
   
