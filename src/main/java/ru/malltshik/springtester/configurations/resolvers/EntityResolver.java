package ru.malltshik.springtester.configurations.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.malltshik.springtester.configurations.annotations.InPath;
import ru.malltshik.springtester.exceptions.http.BadRequestException;
import ru.malltshik.springtester.exceptions.http.ForbiddenException;
import ru.malltshik.springtester.exceptions.http.NotFoundException;
import ru.malltshik.springtester.persistense.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;


@RequiredArgsConstructor
public class EntityResolver implements HandlerMethodArgumentResolver {

    private final EntityManager em;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean inPathAnnotated = parameter.hasParameterAnnotation(InPath.class);
        boolean isEntity = parameter.getParameterType().isAnnotationPresent(Entity.class);
        boolean hasId = Stream.of(parameter.getParameterType().getDeclaredFields())
                .anyMatch(f -> f.isAnnotationPresent(Id.class));
        return inPathAnnotated && isEntity && hasId;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Map<String, String> uriTemplateVars = (Map<String, String>) webRequest.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, SCOPE_REQUEST);
        Class<?> entityType = parameter.getParameterType();
        Class<?> idType = Stream.of(entityType.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(Field::getType).findFirst().orElse(null);
        InPath inPath = parameter.getParameterAnnotation(InPath.class);
        Objects.requireNonNull(entityType);
        Objects.requireNonNull(idType);
        Objects.requireNonNull(inPath);
        Object id = castId(idType, uriTemplateVars.get(inPath.value()));
        Object entity = em.find(entityType, id);
        if (entity == null) {
            throw new NotFoundException(String.format("Entity entityType of %s with identifier %s not found",
                    entityType.toString(), id));
        }
        if(entityType.getSuperclass().equals(BaseEntity.class)) {
            Long accountId = ((BaseEntity) entity).getAccountId();
            if(accountId != null && accountId
                    .equals(42L)) {
                throw new ForbiddenException();
            }
        }
        // if(some other check) { throw some other exception}
        return entity;
    }

    private Object castId(Class<?> type, String id) {
        if (type.equals(Long.class)) {
            return new Long(id);
        } else {
            throw new BadRequestException("Unsupported type of entity identifier");
        }
    }
}