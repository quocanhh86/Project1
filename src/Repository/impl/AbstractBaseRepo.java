package Repository.impl;

import Repository.BaseRepo;
import Repository.DbConnection;
import Utils.DatabaseUtils;
import annotation.Column;
import annotation.Id;
import annotation.Table;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @param <T>
 * @param <ID>
 * @author Admin
 */
public class AbstractBaseRepo<T, ID> implements BaseRepo<T, ID> {

    protected Connection connection = null;
    private final Logger log = Logger.getLogger(AbstractBaseRepo.class.getName());
    private Class<T> clazz;

    public AbstractBaseRepo() {
        connection = DbConnection.getConnection();
        clazz = (Class<T>) ((java.lang.reflect.ParameterizedType)
            this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ".concat(getTableName()));
        query.append(DatabaseUtils.getColumnString(getTableName()));
        query.append(" VALUES(");

        try {
            List<String> columnList = getColumns();
            Field[] fields = entity.getClass().getDeclaredFields();
            System.out.println(fields.length);
            List<Field> entityField = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation == null) {
                    continue;
                }
                String columnName = columnAnnotation.name();
                if (columnName.isEmpty()) {
                    columnName = field.getName();
                }
                Id idAnnotation = field.getAnnotation(Id.class);
                if (idAnnotation == null) {
                    // Thêm tên cột vào danh sách
                    columnList.add(columnName);
                    entityField.add(field);
                    query.append("?,");
                }
            }
            query.deleteCharAt(query.lastIndexOf(","));
            query.append(")");
            PreparedStatement pstm = this.connection.prepareStatement(query.toString());
            String[] params = new String[entityField.size()];
            if (!entityField.isEmpty()) {
                for (int i = 0, j = 1; i < entityField.size(); i++, j++) {
                    Field field = entityField.get(i);
                    setParam(pstm, field.get(entity), j);
                    params[i] = String.valueOf(field.get(entity));
                }
                if(pstm.executeUpdate() > 0) {
                    DatabaseUtils.printQueryLog(query.toString(), params);
                }

            } else {
                throw new RuntimeException("Entity field is empty");
            }
        } catch (SQLException | IllegalAccessException ex) {
            log.severe(ex.getMessage());
        }
        return entity;
    }


    @Override
    public T update(ID id, T entity) {
        T entityFromDb = findById(id).orElse(null);
        if (entityFromDb == null) {
            throw new RuntimeException("Entity not found");
        }
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ".concat(getTableName()));
        query.append(" SET ");
        try {
            List<String> columnList = getColumns();
            Field[] fields = entity.getClass().getDeclaredFields();
            List<Field> entityField = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = columnAnnotation.name();
                if (columnName.isEmpty()) {
                    columnName = field.getName();
                }
                Id idAnnotation = field.getAnnotation(Id.class);
                if (idAnnotation == null) {
                    // Thêm tên cột vào danh sách
                    columnList.add(columnName);
                    entityField.add(field);
                    query.append(columnName).append(" = ?,");
                }
            }
            query.deleteCharAt(query.lastIndexOf(","));
            query.append(" WHERE ").append(getTableName()).append("ID = ?");
            PreparedStatement pstm = this.connection.prepareStatement(query.toString());
            String[] params = new String[entityField.size() + 1];
            if (!entityField.isEmpty()) {
                for (int i = 0, j = 1; i < entityField.size(); i++, j++) {
                    Field field = entityField.get(i);
                    setParam(pstm, field.get(entity), j);
                    params[i] = String.valueOf(field.get(entity));
                }
                setParam(pstm, id, entityField.size() + 1);
                params[entityField.size()] = String.valueOf(id);
                if(pstm.executeUpdate() > 0) {
                    DatabaseUtils.printQueryLog(query.toString(), params);
                }
            } else {
                throw new RuntimeException("Entity field is empty");
            }
        } catch (SQLException | IllegalAccessException ex) {
            log.severe(ex.getMessage());
        }
        return entity;
    }

    @Override
    public void deleteById(ID id) {
        String query = "DELETE FROM " + getTableName() + " WHERE " + getTableName() + "ID = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setObject(1, id);
            if(pstm.executeUpdate() > 0) {
                DatabaseUtils.printQueryLog(query, String.valueOf(id));
            }
        } catch (SQLException ex) {
            log.severe(ex.getMessage());
        }
    }

    @Override
    public void delete(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(Id.class) != null) {
                try {
                    deleteById((ID) field.get(entity));
                } catch (IllegalAccessException ex) {
                    log.severe(ex.getMessage());
                }
            }
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + getTableName() + "ID = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setObject(1, id);
            ResultSet rs = pstm.executeQuery();
            DatabaseUtils.printQueryLog(query, String.valueOf(id));
            if (rs.next()) {
                T entity = clazz.newInstance();
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    String columnName = columnAnnotation.name();
                    field.set(entity, rs.getObject(columnName));
                }
                return Optional.of(entity);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            log.severe(ex.getMessage());
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            DatabaseUtils.printQueryLog(query);
            while (rs.next()) {
                T entity = clazz.newInstance();
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    String columnName = columnAnnotation.name();
                    if (columnName.isEmpty()) {
                        columnName = field.getName();
                    }
                    field.set(entity, rs.getObject(columnName));
                }
                entities.add(entity);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            log.severe(ex.getMessage());
            ex.printStackTrace();
        }
        return entities;
    }

    private List<String> getColumns() throws SQLException {
        System.out.println("Column size : " + DatabaseUtils.getColumns(getTableName()).size());
        return DatabaseUtils.getColumns(getTableName());
    }

    private String getTableName() {
        return clazz.getAnnotation(Table.class).name();
    }

    private void setParam(PreparedStatement pstm, Object value, int index) throws SQLException {
        if (value instanceof String) {
            pstm.setString(index, (String) value);
        } else if (value instanceof Integer) {
            pstm.setInt(index, (Integer) value);
        } else if (value instanceof Date) {
            pstm.setDate(index, (Date) value);
        } else if (value instanceof Double) {
            pstm.setDouble(index, (Double) value);
        } else if (value instanceof Long) {
            pstm.setLong(index, (Long) value);
        } else if (value instanceof Float) {
            pstm.setFloat(index, (Float) value);
        } else if (value instanceof Boolean) {
            pstm.setBoolean(index, (Boolean) value);
        } else {
            pstm.setObject(index, value);
        }
    }
}
