package com.coli;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.time.StopWatch;

import com.coli.carpool.bo.ReservationSimple;
import com.coli.carpool.model.Driver;
import com.coli.carpool.model.Reservation;
import com.coli.carpool.service.CarpoolServiceBackend;
import com.coli.carpool.utils.CarpoolException;
import com.google.gson.Gson;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RESTConnector {

	@Path("driver")
	@POST
	public String createDriver(@FormParam("drivername") String driverName) throws Exception {
		System.out.println("Running createDriver().");
		StopWatch stopWatch = StopWatch.createStarted();

		CarpoolServiceBackend CarpoolServiceBackend = new CarpoolServiceBackend();
		try {
			CarpoolServiceBackend.getDriverService().createDriver(driverName);
		} catch (CarpoolException exc) {
			return exc.getMessage();
		}

		stopWatch.stop();
		long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
		logTime(duration, "createDriver");

		return getDriver(driverName);
	}

	@Path("driver")
	@GET
	public String getDriver(@QueryParam("drivername") String driverName) throws Exception {
		System.out.println("Running getDriver().");
		StopWatch stopWatch = StopWatch.createStarted();

		CarpoolServiceBackend CarpoolServiceBackend = new CarpoolServiceBackend();
		Driver driver;
		try {
			driver = CarpoolServiceBackend.getDriverService().retrieveDriver(driverName);
		} catch (CarpoolException exc) {
			return exc.getMessage();
		}
		Gson gson = new Gson();
		String driverJson = gson.toJson(driver);

		stopWatch.stop();
		long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
		logTime(duration, "getDriver");

		return driverJson;
	}

	@Path("driverList")
	@GET
	public String getDriverList() throws Exception {
		System.out.println("Running getDriverList().");
		StopWatch stopWatch = StopWatch.createStarted();

		CarpoolServiceBackend CarpoolServiceBackend = new CarpoolServiceBackend();
		List<Driver> driverList;
		try {
			driverList = CarpoolServiceBackend.getDriverService().listDriver();
		} catch (Exception exc) {
			return exc.getMessage();
		}

		Gson gson = new Gson();
		String driverListJson = gson.toJson(driverList);

		stopWatch.stop();
		long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
		logTime(duration, "getDriverList");

		return driverListJson;
	}

	@Path("reservationList")
	@GET
	public String getReservationList() throws Exception {
		System.out.println("Running getReservationList().");
		StopWatch stopWatch = StopWatch.createStarted();

		CarpoolServiceBackend CarpoolServiceBackend = new CarpoolServiceBackend();
		List<Reservation> reservationList;
		try {
			reservationList = CarpoolServiceBackend.getReservationService().listReservation();
		} catch (Exception exc) {
			return exc.getMessage();
		}
		Gson gson = new Gson();
		String reservationListJson = gson.toJson(reservationList);

		stopWatch.stop();
		long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
		logTime(duration, "getReservationList");

		return reservationListJson;
	}

	@Path("reservationSimpleList")
	@GET
	public String getReservationSimpleList() throws Exception {
		System.out.println("Running getReservationSimpleList().");
		StopWatch stopWatch = StopWatch.createStarted();

		CarpoolServiceBackend CarpoolServiceBackend = new CarpoolServiceBackend();
		List<ReservationSimple> reservationSimpleList;
		try {
			reservationSimpleList = CarpoolServiceBackend.getReservationService().listReservationSimple();
		} catch (Exception exc) {
			return exc.getMessage();
		}
		Gson gson = new Gson();

		String reservationSimpleListJson = gson.toJson(reservationSimpleList);

		stopWatch.stop();
		long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
		logTime(duration, "getReservationSimpleList");

		return reservationSimpleListJson;
	}

	private void logTime(long duration, String methodName) {
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		long milliseconds = duration - seconds * 1000;
		System.out.println("-> Done " + methodName + "() in " + seconds + "," + milliseconds + " seconds");

	}

}
