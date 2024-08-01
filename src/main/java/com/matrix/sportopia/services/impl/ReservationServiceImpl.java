package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.exceptions.handle.ResourceNotFoundException;
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
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
        ReservationResponseDto responseDto = reservationMapper.toResponse(reservation);
        log.info("-> Successful! reservation-service get by id operation with id = {}", id);
        return responseDto;
    }

    @Override
    public List<ReservationResponseDto> getByUserId(Long userId){
        log.info("-> fetching reservations for user id: {}", userId);
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDto> getByStadiumId(Long stadiumId){
        log.info("-> fetching reservations for stadium id: {}", stadiumId);
        List<Reservation> reservations = reservationRepository.findByStadiumId(stadiumId);
        return reservations.stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDto> getAll() {
        log.info("-> fetching all reservations");
        List<ReservationResponseDto> list = reservationRepository.findAll().stream().
                map(reservationMapper::toResponse).collect(Collectors.toList());
        //?
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

        reservation.setStatus(true);
        reservation.setIsPaid(false);

        Reservation savedReservation = reservationRepository.save(reservation);

        log.info("-> added reservation with ID {}", savedReservation.getId());
        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    public ReservationResponseDto update(Long id,ReservationRequestDto reservationRequest) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Reservation not found with id " + id));
        reservationMapper.updateEntityFromDto(reservationRequest,existingReservation);

        Reservation updatedReservation = reservationRepository.save(existingReservation);
        return reservationMapper.toResponse(updatedReservation);
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
