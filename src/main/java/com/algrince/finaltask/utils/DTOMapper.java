package com.algrince.finaltask.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DTOMapper {
    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <S, T> T mapClass(S sourceClass, Class<T> targetClass) {
        return modelMapper.map(sourceClass, targetClass);
    }


    public <S, T> void mapProperties (S sourceClass, T targetClass) {
        modelMapper.map(sourceClass, targetClass);
    }

}
