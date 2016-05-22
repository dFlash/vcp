package com.maliavin.vcp.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.maliavin.vcp.service.CommandExecutorService;

@Service
public class CommandExecutorServiceImpl implements CommandExecutorService {

    @Override
    public void executeCommand(String cmd) throws IOException {
        Runtime.getRuntime().exec(cmd);
    }

}
