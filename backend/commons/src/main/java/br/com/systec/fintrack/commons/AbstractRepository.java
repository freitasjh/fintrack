package br.com.systec.fintrack.commons;

import br.com.systec.fintrack.commons.filter.FilterPageParam;
import br.com.systec.fintrack.commons.query.PaginatedList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoRepositoryBean
public abstract class AbstractRepository<T, ID> implements CrudRepository<T, ID> {

    @SuppressWarnings("unchecked")
	private Class<T> entityClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @NonNull
    public <S extends T> S save(@NonNull S entity) {
        return savePrivate(entity);
    }

    private <S extends T> S savePrivate(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S update(S entity){
        return updatePrivate(entity);
    }

    private <S extends T> S updatePrivate(S entity){
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @NonNull
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(savePrivate(entity)));
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> Iterable<S> updateAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(updatePrivate(entity)));
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    private Optional<T> findByIdPrivate(ID id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean existsById(ID id) {
        return findByIdPrivate(id).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<T> findAll() {
        return findAllPrivate();
    }

    private Iterable<T> findAllPrivate() {
        return entityManager.createQuery("SELECT o FROM "+entityClass.getSimpleName()+" o", entityClass).getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<T> findAllByTenantId() {
        TypedQuery<T> query = entityManager.createQuery("select obj from "+entityClass.getSimpleName()+" obj where obj.tenantId = :tenantId",entityClass);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<T> findByIdAndTenant(Long id) {
        TypedQuery<T> query = entityManager.createQuery("select obj from "+entityClass.getSimpleName()+" obj " +
                "where obj.tenantId = :tenantId and obj.id = :id",entityClass);

        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("id", id);

        List<T> listReturn = query.getResultList();

        if(listReturn.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listReturn.get(0));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @NonNull
    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> result = new ArrayList<>();

        ids.forEach(id -> {
            Optional<T> obj = findByIdPrivate(id);
            obj.ifPresent(result::add);
        });

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T getOne(ID id){
        return entityManager.getReference(entityClass, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public long count() {
        return (long) (entityManager.createQuery("SELECT COUNT(o) FROM "+entityClass.getSimpleName()+" o")).getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(ID id) {
        Optional<T> entity = findByIdPrivate(id);
        T entityToDelete = entity.orElseThrow(() -> new IllegalArgumentException("Entity not found"));

        entityManager.remove(entityToDelete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> iterable) {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll() {
        Iterable<T> listAll = findAllPrivate();
        listAll.forEach(this::delete);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void flush(){ entityManager.flush();}

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S savedAndFlush(S entity){
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S updateAndFlush(S entity){
        entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T findByIdOrNull(ID id){
        Optional<T> oEntitu = findByIdPrivate(id);
        return oEntitu.orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNativeQuery(String nativeQuery) {
        entityManager.createNativeQuery(nativeQuery).executeUpdate();
    }

    /*
        TODO acredito que para querys simples essa logica vai funcionar, mais o problema pode ser o count, validar com querys mais complexas,
        TODO verificar para melhorar a logica principalmente do count.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected PaginatedList<T> executePaginatedQuery(Query query, FilterPageParam filterPageParam) {
        setParametersOnQuery(query, filterPageParam.getParams());

        int start = (filterPageParam.getPage() - 1) * filterPageParam.getPageSize();
        if(start < 0){
            start = 0;
        }

        query.setFirstResult(start);
        query.setMaxResults(filterPageParam.getPageSize() + 1);
        List list = query.getResultList();
        PaginatedList<T> result = new PaginatedList<>();

        if(list.size() > filterPageParam.getPageSize()){
            result.setHasNext(true);
            list.remove(list.size() - 1);
        }

        result.addAll(list);
        result.setPageSizeResult(list.size());

        result.setTotalResults(executeCountQuery(filterPageParam.getParams()));

        return result;
    }

    protected int executeCountQuery(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(obj) FROM "+entityClass.getSimpleName()+" obj ");
        for(String key : params.keySet()){
            if(sql.indexOf("WHERE") == -1){
                sql.append(" WHERE ");
            } else {
                sql.append(" AND ");
            }

            sql.append("obj.").append(key).append(" = :").append(key);
        }
        TypedQuery<Long> countQuery = entityManager.createQuery(sql.toString(), Long.class);

        setParametersOnQuery(countQuery, params);

        return countQuery.getSingleResult().intValue();
    }

    protected void setParametersOnQuery(Query query, Map<String, Object> setParams){
        if((setParams != null) && !setParams.isEmpty()){
            for (Map.Entry<String, Object> entry : setParams.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void appendOrderBy(StringBuilder sb, List<String> fields, Map<String, String> validFields){
        if(fields != null && !fields.isEmpty() && validFields != null && !validFields.isEmpty()){
            boolean firstOrder = true;

            for(String s : fields){
                boolean desc = s.startsWith("-");
                if(s.startsWith("+") || s.startsWith("-")){
                    s = s.substring(1);
                }
                String value = validFields.get(s);
                if(value != null){
                    if(firstOrder){
                        sb.append(" ORDER BY ").append(value);
                        firstOrder = false;
                    } else {
                        sb.append(", ").append(value);
                    }
                    if(desc){
                        sb.append(" DESC ");
                    }
                }
            }
        }
    }
}
