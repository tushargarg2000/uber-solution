package com.driver.test;

import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.impl.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCases {

    @InjectMocks
    AdminServiceImpl adminService;
    @InjectMocks
    CustomerServiceImpl customerService;
    @InjectMocks
    DriverServiceImpl driverService;

    @Mock
    AdminRepository adminRepository1;
    @Mock
    DriverRepository driverRepository1;
    @Mock
    CustomerRepository customerRepository1;
    @Mock
    CustomerRepository customerRepository2;
    @Mock
    DriverRepository driverRepository2;
    @Mock
    TripBookingRepository tripBookingRepository2;
    @Mock
    DriverRepository driverRepository3;
    @Mock
    CabRepository cabRepository3;

    private Admin admin;
    private Driver driver;
    private Driver driver1;
    private Driver driver2;
    private Driver driverUnavlbl;
    private Customer customer;
    private Customer customer1;
    private Customer customer2;
    private TripBooking tripBooking;
    private ArrayList<Driver> drivers;
    @Before
    public void setup(){
        admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("password");

        driver1 = new Driver();
        driver1.setMobile("1234567890");
        driver1.setPassword("password1");

        driver2 = new Driver();
        driver2.setMobile("0987654321");
        driver2.setPassword("password2");

        customer1 = new Customer();
        customer1.setMobile("1111111111");
        customer1.setPassword("password3");

        customer2 = new Customer();
        customer2.setMobile("2222222222");
        customer2.setPassword("password4");

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setMobile("1234567890");
        customer.setPassword("password");

        driver = new Driver();
        driver.setDriverId(1);
        driver.setMobile("0987654321");
        driver.setPassword("password");
        Cab cab = new Cab();
        cab.setId(1);
        cab.setPerKmRate(10);
        cab.setAvailable(Boolean.TRUE);
        cab.setDriver(driver);
        driver.setCab(cab);

        driverUnavlbl = new Driver();
        driverUnavlbl.setDriverId(0);
        driverUnavlbl.setMobile("098754321");
        driverUnavlbl.setPassword("passwrd");
        Cab cabUnavlbl = new Cab();
        cabUnavlbl.setId(0);
        cabUnavlbl.setPerKmRate(10);
        cabUnavlbl.setAvailable(Boolean.FALSE);
        cabUnavlbl.setDriver(driverUnavlbl);
        driverUnavlbl.setCab(cabUnavlbl);

        driver1.setDriverId(2);
        Cab cab1 = new Cab();
        cab1.setId(2);
        cab1.setPerKmRate(10);
        cab1.setAvailable(Boolean.TRUE);
        cab1.setDriver(driver1);
        driver1.setCab(cab1);

        drivers = new ArrayList<>();
        drivers.add(driverUnavlbl);
        drivers.add(driver);
        drivers.add(driver1);

        tripBooking = new TripBooking();
        tripBooking.setTripBookingId(1);
        tripBooking.setFromLocation("from");
        tripBooking.setToLocation("to");
        tripBooking.setDistanceInKm(10);
        tripBooking.setStatus(TripStatus.CONFIRMED);
        tripBooking.setBill(100);
        tripBooking.setCustomer(customer);
        tripBooking.setDriver(driver);
    }

    @Test
    public void testAdminRegister() {
        adminService.adminRegister(admin);
        verify(adminRepository1, times(1)).save(admin);
    }

    @Test
    public void testUpdatePassword() {
        admin.setAdminId(1);
        when(adminRepository1.findById(any())).thenReturn(Optional.ofNullable(admin));
        when(adminRepository1.save(admin)).thenReturn(admin);
        Admin updatedAdmin = adminService.updatePassword(admin.getAdminId(), "newpassword");
        assertEquals("newpassword", updatedAdmin.getPassword());
    }

    @Test
    public void testDeleteAdmin() {
        admin.setAdminId(1);
        when(adminRepository1.findById(any())).thenReturn(Optional.ofNullable(admin));
        adminService.deleteAdmin(admin.getAdminId());
        verify(adminRepository1, times(1)).delete(admin);
    }

    @Test
    public void testGetListOfDrivers() {
        List<Driver> drivers = Arrays.asList(driver1, driver2);
        when(driverRepository1.findAll()).thenReturn(drivers);
        List<Driver> listOfDrivers = adminService.getListOfDrivers();
        assertEquals(2, listOfDrivers.size());
        assertEquals(driver1.getMobile(), listOfDrivers.get(0).getMobile());
        assertEquals(driver2.getMobile(), listOfDrivers.get(1).getMobile());
    }

    @Test
    public void testGetListOfCustomers() {
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository1.findAll()).thenReturn(customers);
        List<Customer> listOfCustomers = adminService.getListOfCustomers();
        assertEquals(2, listOfCustomers.size());
        assertEquals(customer1.getMobile(), listOfCustomers.get(0).getMobile());
        assertEquals(customer2.getMobile(), listOfCustomers.get(1).getMobile());
    }

    @Test
    public void testCustomerRegister() {
        customerService.register(customer);
        verify(customerRepository2, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer() {
        when(customerRepository2.findById(1)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(1);
        verify(customerRepository2, times(1)).delete(customer);
    }

    @Test
    public void testBookTrip() throws Exception {
        when(driverRepository2.findAll()).thenReturn(drivers);
        when(customerRepository2.findById(1)).thenReturn(Optional.ofNullable(customer));
        String fromLocation = "from";
        String toLocation = "to";
        int distanceInKm = 10;
        TripBooking bookedTrip = customerService.bookTrip(1, fromLocation, toLocation, distanceInKm);
        assertEquals(customer, bookedTrip.getCustomer());
        assertEquals(driver, bookedTrip.getDriver());
        assertEquals(fromLocation, bookedTrip.getFromLocation());
        assertEquals(toLocation, bookedTrip.getToLocation());
        assertEquals(distanceInKm, bookedTrip.getDistanceInKm());
        assertEquals(TripStatus.CONFIRMED, bookedTrip.getStatus());
        verify(customerRepository2, times(1)).save(customer);
        verify(driverRepository2, times(1)).save(driver);
    }

    @Test
    public void testBookTrip1() throws Exception {
        when(driverRepository2.findAll()).thenReturn(drivers);
        when(customerRepository2.findById(1)).thenReturn(Optional.ofNullable(customer));
        String fromLocation = "from";
        String toLocation = "to";
        int distanceInKm = 10;
        TripBooking bookedTrip = customerService.bookTrip(1, fromLocation, toLocation, distanceInKm);
        assertEquals(customer, bookedTrip.getCustomer());
        assertEquals(driver, bookedTrip.getDriver());
        assertEquals(fromLocation, bookedTrip.getFromLocation());
        assertEquals(toLocation, bookedTrip.getToLocation());
        assertEquals(distanceInKm, bookedTrip.getDistanceInKm());
        assertEquals(TripStatus.CONFIRMED, bookedTrip.getStatus());
        verify(customerRepository2, times(1)).save(customer);
        verify(driverRepository2, times(1)).save(driver);
    }

    @Test
    public void testBookTrip2() throws Exception {
        when(driverRepository2.findAll()).thenReturn(drivers);
        when(customerRepository2.findById(1)).thenReturn(Optional.ofNullable(customer));
        String fromLocation = "from";
        String toLocation = "to";
        int distanceInKm = 10;
        TripBooking bookedTrip = customerService.bookTrip(1, fromLocation, toLocation, distanceInKm);
        assertEquals(customer, bookedTrip.getCustomer());
        assertEquals(driver, bookedTrip.getDriver());
        assertEquals(fromLocation, bookedTrip.getFromLocation());
        assertEquals(toLocation, bookedTrip.getToLocation());
        assertEquals(distanceInKm, bookedTrip.getDistanceInKm());
        assertEquals(TripStatus.CONFIRMED, bookedTrip.getStatus());
        verify(customerRepository2, times(1)).save(customer);
        verify(driverRepository2, times(1)).save(driver);
    }

    @Test
    public void testBookTripWithNoCabAvailable(){
        List<Driver> allDrivers = new ArrayList<>();
        when(driverRepository2.findAll()).thenReturn(allDrivers);
        try {
            customerService.bookTrip(1, "from", "to", 10);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "No cab available!");
        }
    }

    @Test
    public void testBookTripWithNoCabAvailable1(){
        List<Driver> allDrivers = new ArrayList<>();
        when(driverRepository2.findAll()).thenReturn(allDrivers);
        try {
            customerService.bookTrip(1, "from", "to", 10);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "No cab available!");
        }
    }

    @Test
    public void testCancelTrip() {
        when(tripBookingRepository2.findById(1)).thenReturn(Optional.of(tripBooking));
        customerService.cancelTrip(1);
        assertEquals(TripStatus.CANCELED, tripBooking.getStatus());
        assertEquals(0, tripBooking.getBill());
        assertEquals(Boolean.TRUE, tripBooking.getDriver().getCab().getAvailable());
        verify(tripBookingRepository2, times(1)).save(tripBooking);
    }

    @Test
    public void testCompleteTrip() {
        when(tripBookingRepository2.findById(1)).thenReturn(Optional.of(tripBooking));
        customerService.completeTrip(1);
        assertEquals(TripStatus.COMPLETED, tripBooking.getStatus());
        assertEquals(100, tripBooking.getBill());
        assertEquals(Boolean.TRUE, tripBooking.getDriver().getCab().getAvailable());
        verify(tripBookingRepository2, times(1)).save(tripBooking);
    }

    @Test
    public void testCompleteTrip1() {
        when(tripBookingRepository2.findById(1)).thenReturn(Optional.of(tripBooking));
        customerService.completeTrip(1);
        assertEquals(TripStatus.COMPLETED, tripBooking.getStatus());
        assertEquals(100, tripBooking.getBill());
        assertEquals(Boolean.TRUE, tripBooking.getDriver().getCab().getAvailable());
        verify(tripBookingRepository2, times(1)).save(tripBooking);
    }

    @Test
    public void testRegister() {
        when(driverRepository3.save(any())).thenReturn(driver);
        String mobile = "0987654321";
        String password = "password";
        driverService.register(mobile, password);
        verify(cabRepository3, never()).save(any());
    }

    @Test
    public void testRegister1() {
        when(driverRepository3.save(any())).thenReturn(driver);
        String mobile = "0987654321";
        String password = "password";
        driverService.register(mobile, password);
        verify(cabRepository3, never()).save(any());
    }
    @Test
    public void testRemoveDriver() {
        when(driverRepository3.findById(1)).thenReturn(Optional.of(driver));
        driverService.removeDriver(1);
        verify(driverRepository3, times(1)).delete(driver);
    }

    @Test
    public void testUpdateStatus() {
        when(driverRepository3.findById(1)).thenReturn(Optional.of(driver));
        driverService.updateStatus(1);
        assertEquals(Boolean.FALSE, driver.getCab().getAvailable());
        verify(cabRepository3, atMost(1)).save(driver.getCab());
        verify(driverRepository3, atMost(1)).save(driver);
    }

    @Test
    public void testUpdateStatus1() {
        when(driverRepository3.findById(1)).thenReturn(Optional.of(driver));
        driverService.updateStatus(1);
        assertEquals(Boolean.FALSE, driver.getCab().getAvailable());
        verify(cabRepository3, atMost(1)).save(driver.getCab());
        verify(driverRepository3, atMost(1)).save(driver);
    }
}

