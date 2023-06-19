package xpeho3em.server.validator;

import jakarta.validation.ValidationException;
import xpeho3em.server.model.Contract;

public class ContractValidator {
    public static void validate (Contract contract) {
        if (contract.getLastUpdate().isBefore(contract.getSigningDate())) {
            throw new ValidationException("Дата обновления не может быть раньше даты подписания договора");
        }
    }
}
