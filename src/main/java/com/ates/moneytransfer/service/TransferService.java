package com.ates.moneytransfer.service;

import com.ates.moneytransfer.model.Transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransferService {



    public final static Map<Integer, Transfer> transfers = new LinkedHashMap();
    static
    {
        Transfer transfer = new Transfer(1,0, BigDecimal.valueOf(23), "make transfer");
        transfers.put(0, transfer);
        Transfer transfer2 = new Transfer(2,0, BigDecimal.valueOf(55), "make transfer 2");
        transfers.put(1, transfer2);
    }


    public Transfer findById(int id) {

        return transfers.get(id);
    }

    public Transfer addTransfer(Transfer transfer) {

        if(checkCredentials(transfer)){

            transfers.put(transfer.getId(), transfer);
            AccountService.accounts.get(transfer.getFromAccountId()).withdraw(transfer.getAmount());
            AccountService.accounts.get(transfer.getToAccountId()).deposit(transfer.getAmount());
            transfer.setStatus(Transfer.TransferStatus.CREATED);


        } else
        {
            updateStatus(transfer);
        }
        return transfer;
    }

    public Transfer updateTransfer(int id, Transfer transfer) {

        if(checkCredentials(transfer))
        {
            transfers.put(id, transfer);
            AccountService.accounts.get(transfer.getFromAccountId()).withdraw(transfer.getAmount());
            AccountService.accounts.get(transfer.getToAccountId()).deposit(transfer.getAmount());

            transfer.setStatus(Transfer.TransferStatus.UPDATED);

        }
        else
        {
            updateStatus(transfer);
        }

        return transfer;

    }


    public List findAll() {
        return new ArrayList<>(transfers.values());
    }


    public Boolean checkCredentials(Transfer transfer)
    {
        if(transfer.getAmount().compareTo(BigDecimal.ZERO) > 0 &&
                AccountService.accounts.get(transfer.getFromAccountId()) != null &&
                AccountService.accounts.get(transfer.getToAccountId()) != null &&
                AccountService.accounts.get(transfer.getFromAccountId()).getBalance().compareTo(transfer.getAmount()) >= 0)
        {
            return true;
        }
        return false;

    }

    public void updateStatus(Transfer transfer)
    {
        if (transfer.getAmount().compareTo(BigDecimal.ZERO) == 0)
        {
            transfer.setStatus(Transfer.TransferStatus.AMOUNTZERO);
        }
        else if (AccountService.accounts.get(transfer.getFromAccountId()) == null)
        {
            transfer.setStatus(Transfer.TransferStatus.FROMIDNULL);
        }
        else if (AccountService.accounts.get(transfer.getToAccountId()) == null)
        {
            transfer.setStatus(Transfer.TransferStatus.TOIDNULL);
        }
        else if (AccountService.accounts.get(transfer.getFromAccountId()).getBalance().compareTo(transfer.getAmount()) < 0)
        {
            transfer.setStatus(Transfer.TransferStatus.AMOUNTLESS);
        }

    }

}
