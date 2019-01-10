package it.uniroma3.icr.service.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.TaskService;

@Component
public class TaskEditor extends PropertyEditorSupport {

    private @Autowired
    TaskService taskService;

    @Override
    public void setAsText(String text) {
        Task t = this.taskService.retrieveTask(Long.valueOf(text));
        this.setValue(t);
    }

}
