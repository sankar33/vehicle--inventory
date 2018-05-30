package com.vehicle.mgmnt;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vehicle.mgmnt.entity.Vehicle;
import com.vehicle.mgmnt.entity.VehicleTypeEnum;
import com.vehicle.mgmnt.resource.VehicleInventoryRestResource;
import com.vehicle.mgmnt.service.VehicleInventoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = VehicleInventoryRestResource.class, secure = false)
public class VehicleInventoryRestResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VehicleInventoryService vehicleInventoryService;

	Vehicle vehicle = new Vehicle();

	@Before
	public void setup() {
		vehicle.setModelId("HondaCityBlack");
		vehicle.setYear(2018);
		vehicle.setMake("Honda");
		vehicle.setModel("Honda City");
		vehicle.setColor("Black");
		vehicle.setMileage(21);
		vehicle.setVehicleType(VehicleTypeEnum.CAR);
	}

	@Test
	public void listAllVehicle() throws Exception {

		Mockito.when(vehicleInventoryService.listAllVehicle()).thenReturn(Arrays.asList(vehicle));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vehicle/all")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{ \"modelId\": \"HondaCityBlack\", \"year\": 2018, \"make\": \"Honda\", \"model\": \"Honda City\", \"color\": \"Black\", \"mileage\": 21, \"vehicleType\": \"CAR\" }]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getVehicleByModel() throws Exception {

		Mockito.when(vehicleInventoryService.findByModelId(Mockito.anyString())).thenReturn(vehicle);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vehicle/HondaCityBlack")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{ \"modelId\": \"HondaCityBlack\", \"year\": 2018, \"make\": \"Honda\", \"model\": \"Honda City\", \"color\": \"Black\", \"mileage\": 21, \"vehicleType\": \"CAR\" }";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
