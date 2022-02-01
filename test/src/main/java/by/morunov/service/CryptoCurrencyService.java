package by.morunov.service;

import by.morunov.domain.entity.CryptoCurrency;

import java.util.List;

/**
 * @author Alex Morunov
 */
public interface CryptoCurrencyService {

    public List<CryptoCurrency> getAll();

}
