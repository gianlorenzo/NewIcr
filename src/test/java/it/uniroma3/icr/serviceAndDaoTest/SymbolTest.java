package it.uniroma3.icr.serviceAndDaoTest;


import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.dao.impl.SymbolDaoImpl;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Symbol;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.StudentService;
import it.uniroma3.icr.service.impl.SymbolService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SymbolTest {

    private Symbol symbol;

    private Manuscript manuscript;

    private String p;

    @Autowired
    private SymbolService symbolService;

    @MockBean
    private SymbolDao symbolDao;

    @MockBean
    private SymbolDaoImpl symbolDaoImpl;

    @Before
    public void setUp() {
        symbol = new Symbol();
        symbol.setId(new Long(1));
        p = new String("path");
        manuscript = new Manuscript();
        Mockito.when(symbolDao.findOne(new Long(1))).thenReturn(symbol);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        Long id = new Long(1);
        Symbol found = symbolService.retrieveSymbol(id);
        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void insertSymbol() throws FileNotFoundException, IOException {
        SymbolService symbolServiceMock = mock(SymbolService.class);
        symbolServiceMock.insertSymbolInDb(p,manuscript);
    }

}
