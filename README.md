#  File Processor and Logging System Design Sample

# Using Java Spring

This is a sample code to demonstrate my coding philosophy. I have incorporated entire functionality and code contains proper and thorough commenting to explain the functionality of program. I used dependency injection and factory design pattern ( **JPARepository** for persistence Database operations)

Salient feature of process:

- Maven Project with Spring (Annotations)
- Custom Exception Handling
- Project is scalable with minimum code changes
  - For any new format: Just create another implementation of **IProcess** Interface and update the Factory method
  - Using the most efficient reading mechanism using **Streams** (Concise) ~ check **getContentFromFile** method
  - Ensure **process recovery** by moving the processed file in different location
  - Proper logging. Table **uploadLog** captures the processing activities.
- Multi-threaded application with manual thread manager: Incorporated the thread pool. Based on processor and CPU consumption parallel thread can be managed using **Constants.NUM\_THREADS**.

**Project Structure:**
