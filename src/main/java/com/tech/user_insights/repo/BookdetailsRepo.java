package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.BookDetails;

@Repository
public interface BookdetailsRepo extends JpaRepository<BookDetails, Integer> {

//	BookDetails findByBookDetails(BookDetailsDto bookDetailsDto);

}
