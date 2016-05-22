package com.maliavin.vcp.service;

import java.io.IOException;

public interface CommandExecutorService {

    void executeCommand(String cmd) throws IOException;

}
