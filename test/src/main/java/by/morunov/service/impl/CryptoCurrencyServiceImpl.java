package by.morunov.service.impl;

import by.morunov.domain.dto.CryptoCurrencyDto;
import by.morunov.domain.entity.CryptoCurrency;
import by.morunov.service.CexIoClient;
import by.morunov.service.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import com.google.common.collect.ImmutableList;

/**
 * @author Alex Morunov
 */
@Service
@AllArgsConstructor
public class CryptoCurrencyServiceImpl  implements CryptoCurrencyService{

    private final CexIoClient cexIoClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoCurrencyServiceImpl.class);

    @Override
    public List<CryptoCurrency> getAll() {
        LOGGER.info("Getting cryptocurrency");
        return cexIoClient.getCurrencies().stream()
                .map(this::toCryptocurrency)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    private CryptoCurrency toCryptocurrency(@NonNull CryptoCurrencyDto cryptoCurrencyDto) {
        BigDecimal price = new BigDecimal(cryptoCurrencyDto.getLprice());
        return new CryptoCurrency(cryptoCurrencyDto.getCurr1(),
                cryptoCurrencyDto.getCurr2(), price);
    }


}
