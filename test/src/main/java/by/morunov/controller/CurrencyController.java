package by.morunov.controller;

import by.morunov.domain.entity.CryptoCurrency;
import by.morunov.service.impl.CryptoCurrencyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alex Morunov
 */
@RestController
@RequestMapping(name = "/currency", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CurrencyController {

    private final CryptoCurrencyServiceImpl cryptoCurrencyService;

    @GetMapping()
    public ResponseEntity<List<CryptoCurrency>> getCrypto() {
        List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyService.getAll();
        return new ResponseEntity<>(cryptoCurrencies, HttpStatus.OK);
     }


}
