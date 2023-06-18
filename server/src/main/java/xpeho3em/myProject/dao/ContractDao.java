package xpeho3em.myProject.dao;

import xpeho3em.myProject.model.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractDao {
    Optional<List<Contract>> getAllContracts();

    Contract addContract(Contract contract);

    Optional<Contract> getContractByNumber(String name);

    Contract updateContract(Contract contract);

    Optional<Contract> deleteContract(String number);
}
