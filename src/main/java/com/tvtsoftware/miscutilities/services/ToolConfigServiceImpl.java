package com.tvtsoftware.miscutilities.services;

import com.tvtsoftware.miscutilities.entity.ToolsEntity;
import com.tvtsoftware.miscutilities.repository.ToolRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ToolConfigServiceImpl implements ToolConfigService {

    private ToolRepository toolRepository;

    public ToolConfigServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Override
    @Cacheable(cacheNames = "getSEODataByToolName")
    public ToolsEntity getSEODataByToolName(String name){
        List<ToolsEntity> tool = toolRepository.findByName(name);
        return tool.stream().findFirst().orElse(new ToolsEntity());
    }

    @Override
    public List<ToolsEntity> getRandomTools() {
        return toolRepository.findRandomTools();
    }
}
