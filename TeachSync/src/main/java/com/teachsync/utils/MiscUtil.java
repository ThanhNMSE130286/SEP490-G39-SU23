package com.teachsync.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MiscUtil {
    public Pageable makePaging(int pageNo, int pageSize, String sortBy, boolean isAsc) {
        if (isAsc) {
            return PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            return PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }
    }

    public Pageable defaultPaging() {
        return makePaging(0, 10, "id", true);
    }

    public List<String> sortSearchableField (Field[] fields) {
        List<String> searchableFieldList = new ArrayList<>();
        String classType;

        for (Field field : fields) {
            classType = field.getType().getSimpleName();
            if (classType.equals("String") || classType.equals("Long")) {
                searchableFieldList.add(field.getName());
            }
        }

        return searchableFieldList;
    }
}
