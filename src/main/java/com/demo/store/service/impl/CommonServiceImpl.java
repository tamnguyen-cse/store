package com.demo.store.service.impl;

import com.demo.store.service.CommonService;
import com.demo.store.common.Constant.Params;
import com.demo.store.error.CommonError;
import com.demo.store.exception.NotFoundException;
import com.demo.store.utils.ObjectUtils;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    private final static String GET_ID_METHOD = "getId";

    @Autowired
    private MessageSource messageSource;

    @Override
    public <E, D, ID> D save(Class<E> entityClass, Class<D> dtoClass,
            JpaRepository<E, ID> repository, Object data) {
        log.debug("IN - entityClass = {}, dtoClass = {}, data = {}", entityClass.getSimpleName(),
                dtoClass.getSimpleName(), data);
        // Merge post data to entity
        E entity = ObjectUtils.parse(data, entityClass);

        // Save resource
        E dbData = repository.save(entity);

        // Parse entity data to DTO object and return
        D result = ObjectUtils.parse(dbData, dtoClass);
        log.debug("OUT - result = {}", result);
        return result;
    }

    @Override
    public <E, D, ID, S> D overrideUpdate(Class<E> entityClass, Class<D> dtoClass,
            JpaRepository<E, ID> repository, S data, ID id, Function<S, ID> idGetter) {
        ID resourceId = idGetter.apply(data);
        return update(entityClass, dtoClass, repository, data, id, resourceId);
    }

    @Override
    public <E, D, ID> D overrideUpdate(Class<E> entityClass, Class<D> dtoClass,
            JpaRepository<E, ID> repository, Object data, ID id) {

        // Check if id not exist
        Object resourceId = null;
        try {
            Method getIdMethod = dtoClass.getMethod(GET_ID_METHOD);
            resourceId = getIdMethod.invoke(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return update(entityClass, dtoClass, repository, data, id, resourceId);
    }

    @Override
    public <E, D, ID> D skipNullUpdate(Class<E> entityClass, Class<D> dtoClass,
            JpaRepository<E, ID> repository, Object data, ID id) {
        log.debug("IN - entityClass = {}, dtoClass = {}, data = {}, id = {}",
                entityClass.getSimpleName(), dtoClass.getSimpleName(), data, id);

        // Current object in database
        E entityObject = checkById(entityClass, repository, id);

        // Merge patch data to current data
        E mergedObject = ObjectUtils.mergeSkipNull(data, entityObject);

        // Update resource
        E updatedObject = repository.save(mergedObject);

        // Parse entity data to DTO object and return
        D result = ObjectUtils.parse(updatedObject, dtoClass);
        log.debug("OUT - result = {}", result);
        return result;
    }

    @Override
    public <E, D, ID> List<D> getAll(Class<D> dtoClass, JpaRepository<E, ID> repository) {
        log.debug("IN -  dtoClass = {}", dtoClass.getSimpleName());

        // Check if provided id is not found in DB
        List<E> objectList = repository.findAll();
        // Parse entity data to DTO object and return
        List<D> result = ObjectUtils.parse(objectList, dtoClass);
        log.debug("OUT - result = {}", result);
        return result;

    }

    @Override
    public <E, D, ID> D getById(Class<E> entityClass, Class<D> dtoClass,
            JpaRepository<E, ID> repository, ID id) {
        log.debug("IN - dtoClass = {}, id = {}", dtoClass.getSimpleName(), id);

        // Parse entity data to DTO object and return
        E entityObject = checkById(entityClass, repository, id);
        D result = ObjectUtils.parse(entityObject, dtoClass);
        log.debug("OUT - result = {}", result);
        return result;
    }

    @Override
    public <E, ID> E checkById(Class<E> entityClass, JpaRepository<E, ID> repository, ID id) {
        // Check if provided id is not found in DB
        Optional<E> optionalObject = repository.findById(id);
        if (!optionalObject.isPresent()) {
            throw new NotFoundException(CommonError.RESOURCE_NOT_FOUND,
                    new Object[] {entityClass.getSimpleName(), id});
        }

        return optionalObject.get();
    }

    @Override
    public <E, ID> void deleteById(Class<E> entityClass, JpaRepository<E, ID> repository, ID id) {
        log.info("IN - id = {}", id);
        // Current object in database
        E entityObject = checkById(entityClass, repository, id);

        // Delete requested resource
        repository.delete(entityObject);
        log.info("OUT");
    }

    private <E, D, ID> D update(Class<E> entityClass, Class<D> dtoClass,
            JpaRepository<E, ID> repository, Object data, ID id, Object resourceId) {
        log.info("IN - entityClass = {}, dtoClass = {}, data = {}, id = {}",
                entityClass.getSimpleName(), dtoClass.getSimpleName(), data, id);
        // Check if the ID in body data is not provided or different with the ID in path parameter
        if (resourceId == null) {
            throw new NotFoundException(CommonError.RESOURCE_NULL,
                    new Object[] {dtoClass.getSimpleName(), Params.ID});
        }

        // Check if provided id is not found in DB
        checkById(entityClass, repository, id);

        // Merge put data to entity
        E updatedObject = ObjectUtils.parse(data, entityClass);

        // Update resource
        E dbData = repository.save(updatedObject);

        // Parse entity data to DTO object and return
        D result = ObjectUtils.parse(dbData, dtoClass);
        log.info("OUT - result = {}", result);
        return result;
    }

}
