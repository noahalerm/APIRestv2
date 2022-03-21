package com.example.apirestv2.model.services;

import com.example.apirestv2.model.entities.Series;
import com.example.apirestv2.model.repositories.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {
    //ATTRIBUTES
    private final SeriesRepository seriesRepository;

    //METHODS
    /**
     * This method is used to list all the series.
     * @return Series List
     */
    public List<Series> listSeries() {
        return seriesRepository.findAll();
    }

    /**
     * This method is used to get a series given its ID.
     * @param id ID
     * @return Series
     */
    public Series consultById(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    /**
     * This method is used to delete a series given its ID.
     * @param id ID
     * @return Series
     */
    public Series deleteSeries(Long id) {
        Series res = seriesRepository.findById(id).orElse(null);

        if (res != null)
            seriesRepository.deleteById(id);

        return res;
    }

    /**
     * This method is used to add a new series to the database.
     * @param series New Series
     * @return New Series
     */
    public Series addSeries(Series series) {
        return seriesRepository.save(series);
    }

    /**
     * This method is used to modify an existing series.
     * @param series Series
     * @return Modified Series
     */
    public Series modifySeries(Series series) {
        Series res = null;

        if (seriesRepository.existsById(series.getId()))
            res = seriesRepository.save(series);

        return res;
    }
}
