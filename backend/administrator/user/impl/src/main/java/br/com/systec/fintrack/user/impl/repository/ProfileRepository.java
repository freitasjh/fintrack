package br.com.systec.fintrack.user.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.user.model.Profile;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepository extends AbstractRepository<Profile, Long> {

    public Optional<Profile> findProfile(String profile) {
        TypedQuery<Profile> query = entityManager.createQuery("select obj from Profile obj where obj.name = :name", Profile.class);

        query.setParameter("name", profile.toUpperCase());

        List<Profile> listOfResult = query.getResultList();

        if (listOfResult.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(listOfResult.get(0));
    }
}
