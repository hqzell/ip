package cha;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import cha.tasks.Task;
import cha.tasks.ToDo;

public class TaskListTest {

    @Test
    public void addTask_increasesSize() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("make matcha"));

        assertEquals(1, list.size());
    }

    @Test
    public void deleteTask_removesCorrectTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("a"));
        list.addTask(new ToDo("b"));

        Task removed = list.deleteTask(0);

        assertEquals("[T][ ] a", removed.toString());
        assertEquals(1, list.size());
        assertEquals("[T][ ] b", list.getTask(0).toString());
    }

    @Test
    public void markTaskAsDone_marksCorrectTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("make matcha"));

        Task task = list.markTaskAsDone(0);

        assertEquals(task.getStatus(), "X");
    }

    @Test
    public void deleteTask_invalidIndex_throwsException() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("a"));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.deleteTask(5));
    }
}
