package com.foamtec.mps.repository;

import com.foamtec.mps.model.AppUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AppUserRepositoryImpl implements AppUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AppUser> findAll() {
        return entityManager.createQuery("SELECT a from AppUser a").getResultList();
    }

    public AppUser findById(Long id) {
        return entityManager.find(AppUser.class, id);
    }

    public AppUser findByUsername(String username) {
        Query query = entityManager.createQuery("SELECT a FROM AppUser a WHERE a.username = :username");
        query.setParameter("username", username);
        AppUser appUser = null;
        try {
            appUser = (AppUser) query.getSingleResult();
        } catch (NoResultException nre) {}
        return appUser;
    }

    public AppUser findByEmployeeId(String employeeId) {
        Query query = entityManager.createQuery("SELECT a FROM AppUser a WHERE a.employeeId = :employeeId");
        query.setParameter("employeeId", employeeId);
        AppUser appUser = null;
        try {
            appUser = (AppUser) query.getSingleResult();
        } catch (NoResultException nre) {}
        return appUser;
    }

    public AppUser save(AppUser appUser) {
        entityManager.persist(appUser);
        return appUser;
    }

    public AppUser update(AppUser appUser) {
        entityManager.merge(appUser);
        entityManager.flush();
        return appUser;
    }

    @Override
    public List<AppUser> searchUsers(String text) {
        String searchText = "%";
        if(text.length() > 0) {
            searchText = "%" + text + "%";
        }
        Query query = entityManager.createQuery("SELECT a FROM AppUser a WHERE a.firstName LIKE :text OR " +
                "a.lastName LIKE :text OR a.department LIKE :text OR a.employeeId LIKE :text");
        query.setParameter("text", searchText);
        return query.getResultList();
    }

    @Override
    public List<AppUser> searchUsersLimit(String text, int start, int limit) {
        String searchText = "%";
        if(text.length() > 0) {
            searchText = "%" + text + "%";
        }
        Query query = entityManager.createQuery("SELECT a FROM AppUser a WHERE a.firstName LIKE :text OR " +
                "a.lastName LIKE :text OR a.department LIKE :text OR a.employeeId LIKE :text");
        query.setParameter("text", searchText);
        query.setMaxResults(limit);
        query.setFirstResult(start);
        return query.getResultList();
    }
}
