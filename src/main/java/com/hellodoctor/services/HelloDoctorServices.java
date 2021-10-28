package com.hellodoctor.services;

import java.util.List;

import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.HospitalAddress;
import com.hellodoctor.entities.HospitalsDetails;
import com.hellodoctor.requestdto.RequestDto;

public interface HelloDoctorServices {
	public Doctor addDoctor(RequestDto requestDto);
    public HospitalsDetails addHospital(RequestDto dto);
    public HospitalsDetails getHospitalById(Long hospitalId);
    public List<HospitalsDetails> getAllHospital();
    public List<Doctor> getAllDoctor();
    public Doctor getDoctorById(Long doctorId);
    public Doctor findBydoctorEmail(String doctorEmail);
    public List<HospitalsDetails>  findByhospitalName(String hospitalName);
    public HospitalAddress getHospitalAddressById(Long addressId);
    public void deletedoctorById(Long doctorId);
}
