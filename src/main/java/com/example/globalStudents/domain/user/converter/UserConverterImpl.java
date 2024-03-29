package com.example.globalStudents.domain.user.converter;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.enums.UserRole;
import com.example.globalStudents.domain.user.enums.UserStatus;
import com.example.globalStudents.domain.user.repository.CountryRepository;
import com.example.globalStudents.domain.user.repository.LanguageRepository;
import com.example.globalStudents.domain.user.repository.UniversityRepository;
import com.example.globalStudents.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor

public class UserConverterImpl implements UserConverter<UserEntity,UserRequestDTO.JoinDTO,UserResponseDTO.JoinResultDTO> {

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final UniversityRepository universityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserEntity toEntity(UserRequestDTO.JoinDTO joinDTO){

        UserStatus universityStatus = UserStatus.UNVERIFIED;
        UserRole userRole = UserRole.ROLE_TEMP;
        if(joinDTO.getVerified()) {
            universityStatus = UserStatus.VERIFIED;
            userRole = UserRole.ROLE_USER;
        }


        return UserEntity.builder()
                .hostUniversity(universityRepository.findByName(joinDTO.getHostUniversity()).get())
                .homeUniversity(universityRepository.findByName(joinDTO.getHostUniversity()).get())
                .hostCountry(countryRepository.findByName(joinDTO.getHostCountry()).get())
                .nationality(countryRepository.findByName(joinDTO.getNationality()).get())
                .language(languageRepository.findByName("English").get())
                .uid(generateUID())
                .userId(joinDTO.getUserId())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .firstName(joinDTO.getFirstName())
                .lastName(joinDTO.getLastName())
                .birth(convertBirth(joinDTO.getBirthYear(),joinDTO.getBirthMonth(),joinDTO.getBirthDate()))
                .nickname(joinDTO.getNickname())
                .status(UserStatus.REGISTERED)
                .privacy(UserStatus.PRIVATE)
                .role(userRole)
                .createdAt(LocalDateTime.now())
                .email(joinDTO.getEmail())
                .verification(universityStatus)
                .build();

    }

    public UserResponseDTO.JoinResultDTO toResponse(UserEntity userEntity){
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(userEntity.getUserId())
                .nickname(userEntity.getNickname())
                .nationality(userEntity.getNationality().getName())
                .build();
    }

    public String generateUID(){
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return "U_"+simpleDateFormat.format(nowDate)+"-"+(userRepository.count()+1);
    }

    public LocalDate convertBirth(String year, String month, String day){
        return LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
    }

}
