package br.com.systec.controle.financeiro.commons;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
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

    @Transactional(propagation = Propagation.SUPPORTS)
    public int nextCod(){
        var sql = "select max(cast(obj.code as int)) from "+entityClass.getSimpleName()+" as obj";

        var code = (Integer) entityManager
                .createQuery(sql)
                .getSingleResult();

        code = code == null || code == 0 ? 1 : code++;

        return code.intValue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S save(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S update(S entity){
        entityManager.merge(entity);
        return entity;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(save(entity)));
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> Iterable<S> updateAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(update(entity)));
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<T> findByCod(String cod){
        String sql = "SELECT o FROM "+entityClass.getSimpleName()+" o WHERE o.codigo = :codigo";

        T entity = entityManager.createQuery(sql,entityClass)
                .setParameter("codigo", cod)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Iterable<T> findAll() {
        return entityManager.createQuery("SELECT o FROM "+entityClass.getSimpleName()+" o", entityClass).getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<T> findAllByTenantId() {
        TypedQuery<T> query = entityManager.createQuery("select obj from "+entityClass.getSimpleName()+" obj where obj.tenantId = :tenantId",entityClass);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> result = new ArrayList<>();
        ids.forEach(id -> {
            Optional<T> obj = findById(id);
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
        T entity = getOne(id);
        delete(entity);
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
        deleteAll(findAll());
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
        Optional<T> oEntitu = findById(id);
        return oEntitu.orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNativeQuery(String nativeQuery) {
        entityManager.createNativeQuery(nativeQuery).executeUpdate();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
//	protected PaginatedList<T> executePaginatedQuery(Query query, Map<String, Object> setParams, int page, int pageSize){
//
//        setParametersOnQuery(query, setParams);
//
//        int start = (page - 1) * pageSize;
//        if(start < 0){
//            start = 0;
//        }
//        query.setFirstResult(start);
//        query.setMaxResults(pageSize + 1);
//        List list = query.getResultList();
//        PaginatedList<T> result = new PaginatedList<>();
//        if(list.size() > pageSize){
//            result.setHasNext(true);
//            list.remove(list.size() - 1);
//        }
//        result.addAll(list);
//
//        return result;
//    }

    protected Query setParametersOnQuery(Query query, Map<String, Object> setParams){
        if((setParams != null) && !setParams.isEmpty()){
            for(String key : setParams.keySet()){
                Object object = setParams.get(key);
                query.setParameter(key, object);
            }
        }

        return query;
    }

    protected void appedOrderBy(StringBuilder sb, List<String> fields, Map<String, String> validFields){
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
