package net.onlineutilities.services;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import net.onlineutilities.entity.ToolsEntity;
import net.onlineutilities.repository.ToolRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ToolConfigServiceImpl implements ToolConfigService {

    private ToolRepository toolRepository;

    public ToolConfigServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Override
    @Cacheable
    public ToolsEntity getSEODataByToolName(String name){
        Optional<ToolsEntity> tool = toolRepository.findByName(name);
        return tool.orElse(null);
    }
}
