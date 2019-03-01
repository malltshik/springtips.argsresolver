package ru.malltshik.springtester.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.malltshik.springtester.configurations.resolvers.EntityResolver;

import javax.persistence.EntityManager;
import java.util.List;

@Configuration
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    private final EntityManager entityManager;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new EntityResolver(entityManager));
    }
}