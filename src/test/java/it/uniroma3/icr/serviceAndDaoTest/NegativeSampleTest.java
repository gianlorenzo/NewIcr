package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.NegativeSampleDao;
import it.uniroma3.icr.dao.impl.NegativeSampleDaoImpl;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.NegativeSample;
import it.uniroma3.icr.service.impl.ImageService;
import it.uniroma3.icr.service.impl.NegativeSampleService;
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
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NegativeSampleTest {

    private NegativeSample negativeSample;

    private Manuscript manuscript;

    private String p;

    @MockBean
    private NegativeSampleDao negativeSampleDao;



    @Before
    public void setUp() {
        negativeSample = new NegativeSample();
        negativeSample.setId(new Long(1));
        manuscript = new Manuscript();
        p = new String("path");
        Mockito.when(negativeSampleDao.findOne(new Long(1))).thenReturn(negativeSample);
    }

    @Test
    public void addNegativeSample() throws FileNotFoundException, IOException {
        NegativeSampleService negativeSampleServiceMock= mock(NegativeSampleService.class);
        negativeSampleServiceMock.getNegativeSampleImage(p,manuscript);
    }

    @Test
    public void getNegativeSamplePath() {
        NegativeSampleService negativeSampleServiceMock= mock(NegativeSampleService.class);
        assertEquals(negativeSample.getPath(),negativeSampleServiceMock.getNegativePath());
    }

}
