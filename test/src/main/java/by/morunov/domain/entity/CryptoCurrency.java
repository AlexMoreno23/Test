package by.morunov.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Alex Morunov
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrency {

    private String crypto;
    private String currency;
    private BigDecimal price;
}
