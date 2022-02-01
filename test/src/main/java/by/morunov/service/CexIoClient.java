package by.morunov.service;

import by.morunov.domain.dto.CryptoCurrencyDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Morunov
 */
@Component
public class CexIoClient {

    HttpClient httpClient = HttpClientBuilder.create().build();
    ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    private final RestTemplate restTemplate = new RestTemplate(requestFactory);
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    public List<CryptoCurrencyDto> getCurrencies() {
        String urlBtc = "https://cex.io/api/last_price/BTC/USD";
        String urlEth = "https://cex.io/api/last_price/ETH/USD";
        String urlXrp = "https://cex.io/api/last_price/XRP/USD";
        try {
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
            List<CryptoCurrencyDto> list = new ArrayList<>();
            list.add(restTemplate.getForObject(new URI(urlBtc), CryptoCurrencyDto.class));
            list.add(restTemplate.getForObject(new URI(urlEth), CryptoCurrencyDto.class));
            list.add(restTemplate.getForObject(new URI(urlXrp), CryptoCurrencyDto.class));
            return list;

        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }

    }
}
