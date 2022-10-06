package com.example.assignmentdatabase.data_access.customer;

import com.example.assignmentdatabase.data_access.connectivity.DatabaseConnectionFactory;
import com.example.assignmentdatabase.models.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository{

    private final DatabaseConnectionFactory connectionFactory;

    public CustomerRepositoryImpl(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    //1. read all customers in the database
    public ArrayList<Customer> selectAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();

        try(Connection connection = connectionFactory.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustomerId,FirstName,LastName,Phone,Email,Country,PostalCode FROM customer");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customers.add(new Customer(
                        resultSet.getString("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("postalCode")


                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //2. Read specific customer from database by id
    public Customer selectSpecificCustomerById(int customerId){
        Customer customer = null;

        try (Connection connection = connectionFactory.getConnection()){
            //connection

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustomerId,FirstName,LastName,Phone,Email,Country,PostalCode FROM customer WHERE CustomerId = ?");
            preparedStatement.setString(1, String.valueOf(customerId));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customer = new Customer(
                        resultSet.getString("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("postalCode")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    //3. Read specific customer by name
    public Customer selectCustomerByName(String firstName){
        Customer customer = null;

        try (Connection connection = connectionFactory.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustomerId,FirstName,LastName,Phone,Email,Country,PostalCode FROM customer WHERE FirstName LIKE ? ");

            preparedStatement.setString(1, "%" + firstName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customer = new Customer(
                        resultSet.getString("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("postalCode")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    //4.
    public ArrayList<Customer> selectPageOfCustomers(String limit, String offset){

        ArrayList<Customer> customers = new ArrayList<Customer>();

        try (Connection connection = connectionFactory.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustomerId,FirstName,LastName," +
                    "Phone,Email,Country,PostalCode FROM customer LIMIT ? OFFSET ?");
            preparedStatement.setString(1, limit);
            preparedStatement.setString(2, offset);

            ResultSet  resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customers.add(new Customer(
                        resultSet.getString("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("postalCode")

                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Boolean addCustomer(Customer customer) {
        Boolean success = false;
        try (Connection connection = connectionFactory.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (CustomerId, FirstName, LastName, Phone, Email, Country, PostalCode) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1,customer.getCustomerId());
            preparedStatement.setString(2,customer.getFirstName());
            preparedStatement.setString(3,customer.getLastName());
            preparedStatement.setString(4,customer.getPhone());
            preparedStatement.setString(5,customer.getEmail());
            preparedStatement.setString(6,customer.getCountry());
            preparedStatement.setString(7,customer.getPostalCode());

            int result = preparedStatement.executeUpdate();
            success = (result !=0);
            System.out.println("Add Customer Successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public Boolean updateCustomer(Customer customer){
        boolean success = false;
        try (Connection connection = connectionFactory.getConnection()){
            // Connect to DB

            // Make SQL query
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Customer SET FirstName = ?, LastName = ?, Phone = ?, Email = ?, Country= ?, PostalCode= ? WHERE CustomerId = ?");
            preparedStatement.setString(1,customer.getFirstName());
            preparedStatement.setString(2,customer.getLastName());
            preparedStatement.setString(3,customer.getPhone());
            preparedStatement.setString(4,customer.getEmail());
            preparedStatement.setString(5,customer.getCountry());
            preparedStatement.setString(6,customer.getPostalCode());
            preparedStatement.setString(7,customer.getCustomerId());

            // Execute Query
            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            if(success){
                System.out.println("Update customer successfully");
            }else{
                System.out.println("Cannot update customer");
            }
        }
        catch (Exception exception){
            System.out.println("Exception");
        }
        return success;
    }

    public ArrayList<CustomerCountry> returnCustomerCountry(){
        ArrayList<CustomerCountry> customerCountry = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Country, COUNT(CustomerId) AS numberOfCustomers FROM customer GROUP BY Country ORDER BY numberOfCustomers DESC");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customerCountry.add(new CustomerCountry(
                        resultSet.getString("country"),
                        resultSet.getInt("numberOfCustomers")


                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCountry;
    }

    public ArrayList<CustomerSpender> returnCustomerSpender(){
        ArrayList<CustomerSpender> customerSpender = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection()){

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustomerId, sum(Total) as TotalInvoice FROM Invoice GROUP BY CustomerId ORDER BY TotalInvoice DESC");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customerSpender.add(new CustomerSpender(
                        resultSet.getString("customerId"),
                        resultSet.getDouble("TotalInvoice")


                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerSpender;
    }


    public ArrayList<CustomerGenre> getCustomerGenre(){
        ArrayList<CustomerGenre> customerGenres = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Customer.CustomerId, G.Name, count(T.TrackId) FROM Customer\n" +
                    "    JOIN Invoice I ON Customer.CustomerId = I.CustomerId\n" +
                    "    JOIN InvoiceLine IL ON I.InvoiceId = IL.InvoiceId\n" +
                    "    JOIN Track T ON IL.TrackId = T.TrackId\n" +
                    "    JOIN Genre G ON T.GenreId = G.GenreId\n" +
                    "WHERE I.CustomerId = 1 \n" +
                    "GROUP BY Customer.CustomerId, G.Name");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customerGenres.add(new CustomerGenre(
                        resultSet.getString("Name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerGenres;
    }

    public ArrayList<Artist> getFiveRandomArtist() {
        ArrayList<Artist> artistList = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection()){
            //connection

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Artist.Name FROM Artist ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                artistList.add(new Artist(
                        resultSet.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artistList;

    }

    public ArrayList<Songs> getFiveRandomSongs() {
        ArrayList<Songs> songsList = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection()){
            //connection

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Track.Name FROM Track ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                songsList.add(new Songs(
                        resultSet.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songsList;

    }

    public ArrayList<Genres> getFiveRandomGenres() {
        ArrayList<Genres> genresList = new ArrayList<>();

        try(Connection connection = connectionFactory.getConnection()) {
            //connection

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Genre.Name FROM Genre ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genresList.add(new Genres(
                        resultSet.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genresList;

    }



}
