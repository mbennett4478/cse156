/**
 * Created by TheWoz on 10/9/14.
 */
public class Invoices {
	private double subtotal; 
	private double equipmentSubtotal;
	private double licenseSubtotal;
	private double consultantSubtotal;
	private double taxes;
	private double fees;
    private String invoiceCode;
    private Customer customerCode;
    private Persons salesPersonCode;
    private MyArrayList<Product> productCodes = new MyArrayList<Product>();
    private MyArrayList<Double> quantity = new MyArrayList<Double>();

    public Invoices(String invoiceCode, Customer customerCode, Persons salesPersonCode, MyArrayList<Product> productCodes, MyArrayList<Double> quantity) {
        this.invoiceCode = invoiceCode;
        this.customerCode = customerCode;
        this.salesPersonCode = salesPersonCode;
        this.productCodes = productCodes;
        this.quantity = quantity;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public Customer getCustomerCode() {
        return customerCode;
    }

    public Persons getSalesPersonCode() {
        return salesPersonCode;
    }

    public MyArrayList<Double> getQuantity() {
        return quantity;
    }
    
    public MyArrayList<Product> getProducts() {
        return productCodes;
    }
  
    public double getSubtotalTotal() {
    	subtotal = 0;
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Equipment) {
    			subtotal += productCodes.get(i).getPricePerUnit() * quantity.get(i);
    		} else if(productCodes.get(i) instanceof Licenses) {
                subtotal += productCodes.get(i).getAnnualLicenseFee() * quantity.get(i) / 365;
            } else if(productCodes.get(i) instanceof Consultant) {
                subtotal += productCodes.get(i).getPricePerUnit() * quantity.get(i);
            }
        }
		return subtotal;
    }

    public void getSubtotalPrint() {
    	subtotal = 0;
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Equipment) {
    			subtotal = productCodes.get(i).getPricePerUnit() * quantity.get(i);
    			fees = 0;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", productCodes.get(i).getCode(), productCodes.get(i).getName(), " (" + quantity.get(i) + " units @ " + productCodes.get(i).getPricePerUnit() + "/unit)", fees, subtotal));
    		} else if(productCodes.get(i) instanceof Licenses) {
                subtotal = productCodes.get(i).getAnnualLicenseFee() * quantity.get(i) / 365;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", productCodes.get(i).getCode(), productCodes.get(i).getName(), " (" + quantity.get(i) + " days @ " + productCodes.get(i).getAnnualLicenseFee() + "/year)", productCodes.get(i).getServiceFee(), subtotal));
            } else if(productCodes.get(i) instanceof Consultant) {
                subtotal = productCodes.get(i).getPricePerUnit() * quantity.get(i);
                fees = 150;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", productCodes.get(i).getCode(), productCodes.get(i).getName(), " (" + quantity.get(i) + " hours @ " + productCodes.get(i).getPricePerUnit() + "/hour)", fees, subtotal));
            }
        }
    }
    
    public double getComplienceFee() {
    	double complianceFee = 0;
    	if(customerCode instanceof CustomerGovernment) {
    		complianceFee = 150;
    	}
    	return complianceFee;
    }
    
    public double getFees() {
    	if(customerCode instanceof CustomerCorp) {
    		fees = 0;
    		for(int i=0; i<productCodes.size(); i++) {
    			if(productCodes.get(i) instanceof Licenses) {
    				fees = productCodes.get(i).getServiceFee() + fees;
    			} else if(productCodes.get(i) instanceof Consultant) {
    				fees = 150 + fees;
    			}
    		}
    	} else if(customerCode instanceof CustomerGovernment) {
    		fees = 125;
    		for(int i=0; i<productCodes.size(); i++) {
    			if(productCodes.get(i) instanceof Licenses) {
    				fees = productCodes.get(i).getServiceFee() + fees;
    			} else if(productCodes.get(i) instanceof Consultant) {
    				fees = 150 + fees;
    			}
    		}
    	}
    	return fees;
    }

    public double getTaxes() {
    	taxes = 0;
    	if(customerCode instanceof CustomerCorp){
    		for(int i=0; i<productCodes.size(); i++) {
    			if(productCodes.get(i) instanceof Equipment) {
    				subtotal = productCodes.get(i).getPricePerUnit() * quantity.get(i);
    				taxes = subtotal * .07 + taxes;
    			} else if(productCodes.get(i) instanceof Licenses) {
    				subtotal = productCodes.get(i).getAnnualLicenseFee() * quantity.get(i) / 365;
    				taxes = subtotal * .0425 + taxes;
    			} else if(productCodes.get(i) instanceof Consultant) {
    				subtotal = productCodes.get(i).getPricePerUnit() * quantity.get(i);
    				taxes = subtotal * .0425 + taxes;
    			}
    		}
    	} else if(customerCode instanceof CustomerGovernment) {
    		taxes = 0;
    	}
    	return taxes;
    }

    public double getTotal() {
        double total = getSubtotalTotal() + getFees() + getTaxes();
        return total;
    }

    public void getEquipmentSubtotal() {
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Equipment) {
    			equipmentSubtotal = productCodes.get(i).getPricePerUnit() * quantity.get(i);
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", productCodes.get(i).getCode(), productCodes.get(i).getName(), " (" + quantity.get(i) + " units @ " + productCodes.get(i).getPricePerUnit() + "/unit)", fees, equipmentSubtotal));
    			break;
    		}
    	}
    }

    public void getLicenseSubtotal() {
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Licenses) {
    			licenseSubtotal = productCodes.get(i).getAnnualLicenseFee() * quantity.get(i) / 365;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", productCodes.get(i).getCode(), productCodes.get(i).getName(), " (" + quantity.get(i) + " hours @ " + productCodes.get(i).getPricePerUnit() + "/hour)", fees, licenseSubtotal));
    			break;
    		}
    	}
    }

    public void getConsultantSutotal() {
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Consultant) {
    			consultantSubtotal = productCodes.get(i).getPricePerUnit() * quantity.get(i);
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", productCodes.get(i).getCode(), productCodes.get(i).getName(), " (" + quantity.get(i) + " days @ " + productCodes.get(i).getAnnualLicenseFee() + "/year)", fees, consultantSubtotal));
    			break;
    		}
    	}
    }
}

