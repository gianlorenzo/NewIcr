package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.ResultDao;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.TaskWrapper;
import it.uniroma3.icr.service.impl.ResultService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultTest {

    private Result result;

    private TaskWrapper taskResults;

    @Autowired
    private ResultService resultService;

    @MockBean
    private ResultDao resultDao;

    @Before
    public void setUp() {
        result = new Result();
        result.setId(new Long(1));
        taskResults = new TaskWrapper();
        Mockito.when(resultDao.findOne(new Long(1))).thenReturn(result);
    }

    @Test
    public void whenValidId_thenResultShouldBeFound() {
        Long id = new Long(1);
        Result found = resultService.retrieveResult(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void addResult() {
        ResultService resultServiceMock = mock(ResultService.class);
        resultServiceMock.addResult(result);
    }

    @Test
    public void updateListResult() {
        ResultService resultServiceMock = mock(ResultService.class);
        resultServiceMock.updateListResult(taskResults);
    }

}
