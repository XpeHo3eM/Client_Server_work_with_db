package xpeho3em.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xpeho3em.server.dal.ContractService;
import xpeho3em.server.model.Contract;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/contracts")
public class ContractController {
    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public List<Contract> getAllContracts(HttpServletRequest req) {
        log.info("{} {}", req.getMethod(), req.getRequestURI());

        return contractService.getAllContracts();
    }

    @PostMapping
    public Contract addContract(@Valid @RequestBody Contract contract,
                                HttpServletRequest req) {
        log.info("{} {} body: {}", req.getMethod(), req.getRequestURI(), contract);

        return contractService.addContract(contract);
    }

    @PutMapping
    public Contract updateContract(@Valid @RequestBody Contract contract,
                                   HttpServletRequest req) {
        log.info("{} {} body: {}", req.getMethod(), req.getRequestURI(), contract);

        return contractService.updateContract(contract);
    }

    @GetMapping("/{number}")
    public Contract getContractByNumber(@Valid @PathVariable String number,
                                        HttpServletRequest req) {
        log.info("{} {}", req.getMethod(), req.getRequestURI());

        return contractService.getContractByNumber(number);
    }

    @DeleteMapping("/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContract(@Valid @PathVariable String number,
                                   HttpServletRequest req) {
        log.info("{} {}", req.getMethod(), req.getRequestURI());

        contractService.deleteContract(number);
    }
}
