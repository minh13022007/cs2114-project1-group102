PRIOTASK



Priotask is a lightweight, high-performance Java-based terminal student planner designed for distraction-free task management, deadline sorting, and efficient priority tracking. Built without a graphical user interface, it provides a fast, keyboard-driven command-line experience tailored for students and power users who want to stay organized without the overhead of heavy software.



================================================================================

FEATURES



Pure Terminal Interface: Operate entirely via the command line with zero GUI/UX bloat for instant feedback and lightning-fast workflows.



Smart Task Prioritization: Assign and sort tasks dynamically based on custom priority levels (High, Medium, Low) and importance weights.



Deadline-Driven Sorting: Automatically organize tasks chronologically by due dates to keep upcoming deliverables front and center.



Core CRUD Operations: Easily add, view, update, and remove tasks from your active planner session.



Robust Object-Oriented Design: Built using clean Java practices, custom data structures, and modular class architectures.



================================================================================

PROJECT STRUCTURE



priotask/

|-- src/

|   |-- main/

|   |   -- java/ |   |       -- com/

|   |           -- priotask/ |   |               |-- Main.java |   |               |-- Task.java |   |               |-- TaskManager.java |   |               -- CommandParser.java

|   -- test/ |       -- java/

|           -- com/ |               -- priotask/

|                   -- TaskTest.java |-- .gitignore -- README.txt



================================================================================

GETTING STARTED



Prerequisites:



Java Development Kit (JDK 17 or higher) installed on your machine.



A terminal or command prompt (Bash, Zsh, PowerShell, or Command Prompt).



Installation:



Clone the repository:

git clone https://github.com/minh13022007/cs2114-project1-group102.git

cd cs2114-project1-group102



Compile the source files:

javac -d bin src/main/java/com/priotask/\*.java



Run the application:

java -cp bin com.priotask.Main



================================================================================

USAGE GUIDE



Once launched within your terminal, Priotask presents an interactive command prompt. Available commands include:



add    - Add a new task with a title, priority, and deadline

Example: add "CS2114 Project 1" --priority high --due 2026-10-15



list   - Display all current tasks sorted by default priority/deadline

Example: list



sort   - Sort tasks explicitly by deadline or priority level

Example: sort --by deadline



done   - Mark a specific task index as completed

Example: done 2



delete - Remove a task from the planner

Example: delete 1



help   - View the full list of available terminal commands

Example: help



exit   - Save state and quit the application

Example: exit

