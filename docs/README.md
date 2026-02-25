# CHA User Guide ☕

# CHA – Your Task Brewing Assistant

CHA helps you keep track of tasks, deadlines, and events — all from a simple command interface.

Fast. Minimal. Straight to the point.

---

## 🚀 Quick Start

1. Ensure you have **Java 17 or above** installed.
2. Download the latest `.jar` file.
3. Open a terminal in the folder containing the `.jar`.
4. Run:

```
java -jar cha.jar
```

5. Type a command and press **Enter** (or click Send in GUI).

---

## 📌 Features

---

## 1️⃣ View All Tasks

### `list`

Shows all tasks currently stored.

**Format**

```
list
```

**Example**

```
list
```

---

## 2️⃣ Add a ToDo

Adds a simple task without date/time.

### `todo`

**Format**

```
todo DESCRIPTION
```

**Example**

```
todo Finish CS2103T tutorial
```

---

## 3️⃣ Add a Deadline

Adds a task with a deadline.

### `deadline`

**Format**

```
deadline DESCRIPTION /by yyyy-MM-dd HHmm
```

**Example**

```
deadline Submit assignment /by 2026-03-01 2359
```

⚠ Time must follow this format exactly:

```
yyyy-MM-dd HHmm
```

---

## 4️⃣ Add an Event

Adds a task that happens during a specific period.

### `event`

**Format**

```
event DESCRIPTION /from START /to END
```

**Example**

```
event Project meeting /from Monday 2pm /to Monday 4pm
```

---

## 5️⃣ Mark Task as Done

Marks a task as completed.

### `mark`

**Format**

```
mark INDEX
```

**Example**

```
mark 2
```

⚠ Index refers to the task number shown in `list`.

---

## 6️⃣ Delete a Task

Removes a task from the list.

### `delete`

**Format**

```
delete INDEX
```

**Example**

```
delete 3
```

---

## 7️⃣ Find Tasks

Searches tasks by keyword.

### `find`

**Format**

```
find KEYWORD
```

**Example**

```
find meeting
```

---

## 8️⃣ Update a Task

Modify an existing task without deleting it.

### `update`

**Format**

```
update INDEX /field value
```

### Supported fields:

| Task Type | Field   | Usage                |
| --------- | ------- | -------------------- |
| All       | `/desc` | Update description   |
| Deadline  | `/by`   | Update deadline time |
| Event     | `/from` | Update start time    |
| Event     | `/to`   | Update end time      |

---

### 🔹 Update Description

```
update 1 /desc New description
```

---

### 🔹 Update Deadline Time

```
update 2 /by 2026-04-01 1800
```

---

### 🔹 Update Event Time

```
update 3 /from Tuesday 3pm
update 3 /to Tuesday 5pm
```

⚠ Deadline time must follow `yyyy-MM-dd HHmm`.

---

## 9️⃣ Exit Application

### `bye`

Closes the application.

```
bye
```

---

# 💾 Data Storage

* Tasks are automatically saved in:

```
data/cha.txt
```

* Data persists between sessions.
* Do not manually edit the file unless you know what you are doing.

---

# ⚠ Error Handling

CHA will notify you if:

* You provide an invalid task index
* You use the wrong time format
* A command format is incorrect
* You try to update unsupported fields

Follow the command formats exactly to avoid errors.

---

# 🧠 Command Summary

| Action       | Command                                    |
| ------------ | ------------------------------------------ |
| View tasks   | `list`                                     |
| Add todo     | `todo DESCRIPTION`                         |
| Add deadline | `deadline DESCRIPTION /by yyyy-MM-dd HHmm` |
| Add event    | `event DESCRIPTION /from START /to END`    |
| Mark done    | `mark INDEX`                               |
| Delete       | `delete INDEX`                             |
| Find         | `find KEYWORD`                             |
| Update       | `update INDEX /field value`                |
| Exit         | `bye`                                      |

---

# ✨ Designed for Simplicity

CHA is built for:

* Anyone managing tasks, whether tea-brewing or not
* Anyone who prefers fast keyboard-driven task management

Brew your tasks. Stay productive. ☕
