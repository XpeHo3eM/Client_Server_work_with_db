package xpeho3em.server.dal;

import xpeho3em.server.model.Contract;

import java.util.List;

public interface ContractService {
    Contract getContractByNumber(String number);

    List<Contract> getAllContracts();

    Contract addContract(Contract contract);

    Contract updateContract(Contract contract);

    void deleteContract(String number);
    void deleteAllContracts();
}
