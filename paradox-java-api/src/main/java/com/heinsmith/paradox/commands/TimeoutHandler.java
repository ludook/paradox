package com.heinsmith.paradox.commands;

import java.io.IOException;

public interface TimeoutHandler<T> {

    void timeout(TxCommand<T> txCommand) throws IOException;
}
