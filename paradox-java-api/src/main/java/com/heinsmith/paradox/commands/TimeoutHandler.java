package com.heinsmith.paradox.commands;

public interface TimeoutHandler<T> {

    void timeout(TxCommand<T> txCommand);
}
