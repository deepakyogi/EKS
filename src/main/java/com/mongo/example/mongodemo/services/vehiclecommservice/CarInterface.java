package com.mongo.example.mongodemo.services.vehiclecommservice;

import java.util.List;
import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.models.apimodel.CarEnum;

public interface CarInterface {

	public Car addNew(Car employee);

	public String handlingTrunk(int id);

	public String handlingAirConditioner(int id);

	public String handlingAirCirculation(int id, String inout);

	public String defrostRear(int id, int value);

	public String defrostFront(int id, int value);

	public String AcTempChange(int id, String slide) ;
	
	public List<Car> getData();

	public String handlingEngine(int id, int value);

	public String handlingHazardLight(int id);

	public String handlingBonet(int id);
	
	public String handlingSlider(int id, int value);

	public String handlingHornOn(int id);
	
	public String handlingHeadLight(int id, String headLight);

	public String frontACState(int id, int state);
	
	public String rearACState(int id, int state);

	public String frontACVentFlow( int id, String frontvent);

	public String rearACVentFlow(String pos,int id);

	public String frontAcTempChange(int id, int temp);
	
    public String rearAcTempChange(int temp,int id);

	public String  handlingSideMirror( String side, int value,int id);
	
	public String handlingWindowMirror(String pos, String side,int value,int id) ;

	public String handlingMasterLock(int id);
	
	public String handlingChildLock(int id);

	public String handlingDoorLockWithEnum(int id, CarEnum carenum);

	public String save(Car car);

}
