/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */
public interface BaseRepo <T, ID> {
    T save(T entity) throws SQLException;
    
    T update(ID id, T entity);
    
    void deleteById(ID id);
    
    void delete(T entity);
    
    Optional<T> findById(ID id);
    
    List<T> findAll();
}
