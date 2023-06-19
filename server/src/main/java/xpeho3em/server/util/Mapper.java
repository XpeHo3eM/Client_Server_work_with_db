package xpeho3em.server.util;

import xpeho3em.server.model.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Mapper {
    private Mapper() {
    }

    public static final Contract mapRowToContract(ResultSet rs, int rowNum) throws SQLException {
        return Contract.builder()
                .number(rs.getString("number"))
                .signingDate(rs.getDate("signing_date").toLocalDate())
                .lastUpdate(rs.getDate("last_update").toLocalDate())
                .build();
    }

    public static final Map<String, Object> contractToMap(Contract contract) {
        return new HashMap<>() {{
            put("number", contract.getNumber());
            put("signing_date", contract.getSigningDate());
            put("last_update", contract.getLastUpdate());
        }};
    }
}
