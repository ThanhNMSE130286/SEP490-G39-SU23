package com.teachsync.dtos.course;

import com.teachsync.dtos.priceLog.PriceLogReadDTO;
import com.teachsync.entities.Classroom;
import com.teachsync.entities.Material;
import com.teachsync.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseReadDTO implements Serializable {
    private Long id;

    private String courseName;

    private String courseDesc;

    private Status status;

    private List<Classroom> classroomList;

    private List<Material> materialList;

//    private List<PriceLog> priceLogList;
    private PriceLogReadDTO currentPrice;
}

