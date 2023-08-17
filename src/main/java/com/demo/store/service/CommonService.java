package com.demo.store.service;

import java.util.List;
import java.util.function.Function;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonService {

    /**
     * POST to create new resource.
     *
     * @param entityClass the entity class
     * @param dtoClass    the DTO class
     * @param repository  the JPA repository
     * @param data        the object data
     * @return new resource
     */
    <E, D, ID> D save(Class<E> entityClass, Class<D> dtoClass, JpaRepository<E, ID> repository,
        Object data);

    /**
     * PUT to update resource by id.
     *
     * @param entityClass the entity class
     * @param dtoClass    the DTO class
     * @param repository  the JPA repository
     * @param data        the object data
     * @param id          the resource id for update
     * @return updated resource
     */
    <E, D, ID, S> D overrideUpdate(Class<E> entityClass, Class<D> dtoClass,
        JpaRepository<E, ID> repository, S data, ID id, Function<S, ID> idGetter);

    /**
     * PUT to update resource by id.
     *
     * @param entityClass the entity class
     * @param dtoClass    the DTO class
     * @param repository  the JPA repository
     * @param data        the object data
     * @param id          the resource id for update
     * @return updated resource
     */
    <E, D, ID> D overrideUpdate(Class<E> entityClass, Class<D> dtoClass,
        JpaRepository<E, ID> repository, Object data, ID id);

    /**
     * PATCH to partly update resource by id or expression.
     *
     * @param entityClass the entity class
     * @param dtoClass    the DTO class
     * @param repository  the JPA repository
     * @param data        the object data
     * @param id          the resource id for update
     * @return updated resource
     */
    <E, D, ID> D skipNullUpdate(Class<E> entityClass, Class<D> dtoClass,
        JpaRepository<E, ID> repository, Object data, ID id);

    /**
     * GET all resource.
     *
     * @param dtoClass   the DTO class
     * @param repository the JPA repository
     * @return list of resource
     */
    <E, D, ID> List<D> getAll(Class<D> dtoClass, JpaRepository<E, ID> repository);

    /**
     * GET to query resource by id.
     *
     * @param entityClass the entity class
     * @param dtoClass    the DTO class
     * @param repository  the JPA repository
     * @param id          the resource id for update
     * @return resource with id
     */
    <E, D, ID> D getById(Class<E> entityClass, Class<D> dtoClass, JpaRepository<E, ID> repository,
        ID id);

    /**
     * Check to query resource by id.
     *
     * @param entityClass the entity class
     * @param repository  the JPA repository
     * @param id          the resource id for update
     * @return resource with id
     */
    <E, ID> E checkById(Class<E> entityClass, JpaRepository<E, ID> repository, ID id);

    /**
     * DELETE to remove resource by id.
     *
     * @param entityClass the entity class
     * @param repository  the JPA repository
     * @param id          the resource id for update
     */
    <E, ID> void deleteById(Class<E> entityClass, JpaRepository<E, ID> repository, ID id);

}
