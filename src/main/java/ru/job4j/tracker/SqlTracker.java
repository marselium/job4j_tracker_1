package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {

    private Connection connection;

    public SqlTracker() {
        init();
    }

    public SqlTracker(Connection connection) {
        this.connection = connection;
    }

    private void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("db/liquibase.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Item getItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        Timestamp timestamp = rs.getTimestamp("created");
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setCreated(timestamp.toLocalDateTime());
        return item;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement
                     = this.connection.prepareStatement("INSERT INTO ITEMS(name, created VALUES(?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean result = false;
        try (PreparedStatement statement
                     = this.connection.prepareStatement("update items set name = (?) , created = (?) where  id = (?)")) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.setInt(3, id);
            result = statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement statement
                     = this.connection.prepareStatement("delete from items where  id = (?)")) {
            statement.setInt(1, id);
            result = statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
            Item item;
            List<Item> items = new ArrayList<>();
            try (PreparedStatement st =
                         this.connection.prepareStatement("select * from items")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        item = getItem(rs);
                        items.add(findById(item.getId()));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return items;
    }

    @Override
    public List<Item> findByName(String key) {
        Item item;
        List<Item> items = new ArrayList<>();
        try (PreparedStatement st =
                     this.connection.prepareStatement("select * from items where name = (?)")) {
            st.setString(1, key);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    item = getItem(rs);
                    items.add(findById(item.getId()));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        try (PreparedStatement st =
                     this.connection.prepareStatement("select * from items where id = (?)")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    item = getItem(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return item;
    }
}