package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.models.dto.request.ReservationRequestDto;
import com.matrix.sportopia.models.dto.response.ReservationResponseDto;
import com.matrix.sportopia.exceptions.handle.EntityNotFoundException;
import com.matrix.sportopia.mapper.ReservationMapper;
import com.matrix.sportopia.repositories.ReservationRepository;
import com.matrix.sportopia.repositories.StadiumRepository;
import com.matrix.sportopia.repositories.UserRepository;
import com.matrix.sportopia.services.ReservationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final StadiumRepository stadiumRepository;

    @Override
    public ReservationResponseDto getById(Long id) {
        log.info("-> reservation-service get by id operation with id = {}", id);
        ReservationResponseDto reservationResponseDto = reservationRepository.findById(id).
                map(reservationMapper::toResponce).
                orElseThrow(() -> new EntityNotFoundException("reservation not found"));
        log.info("-> Successful! reservation-service get by id operation with id = {}", id);
        return reservationResponseDto;
    }

    @Override
    public List<ReservationResponseDto> getAll() {
        log.info("-> started getAll method for reservation");
        List<ReservationResponseDto> list = reservationRepository.findAll().stream().
                map(reservationMapper::toResponce).collect(Collectors.toList());
        if (list.isEmpty()){
            throw new EntityNotFoundException("Reservation not found");
        }
        log.info("-> Successfully!");
        return list;
    }

    @Override
    public ReservationResponseDto add(ReservationRequestDto reservationRequest) {
        Reservation reservation = reservationMapper.toEntity(reservationRequest);
        User user = userRepository.findById(reservationRequest.getUserId()).
                orElseThrow(() -> new NoSuchElementException("User not found"));
        reservation.setUser(user);

        Stadium stadium = stadiumRepository.findById(reservationRequest.getStadiumId()).
                orElseThrow(() -> new NoSuchElementException("Stadium not found"));
        reservation.setStadium(stadium);

        return reservationMapper.toResponce(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDto update(ReservationRequestDto reservationRequest) {
        Reservation reservation = reservationMapper.toEntity(reservationRequest);
        return reservationMapper.toResponce(reservationRepository.save(reservation));
    }

    @Override
    public void delete(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }
    }
}
