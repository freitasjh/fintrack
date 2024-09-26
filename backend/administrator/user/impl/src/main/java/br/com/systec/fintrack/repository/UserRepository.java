package br.com.systec.fintrack.repository;

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
    public User findByLogin(String username) throws ObjectNotFoundException {
        StringBuilder sql = new StringBuilder();

        sql.append("select obj from User obj where obj.username = :username");

        TypedQuery<User> query = entityManager.createQuery(sql.toString(), User.class);
        query.setParameter("username", username);

        List<User> listOfResult = query.getResultList();

        if(listOfResult.isEmpty()){
            throw new ObjectNotFoundException("Usuario não encontrado");
        }

        return listOfResult.get(0);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByLoginOrEmail(String username, String email) {
        StringBuilder sql = new StringBuilder();
        sql.append("select obj from User obj where obj.username = :username or obj.email = :email");

        TypedQuery<User> query = entityManager.createQuery(sql.toString(), User.class);
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
        StringBuilder sql = new StringBuilder();
        sql.append("select obj from User obj where obj.tenantId = :tenantId");

        TypedQuery<User> query = entityManager.createQuery(sql.toString(), User.class);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }
}
