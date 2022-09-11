package com.nix11.command;

import java.io.IOException;

public interface Command {

    boolean execute() throws IOException;
}
