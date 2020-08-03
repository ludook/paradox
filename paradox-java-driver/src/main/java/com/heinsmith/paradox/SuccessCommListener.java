package com.heinsmith.paradox;

import com.heinsmith.paradox.commands.TxCommand;

public interface SuccessCommListener {

    void onSuccess(TxCommand txCommand);

}
