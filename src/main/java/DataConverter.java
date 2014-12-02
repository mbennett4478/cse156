import com.cinco.InvoiceData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * Created by TheWoz on 9/16/14.
 */
public class DataConverter {
    private static MyArrayList<Product> products = new MyArrayList<Product>();//
    private static MyArrayList<Equipment> equipments = new MyArrayList<Equipment>();//
    private static MyArrayList<Licenses> licenses = new MyArrayList<Licenses>();//
    private static MyArrayList<Consultant> consultants = new MyArrayList<Consultant>();//
    private static MyArrayList<Persons> persons = new MyArrayList<Persons>();//
    private static MyArrayList<Customer> customers = new MyArrayList<Customer>();//
    private static MyArrayList<CustomerGovernment> customerG = new MyArrayList<CustomerGovernment>();//
    private static MyArrayList<CustomerCorp> customerC = new MyArrayList<CustomerCorp>();//
    private static MyArrayList<Invoices> invoices = new MyArrayList<Invoices>();
//    private static MyArrayList<Integer> test = new MyArrayList<Integer>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void parseCustomerFile(File f) {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
//            PreparedStatement ps = null;
//            ResultSet rs = null;
            Scanner customerInput =  new Scanner(f);
            customerInput.nextLine();
            InvoiceData.removeAllCustomers();
            while(customerInput.hasNext()){
                String holder = customerInput.nextLine();
                if(!holder.trim().isEmpty()) {
                    String array[] = holder.split(";");
                    String customerCode = array[0];
                    String customerType = array[1];
                    String personCode = array[2];
                    String name = array[3];
                    String addressWhole = array[4].replace(", ", ",");
                    String addressHolder[] = addressWhole.split(",");
                    String street = addressHolder[0];
                    String city = addressHolder[1];
                    String state = addressHolder[2];
                    String zip = addressHolder[3];
                    String country = addressHolder[4];
                    InvoiceData.addCustomer(customerCode, customerType, personCode, name, street, city, state, zip, country);
                }
            }
            customerInput.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void parsePersonFile(File f) {
        try {
            Scanner personsInput = new Scanner(f);
            personsInput.nextLine();
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            PreparedStatement ps = null;
            InvoiceData.removeAllPersons();
            while(personsInput.hasNext()) {
                String holder = personsInput.nextLine();
                if(!holder.trim().isEmpty()) {
                    String array[] = holder.split(";");
                    String personCode = array[0].replace(" ", "");
                    String nameHolder[] = array[1].replace(" ", "").split(",");
                    String lastName = nameHolder[0];
                    String firstName = nameHolder[1];
                    String addressHolder[] = array[2].replace(", ", ",").split(",");
                    String street = addressHolder[0];
                    String city = addressHolder[1];
                    String state = addressHolder[2];
                    String zip = addressHolder[3];
                    String country = addressHolder[4];
                    try {
                        if(array.length > 3) {
                            String email = array[3].replace(", ", ",");
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
                            String personQuery = "INSERT INTO Persons (PersonCode, LastName, FirstName, Address, Emails) VALUES (?, ?, ?, ?, ?);";
                            ps = conn.prepareStatement(personQuery);
                            ps.setString(1, personCode);
                            ps.setString(2, lastName);
                            ps.setString(3, firstName);
                            ps.setString(4, street + "," + city + "," + state + "," + zip + "," + country);
                            ps.setString(5, email);
                            ps.executeUpdate();
                        } else {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
                            String personQuery = "INSERT INTO Persons (PersonCode, LastName, FirstName, Address) VALUES (?, ?, ?, ?);";
                            ps = conn.prepareStatement(personQuery);
                            ps.setString(1, personCode);
                            ps.setString(2, lastName);
                            ps.setString(3, firstName);
                            ps.setString(4, street + ", " + city + ", " + state + ", " + zip + ", " + country);
                            ps.executeUpdate();
                        }
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
            }
            personsInput.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	public static void parseProductData(File f) {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
        try {
            Scanner productsInput = new Scanner(f);
            productsInput.nextLine();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            InvoiceData.removeConsultations();
            InvoiceData.removeEquipment();
            InvoiceData.removeLicenses();
            InvoiceData.removeAllProducts();
            while(productsInput.hasNext()) {
                String holder = productsInput.nextLine();
                if(!holder.trim().isEmpty()) {
                    String array[] = holder.split(";");
                    String code = array[0];
                    String identifier = array[1];
                    String name = array[2];
                    InvoiceData.addProduct(code);
                    if(identifier.equals("E")) {
                        double pricePerUnit = Double.parseDouble(array[3]);
                        InvoiceData.addEquipment(code, name, pricePerUnit);
                    } else if(identifier.equals("L")) {
                        double serviceFee = Double.parseDouble(array[3]);
                        double annualLicenseFee = Double.parseDouble(array[4]);
                        InvoiceData.addLicense(code, name, serviceFee, annualLicenseFee);
                    } else if(identifier.equals("C")){
                        String personCode = array[3];
                        double hourlyFee = Double.parseDouble(array[4]);
                        InvoiceData.addConsultation(code, name, personCode, hourlyFee);
                    }
                }
            }
            productsInput.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				conn.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    public static void parseInvoices(File f) {
        try {
            Scanner invoicesInput = new Scanner(f);
            invoicesInput.nextLine();
            InvoiceData.removeAllInvoices();
            while(invoicesInput.hasNext()) {
                String holder = invoicesInput.nextLine();
                if (!holder.trim().isEmpty()) {
                    String split[] = holder.split(";");
                    String invoiceCode = split[0];
                    String customerCode = split[1];
                    String salesPersonCode = split[2];
                    InvoiceData.addInvoice(invoiceCode, customerCode, salesPersonCode);
                    Connection conn = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    String productHolders[] = split[3].split(",");
                    for (int i = 0; i < productHolders.length; i++) {
                        String productHolderSplit[] = productHolders[i].split(":");
                        String productCode = productHolderSplit[0];
                    	try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
                            String equipmentQuery = "SELECT ProductCode FROM Equipment;";
                            ps = conn.prepareStatement(equipmentQuery);
                            rs = ps.executeQuery();
                            
                            while (rs.next()) {
                                String equipmentProduct = rs.getString("ProductCode");
                                if(equipmentProduct.equals(productCode)) {
                                    int amount = Integer.parseInt(productHolderSplit[1]);
                                    InvoiceData.addEquipmentToInvoice(invoiceCode, productCode, amount);
                                    break;
                                }
                            }
                    	} catch(SQLException se) {
                    		se.printStackTrace();
                    	} finally {
                    		ps.close();
                    		rs.close();
                    	}
                    	try {
                            String licenseQuery = "SELECT ProductCode FROM Licenses;";
                            ps = conn.prepareStatement(licenseQuery);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                String licenseProduct = rs.getString("ProductCode");
                                if(licenseProduct.equals(productCode)) {
                                    InvoiceData.addLicenseToInvoice(invoiceCode, productCode, productHolderSplit[1], productHolderSplit[2]);
                                    break;
                                }
                            }
                    	} catch(SQLException se) {
                    		se.printStackTrace();
                    	} finally {
                    		ps.close();
                    		rs.close();
                    	}
                    	try {
                            String consultantQuery = "SELECT ProductCode FROM Consultations;";
                            ps = conn.prepareStatement(consultantQuery);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                String consultantProduct = rs.getString("ProductCode");
                                if(consultantProduct.equals(productCode)) {
                                    double amount = Double.parseDouble(productHolderSplit[1]);
                                    InvoiceData.addConsultationToInvoice(invoiceCode, productCode, amount);
                                    break;
                                }
                            }
                    	} catch(SQLException se) {
                    		se.printStackTrace();
                    	} finally {
                    		ps.close();
                    		rs.close();
                    	}
                    }
	            }
	        }
            invoicesInput.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        try {
//            parsePersonFile(new File("data/Persons.dat"));
//            parseCustomerFile(new File("data/Customers.dat"));
//            parseProductData(new File("data/Products.dat"));
//            parseInvoices(new File("data/Invoices.dat"));

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DataInfo.url, DataInfo.username, DataInfo.password);
            PreparedStatement ps = null;
            PreparedStatement pre = null;
            ResultSet rs = null;
            ResultSet res = null;
            String equipmentQuery = "SELECT ProductCode, ProductName, ProductPrice FROM Equipment;";
            String licensesQuery = "SELECT ProductCode, ProductName, ServiceFee, AnnualPrice FROM Licenses;";
            String consultantQuery = "SELECT ProductCode, ConsultantCode, ProductName, PricePerHour FROM Consultations;";
            String personsQuery = "SELECT PersonCode, FirstName, LastName, Address, Emails FROM Persons;";
            String customersQuery = "SELECT CustomerCode, CustomerType, OrganizationName, Address, PrimaryContact FROM Customers;";
            String invoiceQuery = "SELECT InvoiceID, InvoiceCode, CustomerCode, SalesPersonCode FROM Invoices;";
            String invoiceProdQuery = "SELECT InvoiceCode, ProductCode, Quantity FROM InvoiceProducts;";
            try {
                ps = conn.prepareStatement(personsQuery);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String personCode = rs.getString("PersonCode").replace(" ", "");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String tempAddress = rs.getString("Address");
                    String tempEmails = rs.getString("Emails");
                    String address[] = tempAddress.split(",");
                    Address add = new Address(address[0], address[1], address[2], address[3], address[4]);
                    if(tempEmails != null) {
                        String emails[] = tempEmails.split(",");
                        Persons person = new Persons(personCode, firstName, lastName, add, emails);
                        persons.add(person);
                    } else {
                        Persons person = new Persons(personCode, firstName, lastName, add);
                        persons.add(person);
                    }
                }
                ps = conn.prepareStatement(consultantQuery);
                rs = ps.executeQuery();
                while(rs.next()) {
                    String productCode = rs.getString("ProductCode");
                    String consultantCode = rs.getString("ConsultantCode");
                    String productName = rs.getString("ProductName");
                    double pricePerHour = rs.getDouble("PricePerHour");
                    for (Persons person : persons) {
                        if(consultantCode.equals(person.getPersonCode())) {
                            Consultant consultant = new Consultant(productCode, productName, person, pricePerHour);
                            consultants.add(consultant);
                            products.add(consultant);
                            break;
                        }
                    }
                }
                ps = conn.prepareStatement(equipmentQuery);
                rs = ps.executeQuery();
                while(rs.next()) {
                    String productCode = rs.getString("ProductCode");
                    String productName = rs.getString("ProductName");
                    double pricePerHour = rs.getDouble("ProductPrice");
                    Equipment equipment = new Equipment(productCode, productName, pricePerHour);
                    equipments.add(equipment);
                    products.add(equipment);
                }
                ps = conn.prepareStatement(licensesQuery);
                rs = ps.executeQuery();
                while(rs.next()) {
                    String productCode = rs.getString("ProductCode");
                    String productName = rs.getString("ProductName");
                    double serviceFee = rs.getDouble("ServiceFee");
                    double annualFee = rs.getDouble("AnnualPrice");
                    Licenses license = new Licenses(productCode, productName, serviceFee, annualFee);
                    licenses.add(license);
                    products.add(license);
                }
                ps = conn.prepareStatement(customersQuery);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String customerCode = rs.getString("CustomerCode");
                    String customerType = rs.getString("CustomerType");
                    String organizationName = rs.getString("OrganizationName");
                    String tempAddress = rs.getString("Address");
                    String primaryContact = rs.getString("PrimaryContact").replace(" ", "");
                    String address[] = tempAddress.split(",");
                    Address add = new Address(address[0], address[1], address[2], address[3], address[4]);
                    for (Persons person : persons) {
                        if(primaryContact.equals(person.getPersonCode())) {
                            if(customerType.equals("C")) {
                                CustomerCorp customer = new CustomerCorp(customerCode, organizationName, add, person, customerType);
                                customerC.add(customer);
                                customers.add(customer);
                            } else if(customerType.equals("G")) {
                                CustomerGovernment customerGovernment = new CustomerGovernment(customerCode, organizationName, add, person, customerType);
                                customerG.add(customerGovernment);
                                customers.add(customerGovernment);
                            }
                            break;
                        }
                    }
                }
                ps = conn.prepareStatement(invoiceQuery);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String invoiceCode = rs.getString("InvoiceCode");
                    String customerCode = rs.getString("CustomerCode");
                    String salesPersonCode = rs.getString("SalesPersonCode");
                    MyArrayList<Product> pro = new MyArrayList<Product>();
                    MyArrayList<Double> quantity = new MyArrayList<Double>();
                    Persons p = null;
                    Customer c = null;
                    pre = conn.prepareStatement(invoiceProdQuery);
                    res = pre.executeQuery();
                    while(res.next()) {
                        String invoiceC = res.getString("InvoiceCode");
                        if(invoiceCode.equals(invoiceC)) {
                            String productCode = res.getString("ProductCode");
                            quantity.add(res.getDouble("Quantity"));
                            for(Product product : products) {
                                if(productCode.equals(product.getCode())) {
                                    pro.add(product);
                                    break;
                                }
                            }
                            for (Customer customer : customers) {
                                if(customerCode.equals(customer.getCustomerCode())) {
                                    for (Persons person : persons) {
                                        if(salesPersonCode.equals(person.getPersonCode())) {
                                            p = person;
                                            c = customer;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    Comparator<Invoices> comp = new Comparator<Invoices>() {
                        @Override
                        public int compare(Invoices o1, Invoices o2) {
                            return o1.getCustomerCode().getName().compareTo(o2.getCustomerCode().getName());
                        }
                    };
                    Invoices invoice = new Invoices(invoiceCode, c, p, pro, quantity);
                    invoices.add(invoice, comp);
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
//            FileWriter w = new FileWriter("output/Persons.json");
//            w.write("{\n\"persons\": \n");
//            w.write(String.format("%2s", gson.toJson(persons)));
//            w.write("}");
//            w.close();
//
//            FileWriter write= new FileWriter("output/Customers.json");
//            write.write("{\n\"customers\": \n");
//            write.write(String.format("%2s", gson.toJson(customers)));
//            write.write("}");
//            write.close();
//
//            FileWriter writer = new FileWriter("output/Products.json");
//            writer.write("{\n\"products\": \n");
//            writer.write(String.format("%2s", gson.toJson(products)));
//            writer.write("}");
//            writer.close();
            System.out.println("Ordered by Customer Name:");
            System.out.println("Executive Summary Reports");
            System.out.println("==========================");
            System.out.println(String.format("Invoice     Customer                           Salesperson        Subtotal   Fees       Taxes      Total"));
            double runningTotal = 0;
            double runningSubtotal = 0;
            double runningFees = 0;
            double runningTaxes = 0;
            for (Invoices invoice : invoices) {  // Looping through invoices
                runningSubtotal = invoice.getSubtotalTotal() + runningSubtotal;
                runningFees = invoice.getFees() + runningFees;
                runningTaxes = invoice.getTaxes() + runningTaxes;
                runningTotal = invoice.getTotal() + runningTotal;
                System.out.println(String.format("%-12s%-35s%-20s$%-10.2f$%-10.2f$%-10.2f$%-10.2f", invoice.getInvoiceCode(), invoice.getCustomerCode().getName(), invoice.getSalesPersonCode().getName(), invoice.getSubtotalTotal(), invoice.getFees(), invoice.getTaxes(), invoice.getTotal()));
            }
            System.out.println("==============================================================================================================");
            System.out.println(String.format("TOTALS                                                             $%-10.2f$%-10.2f$%-10.2f$%-10.2f\n\n\n\n", runningSubtotal, runningFees, runningTaxes, runningTotal));
            System.out.println("Ordered by Invoice Total:");
            System.out.println("Executive Summary Reports");
            System.out.println("==========================");
            System.out.println(String.format("Invoice     Customer                           Salesperson        Subtotal   Fees       Taxes      Total"));
            Comparator<Invoices> compPrices = new Comparator<Invoices>() {
                @Override
                public int compare(Invoices o1, Invoices o2) {
                    return ((Double) o2.getTotal()).compareTo(o1.getTotal());
                }
            };
            invoices.sort(compPrices);
            runningTotal = 0;
            runningSubtotal = 0;
            runningFees = 0;
            runningTaxes = 0;
            for (Invoices invoice : invoices) {  // Looping through invoices
                runningSubtotal = invoice.getSubtotalTotal() + runningSubtotal;
                runningFees = invoice.getFees() + runningFees;
                runningTaxes = invoice.getTaxes() + runningTaxes;
                runningTotal = invoice.getTotal() + runningTotal;
                System.out.println(String.format("%-12s%-35s%-20s$%-10.2f$%-10.2f$%-10.2f$%-10.2f", invoice.getInvoiceCode(), invoice.getCustomerCode().getName(), invoice.getSalesPersonCode().getName(), invoice.getSubtotalTotal(), invoice.getFees(), invoice.getTaxes(), invoice.getTotal()));
            }
            System.out.println("==============================================================================================================");
            System.out.println(String.format("TOTALS                                                             $%-10.2f$%-10.2f$%-10.2f$%-10.2f\n\n\n\n", runningSubtotal, runningFees, runningTaxes, runningTotal));
            System.out.println("Ordered by CustomerType, Salesperson:");
            System.out.println("Executive Summary Reports");
            System.out.println("==========================");
            System.out.println(String.format("Invoice     Customer                           Salesperson        Subtotal   Fees       Taxes      Total"));
            Comparator<Invoices> comparator = new Comparator<Invoices>() {
                @Override
                public int compare(Invoices o1, Invoices o2) {
                    int type = o1.getCustomerCode().getCustomerType().compareTo(o2.getCustomerCode().getCustomerType());
                    return type != 0 ? type : o1.getSalesPersonCode().getLastName().compareTo(o2.getSalesPersonCode().getLastName());
                }
            };
            invoices.sort(comparator);
            runningTotal = 0;
            runningSubtotal = 0;
            runningFees = 0;
            runningTaxes = 0;
            for (Invoices invoice : invoices) {  // Looping through invoices
                runningSubtotal = invoice.getSubtotalTotal() + runningSubtotal;
                runningFees = invoice.getFees() + runningFees;
                runningTaxes = invoice.getTaxes() + runningTaxes;
                runningTotal = invoice.getTotal() + runningTotal;
                System.out.println(String.format("%-12s%-35s%-20s$%-10.2f$%-10.2f$%-10.2f$%-10.2f", invoice.getInvoiceCode(), invoice.getCustomerCode().getName(), invoice.getSalesPersonCode().getName(), invoice.getSubtotalTotal(), invoice.getFees(), invoice.getTaxes(), invoice.getTotal()));
            }
            System.out.println("==============================================================================================================");
            System.out.println(String.format("TOTALS                                                             $%-10.2f$%-10.2f$%-10.2f$%-10.2f\n\n\n\n", runningSubtotal, runningFees, runningTaxes, runningTotal));
//            System.out.println(String.format("Individual Invoice Detail Reports"));
//            System.out.println("===================================================");
//            for (Invoices invoice : invoices) {  // Now go through invoices again to print out the specified invoices
//                System.out.println(String.format("Invoice %s",invoice.getInvoiceCode()));
//                System.out.println("====================================");
//                System.out.println(String.format("Salesperson: %s", invoice.getSalesPersonCode().getName()));
//                System.out.println(String.format("Customer Info:"));
//                System.out.println(String.format("  %s (%s)\n  %s\n  %s", invoice.getCustomerCode().getName(), invoice.getCustomerCode().getCustomerCode(), invoice.getCustomerCode().getPrimaryContact().getName(), invoice.getCustomerCode().getAddress().getWholeAddressLine()));
//                System.out.println(String.format("------------------------------------------------"));
//                System.out.println(String.format("Code      Item%73s%15s", "Fees", "Total"));
//                invoice.getSubtotalPrint();
//                System.out.println(String.format("%105s","======================="));
//                System.out.println(String.format("%-82s$%-10.2f$%-20.2f","SUB_TOTALS", invoice.getFees(), invoice.getSubtotalTotal()));
//                System.out.println(String.format("%-83s%-10s$%-21.2f", "COMPLIANCE FEE", " ", invoice.getComplienceFee()));
//                System.out.println(String.format("%-83s%-10s$%-21.2f", "TAXES", " ", invoice.getTaxes()));
//                System.out.println(String.format("%-83s%-10s$%-21.2f\n\n\n\n", "TOTAL", " ", invoice.getTotal()));
//            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
