package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.ManuscriptDao;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.service.impl.ManuscriptService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManuscriptTest {

    private Manuscript manuscript;

    @Autowired
    private ManuscriptService manuscriptService;

    @MockBean
    private ManuscriptDao manuscriptDao;

    @Before
    public void setUp() {
        manuscript = new Manuscript();
        manuscript.setId(new Long(1));
        manuscript.setName("provaTest");
        Mockito.when(manuscriptDao.findOne(new Long(1))).thenReturn(manuscript);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        Long id = new Long(1);
        Manuscript found = manuscriptService.findOneManuscript(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void findUserByName() {
        String name = "provaTest";
        ManuscriptService manuscriptServiceMock = mock(ManuscriptService.class);
        when(manuscriptServiceMock.findManuscriptByName(name)).thenReturn(manuscript);
        Manuscript found = manuscriptServiceMock.findManuscriptByName(name);
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void createManuscript() {
        ManuscriptService manuscriptServiceMock = mock(ManuscriptService.class);
        when(manuscriptServiceMock.findOneManuscript(manuscript.getId())).thenReturn(manuscript);
        manuscriptServiceMock.saveManuscript(manuscript);
    }

}
