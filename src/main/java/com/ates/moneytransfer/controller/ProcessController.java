package com.ates.moneytransfer.controller;

import static spark.Spark.get;
import static spark.Spark.port;

import java.math.BigDecimal;
import java.util.List;

import com.ates.moneytransfer.service.AccountService;
import com.ates.moneytransfer.service.TransferService;
import com.ates.moneytransfer.model.Account;
import com.ates.moneytransfer.model.Transfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Spark;

public class ProcessController {

    private static ObjectMapper om = new ObjectMapper();

    private String warningMsg = "account with id ";
    private String pathAccountWithId = "/api/accounts/:id";
    private String pathAccountWithoutId = "/api/accounts";
    private String pathTransfertWithId = "/api/transfers/:id";
    private String pathTransfertWithoutId = "/api/transfers";

    public ProcessController()
    {
        processExecute();
    }

    public void processExecute(){

        final AccountService accountService = new AccountService();

        // Start embedded server at this port
        port(8282);

        final TransferService transferService = new TransferService();

        // Main Page, welcome
        get("/", (request, response) -> "Welcome money transfer application / created by Ibrahim Ates");


        // GET - get account with this id
        Spark.get(pathAccountWithId, (request, response) -> {
            Account account = accountService.findById(Integer.valueOf(request.params(":id")));
            if (account != null) {
                return om.writeValueAsString(account);
            } else {
                response.status(404); // 404 Not found
                return om.writeValueAsString(warningMsg + Integer.valueOf(request.params(":id") + " is not found "));
            }
        });

        // Get - list all accounts
        Spark.get(pathAccountWithoutId, (request, response) -> {
            List result = accountService.findAll();
            if (result.isEmpty()) {
                return om.writeValueAsString("accounts not found");
            } else {
                return om.writeValueAsString(accountService.findAll());
            }
        });

        // POST - Add an account
        Spark.post(pathAccountWithoutId, (request, response) -> {
            Gson gson = new GsonBuilder().create();
            Account account2 = gson.fromJson(request.body() , Account.class);
            if(account2.getName()!=null&&account2.getBalance().compareTo(BigDecimal.ZERO)>=0)
            {
                accountService.addAccount(account2);
                response.status(201); // 201 Created
                return om.writeValueAsString(account2);

            }
            else
            {
                response.status(400); // Bad Request
                if(account2.getName()==null || account2.getName()=="")
                {
                    return om.writeValueAsString("account's name can not be empty");
                }
                else if(account2.getBalance().compareTo(BigDecimal.ZERO)<0)
                {
                    return om.writeValueAsString("account's balance can not be less than 0");
                }

            }
            return om;
        });

        // PUT - Update account
        Spark.put(pathAccountWithId, (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Account account = accountService.findById(Integer.valueOf(request.params(":id")));
            if (account != null) {

                Gson gson = new GsonBuilder().create();
                Account account2 = gson.fromJson(request.body() , Account.class);

                return om.writeValueAsString(accountService.updateAccount(id, account2));
            } else {
                response.status(404);
                return om.writeValueAsString(warningMsg + id + " is not found to update");
            }
        });

        // DELETE - delete account
        Spark.delete(pathAccountWithId, (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Account account = accountService.findById(Integer.valueOf(request.params(":id")));
            if (account != null) {
                accountService.delete(id);
                return om.writeValueAsString(warningMsg + id + " is deleted!");
            } else {
                response.status(404);

                return om.writeValueAsString(warningMsg + id + " is not found to delete!");
            }
        });


        // POST - Add an transfer
        Spark.post(pathTransfertWithoutId, (request, response) -> {
            Gson gson = new GsonBuilder().create();
            Transfer transfer = gson.fromJson(request.body() , Transfer.class);

            Transfer transfer2 = transferService.addTransfer(transfer);
            if(transfer2.getStatus()== Transfer.TransferStatus.CREATED)
            {
                response.status(201); // 201 Created
                return om.writeValueAsString(transfer);
            }
            else
            {
                response.status(400); // bad request

                return om.writeValueAsString(transfer2.getStatus());

            }

        });

        // GET - get transfer with this id
        Spark.get(pathTransfertWithId, (request, response) -> {
            Transfer transfer = transferService.findById(Integer.valueOf(request.params(":id")));
            if (transfer != null) {
                return om.writeValueAsString(transfer);
            } else {
                response.status(404); // 404 Not found
                return om.writeValueAsString("transfer not found");
            }
        });

        // Get - list all transfers

        Spark.get(pathTransfertWithoutId, (request, response) -> {
            List result = transferService.findAll();
            if (result.isEmpty()) {

                return om.writeValueAsString("No transfers done yet");
            } else {
                return om.writeValueAsString(transferService.findAll());
            }

        });

        // PUT - Update transfer
        Spark.put(pathTransfertWithId, (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));

            Transfer transfer = transferService.findById(Integer.valueOf(request.params(":id")));
            if (transfer != null) {

                Gson gson = new GsonBuilder().create();
                Transfer transfer2 = gson.fromJson(request.body() , Transfer.class);

                Transfer transfer3 = transferService.updateTransfer(id, transfer2);
                if(transfer3.getStatus()== Transfer.TransferStatus.UPDATED)
                {
                    response.status(200); // 200 Ok
                    return om.writeValueAsString(transfer3);
                }
                else
                {
                    response.status(400); //
                    return om.writeValueAsString(transfer2.getStatus());

                }
            } else {
                response.status(404);
                return om.writeValueAsString("Any transfer wants to update not found");
            }
        });


    }

}
