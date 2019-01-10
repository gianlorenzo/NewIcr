package it.uniroma3.icr.serviceAndDaoTest;


import it.uniroma3.icr.dao.JobDao;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.AdminService;
import it.uniroma3.icr.service.impl.JobService;
import it.uniroma3.icr.service.impl.StudentServiceSocial;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobTest {

    private Job job;

    private Manuscript manuscript;

    private Task task;

    private List<Image> images;

    private Boolean bool;

    @Autowired
    private JobService jobService;

    @MockBean
    private JobDao jobDao;

    @Before
    public void setUp() {
        job = new Job();
        job.setId(new Long(1));
        manuscript = new Manuscript();
        task = new Task();
        images = new ArrayList<>();
        job.setManuscript(manuscript);
        job.setNumberOfWords(images.size());
        job.setImages(images);
        Mockito.when(jobDao.findOne(new Long(1))).thenReturn(job);

    }

    @Test
    public void whenValidId_thenJobShouldBeFound() {
        Long id = new Long(1);
        Job found = jobService.retrieveJob(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void createJob() {
        JobService jobServiceMock = mock(JobService.class);
        jobServiceMock.createJob(job, manuscript, images, bool, task);
    }

}
