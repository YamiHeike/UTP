package com.example.utp.Wave4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import java.util.Locale;

public class Database implements Formattable {
    private TravelData travelData;

    private final String DB_URL;
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "pass";
    private String defaultLocaleCode = "en_GB";
    private String dateFormat = "yyyy-MM-dd";
    private ResourceBundle countries = ResourceBundle.getBundle(ResourceName.COUNTRIES, getDefaultLocale());
    private ResourceBundle dests = ResourceBundle.getBundle(ResourceName.DESTS, getDefaultLocale());

    public Database(String url, TravelData travelData) {
        DB_URL = url;
        this.travelData = travelData;
        create();
    }


    // 1. Create and Populate database

    public void create() {
        //addDb(); //Apparently not needed

        try(Connection conn = getConnection(DB_URL)) {
            addTables(conn);
            insertDataFromBundle(conn, countries, TableName.COUNTRY);
            insertDataFromBundle(conn, dests, TableName.DEST);
            insertTravels(conn);
        } catch (SQLException sqle) {
            sqlError(sqle);
        }
    }

    public List<Travel> findAll() {
        List<Travel> offers = new ArrayList<>();
        String sql = "SELECT t.id, start_date, end_date, price, currency, c.name, d.name FROM travel t\n"
                + "JOIN country c ON country_id = c.id \n"
                + "JOIN dest d ON dest_id = d.id";

        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet records = stmt.executeQuery(sql);) {
            while(records.next()) {
                int id = records.getInt(1);
                Date startDate = records.getDate(2);
                Date endDate = records.getDate(3);
                double price = records.getDouble(4);
                String currency = records.getString(5);
                String country = records.getString(6);
                String dest = records.getString(7);

                Travel travel = Travel.builder()
                        .withId(id)
                        .from(startDate)
                        .to(endDate)
                        .withPrice(price)
                        .inCurrency(currency)
                        .toCountry(country)
                        .withDestination(dest)
                        .build();
                offers.add(travel);
            }
        } catch(SQLException sqle) {
            sqlError(sqle);
        }

