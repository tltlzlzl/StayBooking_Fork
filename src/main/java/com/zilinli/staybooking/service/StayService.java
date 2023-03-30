//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of StayService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.StayNotExistException;
import com.zilinli.staybooking.model.Stay;
import com.zilinli.staybooking.model.StayImage;
import com.zilinli.staybooking.model.User;
import com.zilinli.staybooking.repository.StayRepository;

// Framework includes
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class StayService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public StayService(StayRepository stayRepository, ImageStorageService imageStorageService) {
        this.stayRepository = stayRepository;
        this.imageStorageService = imageStorageService;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public List<Stay> listByUser(String username) {
        return stayRepository.findByHost(new User.Builder().setUsername(username).build());
    }

    public Stay findByIdAndHost(Long stayId, String username) throws StayNotExistException {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay does not exist");
        }
        return stay;
    }

    @Transactional
    public void addStay(Stay stay, MultipartFile[] images) {

        // Arrays.stream(T[] array) -> Stream<T> array
        List<String> mediaLinks = Arrays.stream(images).parallel().
                                                        map(image -> imageStorageService.save(image))
                                                        .collect(Collectors.toList());
        List<StayImage> stayImages = new ArrayList<>();
        for (String mediaLink : mediaLinks) {
            stayImages.add(new StayImage(mediaLink, stay));
        }
        stay.setImages(stayImages);
        stayRepository.save(stay);
    }

    @Transactional
    public void delete(Long stayId, String username) {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay does not exist");
        }
        stayRepository.deleteById(stayId);
    }
//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    private final StayRepository stayRepository;
    private final ImageStorageService imageStorageService;
}
