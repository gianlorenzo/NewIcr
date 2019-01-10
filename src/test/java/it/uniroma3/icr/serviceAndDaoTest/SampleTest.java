package it.uniroma3.icr.serviceAndDaoTest;


import it.uniroma3.icr.dao.SampleDao;
import it.uniroma3.icr.dao.impl.SampleDaoImpl;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.NegativeSampleService;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    private Sample sample;

    private Manuscript manuscript;

    private String p;

    @MockBean
    private SampleDao sampleDao;

    @Before
    public void setUp() {
        sample = new Sample();
        sample.setId(new Long(1));
        manuscript = new Manuscript();
        p = new String("path");
        Mockito.when(sampleDao.findOne(new Long(1))).thenReturn(sample);
    }

    @Test
    public void addSample() throws FileNotFoundException, IOException {
        SampleService sampleServiceMock = mock(SampleService.class);
        sampleServiceMock.getSampleImage(p, manuscript);
    }

    @Test
    public void getSamplePath() {
        SampleService sampleServiceMock = mock(SampleService.class);
        assertEquals(sample.getPath(), sampleServiceMock.getPath());
    }
}
