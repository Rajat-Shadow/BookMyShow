package com.bms.BookMyShow.service;

import com.bms.BookMyShow.dto.TheatreDto;
import com.bms.BookMyShow.exception.ResourceNotFoundException;
import com.bms.BookMyShow.models.Theatre;
import com.bms.BookMyShow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreService {

    @Autowired
    public TheatreRepository theatreRepository;

    public TheatreDto createTheatre(TheatreDto theatreDto) {
        Theatre theatre = mapToEntity(theatreDto);
        Theatre savedTheatre = theatreRepository.save(theatre);
        return mapToDto(savedTheatre);
    }

    public TheatreDto getTheaterById(Long id) {
        Theatre theater = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + id));
        return mapToDto(theater);
    }

    public List<TheatreDto> getAllTheaters() {
        List<Theatre> theaters = theatreRepository.findAll();
        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TheatreDto> getAllTheaterByCity(String city) {
        List<Theatre> theaters = theatreRepository.findByCity(city);
        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TheatreDto updateTheatre(Long id, TheatreDto theatreDto) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id : " + id));
        theatre.setName(theatreDto.getName());
        theatre.setAddress(theatreDto.getAddress());
        theatre.setCity(theatreDto.getCity());
        theatre.setTotalScreens(theatreDto.getTotalScreens());

        Theatre updatedTheatre = theatreRepository.save(theatre);
        return mapToDto(updatedTheatre);
    }

    public void deleteTheatre(Long id) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id : " + id));
        theatreRepository.delete(theatre);
    }

    private TheatreDto mapToDto(Theatre theatre) {
        TheatreDto theatreDto = new TheatreDto();
        theatreDto.setId(theatre.getId());
        theatreDto.setName(theatre.getName());
        theatreDto.setCity(theatre.getCity());
        theatreDto.setAddress(theatre.getAddress());
        theatreDto.setTotalScreens(theatre.getTotalScreens());
        return theatreDto;
    }

    private Theatre mapToEntity(TheatreDto theatreDto) {
        Theatre theatre = new Theatre();
        theatre.setName(theatreDto.getName());
        theatre.setAddress(theatreDto.getAddress());
        theatre.setCity(theatreDto.getCity());         // fixed: was missing
        theatre.setTotalScreens(theatreDto.getTotalScreens());
        return theatre;
    }

}