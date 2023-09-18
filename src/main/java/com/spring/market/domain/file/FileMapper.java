package com.spring.market.domain.file;

import com.spring.market.domain.file.dto.SaveFileDto;
import com.spring.market.domain.file.dto.UpdateFileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    void save(SaveFileDto saveFileDto);
    void updateFile(UpdateFileDto updateFileDto);

    File findByUserId(int userId);

    File findUserImageByUserId(int userId);

    File findThumbnailByProductId(int userId);
}
