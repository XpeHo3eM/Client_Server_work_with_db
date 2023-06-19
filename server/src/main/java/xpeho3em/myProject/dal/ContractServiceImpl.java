package xpeho3em.myProject.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xpeho3em.myProject.dao.ContractDaoImpl;
import xpeho3em.myProject.exception.EntityAlreadyExists;
import xpeho3em.myProject.exception.EntityNotDeletedException;
import xpeho3em.myProject.exception.EntityNotFoundException;
import xpeho3em.myProject.model.Contract;
import xpeho3em.myProject.validator.ContractValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private ContractDaoImpl contractDao;

    public ContractServiceImpl(ContractDaoImpl contractDao) {
        this.contractDao = contractDao;
    }

    @Override
    public Contract getContractByNumber(String number) {
        return contractDao.getContractByNumber(number).orElseThrow(() ->
            new EntityNotFoundException(String.format("Договор с номером %s не найден", number))
        );
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractDao.getAllContracts().orElse(new ArrayList<>());
    }

    @Override
    public Contract addContract(Contract contract) {
        ContractValidator.validate(contract);

        contractDao.getContractByNumber(contract.getNumber()).ifPresent(c -> {
            throw new EntityAlreadyExists(String.format("Договор с номером %s уже существует", contract.getNumber()));
        });

        log.info("Договор с номером {} не найден", contract.getNumber());

        return contractDao.addContract(contract);
    }

    @Override
    public Contract updateContract(Contract contract) {
        ContractValidator.validate(contract);

        Contract contractOnDb = contractDao.getContractByNumber(contract.getNumber()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Договор с номером %s не найден", contract.getNumber()))
        );

        log.info("Договор {} найден", contractOnDb);

        return contractDao.updateContract(contract);
    }

    @Override
    public void deleteContract(String number) {
        contractDao.deleteContract(number).ifPresent(c -> {
            throw new EntityNotDeletedException(String.format("Найден договор с номером %s после удаления", number));
        });

        log.info("Договор с номером {} удален", number);
    }

    @Override
    public void deleteAllContracts() {
        contractDao.deleteAllContracts();

        log.info("Все договора удалены");
    }
}
