package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class AdminServiceUnitTest {
    @Mock
    private AdminsRepository adminsRepository;

    @InjectMocks
    private AdminsService adminsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAdmins() {
        Admins admin1 = new Admins();
        admin1.setUsername("admin1");
        admin1.setPassword("abc123");

        Admins admin2 = new Admins();
        admin2.setUsername("admin2");
        admin2.setPassword("abc123");

        List<Admins> adminsFakeList = new ArrayList<>();
        adminsFakeList.add(admin1);
        adminsFakeList.add(admin2);

        when(adminsRepository.findAll()).thenReturn(adminsFakeList);

        List<Admins> adminsRealList = adminsService.getAllAdmins();

        assertNotNull(adminsRealList);
        assertEquals(2, adminsRealList.size());
        assertEquals(adminsFakeList, adminsRealList);

        verify(adminsRepository, times(1)).findAll();
    }
}
