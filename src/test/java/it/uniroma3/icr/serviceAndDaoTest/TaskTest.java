package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.impl.TaskDaoImpl;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.TaskService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

    private Task task;

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskDaoImpl taskDaoImpl;

    @Before
    public void setUp() {
        task = new Task();
    }
}
