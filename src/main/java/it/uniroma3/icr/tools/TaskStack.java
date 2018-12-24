package it.uniroma3.icr.tools;

import it.uniroma3.icr.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;

public class TaskStack {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private Stack stack;

    public Task popTask(List<Task> tasks) {
        stack = new Stack();
        for(Task t : tasks) {
            stack.push(t);
        }


        return null;
    }
}
