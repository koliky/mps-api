package com.foamtec.mps.repository;

import com.foamtec.mps.model.AppUser;

import java.util.List;

public interface AppUserRepository {
    List<AppUser> findAll();
    AppUser findById(Long id);
    AppUser findByUsername(String username);
    AppUser findByEmployeeId(String employeeId);
    AppUser save(AppUser appUser);
    AppUser update(AppUser appUser);
    List<AppUser> findAllLimit(int start, int limit);
}
