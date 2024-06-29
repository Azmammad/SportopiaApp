package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Sport;
import com.matrix.sportopia.models.dto.request.SportRequestDto;
import com.matrix.sportopia.models.dto.response.SportResponseDto;
import com.matrix.sportopia.exceptions.handle.AlreadyExistException;
import com.matrix.sportopia.exceptions.handle.EntityNotFoundException;
import com.matrix.sportopia.exceptions.handle.UpdateFailedException;
import com.matrix.sportopia.mapper.SportMapper;
import com.matrix.sportopia.repositories.SportRepository;
import com.matrix.sportopia.services.SportService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
public class SportServiceImpl implements SportService {
    private final SportRepository sportRepository;
    private final SportMapper sportMapper;

    @Override
    public SportResponseDto getById(Long id) {
        log.info("-> sport-service get by id operation with id = {}", id);
        SportResponseDto sportResponse = sportRepository.findById(id).map(sportMapper::toResponse).
                orElseThrow(() -> new EntityNotFoundException("Sport not found"));
        if (sportResponse.getStatus() == -1) {
            throw new EntityNotFoundException("Sports is deleted");
        }
        log.info("-> Successful! sport-service get by id operation with id = {}", id);
        return sportResponse;
    }

    @Override
    public List<SportResponseDto> getAll() {
        log.info("-> started getAll method for sports");
        List<Sport> sportList = sportRepository.findAllActiveSports();
        if (sportList.isEmpty()) {
            throw new EntityNotFoundException("Sports not found");
        }
        List<SportResponseDto> responseDtoList = sportList.stream().map(sportMapper::toResponse).collect(Collectors.toList());
        log.info("Successfully!");
        return responseDtoList;
    }

    @Override
    public List<SportResponseDto> getAllNoActiveSports(SportRequestDto sportRequestDto) {
        log.info("-> started getAllNoActiveSports method for sports");
        List<Sport> sportList = sportRepository.findAllActiveSports();
        List<Sport> noActiveSports = sportList.stream().filter(sport -> (sport.getStatus()) == -1).toList();
        if (noActiveSports.isEmpty()) {
            throw new EntityNotFoundException("Non-active sports not found");
        }
        List<SportResponseDto> responseDtoList = noActiveSports.stream().map(sportMapper::toResponse).collect(Collectors.toList());
        log.info("-> successfully founded all non-active sports!");
        return responseDtoList;
    }

    @Override
    public SportResponseDto add(SportRequestDto sportRequest) {
        if (sportRequest == null) {
            log.warn("-> cannot add null sport");
            throw new IllegalArgumentException("Sport cannot be null");
        }
        if (!isValidStatus(sportRequest.getStatus())) {
            log.warn("-> invalid status for sport: " + sportRequest.getStatus());
            throw new IllegalArgumentException("Invalid status. Status can only be 1, 0, or -1");
        }
        log.info("-> started the insert operation");
        Sport addedSport;
        SportResponseDto sportResponseDto;
        try {
            Sport sport = sportMapper.toEntity(sportRequest);
            addedSport = sportRepository.save(sport);
            sportResponseDto = sportMapper.toResponse(addedSport);
            log.info("-> Successfully added");
        } catch (Exception e) {
            throw new AlreadyExistException("Failed");
        }
        return sportResponseDto;
    }

    @Override
    public List<SportResponseDto> addList(List<SportRequestDto> sportRequests) {
        if (sportRequests == null || sportRequests.isEmpty()) {
            log.error("-> cannot add nul or empty sport list");
            throw new IllegalArgumentException("Sport list cannot be null or empty");
        }
        log.info("-> started the bulk insert operation");

        List<SportResponseDto> sportResponses = new ArrayList<>();

        for (SportRequestDto sportRequestDto : sportRequests) {
            try {
                SportResponseDto sportResponseDto = add(sportRequestDto);
                sportResponses.add(sportResponseDto);
            } catch (Exception e) {
                log.error("-> Failed to add sport: " + sportRequestDto.getName(), e);
                throw new AlreadyExistException(e.getMessage());
            }
        }
        log.info("-> successfully added sports list");
        return sportResponses;
    }

    @Override
    public SportResponseDto update(Long id, SportRequestDto sportRequest) {
        if (sportRequest == null || id == null) {
            log.warn("-> cannot update sport with null values");
            throw new IllegalArgumentException("Sport and ID cannot be null");
        }
        log.info("-> started the update operation");
        SportResponseDto sportResponseDto;
        Sport updatedSport;
        try {
            Sport existingSport = sportRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Sport with id = " + id + " not found"));
            sportMapper.updateEntityFromRequest(sportRequest, existingSport);

            updatedSport = sportRepository.save(existingSport);
            sportResponseDto = sportMapper.toResponse(updatedSport);
            log.info("-> successfully updated sport: " + updatedSport);
        } catch (Exception e) {
            log.error("-> update operation failed", e);
            throw new UpdateFailedException("Failed to update user", e);
        }
        return sportResponseDto;
    }

    @Override
    public void delete(Long id) {
        log.info("-> delete operation started");
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sport not found"));

        if (sport.getStatus() != -1) {
            sport.setStatus(-1);
            sportRepository.save(sport);
            log.info("-> sport with id = {} as deleted", id);
        } else {
            log.error("->sport with id = {} is already deleted", id);
            throw new IllegalArgumentException("Sport is already deleted");
        }
    }

    @Override
    public void changeStatus(Long id) {
        log.info("-> change status started");
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sport not found"));
        if (sport.getStatus() == 0) {
            log.info("-> sport status changed is 1");
            sport.setStatus(1);
        } else if (sport.getStatus() == 1) {
            log.info("-> sport status changed id 0");
            sport.setStatus(0);
        } else {
            log.error("-> the status of a deleted sport cannot be changed");
            throw new IllegalArgumentException("Sport is already deleted");
        }
        sportRepository.save(sport);
    }

    private boolean isValidStatus(int status) {
        return status == 1 || status == 0 || status == -1;
    }
}
