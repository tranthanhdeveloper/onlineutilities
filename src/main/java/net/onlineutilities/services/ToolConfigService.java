package net.onlineutilities.services;


import net.onlineutilities.entity.ToolsEntity;

import java.util.List;

public interface ToolConfigService {
    ToolsEntity getSEODataByToolName(String name);

    List<ToolsEntity> getRandomTools();
}
