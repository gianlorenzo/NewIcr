package it.uniroma3.icr.serviceAndDaoTest;


import it.uniroma3.icr.dao.StudentDao;
import it.uniroma3.icr.model.Student;
import org.junit.Before;
import org.junit.Test;
import it.uniroma3.icr.service.impl.StudentService;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {

    private Student student;

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentDao studentDao;


    @Before
    public void setUp() {
        student = new Student();
        student.setId(new Long(1));
        student.setUsername("username");
        Mockito.when(studentDao.findById(new Long(1)))
                .thenReturn(student);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        Long id = new Long(1);
        Student found = studentService.findById(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void findUserByUsername() {
        String username = "username";
        StudentService studentServiceMock = mock(StudentService.class);
        when(studentServiceMock.findUserBySurname(username)).thenReturn(student);
        Student found = studentServiceMock.findUserBySurname(username);
        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    public void createStudent() {
        StudentService studentServiceMock = mock(StudentService.class);
        when(studentServiceMock.findById(student.getId())).thenReturn(student);
        studentServiceMock.saveUser(student);
    }

}
