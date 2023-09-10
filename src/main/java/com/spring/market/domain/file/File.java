package com.spring.market.domain.file;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class File {
    private int id;
    private int userId;
    private String originalName;
    private String name;
    private String path;
    private String type;
    private String createAt;
}
