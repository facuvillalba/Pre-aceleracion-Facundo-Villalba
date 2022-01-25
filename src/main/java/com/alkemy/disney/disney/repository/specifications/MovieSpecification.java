package com.alkemy.disney.disney.repository.specifications;

import com.alkemy.disney.disney.dto.filters.MovieFiltersDTO;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieSpecification {

    //Specification for search of combined filters
    public Specification<MovieEntity> getByFilters(MovieFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            //For  String
            if (StringUtils.hasLength(filtersDTO.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filtersDTO.getTitle().toLowerCase() + "%"
                        )
                );
            }

            //For  number
            if (filtersDTO.getGenreId() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("genreId").as(String.class),
                                "%" + filtersDTO.getGenreId() + "%"
                        )
                );
            }

            // Remove duplicates
            query.distinct(true);

            // Order resolver
            String orderByFied = "creationDate";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByFied)) :
                            criteriaBuilder.desc(root.get(orderByFied))
            );

            return criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }
}
