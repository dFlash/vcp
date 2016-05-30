package com.maliavin.vcp.service;

import java.io.IOException;
import java.util.List;

public interface CommandExecutorService {

    void executeCommand(List<String> cmd) throws IOException;

}
