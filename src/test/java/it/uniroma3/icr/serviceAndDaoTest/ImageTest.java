package it.uniroma3.icr.serviceAndDaoTest;


import it.uniroma3.icr.dao.ImageDao;
import it.uniroma3.icr.dao.impl.ImageDaoImpl;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.service.impl.ImageService;
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

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageTest {

    private Image image;

    private Manuscript manuscript;

    @Autowired
    private ImageService imageService;

    @MockBean
    private ImageDao imageDao;

    @MockBean
    private ImageDaoImpl imageDaoImpl;

    @Before
    public void setUp() {
        image = new Image();
        image.setId(new Long(1));
        image.setPath("path");
        manuscript = new Manuscript();
        image.setManuscript(manuscript);
        Mockito.when(imageDao.findOne(new Long(1))).thenReturn(image);
    }

    @Test
    public void whenValidId_thenImageShouldBeFound() {
        Long id = new Long(1);
        Image found = imageService.retrieveImage(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void addImage() {
        ImageService imageServiceMock = mock(ImageService.class);
        imageServiceMock.addImage(image);
    }




}
