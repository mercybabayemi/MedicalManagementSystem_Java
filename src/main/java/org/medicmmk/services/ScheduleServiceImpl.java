package org.medicmmk.services;

import org.medicmmk.data.models.Schedule;
import org.medicmmk.data.models.TimeSlot;
import org.medicmmk.data.repository.ScheduleRepository;
import org.medicmmk.dtos.requests.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule createOrUpdateSchedule(String doctorId, ScheduleRequest request) {
        Schedule newSchedule = scheduleRepository.findByDoctorId(doctorId);
        if(newSchedule == null) {
            newSchedule = new Schedule();
            newSchedule.setDoctorId(doctorId);
        }
        newSchedule.setTimeSlots(request.getTimeSlots());
        return scheduleRepository.save(newSchedule);
    }

    @Override
    public Schedule viewSchedule(String doctorId) {
        return scheduleRepository.findByDoctorId(doctorId);
    }

    @Override
    public boolean bookTimeSlot(String doctorId, TimeSlot timeSlot) {
        Schedule schedule = scheduleRepository.findByDoctorId(doctorId);
        if(schedule != null) {
            for(TimeSlot slot: schedule.getTimeSlots()){
                if(slot.getStartTime().equals(timeSlot.getStartTime()) && !slot.isBooked()){
                    slot.setBooked(true);
                    scheduleRepository.save(schedule);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cancelTimeSlot(String doctorId, TimeSlot timeSlot) {
        Schedule schedule = scheduleRepository.findByDoctorId(doctorId);
        if(schedule != null){
            for(TimeSlot slot: schedule.getTimeSlots()){
                if(slot.getStartTime().equals(timeSlot.getStartTime()) && slot.isBooked()){
                    slot.setBooked(false);
                    scheduleRepository.save(schedule);
                    return true;
                }
            }
        }
        return false;
    }
}
