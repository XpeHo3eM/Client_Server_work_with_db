package xpeho3em.server;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xpeho3em.server.dal.ContractService;
import xpeho3em.server.model.Contract;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class serverTests {
	private final ContractService service;
	private static Contract contract1;
	private static Contract contract2;

	@BeforeEach
	void createContracts() {
		service.deleteAllContracts();

		contract1 = Contract.builder()
				.number("12-33-2023")
				.signingDate(LocalDate.of(2023, Month.FEBRUARY, 2))
				.lastUpdate(LocalDate.of(2023, Month.FEBRUARY, 5))
				.build();
		contract2 = Contract.builder()
				.number("13-33-2023")
				.signingDate(LocalDate.of(2023, Month.APRIL, 11))
				.lastUpdate(LocalDate.of(2023, Month.JUNE, 3))
				.build();

		assertDoesNotThrow(() -> service.addContract(contract1), String.format("Не добавлен договор: %s", contract1));
		assertDoesNotThrow(() -> service.addContract(contract2), String.format("Не добавлен договор: %s", contract2));
	}

	@Test
	void shouldUpdateContracts() {
		contract1.setSigningDate(LocalDate.of(2023, Month.FEBRUARY, 8));
		contract1.setLastUpdate(LocalDate.of(2023, Month.MARCH, 13));

		assertDoesNotThrow(() -> service.updateContract(contract1), String.format("Не обновлен договор: %s", contract1));
		assertDoesNotThrow(() -> service.getContractByNumber(contract1.getNumber()), "Не получен договор");

		Contract contractOnDb = service.getContractByNumber(contract1.getNumber());

		assertEquals(contract1.getSigningDate(), contractOnDb.getSigningDate(), "Не совпадают даты подписания договора");
		assertEquals(contract1.getLastUpdate(), contractOnDb.getLastUpdate(), "Не совпадают даты последнего изменения договора");
	}

	@Test
	void shouldDeleteContract() {
		assertDoesNotThrow(() -> service.deleteContract(contract1.getNumber()), "Найден договор после удаления");
	}

	@Test
	void shouldGetContract() {
		assertEquals(contract1, service.getContractByNumber(contract1.getNumber()), "Получен некорректный договор");
	}

	@Test
	void shouldGet2Contract() {
		assertEquals(2, service.getAllContracts().size(), "Получено не 2 договора");
		assertEquals(contract1, service.getAllContracts().get(0), "Получен некорректный договор");
		assertEquals(contract2, service.getAllContracts().get(1), "Получен некорректный договор");
	}
}
