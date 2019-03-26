package com.ates.moneytransfer.service;

import com.ates.moneytransfer.model.Transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TransferService {

    private AccountService accountService = new AccountService();

    private static final AtomicInteger COUNTER = new AtomicInteger();

    public final static Map<Integer, Transfer> transfers = new LinkedHashMap();
    static
    {
        Transfer transfer = new Transfer(0,1,0, BigDecimal.valueOf(23), "make transfer");
        transfers.put(0, transfer);
        Transfer transfer2 = new Transfer(1,2,0, BigDecimal.valueOf(55), "make transfer 2");
        transfers.put(1, transfer2);
    }


    private static final AtomicInteger count = new AtomicInteger(0);

    public Transfer findById(int id) {

        return transfers.get(id);
    }

    public Transfer addTransfer(Transfer transfer) {


        if(transfer.getAmount().compareTo(BigDecimal.ZERO) > 0 &&
                accountService.accounts.get(transfer.getFromAccountId()) != null &&
                accountService.accounts.get(transfer.getToAccountId()) != null &&
                accountService.accounts.get(transfer.getFromAccountId()).getBalance().compareTo(transfer.getAmount()) >= 0)
        {

            transfers.put(transfer.getId(), transfer);
            accountService.accounts.get(transfer.getFromAccountId()).withdraw(transfer.getAmount());
            accountService.accounts.get(transfer.getToAccountId()).deposit(transfer.getAmount());
            transfer.setStatus(Transfer.TransferStatus.CREATED);


        } else
        {

            if (transfer.getAmount().compareTo(BigDecimal.ZERO) == 0)
            {
                transfer.setStatus(Transfer.TransferStatus.AMOUNTZERO);
            }
            else  if (accountService.accounts.get(transfer.getFromAccountId()) == null)
            {
                transfer.setStatus(Transfer.TransferStatus.FROMIDNULL);
            }
            else if (accountService.accounts.get(transfer.getToAccountId()) == null)
            {
                transfer.setStatus(Transfer.TransferStatus.TOIDNULL);
            }
            else if (accountService.accounts.get(transfer.getFromAccountId()).getBalance().compareTo(transfer.getAmount()) < 0)
            {
                transfer.setStatus(Transfer.TransferStatus.AMOUNTLESS);
            }
        }
        return transfer;
    }

    public Transfer updateTransfer(int id, Transfer transfer) {

        if(transfer.getAmount().compareTo(BigDecimal.ZERO) > 0 &&
                accountService.accounts.get(transfer.getFromAccountId()) != null &&
                accountService.accounts.get(transfer.getToAccountId()) != null &&
                accountService.accounts.get(transfer.getFromAccountId()).getBalance().compareTo(transfer.getAmount()) >= 0)
        {


            transfers.put(id, transfer);
            accountService.accounts.get(transfer.getFromAccountId()).withdraw(transfer.getAmount());
            accountService.accounts.get(transfer.getToAccountId()).deposit(transfer.getAmount());

            transfer.setStatus(Transfer.TransferStatus.UPDATED);

        }
        else
        {

            if (transfer.getAmount().compareTo(BigDecimal.ZERO) == 0)
            {
                transfer.setStatus(Transfer.TransferStatus.AMOUNTZERO);
            }
            else if (accountService.accounts.get(transfer.getFromAccountId()) == null)
            {
                transfer.setStatus(Transfer.TransferStatus.FROMIDNULL);
            }
            else if (accountService.accounts.get(transfer.getToAccountId()) == null)
            {
                transfer.setStatus(Transfer.TransferStatus.TOIDNULL);
            }
            else if (accountService.accounts.get(transfer.getFromAccountId()).getBalance().compareTo(transfer.getAmount()) < 0)
            {
                transfer.setStatus(Transfer.TransferStatus.AMOUNTLESS);
            }
        }

        return transfer;

    }


    public void delete(int id) {
        transfers.remove(id);
    }

    public List findAll() {
        return new ArrayList<>(transfers.values());
    }

    public TransferService() {
    }


}
