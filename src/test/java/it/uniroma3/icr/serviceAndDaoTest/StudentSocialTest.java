package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.StudentDaoSocial;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentServiceSocial;
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
public class StudentSocialTest {

    private StudentSocial studentSocial;

    @Autowired
    private StudentServiceSocial studentServiceSocial;

    @MockBean
    private StudentDaoSocial studentDaoSocial;

    @Before
    public void setUp() {
        studentSocial = new StudentSocial();
        studentSocial.setId(new Long(1));
        studentSocial.setUsername("username");
        Mockito.when(studentDaoSocial.findById(new Long(1)))
                .thenReturn(studentSocial);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        Long id = new Long(1);
        Student found = studentServiceSocial.findById(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void findUserByUsername() {
        String username = "username";
        StudentServiceSocial studentServiceSocialMock = mock(StudentServiceSocial.class);
        when(studentServiceSocialMock.findUserBySurname(username)).thenReturn(studentSocial);
        Student found = studentServiceSocialMock.findUserBySurname(username);
        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    public void createStudent() {
        StudentServiceSocial studentServiceSocialMock = mock(StudentServiceSocial.class);
        when(studentServiceSocialMock.findById(studentSocial.getId())).thenReturn(studentSocial);
        studentServiceSocialMock.saveUser(studentSocial);
    }

}
