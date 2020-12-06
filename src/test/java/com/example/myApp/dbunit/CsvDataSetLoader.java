package com.example.myApp.dbunit;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.springframework.core.io.Resource;

@Slf4j
public class CsvDataSetLoader extends AbstractDataSetLoader {
    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        return new CsvDataSet(resource.getFile());
    }
}