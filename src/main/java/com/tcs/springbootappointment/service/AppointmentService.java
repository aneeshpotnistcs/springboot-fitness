package com.tcs.springbootappointment.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tcs.springbootappointment.entity.Appointment;
import com.tcs.springbootappointment.entity.User;
import com.tcs.springbootappointment.exception.AppointmentNotFoundException;
import com.tcs.springbootappointment.exception.UserNotFoundException;
import com.tcs.springbootappointment.repository.IAppointmentRepository;
import com.tcs.springbootappointment.repository.IUserRepository;

@Service
public class AppointmentService implements IAppointmentService {
	private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

	@Autowired
	IAppointmentRepository appointmentRepository;
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public void save(Appointment appointment, Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("user does not exist");
		}
		Set<Appointment> appointmentForUser = new HashSet<>();
		appointmentForUser.add(appointment);
		user.get().setAppointments(appointmentForUser);
		appointmentRepository.save(appointment);
		logger.debug("saved");
	}

	@Override
	public Iterable<Appointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}

	@Override
	public Optional<Appointment> getAppointment(Integer id) {
		Optional<Appointment> appointment = appointmentRepository.findById(id);
		if (!appointment.isPresent()) {
			throw new AppointmentNotFoundException("Appointment does not exist");
		}
		return appointment;
	}

	@Override
	public Optional<Appointment> deleteAppointment(Integer id) {
		Optional<Appointment> appointment = appointmentRepository.findById(id);
		if (!appointment.isPresent())
			throw new AppointmentNotFoundException("Appointment does not exist");
		else
			appointmentRepository.deleteById(id);
		return appointment;
	}

	@Override
	public void update(Appointment appointment, Integer id) {
		Optional<Appointment> appointmentFromDB = appointmentRepository.findById(id);
		if (appointmentFromDB.isPresent()) {
			Appointment appointment1 = appointmentFromDB.get();
			if (StringUtils.hasText(appointment.getName()))
				appointment1.setName(appointment.getName());
			appointmentRepository.save(appointment1);
			if (appointment.getPhoneNumber() != null)
				appointment1.setPhoneNumber(appointment.getPhoneNumber());
			appointmentRepository.save(appointment1);
			if (StringUtils.hasText(appointment.getTrainerPreference()))
				appointment1.setTrainerPreference(appointment.getTrainerPreference());
			appointmentRepository.save(appointment1);
			if (StringUtils.hasText(appointment.getAddress()))
				appointment1.setAddress(appointment.getAddress());
			appointmentRepository.save(appointment1);
			if (StringUtils.hasText(appointment.getTrainerPreference()))
				appointment1.setTrainerPreference(appointment.getTrainerPreference());
			appointmentRepository.save(appointment1);
			if (StringUtils.hasText(appointment.getPhysioRequired()))
				appointment1.setPhysioRequired(appointment.getPhysioRequired());
			appointmentRepository.save(appointment1);
			if (StringUtils.hasText(appointment.getPackageSelected()))
				appointment1.setPackageSelected(appointment.getPackageSelected());
			appointmentRepository.save(appointment1);
		}
		else {
			throw new AppointmentNotFoundException("Appointment does not exist");
		}
	}

}
