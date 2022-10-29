package examen.mercantil.examenmercantil.services;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("testSpecificCreationProductRequest")
@Configuration
public class ProductRequestServiceTestsConfiguration {
    @Bean
    @Primary
    public ProductRequestCreationServiceImpl productRequestCreationService() {
        return Mockito.mock(ProductRequestCreationServiceImpl.class);
    }
}