        return offers;
    }



    private Connection getConnection(String url) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            System.out.println("Connection created");
            return conn;
        } catch(ClassNotFoundException cnfe) {
            System.out.println("Driver class not found");
            return null;
        }
    }

    // Possibly to be deleted

	/* private void addDb() {
		int delimIdx = DB_URL.lastIndexOf('/') + 1;
		int config = DB_URL.indexOf("?");
		String baseUrl = DB_URL.substring(0, delimIdx);
		String dbName = DB_URL.substring(delimIdx, (config == -1) ? DB_URL.length() : config);
		String sql = "CREATE DATABASE " + dbName;
		String user = "";
		String password = "";
		boolean needsAuth = false;

		if(config != -1 && DB_URL.contains("&")) {
			String userProp = "user=";
			String passwordProp = "password=";
			int paramDelim = DB_URL.indexOf("&");

			int userIdx = DB_URL.indexOf(userProp);
			int passwordIdx = DB_URL.indexOf(passwordProp);

			if(userIdx == -1 || passwordIdx == -1) {
				throw new RuntimeException("Incorrect database URL");
			}

			user = DB_URL.substring(userIdx + userProp.length(), (userIdx > paramDelim) ? DB_URL.length() : paramDelim);
			password = DB_URL.substring(passwordIdx + passwordProp.length(), (passwordIdx > paramDelim) ? DB_URL.length() : paramDelim);


			needsAuth = true;
		}

		System.out.println(user + " " + password);
		try(Connection conn = (needsAuth) ? DriverManager.getConnection(baseUrl, user, password) : getConnection(baseUrl);
				Statement statement = conn.createStatement()) {
				statement.executeUpdate(sql);
				System.out.println("Database " + dbName + " created successfully");
			} catch(SQLException sqle) {
				System.out.println(sqle.getSQLState());
				sqlError(sqle);
			}

	} */

    private void addTables(Connection conn) throws SQLException {

        // Tables
        String createCountry = "CREATE TABLE country ("
                + "id SERIAL NOT NULL, "
                + "name varchar(70) UNIQUE NOT NULL, "
                + "CONSTRAINT country_pk PRIMARY KEY (id)"
                + ")";
        String createDest = "CREATE TABLE dest ("
                + "id SERIAL NOT NULL, "
                + "name varchar(30) UNIQUE NOT NULL, "
                + "CONSTRAINT dest_pk PRIMARY KEY (id) "
                + ")";
        String createTravel = "CREATE TABLE travel ("
                + "id SERIAL NOT NULL, "
                + "start_date date NOT NULL, "
                + "end_date date NOT NULL, "
                + "price decimal(10,2) NOT NULL, "
                + "currency char(3) NOT NULL, "
                + "country_id int NOT NULL, "
                + "dest_id int NOT NULL, "
                + "CONSTRAINT travel_pk PRIMARY KEY (id)"
                + ")";

        // Foreign keys
        String travelCountry = "ALTER TABLE travel ADD CONSTRAINT travel_country "
                + "FOREIGN KEY (country_id) REFERENCES country (id) NOT DEFERRABLE INITIALLY IMMEDIATE";

        String travelDest = "ALTER TABLE travel ADD CONSTRAINT travel_dest "
                + "FOREIGN KEY (dest_id) REFERENCES dest (id) NOT DEFERRABLE INITIALLY IMMEDIATE";

        Statement statement = conn.createStatement();
        statement.addBatch(createCountry);
        statement.addBatch(createDest);
        statement.addBatch(createTravel);
        statement.addBatch(travelCountry);
        statement.addBatch(travelDest);
        statement.executeBatch();
    }


    // Dest and country tables

    private void insertDataFromBundle(Connection conn, ResourceBundle rb, String tableName) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (name) VALUES (?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        conn.setAutoCommit(false);
        try {
            Enumeration<String> keys = rb.getKeys();
            while(keys.hasMoreElements()) {
                String key = keys.nextElement();
                pstmt.setString(1, key);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch(SQLException sqle) {
            conn.rollback();
            throw sqle;
        } finally {
            conn.setAutoCommit(true);
        }
        conn.setAutoCommit(false);
    }

    private void insertTravels(Connection conn) throws SQLException {

        List<String> data = travelData.getOffersDescriptionsList(defaultLocaleCode, dateFormat);

        String sql = "INSERT INTO travel (start_date, end_date, price, currency, country_id, dest_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        conn.setAutoCommit(false);

        try {
            for(String record : data) {
                String[] travel = parseDataRecord(record);

                LocalDate sd = LocalDate.parse(travel[1]);
                LocalDate ed = LocalDate.parse(travel[2]);

                Date startDate = Date.valueOf(sd);
                Date endDate = Date.valueOf(ed);
                Double price = convertNumber(travel[4], getDefaultLocale());
                int countryId = idForName(travel[0], TableName.COUNTRY, conn);
                int destId = idForName(travel[3], TableName.DEST, conn);
                String currency = travel[5];

                pstmt.setDate(1, startDate);
                pstmt.setDate(2, endDate);
                pstmt.setDouble(3, price);
                pstmt.setString(4, currency);
                pstmt.setInt(5, countryId);
                pstmt.setInt(6, destId);

                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch(SQLException sqle) {
            conn.rollback();
            throw sqle;
        } catch(IllegalArgumentException iae) {
            conn.rollback();
            iae.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }

    }

    // 2. GUI

    public void showGui() {
        SwingUtilities.invokeLater(() -> new TravelView(findAll()));
    }

    // 3. Helper methods

    private int idForName(String name, String tableName, Connection conn) throws SQLException {
        String sql = "SELECT id FROM " + tableName + " WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return rs.getInt(1);
        } else return -1;
    }


    private String[] parseDataRecord(String record) {
        int expectedSpaces = 5;
        int countSpaces = (int) record.chars().filter(c -> c == ' ').count();
        int offset = countSpaces - expectedSpaces;

        String replaced = replaceFirst(record, ' ', '-', offset);
        String[] data = replaced.split(" ");

        data[0] = replaceFirst(data[0], '-', ' ', offset);

        return data;
    }

    private String replaceFirst(String wrd, char from, char to, int num) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(char c : wrd.toCharArray()) {
            if(c == from && count < num) {
                sb.append(to);
                count++;
            } else {
                sb.append((char) c);
            }
        }

        return sb.toString();
    }

    private void sqlError(SQLException sqle) {
        System.out.println("SQL Exception. Check the output console");
        sqle.printStackTrace();
    }


    private Locale getDefaultLocale() {
        if(!defaultLocaleCode.contains("_")) {
            System.out.println("Incorrect code format");
            return null;
        }
        String[] codes = defaultLocaleCode.split("_");
        return new Locale(codes[0], codes[1]);
    }

}
