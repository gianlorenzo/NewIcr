package it.uniroma3.icr.serviceAndDaoTest;

import it.uniroma3.icr.dao.AdminDao;
import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.service.impl.AdminService;
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
public class AdminTest {

    private Administrator admin;

    @Autowired
    private AdminService adminService;

    @MockBean
    private AdminDao adminDao;

    @Before
    public void setUp() {
        admin = new Administrator();
        admin.setId(new Long(1));
        admin.setUsername("username");
        Mockito.when(adminDao.findOne(new Long(1))).thenReturn(admin);
    }

    @Test
    public void findUserByUsername() {
        String username = "username";
        AdminService adminServiceMock = mock(AdminService.class);
        when(adminServiceMock.findAdmin(username)).thenReturn(admin);
        Administrator found = adminServiceMock.findAdmin(username);
        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    public void createAdmin() {
        AdminService adminServiceMock = mock(AdminService.class);
        when(adminServiceMock.findAdmin(admin.getUsername())).thenReturn(admin);
        adminServiceMock.addAdmin(admin);
    }
}
