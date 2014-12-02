package com.cinco;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String delete = "DELETE FROM Consultations;";
            ps = conn.prepareStatement(delete);
            ps.execute();
            String deleteQuery = "DELETE FROM Persons;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
	}

	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Persons WHERE Persons.PersonCode = " + personCode + ";";
            ps = conn.prepareStatement(deleteQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Method to add a person record to the database with the provided data. 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String personQuery = "INSERT INTO Persons (PersonCode, LastName, FirstName, Address) VALUES (?, ?, ?, ?);";
            ps = conn.prepareStatement(personQuery);
            ps.setString(1, personCode);
            ps.setString(2, lastName);
            ps.setString(3, firstName);
            ps.setString(4, street + "," + city + "," + state + ", " + zip + ", " + country);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }
    public static void removeAllEmail() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Emails;";
            ps = conn.prepareStatement(deleteQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String emailQuery = "INSERT INTO Emails (PersonCode, EmailAddress) VALUES (?, ?);";
            ps = conn.prepareStatement(emailQuery);
            ps.setString(1, personCode);
            ps.setString(2, email);
            String updateQuery = "UPDATE Emails, Persons SET Emails.PersonID = Persons.PersonID WHERE Emails.PersonCode = Persons.PersonCode;";
            ps.executeUpdate(updateQuery);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Customers;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
               if(conn != null) {
                   conn.close();
               }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	public static void addCustomer(String customerCode, String type, String primaryContactPersonCode, String name, String street, String city, String state, String zip, String country) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String customerQuery = "INSERT INTO Customers (CustomerCode, CustomerType, PrimaryContact, OrganizationName, Address) values (?, ?, ?, ?, ?);";
            ps = conn.prepareStatement(customerQuery);
            ps.setString(1, customerCode);
            ps.setString(2, type);
            ps.setString(3, primaryContactPersonCode);
            ps.setString(4, name);
            ps.setString(5, street + ", " + city + ", " + state + ", " + zip + ", " + country);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Products;";
            ps = conn.prepareStatement(deleteQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * Removes a particular product record from the database corresponding to the
	 * provided <code>productCode</code>
	 * @paramassetCode
	 */
	public static void removeProduct(String productCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Products WHERE Products.ProductCode = " + productCode + ";";
            ps = conn.prepareStatement(deleteQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addProduct(String productCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String equipmentQuery = "INSERT INTO Products (ProductCode) VALUES (?);";
            ps = conn.prepareStatement(equipmentQuery);
            ps.setString(1, productCode);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeEquipment() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Equipment;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * Adds an equipment record to the database with the
	 * provided data.  
	 */
	public static void addEquipment(String productCode, String name, Double pricePerUnit) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String equipmentQuery = "INSERT INTO Equipment (ProductCode, ProductName, ProductPrice) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(equipmentQuery);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.setDouble(3, pricePerUnit);
            ps.executeUpdate();
            String updateQuery = "UPDATE Equipment, Products SET Equipment.ProductID = Products.ProductID WHERE Equipment.ProductCode = Products.ProductCode;";
            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeLicenses() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Licenses;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Adds an license record to the database with the
	 * provided data.  
	 */
	public static void addLicense(String productCode, String name, double serviceFee, double annualFee) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String licenseQuery = "INSERT INTO Licenses (ProductCode, ProductName, ServiceFee, AnnualPrice) VALUES (?, ?, ?, ?);";
            ps = conn.prepareStatement(licenseQuery);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.setDouble(3, serviceFee);
            ps.setDouble(4, annualFee);
            ps.executeUpdate();
            String updateQuery = "UPDATE Licenses, Products SET Licenses.ProductID = Products.ProductID WHERE Licenses.ProductCode = Products.ProductCode;";
            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeConsultations() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Consultations;";
            ps = conn.prepareStatement(deleteQuery);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * Adds an consultation record to the database with the
	 * provided data.  
	 */
	public static void addConsultation(String productCode, String name, String consultantPersonCode, Double hourlyFee) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String consultationQuery = "INSERT INTO Consultations (ProductCode, ProductName, ConsultantCode, pricePerHour) VALUES (?, ?, ?, ?);";
            ps = conn.prepareStatement(consultationQuery);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.setString(3, consultantPersonCode);
            ps.setDouble(4, hourlyFee);
            ps.executeUpdate();
            String updateQuery = "UPDATE Consultations, Products SET Consultations.ProductID = Products.ProductID WHERE Consultations.ProductCode = Products.ProductCode;";
            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteInvoicePro = "DELETE FROM InvoiceProducts;";
            ps = conn.prepareStatement(deleteInvoicePro);
            ps.execute();
            String deleteQuery = "DELETE FROM Invoices;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
	
	/**
	 * Removes the invoice record from the database corresponding to the
	 * provided <code>invoiceCode</code>
	 * @param invoiceCode
	 */
	public static void removeInvoice(String invoiceCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String deleteQuery = "DELETE FROM Invoices WHERE Invoices.InvoiceCode = " + invoiceCode + ";";
            ps = conn.prepareStatement(deleteQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Adds an invoice record to the database with the given data.  
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String invoiceQuery = "INSERT INTO Invoices (InvoiceCode, CustomerCode, SalesPersonCode) VALUES (?, ?, ?);";
            ps = conn.prepareStatement(invoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, customerCode);
            ps.setString(3, salesPersonCode);
            ps.executeUpdate();
//            String updateQuery = "UPDATE Invoices, Customers, Persons SET Invoices.PersonID = Persons.PersonID, Invoices.CustomerCode = Customers.CustomerCode, Invoices.CustomersID = Customers.CustomersID WHERE Invoices.SalesPersonCode = Customers.PrimaryContact;";
//            ps.executeUpdate(updateQuery);
//            updateQuery = "UPDATE Invoices, Persons SET Invoices.PersonID = Persons.PersonID WHERE Invoices.SalesPersonCode = Persons.PersonCode;";
//            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of units
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String productCode, int numUnits) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String equipmentInvoiceQuery = "INSERT INTO InvoiceProducts (InvoiceCode, ProductCode, Quantity) VALUES (?, ?, ?);";
            ps = conn.prepareStatement(equipmentInvoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setDouble(3, numUnits);
            ps.execute();
//            String updateQuery = "UPDATE InvoiceProducts, Products SET InvoiceProducts.ProductID = Products.ProductID WHERE InvoiceProducts.ProductCode = Products.ProductCode;";
//            ps.executeUpdate(updateQuery);
//            updateQuery = "UPDATE InvoiceProducts, Invoices SET InvoiceProducts.InvoiceID = Invoices.InvoiceID WHERE InvoiceProducts.InvoiceCode = Invoices.InvoiceCode;";
//            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * begin/end dates
	 */
	public static void addLicenseToInvoice(String invoiceCode, String productCode, String startDate, String endDate) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String equipmentInvoiceQuery = "INSERT INTO InvoiceProducts (InvoiceCode, ProductCode, Quantity) VALUES (?, ?, ?);";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date start = null;
            try {
                start = df.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date end = null;
            try {
                end = df.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double quantity = (Days.daysBetween(new LocalDate(start), new LocalDate(end)).getDays());
            ps = conn.prepareStatement(equipmentInvoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setDouble(3, quantity);
            ps.executeUpdate();
//            String updateQuery = "UPDATE InvoiceProducts, Products SET InvoiceProducts.ProductID = Products.ProductID WHERE InvoiceProducts.ProductCode = Products.ProductCode;";
//            ps.executeUpdate(updateQuery);
//            updateQuery = "UPDATE InvoiceProducts, Invoices SET InvoiceProducts.InvoiceID = Invoices.InvoiceID WHERE InvoiceProducts.InvoiceCode = Invoices.InvoiceCode;";
//            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of billable hours.
	 */
	public static void addConsultationToInvoice(String invoiceCode, String productCode, double numHours) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            String consultationInvoiceQuery = "INSERT INTO InvoiceProducts (InvoiceCode, ProductCode, Quantity) VALUES (?, ?, ?);";
            ps = conn.prepareStatement(consultationInvoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setDouble(3, numHours);
            ps.executeUpdate();
//            String updateQuery = "UPDATE InvoiceProducts, Products SET InvoiceProducts.ProductID = Products.ProductID WHERE InvoiceProducts.ProductCode = Products.ProductCode;";
//            ps.executeUpdate(updateQuery);
//            updateQuery = "UPDATE InvoiceProducts, Invoices SET InvoiceProducts.InvoiceID = Invoices.InvoiceID WHERE InvoiceProducts.InvoiceCode = Invoices.InvoiceCode;";
//            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
