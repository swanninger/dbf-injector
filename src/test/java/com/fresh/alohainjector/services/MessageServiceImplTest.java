package com.fresh.alohainjector.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.io.IOException;

import static org.junit.Assert.*;

public class MessageServiceImplTest {

    MessageServiceImpl messageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        messageService = new MessageServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sendCmdMsg() {
        messageService.sendCmdMsg("Hello\n World");

    }
}