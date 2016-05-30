package com.maliavin.vcp.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.service.CommandExecutorService;

@Service
public class CommandExecutorServiceImpl implements CommandExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutorServiceImpl.class);

    @Override
    public void executeCommand(List<String> cmd) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            LOGGER.error("Exception for waiting command: " + cmd.toString() + " " + e.getMessage());
        }
    }

}
