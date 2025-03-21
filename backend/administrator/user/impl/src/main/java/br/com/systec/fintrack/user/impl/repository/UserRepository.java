package br.com.systec.fintrack.user.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.user.model.User;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends AbstractRepository<User, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByLogin(String username) throws ObjectNotFoundException {
        TypedQuery<User> query = entityManager.createQuery("select obj from User obj where obj.username = :username", User.class);
        query.setParameter("username", username);

        List<User> listOfResult = query.getResultList();

        if(listOfResult.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfResult.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByLoginOrEmail(String username, String email) {
        TypedQuery<User> query = entityManager.createQuery("select obj from User obj where obj.username = :username or obj.email = :email", User.class);
        query.setParameter("username", username);
        query.setParameter("email", email);
        query.setMaxResults(1);

        List<User> listOfUser = query.getResultList();

        if (listOfUser.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfUser.get(0));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllByTenant() {
        TypedQuery<User> query = entityManager.createQuery("select obj from User obj where obj.tenantId = :tenantId", User.class);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findUserPrincipal() {
        String sql = "select obj from User obj where obj.userPrincipalTenant = :hasPrincipal and obj.tenantId = :tenantId";

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter("hasPrincipal", true);
        query.setParameter("tenantId", TenantContext.getTenant());

        List<User> listOfUser = query.getResultList();

        if(listOfUser.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfUser.get(0));
    }
}


