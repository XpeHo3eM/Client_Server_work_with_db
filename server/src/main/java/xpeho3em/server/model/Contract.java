package xpeho3em.server.model;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


import java.time.LocalDate;

@Data
@Builder
public class Contract {
    @NonNull
    private String number;

    @NonNull
    @PastOrPresent
    private LocalDate signingDate;

    @NonNull
    @PastOrPresent
    private LocalDate lastUpdate;
}
