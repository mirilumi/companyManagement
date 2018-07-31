package com.project.service;

import com.project.controller.DataPage;
import com.project.dtos.StandardQueryParams;
import com.project.entities.BaseEntity;
import com.project.exceptions.CustomException;
import com.project.exceptions.ErrorCode;
import com.project.exceptions.MessageKey;
import com.project.service.utils.PagingAndSortingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class BaseService {
    @Autowired
    private PagingAndSortingUtil pagingAndSortingUtil;
    @PersistenceContext
    private EntityManager entityManager;

    public <DTO,ENTITY> DataPage<DTO> findAll(String entityNamePlural,
                                              StandardQueryParams queryParams,
                                              java.util.function.Function<PageRequest,Page<ENTITY>> finder,
                                              java.util.function.Function<ENTITY,DTO> mapper){
        PageRequest pageRequest = pagingAndSortingUtil.getPageRequest(queryParams);

        log.debug("Looking for all {} from -> {}, pageSize -> {}, sortOrder -> {}",
                entityNamePlural,
                pageRequest.getPageNumber(), pageRequest.getPageSize(),
                pagingAndSortingUtil.getSortAsString(pageRequest)
        );
        Page<ENTITY> page = finder.apply(pageRequest);

        return DataPage.newPage(page, mapper);
    }
    public <DTO,ENTITY extends BaseEntity> ENTITY create(Class<ENTITY> clazz,DTO dto,
                                                         java.util.function.Function<DTO,ENTITY> toEntity){

        return create(clazz,dto,toEntity,e -> e);
    }
    @Transactional
    public <DTO,ENTITY extends BaseEntity> ENTITY create(Class<ENTITY> clazz,
                                                         DTO dto,
                                                         java.util.function.Function<DTO,ENTITY> toEntity,
                                                         java.util.function.Function<ENTITY,ENTITY> preCreate){
        ;
        return create(clazz,dto,toEntity,preCreate,e -> {});
    }
    @Transactional
    public <DTO,ENTITY extends BaseEntity> ENTITY create(Class<ENTITY> clazz,
                                                         DTO dto,
                                                         java.util.function.Function<DTO,ENTITY> toEntity,
                                                         java.util.function.Function<ENTITY,ENTITY> preCreate,
                                                         Consumer<ENTITY> publisher){

        log.debug("Creating a new {} -> {}",clazz.getSimpleName(), dto);
        ENTITY entity = toEntity.apply(dto);
        try {
            entity = preCreate.apply(entity);
        } catch(Exception e) {
            // Exceptions occurring inside the lambda proved difficult to debug, wrapping in an
            // exception so that they become clearer in the future.
            throw new RuntimeException("An exception has occurred inside the preCreate() Function", e);
        }
        return save(publisher, entity);
    }
    @Transactional
    public <ENTITY  extends BaseEntity> ENTITY delete(Class<ENTITY> clazz, Long id) {
        log.debug("Deleting a {} ", clazz.getSimpleName());
        ENTITY entity = findOne(clazz,id);
        entityManager.remove(entity);
        return entity;
    }
    public <ENTITY> ENTITY findOne(Class<ENTITY> clazz,Supplier<ENTITY> entitySupplier) {
        ENTITY entity = entitySupplier.get();
        if(entity == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND, MessageKey.entity_not_found, clazz.getSimpleName());
        }
        return entity;
    }
    public <ENTITY> ENTITY findOne(Class<ENTITY> clazz, Long entityId) {
        return findOne(clazz,() ->  entityManager.find(clazz, entityId));
    }
    public <ENTITY,DTO> DTO findOne(Class<ENTITY> clazz, Long id, java.util.function.Function<ENTITY,DTO> mapper) {
        return mapper.apply(findOne(clazz, id));
    }
    @Transactional
    public <ENTITY  extends BaseEntity> ENTITY save(Consumer<ENTITY> publisher, ENTITY entity) {
        log.debug("Saving to the database -> {}", entity);
        entityManager.persist(entity);
        publisher.accept(entity);
        return entity;
    }
    @Transactional
    public <DTO, ENTITY extends BaseEntity> ENTITY updateEntity(final Class<ENTITY> clazz,
                                                                                       final Supplier<ENTITY> entitySupplier,
                                                                                       final Supplier<DTO> dtoSupplier,
                                                                                       final BiConsumer<DTO,ENTITY> copier) {
        return updateEntity(clazz,entitySupplier,dtoSupplier,copier, p -> {});
    }
    @Transactional
    public <DTO, ENTITY extends BaseEntity> ENTITY updateEntity(final Class<ENTITY> clazz,
                                                                                       final Supplier<ENTITY> entitySupplier,
                                                                                       final Supplier<DTO> dtoSupplier,
                                                                                       final BiConsumer<DTO,ENTITY> copier,
                                                                                       final Consumer<ENTITY> publisher) {
        ENTITY entity = entitySupplier.get();
        log.debug("Updating a {} to the database -> {}", clazz.getSimpleName(), entity);
        DTO dto = dtoSupplier.get();
        copier.accept(dto,entity);
        ENTITY merged = entityManager.merge(entity);
        entityManager.flush();
        publisher.accept(entity);
        return merged;
    }
}
