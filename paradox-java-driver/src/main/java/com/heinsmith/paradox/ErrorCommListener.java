package com.heinsmith.paradox;

import com.heinsmith.paradox.commands.TxCommand;

public interface ErrorCommListener {

    void onTimeout(TxCommand txCommand);

}
