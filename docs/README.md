# Bossa User Guide

With Bossa, your tasks are remembered, your mind sways freely, your life moves smooth. 

This guide will help you get started and make the most of Bossa’s features.

![Product screenshot](Ui.png)

## Quick Start

1. Ensure you have Java 17 or above installed on your computer.  
   To check, open Command Prompt and run:
   `java -version`

2. Download the latest `.jar` file from the project release page.

3. Copy the `.jar` file to the folder you want to use as the home folder for Bossa.

4. Open Command Prompt.

5. cd into the folder you put the jar file in

6. Run the application using `java -jar bossa.jar` command.


## Features

Notes about the command format:

- Words in `UPPER_CASE` are parameters supplied by the user.  
  Example: `todo DESCRIPTION`

### Types of Input Format Allowed

All commands are **case-insensitive**.  
For example, the following are treated the same:

- `deadline`
- `DEADLINE`
- `DeAdLiNe`




## Adding todo

Add todo task

Format: `todo DESCRIPTION`

Example: `todo 5 jumping jacks`

The todo task will be added into your task list, with `[T]` indicating it is a todo task, and `[]` indicates your completion status.

```
Got it Boss, added this task:
[T][] 5 jumping jacks
You now have 1 task in your list.
```

## Adding deadlines

Add deadline task with specific date

>**Date format must strictly follow yyyy-mm-dd exactly**

Format: `deadline DESCRIPTION /by YYYY-MM-DD`

Example: `deadline return book /by 2019-05-25`

The deadline task will be added into your task list, with `[D]` indicating it is a deadline task, and `[]` indicates your completion status.

```
Got it Boss, added this task:
[D][] return book (by: May 25 2019)
You now have 2 tasks in your list.
```

## Adding events

Add event task with specific start date and end date

>**Date format must strictly follow yyyy-mm-dd exactly**

Format: `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD`

Example: `event prepare for Grammys /from 2019-04-29 /to 2019-05-08`

The event task will be added into your task list, with `[E]` indicating it is an event task, and `[]` indicates your completion status.

```
Got it Boss, added this task:
[E][] prepare for Grammys (from: Apr 29 2019 to: May 08 2019)
You now have 3 tasks in your list.
```


## List all your tasks 

Type `list` to display all your tasks

```
The tasks in your list:
1. [T][] 5 jumping jacks
2. [D][] return book (by: May 25 2019)
3. [E][] prepare for Grammys (from: Apr 29 2019 to: May 08 2019)
```

## Mark / Unmark Tasks

Mark certain tasks as *done* using `mark`

Unmark certain tasks as *not done* using `unmark`

Format: `mark TASK_NUMBER`
>`TASK_NUMBER` is the index of the task in the task list


Example: `mark 3`

The 3rd task will be marked as done, indicated by `[X]` as the completion status.

```
Great job! Marked as Done!
3. [E][X] prepare for Grammys (from: Apr 29 2019 to: May 08 2019)
```


Example: `unmark 3`

The 3rd tasks completion status will become undone, indicated by `[]` as the completion status.

```
Unmarked:
3. [E][] prepare for Grammys (from: Apr 29 2019 to: May 08 2019)
```


## Find Tasks

Displays all tasks in the task list with the specified keyword

Format: `find KEYWORD`

Example: `find book`

All tasks with the word 'book' will be displayed.
```
Boss, here are the matching tasks in your list:
[T][] return book
[E][] book a event space (from: Apr 29 2019 to: May 08 2019)
[D][] go to the bookshop (by: May 29 2019)
[T][X] BOokzzz
```

## Remove Tasks

Remove certain tasks from the task list using `remove`

Format: `remove TASK_NUMBER`
>`TASK_NUMBER` is the index of the task in the list

Example: `remove 3`

The 3rd task will be removed from the task list.

```
Got it Boss, removed this task:
[E][] prepare for Grammys (from: Apr 29 2019 to: May 08 2019)
You now have 2 tasks in your list.
```

## Undo last action

Undo the last command using `undo`
> This cannot be done multiple times in a row.

Example: `undo`

```
Undid the previous command.
```

## Bye 

`bye`
Closes Bossa :cry: 


## Command Summary

| Command   | Format | Example |
|-----------|--------|----------|
| Todo      | `todo DESCRIPTION` | `todo 5 jumping jacks` |
| Deadline  | `deadline DESCRIPTION /by YYYY-MM-DD` | `deadline return book /by 2019-05-25` |
| Event     | `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD` | `event prepare for Grammys /from 2019-04-29 /to 2019-05-08` |
| List      | `list` | `list` |
| Mark      | `mark TASK_NUMBER` | `mark 3` |
| Unmark    | `unmark TASK_NUMBER` | `unmark 3` |
| Find      | `find KEYWORD` | `find book` |
| Remove    | `remove TASK_NUMBER` | `remove 2` |
| Undo      | `undo` | `undo` |
| Bye       | `bye` | `bye` |


        