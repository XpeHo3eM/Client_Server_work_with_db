package xpeho3em.client.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class Contract {
    private String number;
    private LocalDate signingDate;
    private Boolean lastUpdate;
}
