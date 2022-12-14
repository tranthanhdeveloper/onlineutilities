package com.tvtsoftware.miscutilities.services;


import com.tvtsoftware.miscutilities.entity.ToolsEntity;

import java.util.List;

public interface ToolConfigService {
    ToolsEntity getSEODataByToolName(String name);

    List<ToolsEntity> getRandomTools();
}
