package xpeho3em.myProject.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import xpeho3em.myProject.exception.DatabaseAccessException;
import xpeho3em.myProject.model.Contract;
import xpeho3em.myProject.util.Mapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ContractDaoImpl implements ContractDao {
    private final JdbcTemplate jdbcTemplate;

    public ContractDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Contract> getContractByNumber(String number) {
        log.info("Получение договора по номеру = {}", number);

        String sqlQuery = "SELECT * " +
                "FROM contracts " +
                "WHERE number = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, Mapper::mapRowToContract, number));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Contract>> getAllContracts() {
        log.info("Получение всех договоров");

        String sqlQuery = "SELECT * " +
                "FROM contracts";

        try {
            return Optional.of(jdbcTemplate.query(sqlQuery, Mapper::mapRowToContract));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Contract addContract(Contract contract) {
        log.info("Добавление договора: {}", contract);

        try {
            new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("contracts")
                    .execute(Mapper.contractToMap(contract));
        } catch (DataAccessException e) {
           throw new DatabaseAccessException("Ошибка добавления договора");
        }

        log.info("Договор добавлен");

        return contract;
    }

    @Override
    public Contract updateContract(Contract contract) {
        log.info("Обновление договора");

        String sqlQuery = "UPDATE contracts " +
                "SET signing_date = ?, " +
                "    last_update = ? " +
                "WHERE number = ?";

        try {
            jdbcTemplate.update(sqlQuery,
                    contract.getSigningDate(),
                    contract.getLastUpdate(),
                    contract.getNumber());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Ошибка обновления договора");
        }

        log.info("Договор обновлен");

        return contract;
    }

    @Override
    public Optional<Contract> deleteContract(String number) {
        log.info("Удаление договора с номером {}", number);

        String sqlQuery = "DELETE FROM contracts " +
                "WHERE number = ?";

        try {
            jdbcTemplate.update(sqlQuery, number);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Ошибка удаления договора");
        }

        return getContractByNumber(number);
    }
}
