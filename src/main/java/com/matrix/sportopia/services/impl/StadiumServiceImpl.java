package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Sport;
import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.entities.dto.request.StadiumRequestDto;
import com.matrix.sportopia.entities.dto.response.StadiumResponseDto;
import com.matrix.sportopia.enums.StadiumStatus;
import com.matrix.sportopia.exceptions.handle.AlreadyExistException;
import com.matrix.sportopia.exceptions.handle.EntityNotFoundException;
import com.matrix.sportopia.mapper.StadiumMapper;
import com.matrix.sportopia.repositories.SportRepository;
import com.matrix.sportopia.repositories.StadiumRepository;
import com.matrix.sportopia.services.StadiumService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;
    private final SportRepository sportRepository;
    private final StadiumMapper stadiumMapper;

    @Override
    public StadiumResponseDto getById(Long id) {
        log.info("-> stadium-service get by id operation with id = {}", id);
        Stadium stadium = stadiumRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Stadium not found"));
        if (stadium.getStatus() == StadiumStatus.CLOSED) {
            throw new EntityNotFoundException("Stadium is closed");
        }

        StadiumResponseDto stadiumResponseDto = stadiumMapper.toResponse(stadium);
        log.info("->  successful! stadium-service get by id operation with id = {}", id);
        return stadiumResponseDto;
    }

    @Override
    public List<StadiumResponseDto> getAll() {
        log.info("-> started getAll method for stadium");
        List<StadiumResponseDto> list = stadiumRepository.findAllByStatusNot(StadiumStatus.CLOSED).stream().
                map(stadiumMapper::toResponse).collect(Collectors.toList());
        if (list.isEmpty()){
            throw new EntityNotFoundException("Stadiums not found");
        }
        log.info("-> Successfully");
        return list;
    }

    @Override
    public List<StadiumResponseDto> getStadiumsBySportId(Long sportId){
        log.info("-> get stadium by sport id operation started with sportId = {}", sportId);
        List<Stadium> stadiums = stadiumRepository.findBySportIdAndStatusNot(sportId,StadiumStatus.CLOSED);
        if (stadiums.isEmpty()){
            throw new EntityNotFoundException("Stadiums not found for sportId = " + sportId);
        }
        List<StadiumResponseDto> responseDtoList = stadiums.stream().map(stadiumMapper::toResponse).toList();
        log.info("-> successfully retrieved stadiums for sportId = {}", sportId);
        return responseDtoList;
    }

    @Override
    public StadiumResponseDto add(StadiumRequestDto stadiumRequestDto) {
        if (!isValidStatus(stadiumRequestDto.getStatus())){
            log.warn("-> invalid status for stadium: " + stadiumRequestDto.getStatus());
            throw new IllegalArgumentException("Invalid status. Status can only be OPEN, TEMPORARILY_CLOSED");
        }
        Stadium stadium = stadiumMapper.toEntity(stadiumRequestDto);
        Sport sport = sportRepository.findById(stadiumRequestDto.getSportId())
                .orElseThrow(() -> new AlreadyExistException("Sport not found with id: " + stadiumRequestDto.getSportId()));
        stadium.setSport(sport);
        return stadiumMapper.toResponse(stadiumRepository.save(stadium));
    }

    @Override
    public StadiumResponseDto update(Long id,StadiumRequestDto stadiumRequestDto) {
        if (!isValidStatus(stadiumRequestDto.getStatus())) {
            log.warn("-> invalid status for stadium: " + stadiumRequestDto.getStatus());
            throw new IllegalArgumentException("Invalid status. Status can only be OPEN, TEMPORARILY_CLOSED, or CLOSED");
        }
        Stadium existingStadium = stadiumRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Stadium not found with id: " + id));

        Sport sport = sportRepository.findById(stadiumRequestDto.getSportId())
                .orElseThrow(()-> new AlreadyExistException("Sport not found with id: " + stadiumRequestDto.getSportId()));

        stadiumMapper.updateEntityFromDto(stadiumRequestDto,existingStadium);
        existingStadium.setSport(sport);
        Stadium updateStadium = stadiumRepository.save(existingStadium);
        return stadiumMapper.toResponse(updateStadium);
    }

    @Override
    public void delete(Long id) {
        log.info("-> delete operation started");
        Stadium stadium = stadiumRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Stadium not found"));
        if (stadium.getStatus() != StadiumStatus.CLOSED){
            stadium.setStatus(StadiumStatus.CLOSED);
            stadiumRepository.save(stadium);
            log.info("-> successfully deleted stadium with id = {}", id);
        }else {
            log.error("->stadium with id = {} is already deleted", id);
            throw new IllegalArgumentException("stadium is already deleted");
        }
    }

    @Override
    public void changeStatus(Long id){
        log.info("-> change status started");
        Stadium stadium = stadiumRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Stadium not found"));
        if (stadium.getStatus() == StadiumStatus.OPEN){
            stadium.setStatus(StadiumStatus.TEMPORARILY_CLOSED);
        } else if (stadium.getStatus()==StadiumStatus.TEMPORARILY_CLOSED) {
            stadium.setStatus(StadiumStatus.OPEN);
        }else {
            log.error("-> the status of a deleted stadium cannot be changed");
            throw new IllegalArgumentException("Stadium is already deleted");
        }
        stadiumRepository.save(stadium);
    }

    private boolean isValidStatus(StadiumStatus status) {
        return status == StadiumStatus.OPEN || status == StadiumStatus.TEMPORARILY_CLOSED;
    }
}
